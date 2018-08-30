package com.psb.versioncontrol.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.psb.versioncontrol.customview.dialog.CProgressDialog;
import com.psb.versioncontrol.model.ParamsVersion;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class DownloadFileFromURL extends AsyncTask<String, Integer, Boolean> {

    private ParamsVersion model;
    private OnDownloadFinished onDownloadFinished;

    public static String parentPathName ;
    private String path ;

    private CProgressDialog progressDialog;
    public DownloadFileFromURL(Context context, ParamsVersion model,String parentPathName) {
        this.model = new ParamsVersion();
        this.model = model;
        progressDialog=new CProgressDialog(context);
        this.parentPathName=parentPathName;
        path = Environment.getExternalStorageDirectory() + "/" + parentPathName;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setCancelable(false);
        progressDialog.setOnClicked(new CProgressDialog.OnClicked() {
            @Override
            public void onCaneled() {
                if (onDownloadFinished != null) {
                    cancel(false);
                    onDownloadFinished.onFailed();
                }
            }
        });
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... f_url) {
        int count;
        File mydir = new File(path);
        if (!mydir.exists()) {
            mydir.mkdirs();
        }

        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            int lenghtOfFile = conection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            OutputStream output = new FileOutputStream(path + "/" + model.getVersion() + ".apk");
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress((int) (total * 100) / lenghtOfFile);
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        progressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(Boolean isCompleted) {
        progressDialog.cancel();
        if (isCompleted) {
            if (onDownloadFinished != null) {
                onDownloadFinished.onSuccess(path + "/" + model.getVersion() + ".apk");
            }
        } else {
            if (onDownloadFinished != null) {
                onDownloadFinished.onFailed();
            }
        }
    }

    public void setOnDownloadFinished(OnDownloadFinished onDownloadFinished) {
        this.onDownloadFinished = onDownloadFinished;
    }

    public interface OnDownloadFinished {
        void onSuccess(String path);

        void onFailed();
    }

}