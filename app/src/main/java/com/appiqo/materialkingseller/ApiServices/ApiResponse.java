package com.appiqo.materialkingseller.ApiServices;

import java.io.Serializable;

/**
 * Created by Warlock on 12/14/2017.
 */

public class ApiResponse implements Serializable {


    private int status;
    private String message;
    private String id;

    public int getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
