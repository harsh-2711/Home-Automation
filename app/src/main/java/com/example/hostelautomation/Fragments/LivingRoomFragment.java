package com.example.hostelautomation.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hostelautomation.R;

public class LivingRoomFragment extends Fragment {

    EditText lr1, lr2;
    Button b;

    private static final String P1 = "LR_IP1";
    private static final String P2 = "LR_IP2";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_living_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lr1 = view.findViewById(R.id.lr_ip1);
        lr2 = view.findViewById(R.id.lr_ip2);
        b = view.findViewById(R.id.lr_set);

        SharedPreferences preferences = getActivity().getSharedPreferences(P1, Context.MODE_PRIVATE);
        String ip = preferences.getString("lr_ip1", "255.255.0");
        if(!ip.equals("") && !ip.equals("255.255.0")) {
            lr1.setText(ip);
        }

        SharedPreferences preferences1 = getActivity().getSharedPreferences(P1, Context.MODE_PRIVATE);
        String ip2 = preferences1.getString("lr_ip2", "255.255.0");
        if(!ip2.equals("") && !ip2.equals("255.255.0")) {
            lr2.setText(ip2);
        }


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip1 = lr1.getText().toString();
                String ip2 = lr2.getText().toString();

                SharedPreferences.Editor p1 = getActivity().getSharedPreferences(P1, Context.MODE_PRIVATE).edit();
                p1.putString("lr_ip1", ip1);
                p1.apply();

                SharedPreferences.Editor p2 = getActivity().getSharedPreferences(P1, Context.MODE_PRIVATE).edit();
                p2.putString("lr_ip2", ip2);
                p2.apply();

                Toast.makeText(getContext(), "IP changed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}