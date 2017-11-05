package me.kylespencer.me.hackpsu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.TextView;

import android.os.StrictMode;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import retrofit2.Retrofit;
import net.openid.appauth.*;
import java.util.Map;
import java.util.HashMap;
import okhttp3.*;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import static android.nfc.NdefRecord.createMime;
import android.os.Parcelable;
import android.widget.Button;
import android.view.Window;

public class MainActivity extends AppCompatActivity{
    Button login;

    App globalApp = null;
    AuthorizationService authService = null;
    AuthorizationRequest authRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        globalApp = (App) getApplicationContext();

        login = (Button) findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doAuthorization(authRequest);
            }
        });

        AuthorizationServiceConfiguration serviceConfig =
                new AuthorizationServiceConfiguration(
                        Uri.parse("https://www.coinbase.com/oauth/authorize"), // authorization endpoint
                        Uri.parse("https://www.coinbase.com/oauth/token")); // token endpoint

        AuthorizationRequest.Builder authRequestBuilder =
                new AuthorizationRequest.Builder(
                        serviceConfig,
                        globalApp.clientId,
                        ResponseTypeValues.CODE,
                        Uri.parse(globalApp.redirectUri))
                    .setScope("wallet:accounts:read,wallet:addresses:read,wallet:transactions:send,wallet:addresses:create,wallet:transactions:send");
        Map<String, String> params= new HashMap<String, String>();
        params.put("meta[send_limit_amount]", "1");
        params.put("meta[send_limit_currency]", "USD");
        params.put("meta[send_limit_period]", "day");

        authRequest = authRequestBuilder.setAdditionalParameters(params).build();
    }
    private void doAuthorization(AuthorizationRequest authRequest) {
        authService = new AuthorizationService(this);
        Intent authIntent = authService.getAuthorizationRequestIntent(authRequest);
        startActivityForResult(authIntent, globalApp.RC_AUTH);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        authService.dispose();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        Log.d("Activity Result: " , String.valueOf(resultCode));
        Log.d("asdf", "adsf");
        String code = data.getData().toString().split("code=")[1].split("&")[0];
        Log.d("Data: ", ".: " + code);
        //pls god let that work
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("grant_type", "authorization_code")
                .addFormDataPart("code", code)
                .addFormDataPart("client_id", globalApp.clientId)
                .addFormDataPart("client_secret", globalApp.clientSecret)
                .addFormDataPart("redirect_uri", globalApp.redirectUri)
                .build();

        Request request = new Request.Builder()
                .url("https://www.coinbase.com/oauth/token")
                .method("POST", RequestBody.create(null, new byte[0]))
                .post(requestBody)
                .build();
        try {
            Response response = globalApp.client.newCall(request).execute();
            String parsedToken = response.body().string().split("token\":\"")[1].split("\"")[0];
            Log.d("Response: ", parsedToken);
            globalApp.hasToken = true;
            globalApp.token = parsedToken;
            Intent myIntent = new Intent(MainActivity.this, Main22Activity.class);
            startActivity(myIntent);
            //testing
            globalApp.getAccount();
            globalApp.createAddress();

        } catch(Exception e) {
            Log.d("Fuck: ", e.toString());
        }
    }


}
