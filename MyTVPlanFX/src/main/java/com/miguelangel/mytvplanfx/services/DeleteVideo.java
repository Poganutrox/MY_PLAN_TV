package com.miguelangel.mytvplanfx.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miguelangel.mytvplanfx.model.BaseResponse;
import com.miguelangel.mytvplanfx.model.Video;
import com.miguelangel.mytvplanfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.lang.reflect.Type;

public class DeleteVideo extends Service<BaseResponse> {
    private final Video video;
    public DeleteVideo(Video video) {
        this.video = video;
    }

    @Override
    protected Task<BaseResponse> createTask() {
        return new Task<>() {
            @Override
            protected BaseResponse call() {
                try {
                    Gson gson = new Gson();
                    String json = ServiceUtils.getResponse(ServiceUtils.SERVER + "/videos/" + video.getId(), null, "DELETE");
                    Type type = new TypeToken<BaseResponse>() {}.getType();
                    return gson.fromJson(json, type);
                } catch (Exception e) {
                    return new BaseResponse(false, "There has been an error when deleting the video");
                }
            }
        };
    }
}
