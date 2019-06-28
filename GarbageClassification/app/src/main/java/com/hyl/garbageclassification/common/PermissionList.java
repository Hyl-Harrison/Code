package com.hyl.garbageclassification.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yunlai.hao
 * @date 2019/6/21
 */
public class PermissionList {

    public static final String READ_EXTERNAL_STORAGE_PERMISSION = "android.permission.READ_EXTERNAL_STORAGE";

    public static List<String> getPermissionList() {
        List<String> permissionList = new ArrayList<>();
        permissionList.add(READ_EXTERNAL_STORAGE_PERMISSION);
        return permissionList;
    }
}
