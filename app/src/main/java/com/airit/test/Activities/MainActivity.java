package com.airit.test.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.airit.test.Common.FragmentsManager;
import com.airit.test.Fragment.HomeFragment;
import com.airit.test.R;

/**
 * starting point of application
 */
public class MainActivity extends AppCompatActivity {

    /**
     * load the fragment
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HomeFragment homeFragment = new HomeFragment();
        FragmentsManager.addFragment(this, homeFragment, R.id.fragment_container, false);
    }
}
