package com.miguelangel.mytvplanfx.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miguelangel.mytvplanfx.utils.ServiceUtils;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import com.miguelangel.mytvplanfx.model.VideoResponse;

import java.lang.reflect.Type;

public class GetVideos extends Service<VideoResponse> {
    private final String filter;
    public GetVideos(String filter) {
        this.filter = filter;
    }

    @Override
    protected Task<VideoResponse> createTask() {
        return new Task<>() {
            @Override
            protected VideoResponse call() {
                try {
                    String json = ServiceUtils.getResponse(ServiceUtils.SERVER + "/videos/" + filter, null, "GET");
                    Gson gson = new Gson();
                    Type type = new TypeToken<VideoResponse>() {}.getType();
                    return gson.fromJson(json, type);
                } catch (Exception e) {
                    return new VideoResponse(false, "There has been an error when obtaining the videos", null);
                }
            }
        };
    }
}
