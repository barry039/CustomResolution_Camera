package com.doubleservice.customresolution_camera;

import android.content.Context;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class VideoRecorderAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;



    private List<CustomSize> resolutions;

    private List<CustomSize> videoResolutions = new ArrayList<>();

    public VideoRecorderAdapter(Context context, List<CustomSize> resolutions) {
        layoutInflater = LayoutInflater.from(context);
        this.resolutions = resolutions;

        for(CustomSize size : resolutions)
        {
            if (size.getWidth() == size.getHeight() * 4 / 3 && size.getWidth() <= 2160)
            {
                videoResolutions.add(size);
            }
        }
    }

    @Override
    public int getCount() {
        return videoResolutions.size();
    }

    @Override
    public CustomSize getItem(int position) {
        return videoResolutions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getIndex(CustomSize customSize)
    {
        return resolutions.indexOf(customSize);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.layout_spinner_textview,parent,false);
        TextView item = view.findViewById(R.id.layout_spinner_resolution_text);
        item.setText(videoResolutions.get(position).getWidth() + ":" + videoResolutions.get(position).getHeight());
        return view;
    }
}
