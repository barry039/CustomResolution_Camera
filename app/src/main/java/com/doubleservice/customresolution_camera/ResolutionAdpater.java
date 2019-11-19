package com.doubleservice.customresolution_camera;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ResolutionAdpater extends BaseAdapter {

    private LayoutInflater layoutInflater;



    private List<CustomSize> resolutions;

    public ResolutionAdpater(Context context, List<CustomSize> resolutions) {
        layoutInflater = LayoutInflater.from(context);
        this.resolutions = resolutions;
    }

    @Override
    public int getCount() {
        return resolutions.size();
    }

    @Override
    public CustomSize getItem(int position) {
        return resolutions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.layout_spinner_textview,parent,false);
        TextView item = view.findViewById(R.id.layout_spinner_resolution_text);
        item.setText(resolutions.get(position).getWidth() + ":" + resolutions.get(position).getHeight());
        return view;
    }
}
