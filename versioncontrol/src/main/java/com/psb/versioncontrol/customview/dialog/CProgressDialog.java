package com.psb.versioncontrol.customview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.psb.versioncontrol.R;


public class CProgressDialog extends Dialog {

    private Button btnCancel;
    private ProgressBar progress;
    private TextView textProgress;
    private OnClicked onClicked;
    public CProgressDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
        init();
    }

    public CProgressDialog(@NonNull Context context, int themeResId) {
        super(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
        init();
    }

    protected CProgressDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
        init();
    }


    private void init(){
        setContentView(R.layout.dialog_progress);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        btnCancel=findViewById(R.id.button3);
        progress=findViewById(R.id.progress);
        textProgress=findViewById(R.id.text_progress);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClicked != null) {
                    onClicked.onCaneled();
                }
                cancel();
            }
        });

    }

    public void setProgress(int percent){
        progress.setProgress(percent);
        textProgress.setText(percent+" ٪");
    }

    public void setOnClicked(OnClicked onClicked) {
        this.onClicked = onClicked;
    }

    public interface OnClicked{
        void onCaneled();
    }
}
