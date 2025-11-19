package com.example.inventrymanage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.inventrymanage.fragments.FirstSubTypeFragment;

public class SubTypeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_type_list);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Force Light Mode

        int position = getIntent().getIntExtra("position", 0);
        int clicked_id = getIntent().getIntExtra("clicked_id", 0);
        int imagePath = getIntent().getIntExtra("imagePath", 0);


        FirstSubTypeFragment fragment = FirstSubTypeFragment.getInstance(position, clicked_id, imagePath);

//        loadFragment(new FirstSubTypeFragment());
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerFirstSubTypeFrame, fragment)
                    .commit();
        }
    }



//    public void loadFragment(Fragment fragment) {
//
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//
////        if (flag) {
//
//            ft.add(R.id.containerFirstSubTypeFrame, fragment);
//            fm.popBackStack("root_tag", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            ft.addToBackStack("root_tag");
////        } else {
////
////            ft.replace(R.id.containerFirstSubTypeFrame, fragment);
////            ft.addToBackStack(null);
////        }
//
//        ft.commit();
//
//    }

//    public void loadFragment(Fragment fragment) {
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.containerFirstSubTypeFrame, fragment)
//                .addToBackStack(null)
//                .commit();
//    }


//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            finish(); // Completely closes the activity & app if it's the last screen
//        }
//    }
}