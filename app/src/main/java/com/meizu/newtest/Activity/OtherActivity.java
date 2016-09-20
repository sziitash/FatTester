package com.meizu.newtest.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.meizu.newtest.R;


//参考资料：http://vjson.com/wordpress/android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E5%88%86%E6%9E%90%E5%AE%9E%E6%88%98.html

public class OtherActivity extends Activity {

    private Object[] mObjs = new Object[1000000];//
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mObjs.length; i++) {
                    mObjs[i] = new Object();
                }
                finish();
            }
        });

        TestActivityManager.instance().registActivity(this);
    }

    @Override
    protected void onDestroy() {
        Log.i("hello", "other_onDestroy");
        super.onDestroy();
    }
}