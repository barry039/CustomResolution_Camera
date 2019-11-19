package com.doubleservice.customresolution_camera;


import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner resolution_spinner;
    private Spinner video_resolution_spinner;
    private Button camera_btn;
    private ResolutionAdpater resolutionAdpater;
    private VideoRecorderAdapter videoRecorderAdapter;
    private CameraViewModel viewModel;
    private Button gallery_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();

        init();

    }

    private void init() {
        viewModel = ViewModelProviders.of(this).get(CameraViewModel.class);
        getResolution();
        resolutionAdpater = new ResolutionAdpater(this,resolutions);
        resolution_spinner.setAdapter(resolutionAdpater);
        videoRecorderAdapter = new VideoRecorderAdapter(this,video_resolutions);
        video_resolution_spinner.setAdapter(videoRecorderAdapter);
    }
    Camera camera;
    private List<CustomSize> resolutions = new ArrayList<>();
    private List<CustomSize> video_resolutions= new ArrayList<>();
    private void getResolution() {
        releaseCameraAndPreview();
        camera = Camera.open(0);    // For Back Camera
        android.hardware.Camera.Parameters params = camera.getParameters();
        List<Camera.Size> sizes = params.getSupportedPictureSizes();
        Camera.Size result;

        ArrayList<Integer> arrayListForWidth = new ArrayList<Integer>();
        ArrayList<Integer> arrayListForHeight = new ArrayList<Integer>();

        for (int i = 0; i < sizes.size(); i++) {
            result = sizes.get(i);
            resolutions.add(new CustomSize(result.width, result.height));
        }
        camera.release();
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = manager.getCameraIdList()[0];

            // Choose the sizes for camera preview and video recording
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics
                    .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (map == null) {
                throw new RuntimeException("Cannot get available preview/video sizes");
            }
            Size[] choices = map.getOutputSizes(MediaRecorder.class);
            for(Size size : choices)
            {
                video_resolutions.add(new CustomSize(size.getWidth(),size.getHeight()));
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void releaseCameraAndPreview() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    private void setupView()
    {
        resolution_spinner = findViewById(R.id.main_resolution_spinner);
        video_resolution_spinner = findViewById(R.id.main_video_spinner);
        camera_btn = findViewById(R.id.main_camera_btn);
        gallery_btn = findViewById(R.id.main_gallery_btn);
        gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);

                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath());

                intent.setDataAndType(Uri.parse(dir.getAbsolutePath()), "*/*");
                startActivity(intent);
            }
        });
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MainActivity.this,CameraActivity.class);
                cameraIntent.putExtra("resolution_index",resolution_spinner.getSelectedItemPosition());
                cameraIntent.putExtra("video_resolution_index",videoRecorderAdapter.getIndex(videoRecorderAdapter.getItem(video_resolution_spinner.getSelectedItemPosition())));
                startActivity(cameraIntent);
            }
        });
    }

    private  static final String[] PROJECTION = {MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.DATA};

    private static long startTemp = Long.MAX_VALUE;
    private static int state = 0;

    @Override
    protected void onStart() {
        super.onStart();
    }


}
