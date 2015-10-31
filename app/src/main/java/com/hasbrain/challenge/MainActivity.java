package com.hasbrain.challenge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;

public class MainActivity extends AppCompatActivity implements ViewTreeObserver.OnScrollChangedListener {

    private View frame_head, play_trailer1;
    private ScrollView mScrollView;

    private int actionBarHeight,frame_head_defheight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        frame_head_defheight  = getResources().getDimensionPixelSize(R.dimen.frame_head_height);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        KenBurnsView kbv = (KenBurnsView) findViewById(R.id.kbv_poster);
        RandomTransitionGenerator generator = new RandomTransitionGenerator(10000, new AccelerateDecelerateInterpolator());
        kbv.setTransitionGenerator(generator);

        frame_head = findViewById(R.id.frame_head);
        play_trailer1 = findViewById(R.id.iv_play);

        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mScrollView.getViewTreeObserver().addOnScrollChangedListener(this);
    }

    @Override
    public void onScrollChanged() {
        int scrollY = mScrollView.getScrollY();
        if (scrollY >= 0 && scrollY <= frame_head_defheight - actionBarHeight){
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) frame_head.getLayoutParams();
            layoutParams.height = frame_head_defheight - scrollY;
            frame_head.setLayoutParams(layoutParams);

            float alpha = 1 - (scrollY / (float) (frame_head_defheight - actionBarHeight - 20));
            play_trailer1.animate().alpha(alpha).setDuration(0).start();
        } else if (scrollY > frame_head_defheight - actionBarHeight &&
                        frame_head.getLayoutParams().height != actionBarHeight){
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) frame_head.getLayoutParams();
            layoutParams.height = actionBarHeight;
            frame_head.setLayoutParams(layoutParams);

            float alpha = 0;
            play_trailer1.animate().alpha(alpha).setDuration(0).start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}
