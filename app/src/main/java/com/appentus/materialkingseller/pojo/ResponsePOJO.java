package com.appentus.materialkingseller.pojo;

/**
 * Created by sunil on 20-03-2018.
 */

public class ResponsePOJO<T> {
    boolean success;
    String message;
    T result;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
