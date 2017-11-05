package me.kylespencer.me.hackpsu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    App globalApp = null;
    TextView total;
    TextView address;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete);

        globalApp = (App) getApplicationContext();
        total = (TextView) findViewById(R.id.total);
        address = (TextView) findViewById(R.id.address);
        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(Main3Activity.this, Main22Activity.class);
                startActivity(myIntent);
            }
        });

        address.setText(globalApp.otherAddress);
        total.setText(globalApp.transferAmount);


    }
}
