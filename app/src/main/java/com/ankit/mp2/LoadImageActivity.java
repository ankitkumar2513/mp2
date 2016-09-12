package com.ankit.mp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoadImageActivity extends AppCompatActivity implements Button.OnClickListener{

    private Button addNewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);

        addNewButton = (Button) findViewById(R.id.b_load_image_add_new);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == addNewButton.getId()) {
            //TODO: load image from gallery code
        }
    }
}
