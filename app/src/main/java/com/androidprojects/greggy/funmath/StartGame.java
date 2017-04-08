package com.androidprojects.greggy.funmath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StartGame extends AppCompatActivity implements View.OnClickListener {

    String DEBUG_MESSAGE = "MESSAGE";

    Button btn_add,btn_sub,btn_mul;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        dbHelper = new DBHelper(this);
        GenerateDB();
        btn_add = (Button) findViewById(R.id.btn_addition);
        btn_add.setOnClickListener(this);
        btn_sub = (Button) findViewById(R.id.btn_subtraction);
        btn_sub.setOnClickListener(this);
        btn_mul = (Button) findViewById(R.id.btn_multiplication);
        btn_mul.setOnClickListener(this);

        String data = dbHelper.getTableValues();
        Log.d(DEBUG_MESSAGE,data);
        int numRows = dbHelper.CheckRowNum();
        Log.d(DEBUG_MESSAGE,"Number of rows "+numRows);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_addition:
                Log.d(DEBUG_MESSAGE,"Clicked "+btn_add.getText());
                Intent Addition = new Intent(this, com.androidprojects.greggy.funmath.Addition.class);
                this.finish();
                startActivity(Addition);
                break;
            case R.id.btn_subtraction:
                Log.d(DEBUG_MESSAGE,"Clicked "+btn_sub.getText());
                Intent Subtraction = new Intent(this, com.androidprojects.greggy.funmath.Subtraction.class);
                this.finish();
                startActivity(Subtraction);
                break;
            case R.id.btn_multiplication:
                Log.d(DEBUG_MESSAGE,"Clicked "+btn_mul.getText());
                break;
        }

    }

    private void GenerateDB(){
        int numRows = dbHelper.CheckRowNum();
        Log.d(DEBUG_MESSAGE,"Number of rows "+numRows);
        String[] categoryList ={"Addition","Subtraction","Multiplication"};
        if (numRows<1){
            for (int i = 0;i<categoryList.length;i++){
                dbHelper.InsertData(categoryList[i],String.valueOf(0));
            }
            Log.d(DEBUG_MESSAGE,"Successfully Inserted new DB");

        }
    }
}
