package com.laozizuishuai.myp2pview;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
private  int totaProgress = 90; //绘制的总进度（后期网络获取）
    private RoundProgress roundProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roundProgress = findViewById(R.id.qwqwqwq);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        new Thread(new Runnable() {
            @Override
            public void run() {
                int tempProgress = 0;//绘制的当前进度
                while (tempProgress<=totaProgress){
                    roundProgress.setProgress(tempProgress);
                    tempProgress++;
                    SystemClock.sleep(100);
                }
            }
        }).start();


            }
        });


    }
}
