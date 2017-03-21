package com.example.icarus.bmobtest.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.icarus.bmobtest.MainActivity;
import com.example.icarus.bmobtest.R;
import com.example.icarus.bmobtest.utils.BmobUtils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by icarus9527 on 2017/3/18.
 */

public class AddActivity extends BaseActivity {

    private ImageView ivHeadImg;
    private EditText edtName;
    private EditText edtAge;
    private Button btnSave;

    private static final int CAPTURE_PIICTURE_RESULT_CODE = 1001;
    private static final int CAPTURE_PHOTO_RESULT_CODE = 1002;
    private static final int REQUIRE_PERMISSION_REQUEST_CODE = 2001;
    private File headImg = null;

    private BmobUtils bmobUtils = new BmobUtils(this);

    @Override
    public int getLayoutResId() {
        return R.layout.activity_add;
    }

    @Override
    public void init() {
        super.init();
        ivHeadImg = (ImageView) findViewById(R.id.add_iv_headimg);
        edtName = (EditText) findViewById(R.id.add_edt_name);
        edtAge = (EditText) findViewById(R.id.add_edt_age);
        btnSave = (Button) findViewById(R.id.add_btn_save);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUIRE_PERMISSION_REQUEST_CODE);
        }


        ivHeadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AddActivity.this).setTitle("选择图片")
                        .setPositiveButton("拍照", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String state = Environment.getExternalStorageState();
                                if (state.equals(Environment.MEDIA_MOUNTED)) {
                                    Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                                    startActivityForResult(getImageByCamera, CAPTURE_PHOTO_RESULT_CODE);
                                }
                                else {
                                    Toast.makeText(AddActivity.this, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
                                }
                            }
                        }).setNegativeButton("图库", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent,CAPTURE_PIICTURE_RESULT_CODE);
                    }
                }).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                int age = Integer.parseInt(edtAge.getText().toString());
                bmobUtils.insert(name,age,headImg);

                goTo(MainActivity.class);
                finish();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data == null){
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAPTURE_PIICTURE_RESULT_CODE){
            Uri uri = data.getData();

            ivHeadImg.setImageURI(uri);

            headImg = getRealPathFromFile(uri);
        }else if(requestCode == CAPTURE_PHOTO_RESULT_CODE){
            Uri uri = data.getData();

            ivHeadImg.setImageURI(uri);

            headImg = new File(uri.toString());
        }

    }

    public File getRealPathFromFile(Uri uri){
        ContentResolver resolver = this.getContentResolver();
        String[] pojo = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this, uri, pojo, null,null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(pojo[0]));
        File file = null;
        if (path!=null && path.length()>0){
            file = new File(path);
        }

        return file;
    }
}
