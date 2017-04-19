package com.androidprojects.greggy.funmath;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String DEBUG_MESSAGE = "MESSAGE";
    Button btn_startgame, btn_highscore, btn_about;
    TextView tv_FUN,tv_MATH;
    Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_startgame = (Button) findViewById(R.id.btn_startgame);
        btn_startgame.setOnClickListener(this);
        btn_highscore = (Button) findViewById(R.id.btn_highscore);
        btn_highscore.setOnClickListener(this);
        btn_about = (Button) findViewById(R.id.btn_about);
        btn_about.setOnClickListener(this);
        tv_FUN=(TextView)findViewById(R.id.tv_MainHeader_FUN);
        tv_MATH=(TextView)findViewById(R.id.tv_MainHeader_MATH);
        font = Typeface.createFromAsset(getAssets(), "fonts/URW Gothic L Book.ttf");
        tv_FUN.setTypeface(font);
        tv_MATH.setTypeface(font);
        btn_about.setTypeface(font);
        btn_highscore.setTypeface(font);
        btn_startgame.setTypeface(font);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_startgame:
                Log.d(DEBUG_MESSAGE,"Clicked "+btn_startgame.getText());
                Intent StartGame = new Intent(getApplicationContext(), com.androidprojects.greggy.funmath.StartGame.class);
                startActivity(StartGame);
                break;

            case R.id.btn_highscore:
                Log.d(DEBUG_MESSAGE,"Clicked "+btn_highscore.getText());
                Intent Highscore = new Intent(getApplicationContext(), com.androidprojects.greggy.funmath.Highscore.class);
                startActivity(Highscore);
                break;

            case R.id.btn_about:
                Log.d(DEBUG_MESSAGE,"Clicked "+btn_about.getText());
                break;
        }
    }
}
