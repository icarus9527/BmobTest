package com.example.icarus.bmobtest.ui;


import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.icarus.bmobtest.R;
import com.example.icarus.bmobtest.utils.BmobUtils;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

/**
 * Created by icarus9527 on 2017/3/21.
 */

public class RegisterActivity extends BaseActivity {

    private EditText edtUsername,edtPassword,edtComfirmPW,edtCode,edtPhoneNum;
    private Button btnGetCode,btnRegister;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    public void init() {
        super.init();
        edtUsername = (EditText) findViewById(R.id.register_edt_username);
        edtPassword = (EditText) findViewById(R.id.register_edt_password);
        edtComfirmPW = (EditText) findViewById(R.id.register_edt_confrim_password);
        edtPhoneNum = (EditText) findViewById(R.id.register_edt_phonenum);
        edtCode = (EditText) findViewById(R.id.register_edt_code);
        btnGetCode = (Button) findViewById(R.id.register_btn_getCode);
        btnRegister = (Button) findViewById(R.id.register_btn_register);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
        }

        btnGetCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhoneNum.getText().toString().trim();
                Toast.makeText(RegisterActivity.this,"13232632320",Toast.LENGTH_SHORT);
                if(phone!=null && phone.length() == 11){
                    BmobSMS.requestSMSCode(RegisterActivity.this, phone, "BombTest", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if(e == null){
                                Toast.makeText(RegisterActivity.this,"验证短信已发送",Toast.LENGTH_SHORT);
                            }else{
                                Toast.makeText(RegisterActivity.this,"验证短信发送失败，别等了~~",Toast.LENGTH_SHORT);
                            }
                        }
                    });
                }
            }
        });

        btnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this,"13232632320",Toast.LENGTH_SHORT);

                final String username = edtUsername.getText().toString();
                final String password = edtPassword.getText().toString();
                String confirmPassword = edtComfirmPW.getText().toString();
                if (!password.equals(confirmPassword)){
                    Toast.makeText(RegisterActivity.this,"别闹，好好填密码",Toast.LENGTH_SHORT);
                    return;
                }
                String code = edtCode.getText().toString();
                String phone = edtPhoneNum.getText().toString();
                BmobSMS.verifySmsCode(RegisterActivity.this, phone, code, new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            BmobUtils bmobUtils = new BmobUtils(RegisterActivity.this);
                            bmobUtils.register(username,password);
                            goTo(LoginActivity.class);
                        }else{
                            Toast.makeText(RegisterActivity.this,e.toString(),Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });
    }

}
