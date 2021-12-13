package com.example.photomanagerdemo;

import java.util.List;

/**
 * Created by Administrator on 2017/2/19.
 */

public interface PermissionsListener {

  void onDenied(List<String> deniedPermissions, List<String> grantedPermissions);

  void onGranted();
}