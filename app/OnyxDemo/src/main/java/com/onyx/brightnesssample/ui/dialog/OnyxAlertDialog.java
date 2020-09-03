package com.onyx.brightnesssample.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.onyx.brightnesssample.R;

public class OnyxAlertDialog {

    Button cancelButton, nutralButton, okButton;
    ListView listView;
    AlertDialog dialog;

    public AlertDialog.Builder builder;
    static OnyxAlertDialog onyxAlertDialog;


    public static OnyxAlertDialog instance(Context context) {
        if (onyxAlertDialog == null) {
            onyxAlertDialog = new OnyxAlertDialog(context);
        }
        return onyxAlertDialog;
    }

    public OnyxAlertDialog(Context context) {
        if (builder == null) {
            builder = new AlertDialog.Builder(context);
        }
    }

    public AlertDialog show() {
        dialog = onyxAlertDialog.builder.show();
        adjustFocusBackground(dialog);
        return dialog;
    }

    public void dismmis() {
        if (dialog != null) {
            dialog.dismiss();
            onyxAlertDialog = null;
        }
    }

    public void adjustFocusBackground(Dialog dialog) {
        if (dialog == null) {
            return;
        }
        if (dialog instanceof  AlertDialog) {
            final AlertDialog alertDialog = (AlertDialog) dialog;
            if (dialog.isShowing()) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        adjust(alertDialog);
                    }
                });
            } else {
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        adjust(alertDialog);
                    }
                });
            }
        }
    }

    private void adjust(final AlertDialog alertDialog) {
        cancelButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        nutralButton = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        listView = alertDialog.getListView();

        cancelButton.setBackgroundResource(R.drawable.button_focus_background);
        nutralButton.setBackgroundResource(R.drawable.button_focus_background);
        okButton.setBackgroundResource(R.drawable.button_focus_background);
        if (listView != null && listView.getChildCount() > 0) {
            final Drawable background = listView.getChildAt(0).getBackground().getConstantState().newDrawable();
            listView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (listView.getSelectedView() != null) {
                        listView.getSelectedView().setBackground(hasFocus ? background : null);
                    }
                }
            });
        }
    }

}
