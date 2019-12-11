package com.example.hostelautomation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hostelautomation.Fragments.BedroomFragment;
import com.example.hostelautomation.Fragments.ChangeNameFragment;
import com.example.hostelautomation.Fragments.KitchenFragment;
import com.example.hostelautomation.Fragments.LivingRoomFragment;
import com.example.hostelautomation.Fragments.MainFragment;
import com.example.hostelautomation.Fragments.MasterBedroomFragment;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawer extends AppCompatActivity {

    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        sNavigationDrawer = findViewById(R.id.navigationDrawer);

        List<MenuItem> menuItems = new ArrayList<>();

        //Use the MenuItem given by this library and not the default one.
        //First parameter is the title of the menu item and then the second parameter is the image which will be the background of the menu item.

        menuItems.add(new MenuItem("Home",R.drawable.news_bg));
        menuItems.add(new MenuItem("Living Room",R.drawable.news_bg));
        menuItems.add(new MenuItem("Master Bedroom",R.drawable.feed_bg));
        menuItems.add(new MenuItem("Bedroom",R.drawable.message_bg));
        menuItems.add(new MenuItem("Kitchen",R.drawable.music_bg));
        menuItems.add(new MenuItem("Change username",R.drawable.music_bg));

        sNavigationDrawer.setMenuItemList(menuItems);
        sNavigationDrawer.setAppbarTitleTV("Home");
        fragmentClass =  MainFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }

        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {

                switch (position){
                    case 0:{
                        fragmentClass = MainFragment.class;
                        break;
                    }
                    case 1:{
                        fragmentClass = LivingRoomFragment.class;
                        break;
                    }
                    case 2:{
                        fragmentClass = MasterBedroomFragment.class;
                        break;
                    }
                    case 3:{
                        fragmentClass = BedroomFragment.class;
                        break;
                    }
                    case 4: {
                        fragmentClass = KitchenFragment.class;
                        break;
                    }
                    case 5: {
                        fragmentClass = ChangeNameFragment.class;
                        break;
                    }

                }

                //Listener for drawer events such as opening and closing.
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening(){

                    }

                    @Override
                    public void onDrawerClosing(){
                        System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                    }
                });
            }
        });


    }
}
