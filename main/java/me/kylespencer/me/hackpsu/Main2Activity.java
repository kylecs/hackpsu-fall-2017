package me.kylespencer.me.hackpsu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {


    App globalApp = null;
    Button btnConfirm;
    EditText amount;
    EditText twoFactor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);

        globalApp = (App) getApplicationContext();
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        amount = (EditText) findViewById(R.id.txtBtc);
        twoFactor = (EditText) findViewById(R.id.twoFactor);

        globalApp.phonySend();
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //send btc here
                globalApp.sendMoney(amount.getText().toString(), twoFactor.getText().toString());
                Intent myIntent = new Intent(Main2Activity.this, Main3Activity.class);
                startActivity(myIntent);
            }
        });

    }
}
