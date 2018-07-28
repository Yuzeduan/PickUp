package com.yuzeduan.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.yuzeduan.R;
import com.yuzeduan.adapter.ImageAdapter;
import com.yuzeduan.model.PickUpModel;

import java.util.List;

public class PickUpActivity extends AppCompatActivity {
    private RecyclerView mRvImage;
    private ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up);
        checkPermission();
    }

    public void checkPermission(){
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {
            //有权限，加载图片。
            initView();
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(PickUpActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initView();
                }else{
                    Toast.makeText(this, "您未授予权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    public void initView(){
        PickUpModel mPickUpModel = new PickUpModel();
        mRvImage = findViewById(R.id.rv_images);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRvImage.setLayoutManager(manager);
        mPickUpModel.scanPicture(this, new PickUpModel.Callback() {
            @Override
            public void onFinish(List<String> list) {
                adapter = new ImageAdapter(PickUpActivity.this, list, R.layout.image_item);
                mRvImage.setAdapter(adapter);
            }
        });
    }
}
