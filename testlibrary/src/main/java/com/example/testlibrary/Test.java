package com.example.testlibrary;

import android.util.Log;

import com.changhong.domain.aoptool.TimeAspectJ;
import com.changhong.domain.aoptool.TimeTrace;

public class Test {
    @TimeTrace
    public void testLibraryAspectJ(){
        Log.i(TimeAspectJ.TAG,"testLibraryAspectJ real function");
    }
    @TimeTrace
    public int testLibraryAspectJReturn(TestArgs arg){
        Log.i(TimeAspectJ.TAG,"testLibraryAspectJReturn real function");
        return 1;
    }

}
