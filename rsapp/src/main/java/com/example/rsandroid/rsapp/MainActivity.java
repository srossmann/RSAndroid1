package com.example.rsandroid.rsapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentIntegrator integrator = new IntentIntegrator(this);
        //StartUDPServer();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    public void onClick_f1_1(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {




        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String barcode;
            String typ;

            barcode = scanResult.getContents();
            typ = scanResult.getFormatName();

            TextView etBarcode = (TextView) findViewById(R.id.textView);

            etBarcode.setText(barcode);

        }


    }

    public void onClick_f2_1(View view){

        send_data_Slave("CMD=SCHALT&DEV=1&ZUST=EIN", "192.168.0.23");
    }

    public void onClick_f3_1(View view){

        send_data_Slave("CMD=SCHALT&DEV=1&ZUST=AUS","192.168.0.23");
    }

    public void onClick_f4_1(View view){

        Intent myIntent = new Intent(view.getContext(), AufnahmeActivity.class);
        myIntent.putExtra("ID",1234);
        startActivityForResult(myIntent, 0);
    }


    public String send_data_Slave(String cmd, String IP) {
        String result = "";
        try {
            if (IP.length() > 0) {
                InetAddress ia = InetAddress.getByName(IP);
                int port = 4711;
                String s = cmd;
                byte[] data = s.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, ia, port);
                DatagramSocket toSocket = new DatagramSocket();
                toSocket.send(packet);
                //TextView tv = (TextView) findViewById(R.id.textView);
                //tv.setText("OK gesendet");
             }
        } catch (IOException e) {

            //TextView tv = (TextView) findViewById(R.id.textView);
            //tv.setText(e.getMessage());

        }
        return result;
    }

}
