package com.example.d024mapboxevent;

import java.util.List;

public interface PermissionsListener {
    void onExplanationNeeded(List<String> permissionsToExplain);

    void onPermissionResult(boolean granted);
}
