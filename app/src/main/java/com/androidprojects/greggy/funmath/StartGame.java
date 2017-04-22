package com.androidprojects.greggy.funmath;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StartGame extends AppCompatActivity implements View.OnClickListener {

    String DEBUG_MESSAGE = "MESSAGE";

    Button btn_add,btn_sub,btn_mul;
    ImageView iv_btnadd,iv_btnsub,iv_btnmul;
    TextView tv_FUN,tv_MATH;

    DBHelper dbHelper;

    Typeface font;
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

        iv_btnadd =(ImageView)findViewById(R.id.ic_logoadd);
        iv_btnadd.setOnClickListener(this);
        iv_btnsub=(ImageView)findViewById(R.id.ic_logosub);
        iv_btnsub.setOnClickListener(this);
        iv_btnmul=(ImageView)findViewById(R.id.ic_logomul);
        iv_btnmul.setOnClickListener(this);

        tv_FUN=(TextView)findViewById(R.id.tv_startgame_FUN);
        tv_MATH=(TextView)findViewById(R.id.tv_startgame_MATH);
        font = Typeface.createFromAsset(getAssets(), "fonts/URW Gothic L Book.ttf");
        tv_FUN.setTypeface(font);
        tv_MATH.setTypeface(font);
        btn_add.setTypeface(font);
        btn_sub.setTypeface(font);
        btn_mul.setTypeface(font);


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
                OpenAddition();
                break;
            case R.id.btn_subtraction:
                Log.d(DEBUG_MESSAGE,"Clicked "+btn_sub.getText());
                OpenSubtraction();
                break;
            case R.id.btn_multiplication:
                Log.d(DEBUG_MESSAGE,"Clicked "+btn_mul.getText());
                OpenMultiplication();
                break;
            case R.id.ic_logoadd:
                OpenAddition();
                break;
            case R.id.ic_logosub:
                OpenSubtraction();
                break;
            case R.id.ic_logomul:
                OpenMultiplication();
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

    private void OpenAddition(){
        Intent Addition = new Intent(this, com.androidprojects.greggy.funmath.Addition.class);
        this.finish();
        startActivity(Addition);
    }

    private void OpenSubtraction(){
        Intent Subtraction = new Intent(this, com.androidprojects.greggy.funmath.Subtraction.class);
        this.finish();
        startActivity(Subtraction);
    }

    private void OpenMultiplication(){
        Intent Multiplication = new Intent(this, com.androidprojects.greggy.funmath.Multiplication.class);
        this.finish();
        startActivity(Multiplication);
    }

}
