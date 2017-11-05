package me.kylespencer.me.hackpsu;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static android.nfc.NdefRecord.createMime;
import android.util.Log;

public class Main4Activity extends Activity implements NfcAdapter.CreateNdefMessageCallback{
    App globalApp = null;
    NfcAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recieve);
        globalApp = (App) getApplicationContext();

        adapter = NfcAdapter.getDefaultAdapter(this);
        adapter.setNdefPushMessageCallback(this, this);

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        //Sending data
        //Recieving money
        Log.d("ASFD", "Creating Message");

        NdefMessage msg = new NdefMessage(
                new NdefRecord[] { createMime(
                        "application/vnd.com.example.android.beam", globalApp.address.getBytes())
                });
        //move to ack for nonsender
        Log.d("ASFD", "GOING HOME");

        Intent myIntent = new Intent(Main4Activity.this, MainActivity.class);
        startActivity(myIntent);


        return msg;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("FDSF", "onResume");

        if(getIntent().getAction() == null) return;
        Log.d("getIntent().getAction()", getIntent().getAction().toString());
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }

    }

    @Override
    public void onNewIntent(Intent intent) {

        // onResume gets called after this to handle the intent
        Log.d("FDSF", "onNewIntent");
        setIntent(intent);

    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        Log.d("ASFD", "Processing INtent");

        //textView.setText("processIntent");
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present

            globalApp.otherAddress = new String(msg.getRecords()[0].getPayload());
            Log.d("REcieved address", globalApp.otherAddress);
            Log.d("ASFD", "GOING TO MONEY SCREEN");
            Intent myIntent = new Intent(Main4Activity.this, Main2Activity.class);
            startActivity(myIntent);


    }
}
