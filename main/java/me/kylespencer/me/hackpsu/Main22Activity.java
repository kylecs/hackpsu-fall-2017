package me.kylespencer.me.hackpsu;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import static android.nfc.NdefRecord.createMime;

public class Main22Activity extends AppCompatActivity {

    App globalApp = null;

    Button btnSend;
    Button btnRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_receive);

        globalApp = (App) getApplicationContext();

        btnSend = (Button) findViewById(R.id.btnSend);
        btnRec = (Button) findViewById(R.id.btnRec);


        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                globalApp.sender = true;
                done();
            }
        });
        btnRec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                globalApp.sender = false;
                done();

            }
        });
    }

    void done() {
        Intent myIntent = new Intent(Main22Activity.this, Main4Activity.class);
        startActivity(myIntent);
    }

}
