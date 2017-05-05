package com.androidprojects.greggy.funmath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeveloperName extends AppCompatActivity implements View.OnClickListener{

    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_name);
        btn_ok = (Button)findViewById(R.id.btn_developok);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_developok:
                GoToMain();
                break;
        }
    }

    private void GoToMain(){

        Intent gotoMain = new Intent(this,MainActivity.class);
        startActivity(gotoMain);
        this.finish();

    }
}
