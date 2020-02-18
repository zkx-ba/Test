package com.laozizuishuai.myservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ConnectActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText;

    private MyService myService;
    private MyService.MyBind myBind;
    private TextView textView;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            textView.setText(data.getString("str"));

        }
    };


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBind = (MyService.MyBind) service;
            myService = myBind.getService();
            myService.setCallBack(new MyService.CallBack() {
                @Override
                public void DataChange(String str) {
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("str", str);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        findViewById(R.id.StartService).setOnClickListener(this);
        findViewById(R.id.StopService).setOnClickListener(this);
        findViewById(R.id.BindService).setOnClickListener(this);
        findViewById(R.id.UnBindService).setOnClickListener(this);
        findViewById(R.id.syondata).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.StartService:
                Intent intent = new Intent(this, MyService.class);
                intent.putExtra("data", editText.getText().toString());
                startService(intent);
                break;
            case R.id.StopService:
                stopService(new Intent(this, MyService.class));
                break;
            case R.id.BindService:
                bindService(new Intent(this, MyService.class), serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.UnBindService:
                unbindService(serviceConnection);
                break;
            case R.id.syondata:
                myBind.setData(editText.getText().toString());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        stopService(new Intent(this, MyService.class));
    }
}
