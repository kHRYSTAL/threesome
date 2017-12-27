package me.khrystal.threesomeandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.khrystal.threesomeandroid.threesome.ThreesomeActivity;

public class MainActivity extends AppCompatActivity {

    public static final String INDEX_URL = "http://khrystal.tunnel.echomod.cn/index/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.startBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThreesomeActivity.launch(MainActivity.this, INDEX_URL, "index");
            }
        });
    }
}
