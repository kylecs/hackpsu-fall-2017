package me.kylespencer.me.hackpsu;

import android.app.Application;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationService;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.app.Activity;

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

public class App extends Application{
    final String clientId = "78604ab4fa7213750bc51585f0e196962dfc6129baafd8fa4580bf7c5c852aa4";
    final String clientSecret = "2ca8e9cb884ef2b1f1d0c31a6cdf4e4d71eeaf4b56231f6c2d190b0ba4625f94";
    final String redirectUri = "bitpay://oauth-redirect";
    final int RC_AUTH = 100;

    boolean hasToken = false;
    String token = "";
    String account = "";
    Gson gson = new Gson();

    NfcAdapter mNfcAdapter;
    TextView textView;
    Boolean sender = false;
    OkHttpClient client = new OkHttpClient();
    String otherAddress = "";
    String address = "";
    String transferAmount = "";


    void getAccount() {
        Request request = new Request.Builder()
                .url("https://api.coinbase.com/v2/accounts")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();

            Log.d("Full Response: ", res);
            String parsedAccount = res.split("id\":\"")[1].split("\"")[0];
            Log.d("Account: ", parsedAccount);
            account = parsedAccount;

        }catch(Exception e) {
            //no
        }
    }

    void createAddress() {
        //https://api.coinbase.com/v2/accounts/:account_id/addresses
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("name", "temp")
                .build();

        Request request = new Request.Builder()
                .url("https://api.coinbase.com/v2/accounts/" + account + "/addresses")
                .method("POST", RequestBody.create(null, new byte[0]))
                .addHeader("Authorization", "Bearer " + token)
                .post(requestBody)
                .build();
        try {

            Response response = client.newCall(request).execute();
            String strResp = response.body().string();
            Log.d("addresses()", strResp);

            String addressParsed = strResp.split("address\":\"")[1].split("\"")[0];
            Log.d("new address ", addressParsed);
            address = addressParsed;

        } catch(Exception e) {
            Log.d("bad: ", e.toString());
        }
    }

    void phonySend() {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("type", "send")
                .addFormDataPart("to", otherAddress)
                .addFormDataPart("amount", "0.0001")
                .addFormDataPart("currency", "BTC")
                .build();

        Request request = new Request.Builder()
                .url("https://api.coinbase.com/v2/accounts/" + account + "/transactions")
                .method("POST", RequestBody.create(null, new byte[0]))
                .addHeader("Authorization", "Bearer " + token)
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.d("sendMoney()", response.body().string());


        } catch(Exception e) {
            Log.d("Error: ", e.toString());
        }
    }

    void sendMoney(String amount, String twoFactor) {
        Log.d("sendMoney()", "We are sending money");
        Log.d("str", amount);
        Log.d("My id: ", account);
        Log.d("Other address: ", otherAddress);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("type", "send")
                .addFormDataPart("to", otherAddress)
                .addFormDataPart("amount", amount)
                .addFormDataPart("currency", "BTC")
                .addFormDataPart("fee", ".0001")
                .build();

        Request request = new Request.Builder()
                .url("https://api.coinbase.com/v2/accounts/" + account + "/transactions")
                .method("POST", RequestBody.create(null, new byte[0]))
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("CB-2FA-TOKEN", twoFactor)
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.d("sendMoney()", response.body().string());
            transferAmount = amount;


        } catch(Exception e) {
            Log.d("Bad: ", e.toString());
        }
    }

}
