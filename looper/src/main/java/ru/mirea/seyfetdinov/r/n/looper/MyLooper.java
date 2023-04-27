package ru.mirea.seyfetdinov.r.n.looper;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.os.Handler;
//import java.util.logging.Handler;

public class MyLooper extends Thread{
    public Handler mHandler;
    private Handler mainHandler;

    public MyLooper(Handler mainThreadHandler){
        mainHandler = mainThreadHandler;
    }

    public void run(){
        Log.d("MyLooper", "run");
        Looper.prepare();
        mHandler = new Handler(Looper.myLooper()){
            public void HandlerMessage(Message msg){
                String data = msg.getData().getString("KEY");
                Log.d("My Looper get data", data);

                int count = data.length();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", String.format("The number of letters in the world %s is %d", data, count));
                //message.setData(bundle);

                int age = msg.getData().getInt("age");
                String profession = msg.getData().getString("profession");

                int lettersCount = profession.length();

                try {
                    Thread.sleep(age * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bundle.putString("result", String.format("Your profession is %s. It has %d letters.", profession, lettersCount));
                message.setData(bundle);
                mainHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }



}
