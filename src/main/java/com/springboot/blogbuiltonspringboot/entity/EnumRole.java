package com.springboot.blogbuiltonspringboot.entity;

public enum EnumRole {
    ROLE_USER,
    ROLE_ADMIN;

    private String appRoles;

    EnumRole(String appRoles) {
        this.appRoles = appRoles;
    }

    EnumRole() {
    }

    public String getAppRoles() {
        return appRoles;
    }
}
