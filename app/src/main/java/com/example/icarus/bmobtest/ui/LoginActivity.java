package com.example.icarus.bmobtest.ui;

import android.view.View;
import android.widget.TextView;

import com.example.icarus.bmobtest.R;

/**
 * Created by icarus9527 on 2017/3/21.
 */

public class LoginActivity extends BaseActivity {

    private TextView tvRegister;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        super.init();
        tvRegister = (TextView) findViewById(R.id.loging_tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(RegisterActivity.class);
            }
        });
    }
}
