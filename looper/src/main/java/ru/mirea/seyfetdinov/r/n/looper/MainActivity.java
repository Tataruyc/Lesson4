package ru.mirea.seyfetdinov.r.n.looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.os.Handler;

//import java.util.logging.Handler;
import java.util.logging.LogRecord;

import ru.mirea.seyfetdinov.r.n.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String result = msg.getData().getString("result");
                Log.d(MainActivity.class.getSimpleName(), result);

                Log.d(MainActivity.class.getSimpleName(), "Task execute. This is result: " + msg.getData().getString("result"));

            }
        };
        MyLooper myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

        binding.editTextMirea.setText("Мой номер по списку No___");
        binding.mireaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("KEY", "mirea");

                int age = Integer.parseInt(binding.editUserAge.getText().toString());
                String profession = binding.editUserProfession.getText().toString();

                bundle.putInt("age", age);
                bundle.putString("profession", profession);
                msg.setData(bundle);
                myLooper.mHandler.sendMessage(msg);

            }
        });
    }
}