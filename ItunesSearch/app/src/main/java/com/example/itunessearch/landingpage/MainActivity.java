package com.example.itunessearch.landingpage;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.itunessearch.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar actionBarToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(actionBarToolBar);
        loadFragment(LandingFragment.newInstance(), LandingFragment.class.getSimpleName());
    }

    public void loadFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment, tag)
                    .addToBackStack(tag)
                    .commitAllowingStateLoss();
        }
    }

    public void enableBackButton(boolean shouldEnable) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(shouldEnable);
        getSupportActionBar().setHomeButtonEnabled(shouldEnable);
        getSupportActionBar().setDisplayHomeAsUpEnabled(shouldEnable);
        getSupportActionBar().setDisplayShowHomeEnabled(shouldEnable);
    }

    public void showErrorDialog(String title, String message) {
        final AlertDialog alertDialog1 = new AlertDialog.Builder(
                this).create();
        alertDialog1.setTitle(title);
        alertDialog1.setMessage(message);
        alertDialog1.setButton(DialogInterface.BUTTON_NEUTRAL, getString(R.string.okText), (dialog, which) -> alertDialog1.dismiss());
        alertDialog1.show();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}