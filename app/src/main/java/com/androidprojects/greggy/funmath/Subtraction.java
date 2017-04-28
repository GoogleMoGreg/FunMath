package com.androidprojects.greggy.funmath;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.transitionseverywhere.TransitionManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Subtraction extends AppCompatActivity implements View.OnClickListener{

    TextView tv_questionNum,tv_question1,tv_question2,
                tv_question3,tv_FUN,tv_MATH,
                tv_secremaining,tv_scoredpoints;

    Button btn_sub1,btn_sub2,btn_sub3,imgbtn_score;

    String TAG_MESSAGE= "TAG_MESSAGE";
    GifDrawable gifDrawable;
    GifImageView gif_timer;

    long milliLeft;
    int numAns,numPos_1,numPos_2,numPos_3,
        colorPos_1,colorPos_2,colorPos_3,
        bgColorPos_1,bgColorPos_2,bgColorPos_3;

    int colorRed = Color.rgb(255,82,82),
        colorGreen = Color.rgb(76,175,80),
        colorBlue = Color.rgb(68,138,255);

    int Questions;
    String arrayQuestions[] = new String[]{"Answer","Number","Box"};

    String AnsNum;
    int ColorAns,BGColorAns;

    public static final String PREFS_NAME = "User_Sub_Score";
    String TAG_KEY = "sub_score",
            TAG_NUMBER = "sub_number";
    int score;

    final int TimerValue = 3000;
    CountDownTimer timer;

    DBHelper dbHelper;

    String TAG_BUNDLE_SCORE= "score";
    String TAG_BUNDLE_PK ="pk";

    Typeface font;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction);
        dbHelper = new DBHelper(this);

        tv_FUN=(TextView)findViewById(R.id.tv_subFUN);
        tv_MATH=(TextView)findViewById(R.id.tv_subMATH);
        tv_secremaining=(TextView)findViewById(R.id.tv_subsecremaining);
        tv_scoredpoints=(TextView)findViewById(R.id.tv_subscoredpoints);
        imgbtn_score=(Button)findViewById(R.id.btn_subscore);
        tv_questionNum = (TextView)findViewById(R.id.tv_subquestionNum);
        tv_question1 = (TextView)findViewById(R.id.tv_subquestion_1);
        tv_question2 = (TextView)findViewById(R.id.tv_subquestion_2);
        tv_question3 = (TextView)findViewById(R.id.tv_subquestion_3);
        gif_timer=(GifImageView)findViewById(R.id.iv_subtimer);

        font = Typeface.createFromAsset(getAssets(), "fonts/URW Gothic L Book.ttf");
        tv_FUN.setTypeface(font);
        tv_MATH.setTypeface(font);
        tv_secremaining.setTypeface(font);
        tv_scoredpoints.setTypeface(font);
        tv_questionNum.setTypeface(font);
        tv_question1.setTypeface(font);
        tv_question2.setTypeface(font);
        tv_question3.setTypeface(font);
        imgbtn_score.setTypeface(font);

        gif_timer.setImageResource(R.drawable.timer);
        gifDrawable=(GifDrawable)gif_timer.getDrawable();

        btn_sub1 = (Button)findViewById(R.id.btn_sub_1);
        btn_sub1.setOnClickListener(this);
        btn_sub2 = (Button)findViewById(R.id.btn_sub_2);
        btn_sub2.setOnClickListener(this);
        btn_sub3 = (Button)findViewById(R.id.btn_sub_3);
        btn_sub3.setOnClickListener(this);

        btn_sub1.setTypeface(font);
        btn_sub2.setTypeface(font);
        btn_sub3.setTypeface(font);



        SharedPreferences getScore = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        score = getScore.getInt(TAG_KEY,0);
        Log.d(TAG_MESSAGE,"score is: "+score);
        imgbtn_score.setText(String.valueOf(score));

        int x = GenerateNewNum(score),
            y = GenerateSecondNum(x);
        numAns = x-y;

        tv_questionNum.setText(x+" - "+y+" = ?");
        int numPosArray[] = RandomNumber(numAns);
        numPos_1 = numAns;
        numPos_2 = numPosArray[0];
        numPos_3 = numPosArray[1];
        Log.d(TAG_MESSAGE,"numPos_1 "+numPos_1+" numPos_2 "+numPos_2+" numPos_3 "+numPos_3);
        ShufflePosNum(numPos_1,numPos_2,numPos_3);

        int colorPosArray[] = RandomColor();
        colorPos_1 = colorPosArray[0];
        colorPos_2 = colorPosArray[1];
        colorPos_3 = colorPosArray[2];
        Log.d(TAG_MESSAGE,"colorPos_1: "+colorPos_1+" colorPos_2:"+colorPos_2+" colorPos_3:"+colorPos_3);

        bgColorPos_1 = colorPosArray[3];
        bgColorPos_2 = colorPosArray[4];
        bgColorPos_3 = colorPosArray[5];
        Log.d(TAG_MESSAGE,"bgcolorPos_1: "+bgColorPos_1+" bgcolorPos_2:"+bgColorPos_2+" bgcolorPos_3:"+bgColorPos_3);
        SetColors(colorPos_1,colorPos_2,colorPos_3,bgColorPos_1,bgColorPos_2,bgColorPos_3);

        Random rnd = new Random();
        Questions = rnd.nextInt(3);
        InitQuestions(Questions);

        timer = new CountDownTimer(TimerValue,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG_MESSAGE,"TIMER IS:"+millisUntilFinished/1000);
                milliLeft=millisUntilFinished;
            }
            @Override
            public void onFinish() {
                Log.d(TAG_MESSAGE,"TIMES UP!");
                SharedPreferences pref_score = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
                SharedPreferences.Editor edit =pref_score.edit();
                edit.putInt(TAG_KEY,0);
                edit.apply();
                cancel();
                SlideRevealGameOver();
                Intent gameOver = new Intent(getApplicationContext(),GameOver.class);
                finish();
                StoreHighScore(score);
                gameOver.putExtra(TAG_BUNDLE_SCORE,score);
                gameOver.putExtra(TAG_BUNDLE_PK,2);
                startActivity(gameOver);
            }
        }.start();

    }

    @Override
    public void onClick(View v) {

        int question = Questions;
        int numAnswer = numPos_1,
                colorAnswer = colorPos_1,
                bgColorAnswer = bgColorPos_1;

        switch (v.getId()){

            case R.id.btn_sub_1:
                Log.d(TAG_MESSAGE,"clicked "+btn_sub1.getText().toString());
                GetFirstBtnValue(question,numAnswer,colorAnswer,bgColorAnswer);
                break;
            case R.id.btn_sub_2:
                Log.d(TAG_MESSAGE,"clicked "+btn_sub2.getText().toString());
                GetSecondBtnValue(question,numAnswer,colorAnswer,bgColorAnswer);
                break;
            case R.id.btn_sub_3:
                Log.d(TAG_MESSAGE,"clicked "+btn_sub3.getText().toString());
                GetThirdBtnValue(question,numAnswer,colorAnswer,bgColorAnswer);
                break;
        }
    }

    private int GenerateSecondNum(int x){
        int y;
        Random rnd = new Random();
        y= rnd.nextInt(x);
        return y;
    }

    private void ShufflePosNum(int numPos_1,int numPos_2,int numPos_3){
        List<Integer> NumArray = Arrays.asList(new Integer[]{numPos_1,numPos_2,numPos_3});
        Collections.shuffle(NumArray);
        Log.d(TAG_MESSAGE,"number 1:"+NumArray.get(0)+" number 2:"+NumArray.get(1)+" Number 3:"+NumArray.get(2));
        btn_sub1.setText(String.valueOf(NumArray.get(0)));
        btn_sub2.setText(String.valueOf(NumArray.get(1)));
        btn_sub3.setText(String.valueOf(NumArray.get(2)));
    }

    private int[] RandomNumber(int numAns){
        int[] numberArray = new int[2];
        boolean i = true;

        do {
            Random rnd = new Random();
            numberArray[0] = rnd.nextInt(numAns+1)+1;
            numberArray[1] = rnd.nextInt(numAns+2)+2;
            if (numberArray[0]!=numberArray[1]&numberArray[0]!=numAns&
                    numberArray[1]!=numAns&numberArray[0]!=0&numberArray[1]!=0){
                i=false;
            }
        }
        while (i);
        return  numberArray;
    }

    private int[] RandomColor(){
        boolean i = true;
        int ColorArray[]=new int[6];
        List<Integer>txtColorList = Arrays.asList(new Integer[]{colorRed,colorGreen,colorBlue,
                                                    colorRed,colorGreen,colorBlue});
        do {
            Collections.shuffle(txtColorList);
            ColorArray[0]=txtColorList.get(0);
            ColorArray[1]=txtColorList.get(1);
            ColorArray[2]=txtColorList.get(2);
            ColorArray[3]=txtColorList.get(3);
            ColorArray[4]=txtColorList.get(4);
            ColorArray[5]=txtColorList.get(5);
            if (ColorArray[0]!=ColorArray[3]&ColorArray[1]!=ColorArray[4]&ColorArray[2]!=ColorArray[5]&
                    ColorArray[0]!=ColorArray[1]&ColorArray[1]!=ColorArray[2]&ColorArray[2]!=ColorArray[0]){
                i=false;
            }
        }while (i);

        return ColorArray;
    }

    private void SetColors(int colorPos_1,int colorPos_2, int colorPos_3,
                           int bgColorPos_1,int bgColorPos_2,int bgColorPos_3){

        btn_sub1.setTextColor(colorPos_1);
        btn_sub2.setTextColor(colorPos_2);
        btn_sub3.setTextColor(colorPos_3);
        btn_sub1.setBackgroundColor(bgColorPos_1);
        btn_sub2.setBackgroundColor(bgColorPos_2);
        btn_sub3.setBackgroundColor(bgColorPos_3);
    }

    private void InitQuestions(int Question){

        switch (Question){
            case 0:
                tv_question1.setText("Pick the Correct ");
                tv_question2.setText(arrayQuestions[Question]);
                tv_question3.setText(" ");
                break;
            case 1:
                tv_question1.setText("Pick the ");
                ColorPickerTxt(colorPos_1);
                tv_question3.setText(" "+arrayQuestions[Question]);
                break;
            case 2:
                tv_question1.setText("Pick the ");
                ColorPickerBox(bgColorPos_1);
                tv_question3.setText(" "+arrayQuestions[Question]);
                break;
        }
        Log.d(TAG_MESSAGE,"Question Index is "+Question);
    }

    private void ColorPickerTxt(int colorPos_1){
        switch (colorPos_1){

            case -44462:
                tv_question2.setTextColor(colorPos_1);
                tv_question2.setText("RED");
                break;
            case -11751600:
                tv_question2.setTextColor(colorPos_1);
                tv_question2.setText("GREEN");
                break;
            case -12285185:
                tv_question2.setTextColor(colorPos_1);
                tv_question2.setText("BLUE");
                break;

        }

    }

    private void ColorPickerBox(int bgColorPos_1){
        switch (bgColorPos_1){

            case -44462:
                tv_question2.setTextColor(bgColorPos_1);
                tv_question2.setText("RED");
                break;
            case -11751600:
                tv_question2.setTextColor(bgColorPos_1);
                tv_question2.setText("GREEN");
                break;
            case -12285185:
                tv_question2.setTextColor(bgColorPos_1);
                tv_question2.setText("BLUE");
                break;

        }

    }

    private void GetFirstBtnValue(int Question,int numAnswer,int colorAnswer,int bgColorAnswer){

        switch (Question){
            case 0:
                AnsNum = btn_sub1.getText().toString();
                Log.d(TAG_MESSAGE,"Answer is: "+AnsNum);
                GetAnswer(Integer.valueOf(AnsNum),numAnswer);
                break;
            case 1:
                ColorAns = btn_sub1.getCurrentTextColor();
                Log.d(TAG_MESSAGE,"Text Color is: "+ColorAns);
                GetAnswer(ColorAns,colorAnswer);
                break;
            case 2:
                ColorDrawable btnColor = (ColorDrawable) btn_sub1.getBackground();
                BGColorAns = btnColor.getColor();
                Log.d(TAG_MESSAGE,"BG Color is: "+BGColorAns);
                GetAnswer(BGColorAns,bgColorAnswer);
                break;
        }

    }

    private void GetSecondBtnValue(int Question,int numAnswer,int colorAnswer,int bgColorAnswer){
        switch (Question){
            case 0:
                AnsNum = btn_sub2.getText().toString();
                Log.d(TAG_MESSAGE,"Answer is: "+AnsNum);
                GetAnswer(Integer.valueOf(AnsNum),numAnswer);
                break;
            case 1:
                ColorAns = btn_sub2.getCurrentTextColor();
                Log.d(TAG_MESSAGE,"Text Color is: "+ColorAns);
                GetAnswer(ColorAns,colorAnswer);
                break;
            case 2:
                ColorDrawable btnColor = (ColorDrawable) btn_sub2.getBackground();
                BGColorAns = btnColor.getColor();
                Log.d(TAG_MESSAGE,"BG Color is: "+BGColorAns);
                GetAnswer(BGColorAns,bgColorAnswer);
                break;
        }

    }

    private void GetThirdBtnValue(int Question,int numAnswer,int colorAnswer,int bgColorAnswer){
        switch (Question){
            case 0:
                AnsNum = btn_sub3.getText().toString();
                Log.d(TAG_MESSAGE,"Answer is: "+AnsNum);
                GetAnswer(Integer.valueOf(AnsNum),numAnswer);
                break;
            case 1:
                ColorAns = btn_sub3.getCurrentTextColor();
                Log.d(TAG_MESSAGE,"Text Color is: "+ColorAns);
                GetAnswer(ColorAns,colorAnswer);
                break;
            case 2:
                ColorDrawable btnColor = (ColorDrawable) btn_sub3.getBackground();
                BGColorAns = btnColor.getColor();
                Log.d(TAG_MESSAGE,"BG Color is: "+BGColorAns);
                GetAnswer(BGColorAns,bgColorAnswer);
                break;
        }

    }

    private void GetAnswer(int ansValue,int ans){
        Log.d(TAG_MESSAGE,"Your Choosen Answer: "+ansValue);
        Log.d(TAG_MESSAGE, "Correct Answer: "+ans);
        if (ans == ansValue){
            Log.d(TAG_MESSAGE,"Correct!");
            SharedPreferences pref_getScore = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
            int newScore = pref_getScore.getInt(TAG_KEY,0);
            newScore++;

            SharedPreferences pref_score = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
            SharedPreferences.Editor edit =pref_score.edit();
            edit.putInt(TAG_KEY,newScore);
            edit.apply();
            SlideRevealCorrect();
            StoreNewNum(numAns);
            Intent restartActivity = getIntent();
            finish();
            startActivity(restartActivity);
            timer.cancel();
        }
        else {
            Log.d(TAG_MESSAGE,"Wrong!");
            SharedPreferences pref_score = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
            SharedPreferences.Editor edit =pref_score.edit();
            edit.putInt(TAG_KEY,0);
            edit.apply();
            timer.cancel();
            SlideRevealGameOver();
            Intent gameOver = new Intent(this,GameOver.class);
            finish();
            StoreHighScore(score);
            gameOver.putExtra(TAG_BUNDLE_SCORE,score);
            gameOver.putExtra(TAG_BUNDLE_PK,2);
            startActivity(gameOver);
        }
    }

    int x;
    private int GenerateNewNum(int score){

        Log.d(TAG_MESSAGE,"score is :"+score);
        if (score<1){
            x=2;
        }
        else {
            SharedPreferences getStoreNumAns = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
            x = getStoreNumAns.getInt(TAG_NUMBER,0);
            if (x>=200){
                x=5;
            }
        }
        return x*2;
    }

    private void StoreNewNum(int numAns){
        SharedPreferences storeNumAns = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = storeNumAns.edit();
        editor.putInt(TAG_NUMBER,numAns);
        editor.apply();
    }

    private void StoreHighScore(int score){
        Log.d(TAG_MESSAGE,"checking highscore: "+score);
        dbHelper.UpdateDBScore(2,score);
    }

    private void SlideRevealGameOver(){
        final ViewGroup view = (ViewGroup) findViewById(R.id.activity_subtraction);
        TransitionManager.beginDelayedTransition(view,new com.transitionseverywhere.Slide(Gravity.LEFT));
        view.setVisibility(View.GONE);
    }

    private void SlideRevealCorrect(){
        final ViewGroup view = (ViewGroup) findViewById(R.id.activity_subtraction);
        TransitionManager.beginDelayedTransition(view,new com.transitionseverywhere.Slide(Gravity.RIGHT));
        view.setVisibility(View.GONE);
    }
    int backpress_count = 2;
    @Override
    public void onBackPressed(){
        backpress_count--;
        BackPressCounter(backpress_count);
    }

    private void BackPressCounter(int count ){

        Log.d(TAG_MESSAGE,"BACK PRESS COUNT: "+count);

        if(count==0){
            //do nothing...
            Log.d(TAG_MESSAGE,"DO NOTHING...");
        }
        else if(count<0){
            ClosingActivity();
        }
        else {
            TimerPause();
            HideActivity();
            gifDrawable.stop();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Game Pause !");
            alertDialog.setMessage("Do you want to quit ?");
            alertDialog.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ClosingActivity();
                        }
                    });
            alertDialog.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            RevealActivity();
                            TimerLeft(milliLeft-1000);
                            gifDrawable.start();

                        }
                    });
            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }
    }

    private void HideActivity(){

        View view = findViewById(R.id.activity_subtraction);
        view.setVisibility(View.INVISIBLE);
    }

    private void RevealActivity(){
        View view = findViewById(R.id.activity_subtraction);
        view.setVisibility(View.VISIBLE);

    }

    private void TimerPause(){

        timer.cancel();
    }

    private void TimerLeft(long milliLeft){
        timer = new CountDownTimer(milliLeft-1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG_MESSAGE,"TIMER IS: "+millisUntilFinished/1000);
            }
            @Override
            public void onFinish() {
                Log.d(TAG_MESSAGE,"TIMES UP!");
                SharedPreferences pref_score = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
                SharedPreferences.Editor edit =pref_score.edit();
                edit.putInt(TAG_KEY,0);
                edit.apply();
                cancel();
                Intent gameOver = new Intent(getApplicationContext(),GameOver.class);
                finish();
                SlideRevealGameOver();
                StoreHighScore(score);
                gameOver.putExtra(TAG_BUNDLE_SCORE,score);
                gameOver.putExtra(TAG_BUNDLE_PK,2);
                startActivity(gameOver);
            }
        }.start();
    }


    public void ClosingActivity(){

        timer.cancel();
        SharedPreferences pref_score = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit =pref_score.edit();
        edit.putInt(TAG_KEY,0);
        edit.apply();
        Intent startGame = new Intent(this,StartGame.class);
        finish();
        startActivity(startGame);
    }
}
