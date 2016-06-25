package com.wangban.yzbbanban.picture_test_40.fragment;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.wangban.yzbbanban.picture_test_40.R;
import com.wangban.yzbbanban.picture_test_40.adapter.MainImageAdapter;
import com.wangban.yzbbanban.picture_test_40.contast.Contast;
import com.wangban.yzbbanban.picture_test_40.dao.MainPicture;
import com.wangban.yzbbanban.picture_test_40.entity.Image;
import com.wangban.yzbbanban.picture_test_40.ui.DetialActivity;

import java.util.*;
/**
 * Created by YZBbanban on 16/6/5.
 */
public class FragmentNew extends Fragment implements Contast,AdapterView.OnItemClickListener{
    private GridView gvNewImage;
    private MainImageAdapter mainImageAdapter;
    private List<Image> imgs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new, container, false);
        initView(view);
        setListener();
        return view;
    }

    private void setListener() {
        gvNewImage.setOnItemClickListener(this);

    }

    private void initView(View view) {

        gvNewImage = (GridView) view.findViewById(R.id.gv_new);
        MainPicture pic = new MainPicture();

        pic.findImageGridView(new MainPicture.Callback() {

            @Override
            public void onImageDownload(List<Image> imgs) {
                setAdapter(imgs);
            }
        },NEW);

    }

    private void setAdapter(List<Image> images) {
        //Log.i(TAG, "setAdapter: "+images.get(0).getPath());
        imgs=images;
        mainImageAdapter = new MainImageAdapter(getActivity(), (ArrayList<Image>) images,gvNewImage);
        gvNewImage.setAdapter(mainImageAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(), DetialActivity.class);
        intent.putExtra(EXTRA_PATH,imgs.get(position).getPath());
        startActivity(intent);
    }
}
