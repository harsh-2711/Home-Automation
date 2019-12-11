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

public class ChangeNameFragment extends Fragment {

    private static final String PREF_NAME = "userName";
    EditText newName;
    Button go;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.change_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newName = (EditText) view.findViewById(R.id.new_name);
        go = (Button) view.findViewById(R.id.change_it);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newName.getText().toString();
                SharedPreferences preferences = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                String original_name = preferences.getString("user_name", "User");
                if(name.equals(original_name)) {
                    Toast.makeText(getContext(), "Enter new name", Toast.LENGTH_LONG).show();
                }
                else {
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
                    editor.putString("user_name", name);
                    editor.apply();

                    Toast.makeText(getContext(), "Username Changed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
