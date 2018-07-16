package com.appentus.materialkingseller.pojo;

import java.util.List;

/**
 * Created by sunil on 20-03-2018.
 */

public class ResponseListPOJO<T> {

    boolean success;
    String message;
    List<T> resultList;


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

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
