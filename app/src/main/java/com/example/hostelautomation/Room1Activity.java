package com.example.hostelautomation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Room1Activity extends AppCompatActivity {

    Button l1on, l1off, l2on, l2off, c1on, c1off;
    SeekBar seekBar;
    TextView seekText;
    EditText et1;
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room1);

        l1on = (Button) findViewById(R.id.l1on);
        l1off = (Button) findViewById(R.id.l1off);
        l2on = (Button) findViewById(R.id.l2on);
        l2off = (Button) findViewById(R.id.l2off);
        c1on = (Button) findViewById(R.id.l3on);
        c1off = (Button) findViewById(R.id.l3off);
        et1 = (EditText) findViewById(R.id.et1);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekText = (TextView) findViewById(R.id.seekText);

        web = (WebView) findViewById(R.id.web);

        final String s = et1.getText().toString();

        l1on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.loadUrl("http://" + s + "/R1L1on");
                Toast.makeText(getApplicationContext(), "Light 1 is ON", Toast.LENGTH_SHORT).show();
            }
        });

        l1off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.loadUrl("http://" + s + "/R1L1off");
                Toast.makeText(getApplicationContext(), "Light 1 is OFF", Toast.LENGTH_SHORT).show();
            }
        });

        l2on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.loadUrl("http://" + s + "/R1L2on");
                Toast.makeText(getApplicationContext(), "Light 2 is ON", Toast.LENGTH_SHORT).show();
            }
        });

        l2off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.loadUrl("http://" + s + "/R1L2off");
                Toast.makeText(getApplicationContext(), "Light 2 is OFF", Toast.LENGTH_SHORT).show();
            }
        });

        c1on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.loadUrl("http://" + s + "/R1C1on");
                Toast.makeText(getApplicationContext(), "Charging point is ON", Toast.LENGTH_SHORT).show();
            }
        });

        c1off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.loadUrl("http://" + s + "/R1C1off");
                Toast.makeText(getApplicationContext(), "Charging point is OFF", Toast.LENGTH_SHORT).show();
            }
        });

        seekText.setText("0");
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekText.setText(String.valueOf(progress));
                web.loadUrl("http://" + s + "/R1F1S" + progress);
                Toast.makeText(getApplicationContext(), "Fan speed set to " + progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
