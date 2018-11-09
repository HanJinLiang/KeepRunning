package com.hanjinliang.keeprunning.entity;



public class ResultEntity<T> {

    /**
     * 请求返回标识
     */
    private int code;



    /**
     * 错误信息
     */
    private String message;


    /**
     * 数据
     */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
