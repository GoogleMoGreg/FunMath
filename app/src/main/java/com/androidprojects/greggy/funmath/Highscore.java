package com.androidprojects.greggy.funmath;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Highscore extends AppCompatActivity {

    DBHelper dbHelper;
    Button btn_HSadd,btn_HSsub,btn_HSmul;
    TextView tv_BEST,tv_SCORES,tv_add,tv_sub,tv_mul;
    Typeface font;
    int dbRowNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        dbHelper = new DBHelper(this);
        btn_HSadd=(Button)findViewById(R.id.imgbtn_addHS);
        btn_HSsub=(Button)findViewById(R.id.imgbtn_subHS);
        btn_HSmul=(Button)findViewById(R.id.imgbtn_mulHS);
        tv_BEST=(TextView)findViewById(R.id.tv_BEST);
        tv_SCORES=(TextView)findViewById(R.id.tv_SCORE);
        tv_add=(TextView)findViewById(R.id.tv_HSaddition);
        tv_sub=(TextView)findViewById(R.id.tv_HSsubtraction);
        tv_mul=(TextView)findViewById(R.id.tv_HSmultiplication);
        font = Typeface.createFromAsset(getAssets(), "fonts/URW Gothic L Book.ttf");
        btn_HSadd.setTypeface(font);
        btn_HSsub.setTypeface(font);
        btn_HSmul.setTypeface(font);
        tv_BEST.setTypeface(font);
        tv_SCORES.setTypeface(font);
        tv_add.setTypeface(font);
        tv_sub.setTypeface(font);
        tv_mul.setTypeface(font);

        CheckDB();

    }

    public void CheckDB(){

        dbRowNum = dbHelper.CheckRowNum();
        if (dbRowNum>0){
            int hsAdd,hsSub,hsMul;
            hsAdd=dbHelper.ViewHighScore(1);
            hsSub=dbHelper.ViewHighScore(2);
            hsMul=dbHelper.ViewHighScore(3);

            btn_HSadd.setText(String.valueOf(hsAdd));
            btn_HSsub.setText(String.valueOf(hsSub));
            btn_HSmul.setText(String.valueOf(hsMul));

        }
        else {


        }
    }
}
