package com.laozizuishuai.myp2pview;

public class UIUtils {

    public static void runOnUIThread(Runnable runnable){
        if (isInMainthread()){
            runnable.run();
        }else {

        }
    }

    private static boolean isInMainthread() {
        return false;
    }
}
