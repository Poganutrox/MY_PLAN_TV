package com.miguelangel.mytvplanfx.model;

import java.io.Serializable;

public class BaseResponse implements Serializable{
    public boolean ok;
    public String error;
    public boolean getOk() {
        return ok;
    }
    public String getError() {return error; }
    public BaseResponse(boolean ok, String error) {
        this.ok = ok;
        this.error = error;
    }
}
