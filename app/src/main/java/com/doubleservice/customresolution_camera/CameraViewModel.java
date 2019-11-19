package com.doubleservice.customresolution_camera;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.hardware.Camera;
import android.support.annotation.NonNull;


public class CameraViewModel extends AndroidViewModel {

    private int camera_resolution = 0;

    private int video_resolution_index = 0;

    private MutableLiveData<Integer> num_page = new MutableLiveData<>();

    public CameraViewModel(@NonNull Application application) {
        super(application);
    }

    public int getCamera_resolution() {
        return camera_resolution;
    }

    public void setCamera_resolution(int camera_resolution) {
        this.camera_resolution = camera_resolution;
    }

    public int getVideo_resolution_index() {
        return video_resolution_index;
    }

    public void setVideo_resolution_index(int video_resolution_index) {
        this.video_resolution_index = video_resolution_index;
    }

    public MutableLiveData<Integer> getNum_page() {
        return num_page;
    }

    public void switchPage(int i)
    {
        num_page.postValue(i);
    }
}
