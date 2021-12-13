package com.example.skunkworkdemo.share.views.ui.bluetooth;


import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import org.odk.share.R;
import org.odk.share.bluetooth.BluetoothUtils;
import org.odk.share.events.BluetoothEvent;
import org.odk.share.events.UploadEvent;
import org.odk.share.rx.RxEventBus;
import org.odk.share.rx.schedulers.BaseSchedulerProvider;
import org.odk.share.services.SenderService;
import org.odk.share.views.ui.common.injectable.InjectableActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static org.odk.share.utilities.ApplicationConstants.ASK_REVIEW_MODE;
import static org.odk.share.views.ui.instance.InstancesList.INSTANCE_IDS;
import static org.odk.share.views.ui.instance.fragment.ReviewedInstancesFragment.MODE;
import static org.odk.share.views.ui.send.fragment.BlankFormsFragment.FORM_IDS;


/**
 * Send activity, for testing, needs refactor.
 *
 * @author huangyz0918 (huangyz0918@gmail.com)
 */
//@RuntimePermissions
public class BtSenderActivity extends InjectableActivity {

    @BindView(R.id.test_text_view)
    TextView resultTextView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    RxEventBus rxEventBus;

    @Inject
    BaseSchedulerProvider schedulerProvider;

    @Inject
    SenderService senderService;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private CountDownTimer countDownTimer;
    private static final int CONNECT_TIMEOUT = 120;
    private static final int COUNT_DOWN_INTERVAL = 1000;
    private static final int DISCOVERABLE_CODE = 0x121;
    private static final int SUCCESS_CODE = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_send);
        ButterKnife.bind(this);

        setTitle(getString(R.string.send_instance_title));
        setSupportActionBar(toolbar);

        if (!BluetoothUtils.isBluetoothEnabled()) {
            BluetoothUtils.enableBluetooth();
        }

        long[] formIds = getIntent().getLongArrayExtra(INSTANCE_IDS);
        int mode = getIntent().getIntExtra(MODE, ASK_REVIEW_MODE);
        if (formIds == null) {
            formIds = getIntent().getLongArrayExtra(FORM_IDS);
        }

//        BtSenderActivityPermissionsDispatcher.enableDiscoveryWithPermissionCheck(this);
        enableDiscovery();
        senderService.startUploading(formIds, mode);

    }

    /**
     * Creates a subscription for listening to all hotspot events being send through the
     * application's {@link RxEventBus}
     */
    private Disposable addBluetoothEventSubscription() {
        return rxEventBus.register(BluetoothEvent.class)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.androidThread())
                .subscribe(bluetoothEvent -> {
                    switch (bluetoothEvent.getStatus()) {
                        case CONNECTED:
                            countDownTimer.cancel();
                            resultTextView.setText(getString(R.string.connecting_transfer_message));
                            break;
                        case DISCONNECTED:
                            break;
                    }
                });
    }

    private Disposable addUploadEventSubscription() {
        return rxEventBus.register(UploadEvent.class)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.androidThread())
                .subscribe(uploadEvent -> {
                    switch (uploadEvent.getStatus()) {
                        case QUEUED:
                            Toast.makeText(this, R.string.upload_queued, Toast.LENGTH_SHORT).show();
                            break;
                        case UPLOADING:
                            int progress = uploadEvent.getCurrentProgress();
                            int total = uploadEvent.getTotalSize();

                            String alertMsg = getString(R.string.sending_items, String.valueOf(progress), String.valueOf(total));
                            Toast.makeText(this, alertMsg, Toast.LENGTH_SHORT).show();
                            break;
                        case FINISHED:
                            String result = uploadEvent.getResult();
                            if (TextUtils.isEmpty(result)) {
                                resultTextView.setText(getString(R.string.tv_form_already_exist));
                            } else {
                                resultTextView.setText(getString(R.string.tv_form_send_success));
                                resultTextView.append(result);
                            }
                            Toast.makeText(this, getString(R.string.transfer_result) + " : " + result, Toast.LENGTH_SHORT).show();
                            break;
                        case ERROR:
                            Toast.makeText(this, getString(R.string.error_while_uploading, uploadEvent.getResult()), Toast.LENGTH_SHORT).show();
                            break;
                        case CANCELLED:
                            Toast.makeText(this, getString(R.string.canceled), Toast.LENGTH_LONG).show();
                            break;
                    }
                }, Timber::e);
    }

    /**
     * Enable the bluetooth discovery for other devices. The timeout is specific seconds.
     */
//    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION})
    void enableDiscovery() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        // set the discovery timeout for 120s.
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, CONNECT_TIMEOUT);
        startActivityForResult(discoverableIntent, DISCOVERABLE_CODE);
    }

    /**
     * If the permission was denied, finishing this activity.
     */
//    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION})
//    void showDeniedForLocation() {
//        Toast.makeText(this, R.string.permission_location_denied, Toast.LENGTH_LONG).show();
//        finish();
//    }

    /**
     * If clicked the "never ask", we should show a toast to guide user.
     */
//    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION})
//    void showNeverAskForLocation() {
//        PermissionUtils.showAppInfo(this, getPackageName());
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        BtSenderActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case DISCOVERABLE_CODE:
//                if (resultCode == SUCCESS_CODE) {
//                    startCheckingDiscoverableDuration();
//                } else {
//                    finish();
//                }
//                break;
//            case APP_SETTING_REQUEST_CODE:
//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    PermissionUtils.showAppInfo(this, getPackageName());
//                } else {
        enableDiscovery();
//                }
//                break;
//        }
    }

    /**
     * Checking the discoverable time, if the device is no longer discoverable, we should show
     * an {@link AlertDialog} to notice our users.
     */
    private void startCheckingDiscoverableDuration() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.timeout))
                .setMessage(getString(R.string.bluetooth_send_time_up))
                .setCancelable(false)
                .setNegativeButton(R.string.quit, (DialogInterface dialog, int which) -> {
                    finish();
                })
                .create();

        resultTextView.setText(getString(R.string.tv_sender_wait_for_connect));
        countDownTimer = new CountDownTimer(CONNECT_TIMEOUT * COUNT_DOWN_INTERVAL, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                resultTextView.setText(String.format(getString(R.string.tv_sender_wait_for_connect),
                        String.valueOf(millisUntilFinished / COUNT_DOWN_INTERVAL)));
            }

            @Override
            public void onFinish() {
                alertDialog.show();
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        compositeDisposable.add(addUploadEventSubscription());
        compositeDisposable.add(addBluetoothEventSubscription());
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }
}