package com.example.hostelautomation.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hostelautomation.Adapter.RoomsAdapter;
import com.example.hostelautomation.MainActivity;
import com.example.hostelautomation.R;
import com.example.hostelautomation.RoomActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment {

    private static final String PREFS_NAME = "userName";

    RecyclerView recyclerView;
    ArrayList<Pair<String, Integer>> list;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RoomsAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    View ChildView ;
    int RecyclerViewItemPosition ;

    TextView userName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        userName = (TextView) view.findViewById(R.id.user_name);

        SharedPreferences preferences = this.getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String name = preferences.getString("user_name", "User");
        userName.setText(name);

        recyclerView = (RecyclerView) view.findViewById(R.id.roomsRecycler);
        RecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // Adding items to recycler view
        list = new ArrayList<>();
        AddItemsToRecyclerView addItemsToRecyclerView = new AddItemsToRecyclerView();
        addItemsToRecyclerView.execute();

        RecyclerViewHorizontalAdapter = new RoomsAdapter(list);

        HorizontalLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);

        recyclerView.setAdapter(RecyclerViewHorizontalAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

                ChildView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting clicked value.
                    RecyclerViewItemPosition = recyclerView.getChildAdapterPosition(ChildView);

                    Intent intent = new Intent(getContext(), RoomActivity.class);
                    intent.putExtra("room_name", list.get(RecyclerViewItemPosition).first);
                    startActivity(intent);
                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });

    }


    private class AddItemsToRecyclerView extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            list.add(new Pair<String, Integer>("Living Room", R.drawable.living_room));
            list.add(new Pair<String, Integer>("Master Bedroom", R.drawable.master_bedroom));
            list.add(new Pair<String, Integer>("Bedroom", R.drawable.bedroom));
            list.add(new Pair<String, Integer>("Kitchen", R.drawable.kitchen));
            return null;
        }
    }
}
