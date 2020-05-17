package com.zp.api.fileupload.enums;

public enum UploadEnum {
    UPLOAD_10(10, "已完成"),
    UPLOAD_20(20, "转码中"),
    UPLOAD_30(30, "已转码");
    int code;
    String Name;

    UploadEnum(int code, String name) {
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
