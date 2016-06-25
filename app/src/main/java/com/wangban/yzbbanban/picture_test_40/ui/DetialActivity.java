package com.wangban.yzbbanban.picture_test_40.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.wangban.yzbbanban.picture_test_40.R;

public class DetialActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener{
    private GridView gvDetial;
    private Button btnBack;
    private TextView tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial);
        initView();
        setListener();
        setAdapter();
    }

    private void setListener() {
        gvDetial.setOnItemClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void setAdapter() {

    }

    private void initView() {
        gvDetial= (GridView) findViewById(R.id.gv_detial);
        btnBack= (Button) findViewById(R.id.btn_back);
        tvName= (TextView) findViewById(R.id.tv_beauty_pic_name);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
        }
    }
}
