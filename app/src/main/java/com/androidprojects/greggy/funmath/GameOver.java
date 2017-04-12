package com.androidprojects.greggy.funmath;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity implements View.OnClickListener {

    TextView tv_score, tv_highscore;
    Button btn_y,btn_n;

    String TAG_BUNDLE_SCORE = "score";
    String TAG_BUNDLE_PK = "pk";

    String TAG_MESSAGE = "DEBUG_MESSAGE";

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        dbHelper = new DBHelper(this);
        tv_score = (TextView) findViewById(R.id.tv_gameoverScore);
        tv_highscore = (TextView) findViewById(R.id.tv_highScore);
        btn_y=(Button)findViewById(R.id.btn_tryagain_y);
        btn_y.setOnClickListener(this);
        btn_n=(Button)findViewById(R.id.btn_tryagain_n);
        btn_n.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt(TAG_BUNDLE_SCORE);
        int pk = bundle.getInt(TAG_BUNDLE_PK);
        Log.d(TAG_MESSAGE, "score is :" + score);
        int highScore = dbHelper.ViewHighScore(pk);
        tv_score.setText("Your score is " + String.valueOf(score));
        tv_highscore.setText("Highscore is " + String.valueOf(highScore));
        IdentifyScore(score,highScore);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_tryagain_y:
                Log.d(TAG_MESSAGE,"pressed yes button...");
                break;
            case R.id.btn_tryagain_n:
                Log.d(TAG_MESSAGE,"pressed no button...");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent startGame = new Intent(this,StartGame.class);
        finish();
        startActivity(startGame);
    }
}
