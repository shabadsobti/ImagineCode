package com.imaginecode.imaginecode;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class IntroModActivity extends AppCompatActivity {

    DatabaseHelper db;
    Integer module_id;
    Integer student_id;


    PendingIntent mPermissionIntent = null;
    UsbManager mUsbManager = null;

    private static final String ACTION_USB_PERMISSION = "com.imaginecode.USB_PERMISSION";

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            UsbDevice usbDevice = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            if (ACTION_USB_PERMISSION.equals(action)) {
                // Permission requested
                synchronized (this) {
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        // User has granted permission
                        // ... Setup your UsbDeviceConnection via mUsbManager.openDevice(usbDevice) ...
                    } else {
                        // User has denied permission
                    }
                }
            }
            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                Toast.makeText(context, "DISCONNECTED", Toast.LENGTH_LONG).show();
                synchronized (this) {
                    // ... Check to see if usbDevice is yours and cleanup ...
                }
            }
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                // Device attached
                Toast.makeText(context, "CONNECT", Toast.LENGTH_LONG).show();
                synchronized (this) {
                    // Qualify the new device to suit your needs and request permission
                    if ((usbDevice.getVendorId() == 9025) && (usbDevice.getProductId() == 67)) {
                        mUsbManager.requestPermission(usbDevice, mPermissionIntent);
                    }
                }
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(this);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_intro);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.top_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.headbar));
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        TextView toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(R.string.title_activity_levels);


        Intent intent = getIntent();
        module_id = intent.getIntExtra("Module_ID", 1);
        student_id = intent.getIntExtra("student_ID", 1);

        showGridTask task = new showGridTask();
        task.execute(student_id, module_id);

        if(module_id == 2){
            setupUSBDevice();

        }


    }



    public void setupUSBDevice(){
        mUsbManager = (UsbManager)getSystemService(Context.USB_SERVICE);

        // Register an intent filter so we can get permission to connect
        // to the device and get device attached/removed messages
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(mUsbReceiver, filter);
    }


    @Override
    protected void onResume() {
        super.onResume();

        showGridTask task = new showGridTask();
        task.execute(student_id, module_id);


    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
//        unregisterReceiver(mUsbReceiver);
    }






    private class showGridTask extends AsyncTask<Integer, Integer, LessonAdapter> {

        @Override
        protected LessonAdapter doInBackground(Integer... params) {
            ArrayList<LessonClass> lessons = db.getLessons(params[0], params[1]);
            publishProgress(1);
            return new LessonAdapter(getApplicationContext(), lessons, params[0], params[1]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d("LOADING", "loading");

        }

        @Override
        protected void onPostExecute(LessonAdapter result) {
            GridView gridView = findViewById(R.id.gridview);
            gridView.setAdapter(result);
            Log.d("FINAL", "success");
        }

    }



}
