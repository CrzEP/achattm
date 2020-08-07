package com.dlg.achattm.util;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WebDownloadUtil {

    final String TAG=WebDownloadUtil.class.getName();

    private static WebDownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;

    public static WebDownloadUtil getInstance() {
        if (downloadUtil == null) {
            downloadUtil = new WebDownloadUtil();
        }
        return downloadUtil;
    }

    private WebDownloadUtil() {
        okHttpClient = new OkHttpClient();
    }

    /**
     * @param url 下载连接
     * @param savePath 储存下载文件的地址
     * @param listener 下载监听
     */
    public void download(final String url, final String savePath, final WebDownloadListener listener) {
        if (!URLUntil.ifUrl(url)){
            Log.i(TAG,"非法的下载地址："+url);
            return;
        }
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                listener.onDownloadFailed();
            }
            @Override
            public void onResponse(Call call, Response response){
                InputStream is = null;
                byte[] buf = new byte[2048];
                FileOutputStream fos = null;
                ResponseBody body=response.body();
                if (body==null){
                    listener.onDownloadFailed();
                    return;
                }
                try {
                    is = body.byteStream();
                    long total = body.contentLength();
                    File file = new File(savePath);
                    if (file.exists()){
                        Log.i(TAG,"文件已存在："+savePath);
                        return;
                    }
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    int len;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public interface WebDownloadListener {

        /**
         * 下载成功
         */
        void onDownloadSuccess(File file);

        /**
         * @param progress
         * 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }
}