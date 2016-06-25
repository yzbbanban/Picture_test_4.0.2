package com.wangban.yzbbanban.picture_test_40.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.wangban.yzbbanban.picture_test_40.R;
import com.wangban.yzbbanban.picture_test_40.adapter.MainImageAdapter;
import com.wangban.yzbbanban.picture_test_40.contast.Contast;
import com.wangban.yzbbanban.picture_test_40.dao.MainPicture;
import com.wangban.yzbbanban.picture_test_40.entity.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YZBbanban on 16/6/5.
 */
public class FragmentYoung extends Fragment implements Contast{
    private GridView gvYoungImage;
    private MainImageAdapter mainImageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_young, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {

        gvYoungImage = (GridView) view.findViewById(R.id.gv_young);
        MainPicture pic = new MainPicture();

        pic.findImageGridView(new MainPicture.Callback() {

            @Override
            public void onImageDownload(List<Image> imgs) {
                setAdapter(imgs);
            }
        },YOUNG);

    }

    private void setAdapter(List<Image> images) {
        mainImageAdapter = new MainImageAdapter(getActivity(), (ArrayList<Image>) images,gvYoungImage);
        gvYoungImage.setAdapter(mainImageAdapter);


    }
}
