package com.yzd.create;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yzd.create.widgets.NineGridView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private NineGridView nine;

    private List<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nine = findViewById(R.id.nine);
        urls.add("http://img1.3lian.com/img013/v5/69/d/85.jpg");
        urls.add("http://img3.3lian.com/2013/s1/51/d/118.jpg");
        urls.add("http://pic3.16pic.com/00/03/88/16pic_388730_b.jpg");
        urls.add("http://img15.3lian.com/2015/a1/13/d/22.jpg");
        urls.add("http://pic2.16pic.com/00/33/17/16pic_3317149_b.jpg");

        nine.setOnNineListener(new NineGridView.OnNineClickListener() {
            @Override
            public void onNineListener(View v, int position, String url) {
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra("url",urls.get(position));
                startCompat(intent, v, "NineView");
            }
        });

        nine.setUrls(urls);

    }

    public void add(View view) {
        urls.add("http://img1.3lian.com/img013/v5/69/d/85.jpg");
        nine.setUrls(urls);
    }

    public void dele(View view) {
        if (urls.size() > 0) {
            urls.remove(urls.size() - 1);
        }
        nine.setUrls(urls);
    }

    public void clear(View view) {
        urls.clear();
        nine.setUrls(urls);

        nine.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("NINE : ", "height: " + nine.getMeasuredHeight() + " width: " + nine.getMeasuredWidth());
            }
        }, 1000);
    }

    /**
     * 过程动画
     *
     * @param intent
     * @param view
     * @param shareElement
     */
    public void startCompat(Intent intent, View view, String shareElement) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, shareElement);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
