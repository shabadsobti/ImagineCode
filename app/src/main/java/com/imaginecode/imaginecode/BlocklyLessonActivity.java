/*
 *  Copyright 2015 Google Inc. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.imaginecode.imaginecode;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.VoiceInteractor;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.blockly.model.DefaultBlocks;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.physicaloid.lib.Boards;
import com.physicaloid.lib.Physicaloid;
import com.physicaloid.lib.Physicaloid.UploadCallBack;
import com.physicaloid.lib.fpga.PhysicaloidFpga;
import com.physicaloid.lib.programmer.avr.UploadErrors;
import com.physicaloid.lib.usb.driver.uart.ReadLisener;




/**
 * Demo activity that programmatically adds a view to split the screen between the Blockly workspace
 * and an arbitrary other view or fragment.
 */
public class BlocklyLessonActivity extends AbstractBlocklyActivity implements ViewPager.OnPageChangeListener {
    private static final int INTRO_MODULE_ID = 1;
    private static final int ARDUINO_MODULE_ID = 2;

    private static final String TAG = "SplitActivity";
    private WebView webView;
    private ViewPager viewPager;
    private JsHandler _jsHandler;
    private static final String SAVE_FILENAME = "split_workspace.xml";
    private static final String AUTOSAVE_FILENAME = "split_workspace_temp.xml";
    private static final List<String> BLOCK_DEFINITIONS = DefaultBlocks.getAllBlockDefinitions();
    private static final List<String> JAVASCRIPT_GENERATORS = Arrays.asList(
            // Custom block generators go here. Default blocks are already included.
            // TODO(#99): Include Javascript defaults when other languages are supported.
    );
    static final List<String> TURTLE_BLOCK_DEFINITIONS = Arrays.asList(
            DefaultBlocks.COLOR_BLOCKS_PATH,
            DefaultBlocks.LOGIC_BLOCKS_PATH,
            DefaultBlocks.LOOP_BLOCKS_PATH,
            DefaultBlocks.MATH_BLOCKS_PATH,
            DefaultBlocks.TEXT_BLOCKS_PATH,
            DefaultBlocks.VARIABLE_BLOCKS_PATH,
            "turtle_blocks.json"
    );

    private TextView mGeneratedTextView;
    private Handler mHandler;

    private String mNoCodeText;

    private Integer module_id = 1;

    String[] instruction;
    int[] project_image;

    ViewPagerAdapter adapter;

    TextView instructions;

    Physicaloid mPhysicaloid;


    private static final String ACTION_USB_PERMISSION  = "android.imaginecode.USB_PERMISSION";
    private static final String ACTION_USB_ATTACHED  = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    private static final String ACTION_USB_DETACHED  = "android.hardware.usb.action.USB_DEVICE_DETACHED";



    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    






    CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
            new CodeGenerationRequest.CodeGeneratorCallback() {
                @Override
                public void onFinishCodeGeneration(final String generatedCode) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //mGeneratedTextView.setText(generatedCode);
                            //updateTextMinWidth();
                            Log.e("Generated code", generatedCode);
                            if (module_id == 1){
                                _jsHandler.javaFnCall(generatedCode);

                            }
                            else{


                                RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
                                String url = "http://178.128.14.91/upload.php";

                                StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(BlocklyLessonActivity.this, response, Toast.LENGTH_SHORT).show();
                                        new DownloadFileFromURL().execute(response);





                                    }
                                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //This code is executed if there is an error.
                                    }
                                }) {
                                    protected Map<String, String> getParams() {
                                        Map<String, String> MyData = new HashMap<String, String>();
                                        MyData.put("ino_code", "void setup() {\n" +
                                                " \n" +
                                                "  pinMode(13, OUTPUT);\n" +
                                                "}\n" +
                                                "\n" +
                                                "void loop() {\n" +
                                                "\n" +
                                                "  digitalWrite(13, HIGH);\n" +
                                                "  delay(1000);\n" +
                                                "  digitalWrite(13, LOW);\n" +
                                                "  delay(1000);\n" +
                                                "}\n"); //Add the data you'd like to send to the server.
                                        MyData.put("student_id", "15"); //Add the data you'd like to send to the server.
                                        MyData.put("lesson_id", "23"); //Add the data you'd like to send to the server.
                                        return MyData;
                                    }
                                };


                                MyRequestQueue.add(MyStringRequest);

                            }

                        }
                    });
                }
            };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }





    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }



        @Override
        protected String doInBackground(String... f_url) {




            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                // Output stream to write file
                OutputStream output = new FileOutputStream(getFilesDir()  + "/work.hex");


                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(""+(int)((total*100)/lenghtOfFile));

                    // writing data to file

                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            final String UPLOAD_FILE_UNO = getFilesDir()  + "/work.hex";
            mPhysicaloid = new Physicaloid(getApplicationContext());
            mPhysicaloid.upload(Boards.ARDUINO_UNO, UPLOAD_FILE_UNO, mUploadCallback);

            return null;


        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            Log.d("PROGRESS: ", progress[0]);
        }



        @Override
        protected void onPostExecute(String file_url) {
            Log.d("FILE URL", "DONE");






        }

    }


    UploadCallBack mUploadCallback = new UploadCallBack() {

        @Override
        public void onUploading(int value) {
            Log.d("ERROR", "ERROR");

        }

        @Override
        public void onPreUpload() {

        }

        @Override
        public void onPostUpload(boolean success) {
            if(success) {

            } else {

            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(UploadErrors err) {

        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bara
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Integer lesson_id = intent.getIntExtra("Lesson_ID", 1);

        Integer lesson_number = intent.getIntExtra("Lesson_Number", 1);
        String lesson_instructions = intent.getStringExtra("Lesson_Instructions");
        Integer student_id = intent.getIntExtra("Student_ID", 1);
        Log.d("Student_ID: ", student_id.toString());



        verifyStoragePermissions(this);






        instructions = findViewById(R.id.instructions);

    if(module_id == INTRO_MODULE_ID){
        instructions.setText(lesson_instructions);
    }

    else {
        DatabaseHelper db = new DatabaseHelper(this);
        project_image = db.getGraphics(lesson_id);
        instruction = lesson_instructions.split(",");
        instructions.setText(instruction[0]);
    }








        Toolbar myToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.headbar));
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        TextView toolbar_title = findViewById(R.id.toolbar_title);
        Resources res = getResources();
        String text = res.getString(R.string.title_activity_blockly, lesson_number.toString());

        toolbar_title.setText(text);

        mHandler = new Handler();
        mHandler = new Handler();
        if(module_id == INTRO_MODULE_ID) {
            initWebView(lesson_id, student_id, lesson_number, lesson_instructions);
        }
        if(module_id == ARDUINO_MODULE_ID){
            initViewPager();
        }
    }

    @Override
    public void onResume(){
        super.onResume();


    }

    @Override
    protected View onCreateContentView(int parentId) {
        Intent intent = getIntent();
        module_id = intent.getIntExtra("Module_ID", 1);

        View root;
        if(module_id == ARDUINO_MODULE_ID){
            root = getLayoutInflater().inflate(R.layout.activity_blockly_arduino, null);
        }
        else{
            root = getLayoutInflater().inflate(R.layout.activity_blockly, null);
        }


        //mGeneratedTextView = (TextView) root.findViewById(R.id.generated_code);
        //updateTextMinWidth();

        //mNoCodeText = mGeneratedTextView.getText().toString(); // Capture initial value.

        return root;
    }



    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        return TURTLE_BLOCK_DEFINITIONS;
    }

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "toolbox_advanced.xml";
    }

    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        List<String> paths = new ArrayList<String>(1);
        paths.add("generators.js");
        return paths;
    }

    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        // Uses the same callback for every generation call.
        return mCodeGeneratorCallback;
    }

    @Override
    public void onClearWorkspace() {
        super.onClearWorkspace();
        //mGeneratedTextView.setText(mNoCodeText);
        //updateTextMinWidth();
    }

    /**
     * Estimate the pixel size of the longest line of text, and set that to the TextView's minimum
     * width.
     */
//    private void updateTextMinWidth() {
//        String text = mGeneratedTextView.getText().toString();
//        int maxline = 0;
//        int start = 0;
//        int index = text.indexOf('\n', start);
//        while (index > 0) {
//            maxline = Math.max(maxline, index - start);
//            start = index + 1;
//            index = text.indexOf('\n', start);
//        }
//        int remainder = text.length() - start;
//        if (remainder > 0) {
//            maxline = Math.max(maxline, remainder);
//        }
//
//        float density = getResources().getDisplayMetrics().density;
//        mGeneratedTextView.setMinWidth((int) (maxline * 13 * density));
//    }

    /**
     * Optional override of the save path, since this demo Activity has multiple Blockly
     * configurations.
     * @return Workspace save path used by this Activity.
     */
    @Override
    @NonNull
    protected String getWorkspaceSavePath() {
        return SAVE_FILENAME;
    }

    /**
     * Optional override of the auto-save path, since this demo Activity has multiple Blockly
     * configurations.
     * @return Workspace auto-save path used by this Activity.
     */
    @Override
    @NonNull
    protected String getWorkspaceAutosavePath() {
        return AUTOSAVE_FILENAME;
    }

    private void initWebView(Integer lesson_id, Integer student_id, Integer lesson_number, String lesson_instructions){

        webView = findViewById(R.id.webview);
        //Tell the WebView to enable javascript execution.
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setBackgroundColor(Color.parseColor("#808080"));

        //Set whether the DOM storage API is enabled.
        webView.getSettings().setDomStorageEnabled(true);

        //setBuiltInZoomControls = false, removes +/- controls on screen
        webView.getSettings().setBuiltInZoomControls(false);

        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        webView.getSettings().setAllowFileAccess(true);

        webView.getSettings().setAppCacheMaxSize(1024 * 8);
        webView.getSettings().setAppCacheEnabled(true);

        _jsHandler = new JsHandler(this, webView, lesson_id, student_id, lesson_number, lesson_instructions);
        webView.addJavascriptInterface(_jsHandler, "JsHandler");



        webView.getSettings().setUseWideViewPort(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                //Toast.makeText(TableContentsWithDisplay.this, "url "+url, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //Toast.makeText(TableContentsWithDisplay.this, "Width " + view.getWidth() +" *** " + "Height " + view.getHeight(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                super.onReceivedSslError(view, handler, error);
                //Toast.makeText(TableContentsWithDisplay.this, "error "+error, Toast.LENGTH_SHORT).show();

            }
        });

        // these settings speed up page load into the webview

        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.requestFocus(View.FOCUS_DOWN);
        // load the main.html file that kept in assets folder
        webView.loadUrl("file:///android_asset/game/index.html");

    }

    private void initViewPager(){






        viewPager = findViewById(R.id.viewPager);

        viewPager.addOnPageChangeListener(this);

        adapter = new ViewPagerAdapter(this, instruction, project_image);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);




    }

    @Override
    public void onPageScrollStateChanged(int position) {




    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }


    @Override
    public void onPageSelected(int position) {
        instructions.setText(instruction[position]);


    }


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }





}
