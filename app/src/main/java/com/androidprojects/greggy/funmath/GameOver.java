package com.androidprojects.greggy.funmath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity implements View.OnClickListener {

    Button btn_y,btn_n,btn_scoreis,btn_highscore;
    TextView tv_gamecategory;

    String TAG_BUNDLE_SCORE = "score";
    String TAG_BUNDLE_PK = "pk";

    int pk;

    String TAG_MESSAGE = "DEBUG_MESSAGE";

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        dbHelper = new DBHelper(this);
        tv_gamecategory=(TextView) findViewById(R.id.tv_gamecategory);
        btn_scoreis=(Button)findViewById(R.id.imgbtn_score);
        btn_highscore=(Button)findViewById(R.id.imgbtn_bestscore);
        btn_y=(Button)findViewById(R.id.btn_tryagain_y);
        btn_y.setOnClickListener(this);
        btn_n=(Button)findViewById(R.id.btn_tryagain_n);
        btn_n.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt(TAG_BUNDLE_SCORE);
        pk = bundle.getInt(TAG_BUNDLE_PK);
        SetCategory(pk);
        Log.d(TAG_MESSAGE,"pk is "+pk);
        Log.d(TAG_MESSAGE, "score is :" + score);
        int highScore = dbHelper.ViewHighScore(pk);
        btn_scoreis.setText(String.valueOf(score));
        btn_highscore.setText(String.valueOf(highScore));
        IdentifyScore(score,highScore);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_tryagain_y:
                Log.d(TAG_MESSAGE,"pressed yes button...");
                Log.d(TAG_MESSAGE,"pk is "+pk);
                GoToActivity(pk);
                break;
            case R.id.btn_tryagain_n:
                Log.d(TAG_MESSAGE,"pressed no button...");
                CloseAcitivty();
                break;
        }
    }

    private void IdentifyScore(int score, int highscore) {
        if (score == highscore) {

            Log.d(TAG_MESSAGE, "Congratulations new highscore!");
        } else {
            Log.d(TAG_MESSAGE,"No new highscore!");

        }

    }

    private void SetCategory(int pk){

        switch (pk){

            case 1:
                tv_gamecategory.setText("a d d i t i o n");
                break;
            case 2:
                tv_gamecategory.setText("s u b t r a c t i o n");
                break;
            case 3:
                tv_gamecategory.setText("m u  l t i p l i c a t i o n");
                break;
        }
    }

    private void GoToActivity(int pk){

        switch (pk){

            case 1:
                OpenAddition();
                break;
            case 2:
                OpenSubtraction();
                break;
            case 3:
                OpenMultiplication();
                break;
        }
    }

    private void OpenAddition(){
        Intent add = new Intent(this,Addition.class);
        this.finish();
        startActivity(add);
    }

    private void OpenSubtraction(){
        Intent sub = new Intent(this,Subtraction.class);
        this.finish();
        startActivity(sub);
    }

    private void OpenMultiplication(){
        Intent mul = new Intent(this,Multiplication.class);
        this.finish();
        startActivity(mul);
    }

    private void CloseAcitivty(){
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startGame = new Intent(this,StartGame.class);
        finish();
        startActivity(startGame);
    }
}
