package com.studio.matchtune.utility;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import com.studio.matchtune.activity.DialogButtonClickListener;

/**
 * Created by Wasim on 29-06-2015.
 */
public class Common {

    public static class App_Network_values
    {
        public static final String METHOD_GET = "GET";
        public static final String METHOD_POST = "POST";

        public static final String Code = "code";
        public static final String Message = "message";

        public static final int Api_call_getData = 1;
        public static final int Api_call_Success = 200;
        public static final String URL_APP_BASE = "https://api.matchtune.com/";
    }

    public static void showToast(Context ctx, String message)
    {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }

    public static int getThemeColor(Context c, int resId)
    {
        // To get Primary selector color runtime.
        TypedValue typedValue = new TypedValue();
        c.getTheme().resolveAttribute(resId, typedValue, true);
        return typedValue.data;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void showDialog(Context context,
                                  String dialogTitle, String dialogMessage, String textPositiveButton,
                                  String textNegativeButton,
                                  final DialogButtonClickListener buttonClickListener,
                                  final int dialogIdentifier) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton(textPositiveButton, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id1) {
                if (buttonClickListener == null) {
                    dialog.dismiss();
                } else {
                    buttonClickListener.onPositiveButtonClicked(dialogIdentifier);
                }
            }
        });

        if (textNegativeButton != null) {
            alertDialogBuilder.setNegativeButton(textNegativeButton, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id1) {
                    if (buttonClickListener == null) {
                        dialog.dismiss();
                    } else {
                        buttonClickListener.onNegativeButtonClicked(dialogIdentifier);
                    }
                }
            });
        }

        AlertDialog alertDialog = alertDialogBuilder.create();
        //	alertDialog.getWindow().getAttributes().windowAnimations = R.style.dialog_animation;

        if (!((Activity) context).isFinishing()) {
            alertDialog.show();
        }

    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }
}
