package com.zp.api.log.enums;

public enum LogEnum {
    /**
     * 日志状态
     */
    LOGSTATUS_10(10, "成功"),
    LOGSTATUS_20(20, "失败"),;


    int code;
    String Name;

    LogEnum(int code, String name) {
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
