package com.bignerdranch.android.myhometask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

// TODO: 4/12/21 Counter is increasing even if we rotate the screen
//  When switching orientation to landscape - layout is broken
public class MainActivity extends AppCompatActivity {

    // TODO: 4/12/21 constants should be declared as `private static final UPPER_CASE_NAME = "value"` 
    private final String TAG = "States";
    // TODO: 4/12/21 follow selected codestyle. If you chose to go with `m` prefix - then apply this prefix to all private fields.
    //  OR don't apply it at all
    private TextView ourText;
    // TODO: 4/12/21 I suggest you to use some prefixes for views, e.g.: tvReceivedItem, btnSomeAction, etc.
    //  It will be easier to read and navigate
    private TextView hiddenText;
    private TextView mShowCountTextView;
    private SharedPreferences mPreferences;
    // TODO: 4/12/21 should be a constant.
    private String sharedPrefFile = "com.bignerdranch.android.myhometask";
    private int mCount = 0;
    // TODO: 4/12/21 Constants should be at the top of a class file (where TAG is declared).
    //  Also I suggest you to put "KEY" and similar prefixes before the actual name (will be easier to navigate).
    //  E.g. KEY_COUNT, EXTRA_RESULT, etc.
    private final String COUNT_KEY = "count";


    // TODO: 4/12/21 Keep onCreate as simple as possible. Keep in mind that one method should (ideally) do one thing
    //  You can split code in onCreate to different methods, e.g.: initUi(bind views), handleIntent(), etc.
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
        // TODO: 4/12/21 "our_message" is hardcoded. Avoid hardcode and move it to constants (you repeat this text on SecondActivity as well)
        String message = intent.getStringExtra("our_message");
        ourText = (TextView) findViewById(R.id.our_text);
        ourText.setText(message);

        // TODO: 4/12/21 Why intent2 is needed if you already declared intent above?
        //  This intent handling should be moved to a separate method to keep things clean in onCreate
        Intent intent2 = getIntent();
        String action = intent2.getAction();
        String type = intent2.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedText = intent2.getStringExtra(Intent.EXTRA_TEXT);
                // TODO: 4/12/21 I suggest you to use prefixes for ids as well. E.g. tv_selected_item.
                //  It will simplify navigation and code readability
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

    // TODO: 4/12/21 No need to override empty methods
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