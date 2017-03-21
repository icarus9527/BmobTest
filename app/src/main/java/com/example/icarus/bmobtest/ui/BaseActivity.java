package com.example.icarus.bmobtest.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by icarus9527 on 2017/3/18.
 */

public abstract class BaseActivity extends Activity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        init();
    }

    public void init() {

    }

    public abstract int getLayoutResId();

    public void goTo(Class activity){
        Intent i = new Intent(this,activity);
        startActivity(i);
    }
}
