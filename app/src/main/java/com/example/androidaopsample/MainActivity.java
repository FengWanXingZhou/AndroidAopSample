package com.example.androidaopsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.changhong.domain.aoptool.TimeAspectJ;
import com.changhong.domain.aoptool.TimeTrace;
import com.example.testlibrary.Test;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //test();
        new Test().testLibraryAspectJ();
        new Test().testLibraryAspectJReturn();
    }
    @TimeTrace(tag = "time")
    private void test(){
        Log.i(TimeAspectJ.TAG,"test real function");
    }

}
