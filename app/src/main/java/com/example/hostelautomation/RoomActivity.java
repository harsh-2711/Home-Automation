package com.example.hostelautomation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.hostelautomation.Adapter.ApplianceAdapter;
import com.example.hostelautomation.Dialog.CustomDialog;

import java.util.ArrayList;

public class RoomActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "AppliancesStates";
    private static final String ROOM_NAME = "Room";
    private static final String SEPERATOR = "/";
    private static final String PREFS_FAN = "fan_selected";

    private static final String LIVING_ROOM_1 = "LR_IP1";
    private static final String MASTER_BEDROOM = "MB_IP1";
    private static final String BEDROOM = "B_IP1";
    private static final String KITCHEN = "K_IP1";

    private static final Integer TV = 1;
    private static final Integer FAN = 2;
    private static final Integer LIGHT = 3;
    private static final Integer CHANDELIER = 4;
    private static final Integer SOCKET = 5;

    private float mDownX;
    private float mDownY;
    private final float SCROLL_THRESHOLD = 10;
    private boolean isOnClick;

    RecyclerView recyclerView;
    ArrayList<Pair<Pair<Pair<String, Integer>, String>, Integer>> list;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    ApplianceAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    View ChildView ;
    int RecyclerViewItemPosition ;

    String ip = "255.255.255.0";

    ImageView back;
    RelativeLayout roomRelativeLayout;
    TextView roomName;
    String room;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        recyclerView = (RecyclerView) findViewById(R.id.roomAppliancesRecycler);
        back = (ImageView) findViewById(R.id.back_arrow);
        roomRelativeLayout = (RelativeLayout) findViewById(R.id.roomRelativeLayout);
        roomName = (TextView) findViewById(R.id.room);
        webView = (WebView) findViewById(R.id.webView);

        Intent intent = getIntent();
        room = intent.getStringExtra("room_name");
        roomName.setText(room);

        switch (room) {
            case "Living Room":
                SharedPreferences preferences1 = getSharedPreferences(LIVING_ROOM_1, MODE_PRIVATE);
                ip = preferences1.getString("lr_ip2", "255.255.0");
                break;
            case "Master Bedroom":
                SharedPreferences preferences3 = getSharedPreferences(MASTER_BEDROOM, MODE_PRIVATE);
                ip = preferences3.getString("mb_ip1", "255.255.0");
                break;
            case "Bedroom":
                SharedPreferences preferences4 = getSharedPreferences(BEDROOM, MODE_PRIVATE);
                ip = preferences4.getString("b_ip1", "255.255.0");
                break;
            case "Kitchen":
                SharedPreferences preferences5 = getSharedPreferences(KITCHEN, MODE_PRIVATE);
                ip = preferences5.getString("k_ip1", "255.255.0");
                break;
            default:
                break;
        }
        setBackground(room);

        final SharedPreferences.Editor editor = getSharedPreferences(ROOM_NAME, MODE_PRIVATE).edit();
        editor.putString("room_name", room);
        editor.apply();

        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // Adding items to recycler view
        list = new ArrayList<>();
        LoadAppliances loadAppliances = new LoadAppliances();
        loadAppliances.execute(room);

        RecyclerViewHorizontalAdapter = new ApplianceAdapter(list);

        HorizontalLayout = new LinearLayoutManager(RoomActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);

        recyclerView.setAdapter(RecyclerViewHorizontalAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(RoomActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }

                @Override  public void onLongPress(MotionEvent e) {
                    return;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

                ChildView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting clicked value.
                    RecyclerViewItemPosition = recyclerView.getChildAdapterPosition(ChildView);
                    String code = list.get(RecyclerViewItemPosition).first.second;

                    SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

                    if(preferences.getBoolean(code, false)) {
                        // Appliance is ON
                        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putBoolean(code, false);
                        editor.apply();

                        if(room.equals("Living Room")) {
                            String name = list.get(RecyclerViewItemPosition).first.first.first;
                            if(name.equals("TV") || name.equals("Chandelier") || name.equals("Light") || name.equals("Fan 1")) {
                                SharedPreferences preferences20 = getSharedPreferences(LIVING_ROOM_1, MODE_PRIVATE);
                                ip = preferences20.getString("lr_ip1", "255.255.0");
                            }
                            else {
                                SharedPreferences preferences21 = getSharedPreferences(LIVING_ROOM_1, MODE_PRIVATE);
                                ip = preferences21.getString("lr_ip2", "255.255.0");
                            }
                        }
                        webView.loadUrl("http://" + ip + SEPERATOR + code + "0");
                        //Toast.makeText(getApplicationContext(), ip, Toast.LENGTH_LONG).show();

                        String appl_name = list.get(RecyclerViewItemPosition).first.first.first;
                        String appl_code = list.get(RecyclerViewItemPosition).first.second;

                        switch (list.get(RecyclerViewItemPosition).second) {
                            case 1:
                                list.add(RecyclerViewItemPosition, new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(appl_name, R.drawable.tv_off), appl_code), TV));
                                break;
                            case 2:
                                list.add(RecyclerViewItemPosition, new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(appl_name, R.drawable.fan_off), appl_code), FAN));
                                break;
                            case 3:
                                list.add(RecyclerViewItemPosition, new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(appl_name, R.drawable.light_bulb_off), appl_code), LIGHT));
                                break;
                            case 4:
                                list.add(RecyclerViewItemPosition, new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(appl_name, R.drawable.chandelier_off), appl_code), CHANDELIER));
                                break;
                            case 5:
                                list.add(RecyclerViewItemPosition, new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(appl_name, R.drawable.socket_off), appl_code), SOCKET));
                                break;
                            default:
                                break;
                        }

                        list.remove(RecyclerViewItemPosition+1);
                        RecyclerViewHorizontalAdapter.notifyDataSetChanged();
                    }
                    else {
                        // Appliance is OFF
                        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putBoolean(code, true);
                        editor.apply();

                        if(room.equals("Living Room")) {
                            String name = list.get(RecyclerViewItemPosition).first.first.first;
                            if(name.equals("TV") || name.equals("Chandelier") || name.equals("Light") || name.equals("Fan 1")) {
                                SharedPreferences preferences20 = getSharedPreferences(LIVING_ROOM_1, MODE_PRIVATE);
                                ip = preferences20.getString("lr_ip1", "255.255.0");
                            }
                            else {
                                SharedPreferences preferences21 = getSharedPreferences(LIVING_ROOM_1, MODE_PRIVATE);
                                ip = preferences21.getString("lr_ip2", "255.255.0");
                            }
                        }
                        webView.loadUrl("http://" + ip + SEPERATOR + code + "1");
                        //Toast.makeText(getApplicationContext(), ip, Toast.LENGTH_LONG).show();

                        String appl_name = list.get(RecyclerViewItemPosition).first.first.first;
                        String appl_code = list.get(RecyclerViewItemPosition).first.second;

                        switch (list.get(RecyclerViewItemPosition).second) {
                            case 1:
                                list.add(RecyclerViewItemPosition, new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(appl_name, R.drawable.tv_on), appl_code), TV));
                                break;
                            case 2:
                                list.add(RecyclerViewItemPosition, new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(appl_name, R.drawable.fan_on), appl_code), FAN));
                                break;
                            case 3:
                                list.add(RecyclerViewItemPosition, new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(appl_name, R.drawable.light_bulb_on), appl_code), LIGHT));
                                break;
                            case 4:
                                list.add(RecyclerViewItemPosition, new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(appl_name, R.drawable.chandelier_on), appl_code), CHANDELIER));
                                break;
                            case 5:
                                list.add(RecyclerViewItemPosition, new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(appl_name, R.drawable.socket_on), appl_code), SOCKET));
                                break;
                            default:
                                break;
                        }

                        list.remove(RecyclerViewItemPosition+1);
                        RecyclerViewHorizontalAdapter.notifyDataSetChanged();
                    }
                }
                else {
                    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            mDownX = motionEvent.getX();
                            mDownY = motionEvent.getY();
                            isOnClick = true;
                            break;
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_UP:
                            if (isOnClick) {
                                RecyclerViewItemPosition = recyclerView.getChildAdapterPosition(ChildView);
                                Integer type = list.get(RecyclerViewItemPosition).second;
                                if(type == 2) {
                                    if(room.equals("Living Room")) {
                                        SharedPreferences.Editor editor1 = getSharedPreferences(PREFS_FAN, MODE_PRIVATE).edit();
                                        editor1.putString("fan_selected", list.get(RecyclerViewItemPosition).first.first.first);
                                        editor1.apply();
                                    }
                                    CustomDialog customDialog = new CustomDialog(RoomActivity.this);
                                    customDialog.show();
                                    customDialog.setCanceledOnTouchOutside(false);
                                }
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (isOnClick && (Math.abs(mDownX - motionEvent.getX()) > SCROLL_THRESHOLD || Math.abs(mDownY - motionEvent.getY()) > SCROLL_THRESHOLD)) {
                                isOnClick = false;
                            }
                            break;
                        default:
                            break;
                    }
                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent event) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class LoadAppliances extends AsyncTask<String,Void,Void> {
        @Override
        protected Void doInBackground(String... strings) {
            switch (strings[0]) {
                case "Living Room":
                    addTV("TV", "htv");
                    addChandelier("Chandelier", "hc");
                    addLight("Light", "hl");
                    addFan("Fan 1", "hf");
                    addFan("Fan 2", "hf");
                    addLight("LED 1", "hl1");
                    addLight("LED 2", "hl2");
                    addLight("LED 3", "hl3");
                    addLight("LED 4", "hl4");
                    addSocket("Charging Plug", "hc");
                    break;
                case "Kitchen":
                    addSocket("RO", "kro");
                    addLight("Light", "kl1");
                    addFan("Fan", "kf1");
                    addSocket("Charging Plug", "kc1");
                    break;
                case "Master Bedroom":
                    addFan("Fan", "r1f1");
                    addLight("Light 1", "r1l1");
                    addLight("Light 2", "r1l2");
                    addSocket("Charging Plug", "r1c1");
                    break;
                case "Bedroom":
                    addFan("Fan", "r2f1");
                    addLight("Light", "r2l1");
                    addLight("Bathroom Light", "r1l2");
                    addSocket("Charging Plug", "r2c1");
                    break;
                default:
                    addTV("TV", "htv");
                    addFan("Fan", "hf1");
                    addLight("Light", "hl1");
                    addSocket("Socket", "hc1");
                    break;
            }

            return null;
        }
    }

    public void addTV(String name, String code) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if(preferences.getBoolean(code, false))
            list.add(new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(name, R.drawable.tv_on), code),TV));
        else
            list.add(new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(name, R.drawable.tv_off), code), TV));
    }

    public void addFan(String name, String code) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if(preferences.getBoolean(code, false))
            list.add(new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(name, R.drawable.fan_on), code), FAN));
        else
            list.add(new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(name, R.drawable.fan_off), code), FAN));
    }

    public void addLight(String name, String code) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if(preferences.getBoolean(code, false))
            list.add(new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(name, R.drawable.light_bulb_on), code), LIGHT));
        else
            list.add(new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(name, R.drawable.light_bulb_off), code), LIGHT));
    }

    public void addChandelier(String name, String code) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if(preferences.getBoolean(code, false))
            list.add(new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(name, R.drawable.chandelier_on), code), CHANDELIER));
        else
            list.add(new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(name, R.drawable.chandelier_off), code), CHANDELIER));
    }

    public void addSocket(String name, String code) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if(preferences.getBoolean(code, false))
            list.add(new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(name, R.drawable.socket_on), code), SOCKET));
        else
            list.add(new Pair<Pair<Pair<String, Integer>, String>, Integer>(new Pair<Pair<String, Integer>, String>(new Pair<String, Integer>(name, R.drawable.socket_off), code), SOCKET));
    }

    private void setBackground(String roomname) {
        switch(roomname) {
            case "Living Room":
                Glide.with(this).load(R.drawable.living_room_portrait).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            roomRelativeLayout.setBackground(resource);
                        }
                    }
                });
                break;
            case "Master Bedroom":
                Glide.with(this).load(R.drawable.master_bedroom_portrait).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            roomRelativeLayout.setBackground(resource);
                        }
                    }
                });
                break;
            case "Bedroom":
                Glide.with(this).load(R.drawable.bedroom_portrait).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            roomRelativeLayout.setBackground(resource);
                        }
                    }
                });
                break;
            case "Kitchen":
                Glide.with(this).load(R.drawable.kitchen_portrait).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            roomRelativeLayout.setBackground(resource);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
