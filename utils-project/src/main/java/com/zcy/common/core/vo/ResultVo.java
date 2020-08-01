package com.zcy.common.core.vo;

public class ResultVo {
    private int code;
    private boolean success;
    private String message;
    private Object data;

    private ResultVo(int code, boolean success, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }

    public static ResultVo success() {
        return new ResultVo(200, true, "操作成功");
    }

    public static ResultVo error() {
        return new ResultVo(400, false, "操作失败");
    }

    public ResultVo code(int code) {
        this.code = code;
        return this;
    }

    public ResultVo msg(String message) {
        this.message = message;
        return this;
    }

    public ResultVo data(Object data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
