package com.selfapps.a8queengame.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.selfapps.a8queengame.Constants;
import com.selfapps.a8queengame.R;

public class DifficultySettingsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_settings);
        getSupportActionBar().hide();

        findViewById(R.id.btn_easy).setOnClickListener(this);
        findViewById(R.id.btn_normal).setOnClickListener(this);
        findViewById(R.id.btn_hard).setOnClickListener(this);
        findViewById(R.id.btn_profi).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(DifficultySettingsActivity.this, MainActivity.class);
        int i = v.getId();
        if (i == R.id.btn_easy) {
            intent.putExtra(Constants.EXTRA_DIFFICULTY_LEVEL, 0);

        } else if (i == R.id.btn_normal) {
            intent.putExtra(Constants.EXTRA_DIFFICULTY_LEVEL, 1);

        } else if (i == R.id.btn_hard) {
            intent.putExtra(Constants.EXTRA_DIFFICULTY_LEVEL, 2);

        } else if (i == R.id.btn_profi) {
            intent.putExtra(Constants.EXTRA_DIFFICULTY_LEVEL, 3);

        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
