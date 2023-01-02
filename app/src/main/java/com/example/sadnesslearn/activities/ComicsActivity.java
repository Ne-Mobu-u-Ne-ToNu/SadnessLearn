package com.example.sadnesslearn.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.AnimationHelper;
import com.example.sadnesslearn.classes.Comics;
import com.example.sadnesslearn.classes.Constants;
import com.example.sadnesslearn.classes.MyImageSwitcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

public class ComicsActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory, GestureDetector.OnGestureListener {
    private MyImageSwitcher is_comics;
    private ImageButton ib_back, ib_forward;
    private int position;
    private GestureDetector gestureDetector;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;
    private Map<Integer, String> imageMap;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics);

        init();
        buttonNavigation();
        getDataFromDB();
    }

    private void init(){
        Toolbar tlb_comics = findViewById(R.id.tlb_comics);
        setSupportActionBar(tlb_comics);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ib_back = findViewById(R.id.ib_comics_back);
        AnimationHelper.buttonAnimation(ib_back, this);
        ib_forward = findViewById(R.id.ib_comics_forward);
        AnimationHelper.buttonAnimation(ib_forward, this);

        position = 0;
        imageMap = new HashMap<>();
        is_comics = findViewById(R.id.is_comics);
        is_comics.setFactory(this);
        mDataBase = FirebaseDatabase.getInstance().getReference(Constants.COMICS_KEY);

        gestureDetector = new GestureDetector(this,this);
    }

    private void buttonNavigation() {
        ib_back.setOnClickListener(view -> {
            setPositionPrev();
            is_comics.setImageUrl(imageMap.get(position), this);
        });

        ib_forward.setOnClickListener(view -> {
            setPositionNext();
            is_comics.setImageUrl(imageMap.get(position), this);
        });
    }

    private void setPositionNext() {
        if (position + 1 <= imageMap.size() - 1) {
            position++;
        }
    }

    private void setPositionPrev() {
        if (position - 1 >= 0) {
            position--;
        }
    }

    private void getDataFromDB(){
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(imageMap.size() > 0) imageMap.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Comics comics =  ds.getValue(Comics.class);
                    assert comics != null;
                    imageMap.put(comics.getNumber() - 1, comics.getLink());
                }
                is_comics.setImageUrl(imageMap.get(0), ComicsActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDataBase.addValueEventListener(vListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            // справа налево
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                setPositionNext();
                is_comics.setImageUrl(imageMap.get(position), this);
            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                // слева направо
                setPositionPrev();
                is_comics.setImageUrl(imageMap.get(position), this);
            }
        } catch (Exception e) {
            return true;
        }
        return true;
    }

    @Override
    public View makeView() {
        GifImageView imageView = new GifImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new
                ImageSwitcher.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        imageView.setBackgroundColor(0xFF000000);
        return imageView;
    }
}