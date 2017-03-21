package com.example.icarus.bmobtest.Bean;

import java.io.File;

import cn.bmob.v3.BmobObject;

/**
 * Created by icarus9527 on 2017/3/18.
 */

public class Person extends BmobObject{
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public File getHeadImg() {
        return headImg;
    }

    public void setHeadImg(File headImg) {
        this.headImg = headImg;
    }

    String name;
    int age;
    File headImg;
}
