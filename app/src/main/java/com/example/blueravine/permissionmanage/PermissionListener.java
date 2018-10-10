package com.example.blueravine.permissionmanage;

import java.io.Serializable;

public interface PermissionListener extends Serializable {
    void permissionResult(boolean hasPermission);
}