package com.psb.versioncontrol.customview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.psb.versioncontrol.R;


public class QuestionDialog extends Dialog {

    private View btnCancel;
    private View btnOk;
    private TextView textTitle;
    private TextView textMessage;

    private OnClickListener onClickListener;

    public QuestionDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
        init();

    }

    public QuestionDialog(@NonNull Context context, int themeResId) {
        super(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
        init();
    }

    protected QuestionDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_question);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btnCancel = findViewById(R.id.buttonCancel);
        btnOk = findViewById(R.id.buttonTaiid);
        textTitle = findViewById(R.id.titleerg);
        textMessage = findViewById(R.id.textView24w);

        if (btnCancel != null) {
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        onClickListener.onCancel();
                    }
                    cancel();
                }
            });
        }

        if (btnOk != null) {
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        onClickListener.onOk();
                    }
                    cancel();
                }
            });
        }
    }

    public void setTitle(String message) {
        if (textTitle != null) {
            textTitle.setText(message);
        }
    }

    public void setMessage(String message) {
        if (textMessage != null) {
            textMessage.setText(message);
        }
    }

    public void setOnClickListener(String textCancel, String textOK, OnClickListener onClickListener) {
        if (btnOk != null && btnOk instanceof TextView) {
            ((TextView) btnOk).setText(textOK);
        }
        if (btnCancel != null && btnCancel instanceof TextView) {
            ((TextView) btnCancel).setText(textCancel);
        }

        setOnClickListener(onClickListener);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onCancel();

        void onOk();
    }

}
