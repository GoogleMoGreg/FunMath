package com.androidprojects.greggy.funmath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Highscore extends AppCompatActivity {

    TextView tv_highscoreAdd,tv_highscoreSub,tv_highscoreMul;
    DBHelper dbHelper;

    int dbRowNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        dbHelper = new DBHelper(this);
        tv_highscoreAdd=(TextView)findViewById(R.id.tv_highscoreAdd);
        tv_highscoreSub=(TextView)findViewById(R.id.tv_highscoreSub);
        tv_highscoreMul=(TextView)findViewById(R.id.tv_highscoreMul);

        CheckDB();

    }

    public void CheckDB(){

        dbRowNum = dbHelper.CheckRowNum();
        if (dbRowNum>0){
            int hsAdd,hsSub,hsMul;
            hsAdd=dbHelper.ViewHighScore(1);
            hsSub=dbHelper.ViewHighScore(2);
            hsMul=dbHelper.ViewHighScore(3);

            tv_highscoreAdd.setText("Highscore Addition: "+String.valueOf(hsAdd));
            tv_highscoreSub.setText("Highscore Subtraction: "+String.valueOf(hsSub));
            tv_highscoreMul.setText("Highscore Multiplication: "+String.valueOf(hsMul));
        }
        else {
            tv_highscoreAdd.setText("No data in highscore...");
            tv_highscoreSub.setText(" ");
            tv_highscoreMul.setText(" ");

        }
    }
}
