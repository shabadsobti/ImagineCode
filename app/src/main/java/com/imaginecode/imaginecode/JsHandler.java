package com.imaginecode.imaginecode;

/**
 * Created by shabadsobti on 6/16/18.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Class to handle all calls from JS & from Java too
 **/
public class JsHandler {

    Activity activity;
    String TAG = "JsHandler";
    Integer lesson_id;
    Integer student_id;
    Integer lesson_number;
    WebView webView;
    private PopupWindow mPopupWindow;



    public JsHandler(Activity _contxt,WebView _webView, Integer lesson_id, Integer student_id, Integer lesson_number, String lesson_instructions) {
        activity = _contxt;
        webView = _webView;
        this.lesson_id = lesson_id;
        this.student_id = student_id;
        this.lesson_number = lesson_number;
    }


    /**
     * This function handles call from Android-Java
     */
    public void javaFnCall(String code) {

        final String webUrl = "javascript:" + code;
        // Add this to avoid android.view.windowmanager$badtokenexception unable to add window
        if(!activity.isFinishing())
            // loadurl on UI main thread
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    webView.loadUrl(webUrl);
                }
            });
    }


    @JavascriptInterface
    public void repeatLessonModal(int stars) {

        final Dialog dialog = new Dialog(webView.getContext());
        dialog.setContentView(R.layout.blockly_repeat_modal);

        TextView text = (TextView) dialog.findViewById(R.id.text);
        TextView starText = (TextView) dialog.findViewById(R.id.starText);
        starText.setText("" + stars);
        text.setText("Well Done!");
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @JavascriptInterface
    public void failedLessonModal() {

        final Dialog dialog = new Dialog(webView.getContext());
        dialog.setContentView(R.layout.blockly_repeat_modal);

        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("You cashed. Try changing your code and running it again.");
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    @JavascriptInterface
    public void successModal(final int stars) {

            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Inflate the custom layout/view
            View customView = inflater.inflate(R.layout.blockly_success_modal,null);
            TextView instructions =  customView.findViewById(R.id.text);
            instructions.setText(activity.getResources().getText(R.string.well_done));
            mPopupWindow = new PopupWindow(
                    customView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );


            // Set an elevation value for popup window
            // Call requires API level 21
            if(Build.VERSION.SDK_INT>=21){
                mPopupWindow.setElevation(5.0f);
            }

//             Get a reference for the custom view close button
            Button closeButton =  customView.findViewById(R.id.nextLesson);
            final DatabaseHelper db = new DatabaseHelper(activity);

            // Set a click listener for the popup window close button
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow.dismiss();
                    Intent intent = new Intent(webView.getContext(), BlocklyLessonActivity.class);
                intent.putExtra("Student_ID", student_id);
                Integer module_id = db.getModuleID(lesson_id);

                Integer nextLessonID = db.getLessonID(module_id, lesson_number+1);
                intent.putExtra("Lesson_ID", nextLessonID);
                intent.putExtra("Lesson_Number", lesson_number + 1);
                intent.putExtra("Lesson_Instructions", db.getLessonInstructions(nextLessonID));
                webView.getContext().startActivity(intent);
                }
            });

            mPopupWindow.showAtLocation(activity.findViewById(R.id.blockly_rl), Gravity.CENTER,0,0);


    }



    @JavascriptInterface
    public void giveStars(int stars){
        DatabaseHelper db = new DatabaseHelper(webView.getContext());
        db.giveStars(student_id, lesson_id, stars);
    }




}
