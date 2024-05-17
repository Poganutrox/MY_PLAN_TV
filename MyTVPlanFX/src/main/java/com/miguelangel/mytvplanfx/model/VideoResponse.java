package com.miguelangel.mytvplanfx.model;

import java.util.List;

public class VideoResponse extends BaseResponse{
    public List<Video> result;
    public List<Video> getResult() {
        return result;
    }

    public VideoResponse(boolean ok, String error, List<Video> result) {
        super(ok, error);
        this.result = result;
    }

}
