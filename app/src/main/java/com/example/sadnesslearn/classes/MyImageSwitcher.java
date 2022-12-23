package com.example.sadnesslearn.classes;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageSwitcher;

import com.bumptech.glide.Glide;

import pl.droidsonroids.gif.GifImageView;

public class MyImageSwitcher extends ImageSwitcher {

    public MyImageSwitcher(Context context) {
        super(context);
    }

    public MyImageSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImageUrl(String url, Context context) {
        GifImageView image = (GifImageView) this.getNextView();

        Glide.with(context).load(url).into(image);

        showNext();
    }
}
