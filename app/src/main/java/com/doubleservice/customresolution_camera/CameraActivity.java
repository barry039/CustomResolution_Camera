package com.doubleservice.customresolution_camera;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public class CameraActivity extends AppCompatActivity {
    private CameraViewModel viewModel;
    private int resolution_index = 0;
    private int video_resolution_index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        viewModel = ViewModelProviders.of(this).get(CameraViewModel.class);
        resolution_index = getIntent().getIntExtra("resolution_index",0);
        video_resolution_index = getIntent().getIntExtra("video_resolution_index",0);

        viewModel.setVideo_resolution_index(video_resolution_index);
        viewModel.setCamera_resolution(resolution_index);

        viewModel.getNum_page().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if(integer == 0)
                {
                    switchCameraPage();
                }else
                {
                    switchVideoPage();
                }
            }
        });

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
    }

    private void switchCameraPage()
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, Camera2BasicFragment.newInstance())
                .commit();
    }

    private void switchVideoPage()
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, Camera2VideoFragment.newInstance())
                .commit();
    }


}

