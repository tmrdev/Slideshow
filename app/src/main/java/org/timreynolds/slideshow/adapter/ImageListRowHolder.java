package org.timreynolds.slideshow.adapter;

import org.timreynolds.slideshow.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Tim Reynolds on 9/26/15.
 * ImageListRowHolder is part of adapter package so that protected ImageView can be honored by MyRecyclerAdapter
 */

public class ImageListRowHolder extends RecyclerView.ViewHolder {
    protected ImageView thumbnail;//ImageView thumbnail;

    public ImageListRowHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        //this.title = (TextView) view.findViewById(R.id.title);
    }

}