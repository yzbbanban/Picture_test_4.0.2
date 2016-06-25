package com.wangban.yzbbanban.picture_test_40.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.wangban.yzbbanban.picture_test_40.R;
import com.wangban.yzbbanban.picture_test_40.contast.Contast;
import com.wangban.yzbbanban.picture_test_40.entity.Image;
import com.wangban.yzbbanban.picture_test_40.util.BitmapUtil;
import com.wangban.yzbbanban.picture_test_40.util.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YZBbanban on 16/6/5.
 */
public class MainImageAdapter extends BaseAdapter<Image> implements Contast {
    private Context context;
    private GridView gridView;
    private List<ImageLoadTask> tasks = new ArrayList<ImageLoadTask>();
    private boolean isLoop = true;
    private Thread workThread;
    private Map<String, SoftReference<Bitmap>> cache = new HashMap<>();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_LOAD_BITMAP_SUCCESSS:
                    ImageLoadTask task = (ImageLoadTask) msg.obj;
                    Bitmap bitmap = task.bm;
                    ImageView imageView = (ImageView) gridView.findViewWithTag(task.path);
                    Log.i(TAG, "handleMessage: 1111111");
                    if (imageView != null) {
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                        } else {
                            imageView.setImageResource(R.drawable.katong);
                        }
                    }

                    break;
            }
        }
    };

    public MainImageAdapter(Context context, ArrayList<Image> data, GridView gridView) {
        super(context, data);
        this.context = context;
        this.gridView = gridView;
        workThread = new Thread() {
            @Override
            public void run() {
                while (isLoop) {
                    if (!tasks.isEmpty()) {
                        ImageLoadTask task = tasks.remove(0);
                        String path = task.path;
                        Bitmap bitmap = loadBitmap(path);
                        task.bm = bitmap;
                        Message msg = Message.obtain();
                        msg.what = HANDLER_LOAD_BITMAP_SUCCESSS;
                        msg.obj = task;
                        handler.sendMessage(msg);
                    } else {
                        try {
                            synchronized (workThread) {
                                workThread.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        workThread.start();
    }

    private Bitmap loadBitmap(String path) {
        try {
            InputStream is = HttpUtil.get(path);
            Bitmap bitmap = BitmapUtil.loadBitmap(is, 190, 125);
            cache.put(path, new SoftReference<Bitmap>(bitmap));

            String fileName=path;
            File file=new File(context.getCacheDir(),fileName);
            BitmapUtil.saveImage(file.getAbsolutePath(),bitmap);

            Log.i(TAG, "loadBitmap: " + path);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Image img = getData().get(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = getLayoutInflater().inflate(R.layout.main_img_item, null);
            holder.mianImg = (ImageView) convertView.findViewById(R.id.iv_main_image);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        SoftReference<Bitmap> ref = cache.get(img.getPath());
        if (ref != null) {
            Bitmap bitmap = ref.get();
            if (bitmap!=null){
                holder.mianImg.setImageBitmap(bitmap);
                return convertView;
            }

        }
        File file=new File(context.getCacheDir(),img.getPath());
        Bitmap bitmap=BitmapUtil.loadBitmap(file.getAbsolutePath());

        if (bitmap != null) {
            holder.mianImg.setImageBitmap(bitmap);
            cache.put(img.getPath(),new SoftReference<Bitmap>(bitmap));
            return convertView;
        }

        holder.mianImg.setTag(img.getPath());
        ImageLoadTask task = new ImageLoadTask();
        task.path = img.getPath();
        tasks.add(task);
        synchronized (workThread) {
            workThread.notify();
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView mianImg;
    }

    class ImageLoadTask {
        String path;
        Bitmap bm;
    }

    public void stopThread() {
        isLoop = false;
        synchronized (workThread) {
            workThread.notify();
        }
    }
}
