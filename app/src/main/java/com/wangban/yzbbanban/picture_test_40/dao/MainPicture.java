package com.wangban.yzbbanban.picture_test_40.dao;


import android.os.AsyncTask;
import android.util.Log;

import com.wangban.yzbbanban.picture_test_40.contast.Contast;
import com.wangban.yzbbanban.picture_test_40.entity.Image;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by YZBbanban on 16/6/6.
 */
public class MainPicture implements Contast {

    public MainPicture() {
    }


    public void findImageGridView(final Callback callback,final String webPath) {
        AsyncTask<String, String, List<Image>> task = new AsyncTask<String, String, List<Image>>() {
            List<Image> images = new ArrayList<Image>();

            @Override
            protected List<Image> doInBackground(String... params) {
                try {
                    Log.i(TAG, "doInBackground: "+webPath);
                    Document doc = Jsoup.connect(webPath).get();
                    Elements e = doc.getElementsByClass("post-thumb");
                    for (int i = 0; i < e.size(); i++) {
                        Elements a = e.get(i).getElementsByTag("a");
                        String skipPagePath = a.first().attr("href");

                        String width2 = a.first().getElementsByTag("img").attr("width");
                        String height2 = a.first().getElementsByTag("img").attr("height");

                        String path2 = a.first().getElementsByTag("img").attr("src");
                        int width = Integer.parseInt(width2);
                        int height = Integer.parseInt(height2);
                        // Log.i(TAG, "run: " + "path2  " + path2 + "\n" + "width " + width2 + "\nheight " + height2);
                        Image img = new Image();
                        img.setSetSkipPagePath(skipPagePath);
                        img.setPath(path2);
                        img.setHeight(height);
                        img.setWidth(width);
                        img.setLocalPath(webPath);
                        images.add(img);
                        //Log.i(TAG, "getData: path:"+ i+";"+img.getPath());

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return images;
            }

            @Override
            protected void onPostExecute(List<Image> images) {
                callback.onImageDownload(images);
            }
        };
        task.execute();

    }

    public interface Callback {
        void onImageDownload(List<Image> list);
    }

    ;
}
