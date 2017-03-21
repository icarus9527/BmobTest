package com.example.icarus.bmobtest.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.UiThread;
import android.util.Log;
import android.widget.Toast;

import com.example.icarus.bmobtest.Bean.Person;
import com.example.icarus.bmobtest.MainActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by icarus9527 on 2017/3/18.
 */

public class BmobUtils {

    private ThreadUtils threadUtils = new ThreadUtils();

    private static final int QUERRY_ALL = 102;
    private static final int QUERRY_ONE = 101;

    private Context context;
    private Handler mHandler;
    public BmobUtils(Context context,Handler mHandler){
        this.context = context;
        this.mHandler = mHandler;
    }

    public BmobUtils(Context context){
        this.context = context;
    }

    //增加一条数据
    public void insert(String name, int age, File headImg){
        Person p = new Person();
        p.setName(name);
        p.setAge(age);
        p.setHeadImg(headImg);
        p.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                threadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"存储成功",Toast.LENGTH_SHORT);
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                threadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"存储失败",Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    //获取一条数据
    public void queryOne(String objectId){
        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        bmobQuery.getObject(context, objectId, new GetListener<Person>() {
            @Override
            public void onSuccess(Person person) {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

    }

    //获取多条数据
    public void querryAll(){
        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        //查询playerName叫"比目"的数据
        //bmobQuery.addWhereEqualTo("playerName","比目");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        bmobQuery.setLimit(50);
        bmobQuery.findObjects(context, new FindListener<Person>() {
            @Override
            public void onSuccess(List<Person> list) {
                Message msg = Message.obtain();
                msg.what = QUERRY_ALL;
                msg.obj = list;
                mHandler.sendMessage(msg);

            }

            @Override
            public void onError(int i, final String s) {
                threadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,s,Toast.LENGTH_SHORT);
                    }
                });
            }
        });

    }


    //更新数据
    public void update(String name,int age,File headImg,String objectId){
        Person p = new Person();
        p.setName(name);
        p.setAge(age);
        p.setHeadImg(headImg);
        p.update(context, objectId, new UpdateListener() {
            @Override
            public void onSuccess() {
                threadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"更新成功",Toast.LENGTH_SHORT);
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                threadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"更新失败",Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }


    //删除数据
    public void delete(String objectId){
        Person p = new Person();
        p.setObjectId(objectId);
        p.delete(context, new DeleteListener() {
            @Override
            public void onSuccess() {
                threadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT);
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                threadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"删除失败",Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    //注册用户
    public void register(String usernaem,String password){
        BmobUser user = new BmobUser();
        user.setUsername(usernaem);
        user.setPassword(password);
        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                threadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"注册成功",Toast.LENGTH_SHORT);
                    }
                });
            }

            @Override
            public void onFailure(int i, final String s) {
                threadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"注册失败"+s,Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    //验证登陆
    public void login(String username,String password){
        BmobUser user = new BmobUser();
        user.setUsername(username);
        user.setPassword(password);
        user.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                        Intent i = new Intent(context,MainActivity.class);
                        context.startActivity(i);
            }

            @Override
            public void onFailure(int i, final String s) {
                threadUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"登陆失败也不是我的错"+s,Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }
}
