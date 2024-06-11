package com.abdilahstudio.firebasecrud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.abdilahstudio.firebasecrud.DBCreateActivity;
import com.abdilahstudio.firebasecrud.DBReadActivity;
import com.abdilahstudio.firebasecrud.R;

public class MainActivity extends AppCompatActivity {

    private Button btCreateDB;
    private Button btViewDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCreateDB = findViewById(R.id.bt_createdata);
        btViewDB = findViewById(R.id.bt_viewdata);

        btCreateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to DBCreateActivity
                startActivity(DBCreateActivity.getActIntent(MainActivity.this));
            }
        });

        btViewDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to DBReadActivity
                startActivity(DBReadActivity.getActIntent(MainActivity.this));
            }
        });
    }
}
