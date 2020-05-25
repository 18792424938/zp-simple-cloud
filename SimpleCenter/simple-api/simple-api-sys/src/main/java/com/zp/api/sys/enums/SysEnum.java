package com.zp.api.sys.enums;

public enum SysEnum {
    /**
     * 用户状态
     */
    USERSTATUS_10(10, "启用"),
    USERSTATUS_20(20, "禁用"),
    USERSTATUS_30(30, "删除"),

    /**
     * 字典表状态
     */
    DICTSTATUS_10(10, "启用"),
    DICTSTATUS_20(20, "禁用"),
    ;
    int code;
    String Name;

    SysEnum(int code, String name) {
        this.code = code;
        Name = name;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
