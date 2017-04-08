package com.androidprojects.greggy.funmath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    TextView tv_score, tv_highscore;

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

        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt(TAG_BUNDLE_SCORE);
        int pk = bundle.getInt(TAG_BUNDLE_PK);
        Log.d(TAG_MESSAGE, "score is :" + score);
        int highScore = dbHelper.ViewHighScore(pk);
        tv_score.setText("Your score is " + String.valueOf(score));
        tv_highscore.setText("Highscore is " + String.valueOf(highScore));
        IdentifyScore(score,highScore);


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
