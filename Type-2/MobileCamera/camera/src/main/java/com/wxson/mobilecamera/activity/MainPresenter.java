package com.wxson.mobilecamera.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaCodec;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.webkit.MimeTypeMap;

import com.wxson.mobilecamera.R;
import com.wxson.mobilecamera.connect.CameraWifiServerService;
import com.wxson.mobilecamera.mediacodec.MediaCodecCallback;
import com.wxson.mobilecomm.codec.H264VgaFormat;
import com.wxson.mobilecomm.connect.ByteBufferTransfer;
import com.wxson.mobilecomm.connect.ByteBufferTransferTask;
import com.wxson.mobilecomm.connect.DirectBroadcastReceiver;
import com.wxson.mobilecomm.connect.FileTransfer;
import com.wxson.mobilecomm.connect.IWifiP2pConnectStatusListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.os.Looper.getMainLooper;
import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by wxson on 2018/8/15.
 * Package com.wxson.mobilecamera.activity.
 */
public class MainPresenter implements IMainContract.Presenter {

    private static final String TAG = "MainPresenter";
    private final IMainContract.View mMainView;
    private Context mContext;
    private boolean mWifiP2pConnected;
    private static IWifiP2pConnectStatusListener mWifiP2pConnectStatusListener;
    private CameraWifiServerService mCameraWifiServerService;
    private BroadcastReceiver mBroadcastReceiver;
    private WifiP2pManager mWifiP2pManager;
    private WifiP2pManager.Channel mChannel;
    //for camera
    private int mCameraWidth;
    private int mCameraHigh;
    private CameraDevice mCameraDevice;
    private ImageReader imageReader;
    private CaptureRequest.Builder mPreviewRequestBuilder;
    private CameraCaptureSession mCaptureSession;
    private CaptureRequest mPreviewRequest;

    // ?????????ID?????????0????????????????????????1????????????????????????
    private String mCameraId = "0";
    // ????????????
    private Size mPreviewSize;

    //MediaCodec
    //????????????
    private String mime = "video/avc";      //H264
    //    private String mime = MediaFormat.MIMETYPE_VIDEO_HEVC;      //H265
    //????????????
    private MediaCodec mMediaCodec;
    //ByteBufferTransfer
    private ByteBufferTransfer mByteBufferTransfer;


    /**
     * @param mainView
     *     ??????mMainView
     *     ???mMainView??????Presenter
     */
    @SuppressLint("RestrictedApi")
    MainPresenter(@NonNull IMainContract.View mainView, Context context) {
        this.mMainView = checkNotNull(mainView, "MainView cannot be null!");
        mMainView.setPresenter(this);
        mContext = context;
    }

    //region override method
    @Override
    public void wifiP2pEnabled(boolean enabled) {
        Log.i(TAG, "wifiP2pEnabled: " + enabled);
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
        Log.i(TAG, "onConnectionInfoAvailable");
        Log.i(TAG, "isGroupOwner???" + wifiP2pInfo.isGroupOwner);
        Log.i(TAG, "groupFormed???" + wifiP2pInfo.groupFormed);
        if (wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner) {
            startWifiServerService();
        }
    }

    @Override
    public void onDisconnection() {
        Log.i(TAG, "onDisconnection");
        mWifiP2pConnected = false;
        mMainView.showConnectStatus(mWifiP2pConnected);
        setWifiP2pConnectStatus();
    }

    @Override
    public void onSelfDeviceAvailable(WifiP2pDevice wifiP2pDevice) {
        Log.i(TAG, "onSelfDeviceAvailable");
    }

    @Override
    public void onPeersAvailable(Collection<WifiP2pDevice> wifiP2pDeviceList) {
        Log.i(TAG, "onPeersAvailable");
    }

    @Override
    public void onP2pDiscoveryStopped() {
        Log.i(TAG, "onP2pDiscoveryStopped");
    }

    @Override
    public void onChannelDisconnected() {
        Log.i(TAG, "onChannelDisconnected");
    }

    @Override
    public void start() {
        mWifiP2pManager = (WifiP2pManager) mMainView.getActivity().getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mWifiP2pManager.initialize(mMainView.getActivity(), getMainLooper(), this);
        mBroadcastReceiver = new DirectBroadcastReceiver(mWifiP2pManager, mChannel, this);
//        mMainView.getActivity().registerReceiver(mBroadcastReceiver, DirectBroadcastReceiver.getIntentFilter());
        bindService();
        mByteBufferTransfer = new ByteBufferTransfer();
        mWifiP2pConnected = false;
    }

    @Override
    public void setWifiP2pConnectStatusListener(IWifiP2pConnectStatusListener wifiP2pConnectStatusListener) {
        mWifiP2pConnectStatusListener = wifiP2pConnectStatusListener;
    }

    @Override
    public void unbindServiceConnection() {
        Log.i(TAG, "unbindServiceConnection");
        if (mCameraWifiServerService != null){
            mCameraWifiServerService.setProgressChangListener(null);
            mMainView.getActivity().unbindService(mServiceConnection);
        }
    }

    @Override
    public void removeGroup() {
        mWifiP2pManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                mMainView.showConnectStatus(false);
                mWifiP2pConnected = false;
                setWifiP2pConnectStatus();
                Log.i(TAG, "removeGroup onSuccess");
                mMainView.showToast("onSuccess");
            }

            @Override
            public void onFailure(int reason) {
                Log.i(TAG, "removeGroup onFailure");
                mMainView.showToast("onFailure");
            }
        });
    }

    @Override
    public void createGroup() {
        mMainView.showLoadingDialog("??????????????????");
        mWifiP2pManager.createGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "createGroup onSuccess");
                mMainView.dismissLoadingDialog();
                mMainView.showToast("onSuccess");
            }

            @Override
            public void onFailure(int reason) {
                Log.i(TAG, "createGroup onFailure: " + reason);
                mMainView.dismissLoadingDialog();
                mMainView.showToast("onFailure");
            }
        });
    }

    @Override
    public void openCamera() {
        Log.i(TAG, "openCamera");
        setUpCameraOutputs(mCameraWidth, mCameraHigh);
        CameraManager manager = (CameraManager) mMainView.getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            // ???????????????
            manager.openCamera(mCameraId, mStateCallback, null); // ???
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void closeCamera() {
        Log.i(TAG, "closeCamera");
        if (mCameraDevice != null){
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }

    @Override
    public void unregisterBroadcastReceiver() {
        mMainView.getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public TextureView.SurfaceTextureListener getSurfaceTextureListener() {
        return mSurfaceTextureListener;
    }

    @Override
    public void captureStillPicture() {
        Log.i(TAG, "captureStillPicture");
        try {
            if (mCameraDevice == null) {
                return;
            }
            // ?????????????????????CaptureRequest.Builder
            final CaptureRequest.Builder captureRequestBuilder =
                    mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            // ???imageReader???surface??????CaptureRequest.Builder?????????
            captureRequestBuilder.addTarget(imageReader.getSurface());
            // ????????????????????????
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // ????????????????????????
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                    CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            // ??????????????????
            int rotation = mMainView.getActivity().getWindowManager().getDefaultDisplay().getRotation();
            // ?????????????????????????????????????????????
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION
                    , ORIENTATIONS.get(rotation));
            // ??????????????????
            mCaptureSession.stopRepeating();
            // ??????????????????
            mCaptureSession.capture(captureRequestBuilder.build()
                    , new CameraCaptureSession.CaptureCallback()  // ???
                    {
                        // ??????????????????????????????
                        @Override
                        public void onCaptureCompleted(CameraCaptureSession session
                                , CaptureRequest request, TotalCaptureResult result)
                        {
                            try {
                                // ????????????????????????
                                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER,
                                        CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
                                // ????????????????????????
                                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                                        CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                                // ????????????????????????
                                mCaptureSession.setRepeatingRequest(mPreviewRequest, null,
                                        null);
                            }
                            catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }, null);
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    //endregion

    //region Internal class
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected");
            CameraWifiServerService.MyBinder binder = (CameraWifiServerService.MyBinder) service;
            mCameraWifiServerService = binder.getService();
            mCameraWifiServerService.setProgressChangListener(mProgressChangListener);
            mCameraWifiServerService.setStringTransferListener(mStringTransferListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
            mCameraWifiServerService = null;
            bindService();
        }
    };

    private CameraWifiServerService.OnProgressChangListener mProgressChangListener = new CameraWifiServerService.OnProgressChangListener() {
        @Override
        public void onProgressChanged(final FileTransfer fileTransfer, final int progress) {
            mMainView.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMainView.ProgressDialog_setMessage("???????????? " + new File(fileTransfer.getFilePath()).getName());
                    mMainView.ProgressDialog_setProgress(progress);
                    mMainView.ProgressDialog_show();
                }
            });
        }

        @Override
        public void onTransferFinished(final File file) {
            mMainView.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMainView.ProgressDialog_cancel();
                    if (file != null && file.exists()) {
                        openFile(file.getPath());
                    }
                }
            });
        }
    };

    private CameraWifiServerService.StringTransferListener mStringTransferListener = new CameraWifiServerService.StringTransferListener() {
        @Override
        public void onStringArrived(final String arrivedString, final InetAddress clientInetAddress) {
            mMainView.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //????????????????????????ByteBufferTransferTask
                    ByteBufferTransferTask.setInetAddress(clientInetAddress);
                    Log.i(TAG, "onStringArrived. clientInetAddress=" + clientInetAddress.getHostAddress());
                    switch (arrivedString){
                        case "connected":
                            mWifiP2pConnected = true;
                            mMainView.showConnectStatus(true);
                            setWifiP2pConnectStatus();
                            break;
                        case "disconnected":
                            mWifiP2pConnected = false;
                            mMainView.showConnectStatus(false);
                            setWifiP2pConnectStatus();
                            break;
                        case "btnCapture":
                            //??????
                             captureStillPicture();
                            break;
                    }
                }
            });
        }
    };

    private final CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback()
    {
        //  ????????????????????????????????????
        @Override
        public void onOpened(CameraDevice cameraDevice) {
            Log.i(TAG, "onOpened");
            mCameraDevice = cameraDevice;
            //WifiP2pConnectStatus???????????????????????????
            setWifiP2pConnectStatus();
            // ????????????
            createCameraPreviewSession();
        }
        // ???????????????????????????????????????
        @Override
        public void onDisconnected(CameraDevice cameraDevice) {
            Log.i(TAG, "onDisconnected");
            closeCamera();
        }
        // ?????????????????????????????????????????????
        @Override
        public void onError(CameraDevice cameraDevice, int error) {
            Log.i(TAG, "onError");
            closeCamera();
            mMainView.getActivity().finish();
        }
    };

    private final TextureView.SurfaceTextureListener mSurfaceTextureListener
            = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            Log.i(TAG, "onSurfaceTextureAvailable");
            mCameraWidth = width;
            mCameraHigh = height;
            // ???TextureView??????????????????????????????
            mMainView.requestCameraPermission();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture
                , int width, int height){
            Log.i(TAG, "onSurfaceTextureSizeChanged");
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            Log.i(TAG, "onSurfaceTextureDestroyed");
            closeCamera();
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture){
            Log.i(TAG, "onSurfaceTextureUpdated");
        }
    };

//endregion

    //region private method
    private void startWifiServerService() {
        if (mCameraWifiServerService != null) {
            mMainView.getActivity().startService(new Intent(mMainView.getActivity(), CameraWifiServerService.class));
        }
    }

    private void bindService() {
        Intent intent = new Intent(mMainView.getActivity(), CameraWifiServerService.class);
        mMainView.getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void openFile(String filePath) {
        String ext = filePath.substring(filePath.lastIndexOf('.')).toLowerCase(Locale.US);
        try {
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String mime = mimeTypeMap.getMimeTypeFromExtension(ext.substring(1));
            mime = TextUtils.isEmpty(mime) ? "" : mime;
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                //android 7.0 ????????????
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(mMainView.getActivity(), "com.wxson.mobilecamera.fileprovider", new File(filePath));
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(contentUri, mime);
            }
            else{
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(filePath)), mime);
            }
            mMainView.getActivity().startActivity(intent);
        }
        catch (Exception e) {
            Log.i(TAG, "?????????????????????" + e.getMessage());
            mMainView.showToast("?????????????????????" + e.getMessage());
        }
    }

    //????????????
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private void setWifiP2pConnectStatus(){
        if (mWifiP2pConnectStatusListener != null){
            mWifiP2pConnectStatusListener.onWifiP2pConnectStatusChanged(mWifiP2pConnected);
        }
    }

    private void setUpCameraOutputs(int width, int height)
    {
        Log.i(TAG, "setUpCameraOutputs");
        CameraManager manager = (CameraManager)mMainView.getActivity().getSystemService(Context.CAMERA_SERVICE);
        try
        {
            // ??????????????????????????????
            CameraCharacteristics characteristics
                    = manager.getCameraCharacteristics(mCameraId);
            // ????????????????????????????????????
            StreamConfigurationMap map = characteristics.get(
                    CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

            // ????????????????????????????????????
            Size largest = Collections.max(
                    Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),
                    new CompareSizesByArea());
            // ????????????ImageReader?????????????????????????????????????????????
            imageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(),
                    ImageFormat.JPEG, 2);
            imageReader.setOnImageAvailableListener(
                    new ImageReader.OnImageAvailableListener() {
                        // ???????????????????????????????????????
                        @Override
                        public void onImageAvailable(ImageReader reader) {
                            Log.i(TAG, "imageReader.onImageAvailable");
                            // ???????????????????????????
                            Image image = reader.acquireNextImage();
                            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                            byte[] bytes = new byte[buffer.remaining()];
                            // ??????IO??????????????????????????????
                            @SuppressLint("SimpleDateFormat") String timeStamp =
                                    (new SimpleDateFormat("yyyyMMdd_HHmmss").
                                            format(new Date(System.currentTimeMillis())));
                            File file = new File(mMainView.getActivity().
                                    getExternalFilesDir(null),
                                    "img" + timeStamp + ".jpg");
                            buffer.get(bytes);
                            try (
                                    FileOutputStream output = new FileOutputStream(file)) {
                                output.write(bytes);
                                mMainView.showToast("??????: " + file);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                image.close();
                            }
                        }
                    }, null);

            // ???????????????????????????
            mPreviewSize = chooseOptimalSize(map.getOutputSizes(
                    SurfaceTexture.class), width, height, largest);
            // ???????????????????????????????????????????????????TextureView??????????????????
            int orientation = mMainView.getActivity().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mMainView.getTextureView().setAspectRatio(
                        mPreviewSize.getWidth(), mPreviewSize.getHeight());
            }
            else {
                mMainView.getTextureView().setAspectRatio(
                        mPreviewSize.getHeight(), mPreviewSize.getWidth());
            }
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            System.out.println("???????????????");
        }
    }

    // ???Size?????????????????????Comparator
    static class CompareSizesByArea implements Comparator<Size> {
        @Override
        public int compare(Size lhs, Size rhs) {
            // ?????????long????????????????????????
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }
    }

    private static Size chooseOptimalSize(Size[] choices
            , int width, int height, Size aspectRatio) {
        // ????????????????????????????????????Surface????????????
        List<Size> bigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getHeight() == option.getWidth() * h / w &&
                    option.getWidth() >= width && option.getHeight() >= height) {
                bigEnough.add(option);
            }
        }
        // ???????????????????????????????????????????????????????????????
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        }
        else {
            System.out.println("???????????????????????????????????????");
            return choices[0];
        }
    }

    private void createCameraPreviewSession() {
        Log.i(TAG, "createCameraPreviewSession");
        try {
            SurfaceTexture texture = mMainView.getTextureView().getSurfaceTexture();
            texture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());

            // Set up Surface for the camera preview
            Surface previewSurface = new Surface(texture);

            // ?????????????????????CaptureRequest.Builder
            mPreviewRequestBuilder = mCameraDevice
                    .createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            // ???textureView???surface??????CaptureRequest.Builder?????????
            mPreviewRequestBuilder.addTarget(new Surface(texture));

            //region added by wan
            // ???????????????????????????????????????
            mMediaCodec = MediaCodec.createEncoderByType(mime);
            // Set up Callback for the Encoder
            final int PORT = mMainView.getActivity().getResources().getInteger(R.integer.portNumberB);
            MediaCodecCallback mediaCodecCallback = new MediaCodecCallback(mByteBufferTransfer, PORT, this);
            mMediaCodec.setCallback(mediaCodecCallback.getCallback());

            //set up output mediaFormat
            H264VgaFormat h264VgaFormat = new H264VgaFormat();
//            H265VgaFormat h265VgaFormat = new H265VgaFormat();
//            H264QVgaFormat qVgaFormat = new H264QVgaFormat();
            // configure mMediaCodec
            mMediaCodec.configure(h264VgaFormat.getEncodeFormat(), null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
//            mMediaCodec.configure(h265VgaFormat.getEncodeFormat(), null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
//            mMediaCodec.configure(qVgaFormat.getEncodeFormat(), null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);

            // Set up Surface for the Encoder
            Surface encoderInputSurface = MediaCodec.createPersistentInputSurface();
            mMediaCodec.setInputSurface(encoderInputSurface);
            mMediaCodec.start();
            mPreviewRequestBuilder.addTarget(encoderInputSurface);

            // ??????CameraCaptureSession??????????????????????????????????????????????????????????????????????????????
            mCameraDevice.createCaptureSession(Arrays.asList(previewSurface, encoderInputSurface
                    , imageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                            Log.i(TAG, "onConfigured");
                            // ??????????????????null?????????????????????
                            if (null == mCameraDevice) {
                                return;
                            }

                            // ???????????????????????????????????????????????????
                            mCaptureSession = cameraCaptureSession;
                            try {
                                // ????????????????????????
                                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                                        CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                                // ????????????????????????
                                mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE,
                                        CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                                // ????????????????????????
                                mPreviewRequest = mPreviewRequestBuilder.build();

                                // ???????????????????????????????????????
                                mCaptureSession.setRepeatingRequest(mPreviewRequest,
                                        null, null);  // ???
                            }
                            catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                            Log.e(TAG, "onConfigureFailed");
                            mMainView.showToast("???????????????");
                        }
                    }, null
            );
            //endregion
        }
        catch (CameraAccessException e)
        {
            e.printStackTrace();
        }
        catch (IOException ie){
            ie.printStackTrace();
        }
    }

    //endregion


}
