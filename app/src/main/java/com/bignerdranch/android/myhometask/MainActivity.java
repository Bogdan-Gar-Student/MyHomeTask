package com.bignerdranch.android.myhometask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "States";
    private TextView ourText;
    private TextView hiddenText;
    private TextView mShowCountTextView;
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.bignerdranch.android.myhometask";
    private int mCount = 0;
    private final String COUNT_KEY = "count";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "MainActivity: onCreate()");

        mShowCountTextView = findViewById(R.id.show_count);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mCount = mPreferences.getInt(COUNT_KEY, 0);
        mCount++;
        mShowCountTextView.setText(String.format("%s", mCount));

        Intent intent = getIntent();
        String message = intent.getStringExtra("our_message");
        ourText = (TextView) findViewById(R.id.our_text);
        ourText.setText(message);

        Intent intent2 = getIntent();
        String action = intent2.getAction();
        String type = intent2.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent2.getStringExtra(Intent.EXTRA_TEXT);
                hiddenText = (TextView) findViewById(R.id.hidden_text);
                hiddenText.setVisibility(View.VISIBLE);
                hiddenText.setText(sharedText);
            }
        }
    }

    public void chooseButton(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void shareButton(View view) {
        String tmpStr = ourText.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(R.string.share_button)
                .setText(tmpStr)
                .startChooser();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(COUNT_KEY, mCount);
        preferencesEditor.apply();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}