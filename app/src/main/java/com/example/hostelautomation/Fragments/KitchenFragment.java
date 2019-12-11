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

public class KitchenFragment extends Fragment {

    EditText kb;
    Button b;

    private static final String P = "K_IP1";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_kitchen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        kb = view.findViewById(R.id.k_ip1);
        b = view.findViewById(R.id.k_set);

        SharedPreferences preferences = getActivity().getSharedPreferences(P, Context.MODE_PRIVATE);
        String ip = preferences.getString("k_ip1", "255.255.0");
        if(!ip.equals("") && !ip.equals("255.255.0")) {
            kb.setText(ip);
        }

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip1 = kb.getText().toString();

                SharedPreferences.Editor p1 = getActivity().getSharedPreferences(P, Context.MODE_PRIVATE).edit();
                p1.putString("k_ip1", ip1);
                p1.apply();

                Toast.makeText(getContext(), "IP changed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
