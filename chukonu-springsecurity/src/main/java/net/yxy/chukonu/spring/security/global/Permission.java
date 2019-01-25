package net.yxy.chukonu.spring.security.global;

public abstract class Permission {
    public static final String PERMISSION_ADMIN = "hasRole('admin')";
    public static final String PERMISSION_EDIT = "hasRole('edit')";
    public static final String PERMISSION_VIEW = "hasRole('view')";
}
