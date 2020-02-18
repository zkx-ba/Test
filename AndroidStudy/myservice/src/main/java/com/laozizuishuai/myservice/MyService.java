package com.laozizuishuai.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.widget.Toast;

public class MyService extends Service {


    //    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1:
//                    Toast.makeText(getApplicationContext(), (CharSequence) msg.obj, Toast.LENGTH_SHORT).show();
//                    System.out.println((CharSequence) msg.obj);
//                    break;
//
//
//
//            }
//        }
//    };
    private String data = "原始信息";
    private boolean IsRuning = false;

    public MyService() {
    }


    public class MyBind extends Binder {

        public MyService getService() {
            return MyService.this;
        }


        public void setData(String str) {
            MyService.this.data = str;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("onBind(Intent intent)");
        return new MyBind();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("onCreate()");


        IsRuning = true;
        new Thread() {
            @Override
            public void run() {
                super.run();
                int i = 0;
                while (IsRuning) {
                    i++;
                    System.out.println(i + data);
                    if (callBack != null){
                        callBack.DataChange(i +"  ----  "+ data);
                    }

                    SystemClock.sleep(1000);
                }

            }
        }.start();


     /*   Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj = data;
                handler.sendMessage(message);

                Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                System.out.println(data);


            }
        }, 1000);*/


    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        data = intent.getStringExtra("data");
        System.out.println("onStartCommand()");


//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Message message = handler.obtainMessage();
//                data = intent.getStringExtra("data");
//                message.what = 1;
//                message.obj = data;
//                handler.sendMessage(message);
//                System.out.println("我就是下给看下到底走了几次");
//            }
//        }, 1000);


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        IsRuning = false;
    }


public CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public interface CallBack{
        void DataChange(String str);
    }
}
