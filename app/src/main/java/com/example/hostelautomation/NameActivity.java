package com.example.hostelautomation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NameActivity extends AppCompatActivity {

    private static final String PREF_NAME = "userName";
    EditText userName;
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        userName = (EditText) findViewById(R.id.user_name);
        go = (Button) findViewById(R.id.go);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString();

                SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit();
                editor.putString("user_name", name);
                editor.apply();

                Intent intent = new Intent(NameActivity.this, NavigationDrawer.class);
                startActivity(intent);
            }
        });

        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        if(preferences.contains("user_name")) {
            Intent intent = new Intent(NameActivity.this, NavigationDrawer.class);
            startActivity(intent);
            finish();
        }

    }
}
