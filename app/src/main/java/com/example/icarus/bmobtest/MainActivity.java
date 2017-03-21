package com.example.icarus.bmobtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.example.icarus.bmobtest.Bean.Person;
import com.example.icarus.bmobtest.adapter.MyAdapter;
import com.example.icarus.bmobtest.listener.MyItemClickListener;
import com.example.icarus.bmobtest.listener.MyItemLongClickListener;
import com.example.icarus.bmobtest.ui.AddActivity;
import com.example.icarus.bmobtest.ui.BaseActivity;
import com.example.icarus.bmobtest.utils.BmobUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity implements MyItemClickListener,MyItemLongClickListener{

    private RecyclerView recyclerView;
    private Button btnAdd;

    private List<Person> resource = new ArrayList<Person>();
    private static final int QUERRY_ALL = 102;

    private BmobUtils bmobUtils;
    private MyAdapter adapter;
    private Handler mHandler;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        super.init();
        Bmob.initialize(this, "c005c78efe2c7d38b2d704d9e96cb981");

        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        btnAdd = (Button) findViewById(R.id.main_btn_add);

        initRecyclerView();
        bmobUtils  = new BmobUtils(this,mHandler);
        bmobUtils.querryAll();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        adapter = new MyAdapter(resource);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo(AddActivity.class);
            }
        });
    }

    private void initRecyclerView() {

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == QUERRY_ALL){
                    resource = (List<Person>) msg.obj;
                    adapter.changeDate(resource);
                }
            }
        };

    }

    @Override
    public void onItemClick(View v, int position) {

    }

    @Override
    public void onItemLongClick(View v, int position) {

    }
}
