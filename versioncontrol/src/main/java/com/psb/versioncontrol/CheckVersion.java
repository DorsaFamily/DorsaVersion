package com.psb.versioncontrol;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.psb.versioncontrol.customview.dialog.QuestionDialog;
import com.psb.versioncontrol.model.ParamsVersion;
import com.psb.versioncontrol.model.ResponseVersion;
import com.psb.versioncontrol.utils.DownloadFileFromURL;
import com.psb.versioncontrol.utils.RetrofitService;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;


public class CheckVersion {

    private Activity activity;

    private final int REQUEST_INSTALL = 10;
    private final int REQUEST_PERMISSION = 11;
    private ParamsVersion paramsVersion;
    private onTaskFinished onTaskFinished;
    private ProgressDialog progressDialog;
    private String projectName;
    private int currentVersion;


    public CheckVersion(Activity activity,
                        String baseUrl,
                        String projectName,
                        int currentVersion
                        ) {
        this.activity = activity;
        progressDialog=new ProgressDialog(activity);
        progressDialog.setMessage("در حال بررسی ...");

        this.projectName=projectName;
        this.currentVersion=currentVersion;

        setBaseUrl(baseUrl);
    }

    public void getVersion(onTaskFinished onTaskFinished) {
        this.onTaskFinished = onTaskFinished;

        progressDialog.show();
        Get_Check_Version service = RetrofitService.getClient().create(Get_Check_Version.class);
        Call<ResponseVersion> call = service.getVersion();

        call.enqueue(new Callback<ResponseVersion>() {
            @Override
            public void onResponse(Call<ResponseVersion> call, Response<ResponseVersion> response) {
                progressDialog.cancel();
                if (response.code() == 200) {
                    if (response.body().getExtra().size() > 0) {
                        if(response.body().getExtra().get(0).getVersion()>currentVersion){
                            paramsVersion = response.body().getExtra().get(0);
                            showMessageDialog();
                        }else{
                            finishTask(false);
                        }
                    } else {
                        finishTask(false);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseVersion> call, Throwable t) {
                progressDialog.cancel();
                finishTask(false);
            }
        });
    }

    public void setBaseUrl(String url){
        if(url==null || url.isEmpty()){
            throw new RuntimeException(activity.toString()
                    + " must have valid url");
        }else{
            if(!url.substring(url.length()-1).contains("/")){
                url=url+"/";
            }
            RetrofitService.setBaseUrl(url);
        }
    }

    public void setDebug(boolean isDebug){
        RetrofitService.setDebug(isDebug);
    }

    private void showMessageDialog() {
        QuestionDialog dialog = new QuestionDialog(activity);
        dialog.setTitle(paramsVersion.getTitle());
        dialog.setMessage(paramsVersion.getMsg());
        dialog.setCancelable(false);
        dialog.setOnClickListener("انصراف", "دریافت", new QuestionDialog.OnClickListener() {
            @Override
            public void onCancel() {
                if (paramsVersion.isForce()) {
                    finishTask(true);
                } else {
                    finishTask(false);
                }
            }

            @Override
            public void onOk() {
                startDownload();
            }
        });
        dialog.show();
    }

    private void finishTask(boolean status) {
        if (onTaskFinished != null) {
            onTaskFinished.onFinished(status);
        }
    }

    private void startDownload() {
        if(storagePermission(true)) {
            try {
                DownloadFileFromURL downloadFileFromURL = new DownloadFileFromURL(getActivity(), paramsVersion, projectName);
                downloadFileFromURL.setOnDownloadFinished(new DownloadFileFromURL.OnDownloadFinished() {
                    @Override
                    public void onSuccess(String path) {
                        install(path);
                    }

                    @Override
                    public void onFailed() {
                        openDownloadPage();
                    }
                });
                downloadFileFromURL.execute(paramsVersion.getUrl());
            }catch (Exception ex){
                ex.printStackTrace();
                openDownloadPage();
            }
        }

    }

    boolean storagePermission(boolean showRequest) {
        boolean storagePer = true;
        ArrayList<String> permissions = new ArrayList<String>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                storagePer = false;
            }
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                storagePer = false;
            }
            if (!storagePer && showRequest) {
                getActivity().requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_PERMISSION);
            }
        }
        return storagePer;
    }

    private void install(String path) {
        try {
            File toInstall = new File(path);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri apkUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName()+ ".provider", toInstall);
                Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                intent.setData(apkUri);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                getActivity().startActivityForResult(intent, REQUEST_INSTALL);
            } else {
                Uri apkUri = Uri.fromFile(toInstall);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivityForResult(intent, REQUEST_INSTALL);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            openDownloadPage();
        }
    }

    public boolean checkPermission(int requestCode){
        if(requestCode==REQUEST_PERMISSION){
            if(storagePermission(false)){
                startDownload();
            }else{
                openDownloadPage();
            }
            return true;
        }

        return false;
    }

    public boolean checkActivityResult(int requestCode, int resultCode) {
        if (requestCode == REQUEST_INSTALL) {
            if (resultCode != Activity.RESULT_OK) {
                openDownloadPage();
            }
            return true;
        }
        return false;
    }

    private void openDownloadPage() {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(paramsVersion.getUrl()));
            getActivity().startActivity(browserIntent);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (paramsVersion.isForce()) {
                finishTask(true);
            }
        }

    }

    public Activity getActivity() {
        return activity;
    }

    public interface onTaskFinished {
        void onFinished(boolean exit);
    }

    public interface Get_Check_Version {
//        @GET("VersionsDorsa")
        @GET("?")
        Call<ResponseVersion> getVersion();
    }

}
