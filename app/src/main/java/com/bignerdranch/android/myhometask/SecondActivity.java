package com.bignerdranch.android.myhometask;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private String[] strTmp = { "Первая строка", "Вторая строка", "Третья строка", "Четвертая строка", "Пятая строка"};
    private String MESSAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ListView strExampleList = (ListView) findViewById(R.id.list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, strTmp);

        strExampleList.setAdapter(adapter);

        final Intent intent = new Intent(this, MainActivity.class);

        strExampleList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                MESSAGE = strTmp[position];
                intent.putExtra("our_message", MESSAGE);
                startActivity(intent);
            }
        });
    }
}