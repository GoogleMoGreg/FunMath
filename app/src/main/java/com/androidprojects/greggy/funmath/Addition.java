package com.androidprojects.greggy.funmath;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Addition extends AppCompatActivity implements View.OnClickListener{

    Button btn_firstNum,btn_secondNum,btn_thirdNum;
    TextView tv_score,tv_timer,tv_questionNum,tv_question,tv_question1,tv_question2;

    String DEBUG_MESSAGE = "MESSAGE";

    int score;
    int  numAns, numPos_1, numPos_2, numPos_3;

        int textRed = Color.rgb(255,0,0),
            textGreen = Color.rgb(0,255,0),
            textBlue = Color.rgb(0,0,255);

        int colorPos_1,colorPos_2,colorPos_3;

        int bgColorPos_1,bgColorPos_2,bgColorPos_3;

    String arrayQuestions[] = new String[]{"Answer","Number","Box"};

    int Question;

    String AnsNum;
    int ColorAns,BGColorAns;

    public static final String PREFS_NAME = "User_Score";
    String TAG_KEY = "score",
           TAG_NUMBER = "number";

    final int TimerValue = 3000;
    CountDownTimer timer;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        tv_score = (TextView) findViewById(R.id.tv_addscore);
        tv_timer = (TextView) findViewById(R.id.tv_addtimer);
        tv_questionNum = (TextView) findViewById(R.id.tv_addNumQuestion);
        tv_question = (TextView) findViewById(R.id.tv_addQuestion);
        tv_question1 = (TextView) findViewById(R.id.tv_addQuestion_1);
        tv_question2 = (TextView) findViewById(R.id.tv_addQuestion_2);

        dbHelper = new DBHelper(this);
        String data = dbHelper.getTableValues();
        Log.d(DEBUG_MESSAGE,data);
        int numRows = dbHelper.CheckRowNum();
        Log.d(DEBUG_MESSAGE,"Number of rows "+numRows);

        btn_firstNum =(Button) findViewById(R.id.btn_FirstNum);
        btn_firstNum.setOnClickListener(this);

        btn_secondNum =(Button) findViewById(R.id.btn_SecondNum);
        btn_secondNum.setOnClickListener(this);

        btn_thirdNum =(Button) findViewById(R.id.btn_ThirdNum);
        btn_thirdNum.setOnClickListener(this);

        SharedPreferences getScore = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        score = getScore.getInt(TAG_KEY,0);
        Log.d(DEBUG_MESSAGE,"score is: "+score);
        tv_score.setText(String.valueOf(score));

        int x,y;
        x  = GenerateNewNum(score);
        y=GenerateSecondNum(x);
        Log.d(DEBUG_MESSAGE,"Value of x is :"+x+" Value of y is:"+y);
        tv_questionNum.setText(String.valueOf(x)+" + "+ String.valueOf(y)+"= ?");
        numAns = x+y;

        Log.d(DEBUG_MESSAGE,"value of numAns is :"+numAns);
        RandomNumber rndNumber = GenerateRndNum(numAns);
        numPos_1 = numAns;
        numPos_2 = rndNumber.getNumDecoy_1();
        numPos_3 = rndNumber.getNumDecoy_2();
        ShufflePositionNumber(numPos_1,numPos_2,numPos_3);

        RandomColor rndColor = GenerateRndColor();
        colorPos_1 = rndColor.getTxtColor();
        colorPos_2 = rndColor.getTxtColorDecoy_1();
        colorPos_3 = rndColor.getTxtColorDecoy_2();
        Log.d(DEBUG_MESSAGE,"colorPos_1:"+colorPos_1+" colorPos_2:"+colorPos_2+" colorPos_3:"+colorPos_3);

        bgColorPos_1 = rndColor.getBgColor();
        bgColorPos_2 = rndColor.getBgColorDecoy_1();
        bgColorPos_3 = rndColor.getBgColorDecoy_2();
        Log.d(DEBUG_MESSAGE,"BGcolorPos_1:"+bgColorPos_1+" BGcolorPos_2:"+bgColorPos_2
                +" BGcolorPos_3:"+bgColorPos_3);
        SetTxtColors(colorPos_1,colorPos_2,colorPos_3,bgColorPos_1,bgColorPos_2,bgColorPos_3);

        Random rndquestion = new Random();
        Question = rndquestion.nextInt(3);
        InitQuestions(Question);

        timer = new CountDownTimer(TimerValue,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(DEBUG_MESSAGE,"TIMER IS:"+millisUntilFinished/1000);
            }
            @Override
            public void onFinish() {
                Log.d(DEBUG_MESSAGE,"TIMES UP!");
                SharedPreferences pref_score = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
                SharedPreferences.Editor edit =pref_score.edit();
                edit.putInt(TAG_KEY,0);
                edit.apply();
                cancel();
                Intent gameOver = new Intent(getApplicationContext(),StartGame.class);
                StoreHighScore(score);
                finish();
                startActivity(gameOver);

            }
        }.start();

    }

    @Override
    public void onClick(View v) {

        int question = Question;
        int numAnswer = numPos_1,
            colorAnswer = colorPos_1,
            bgColorAnswer = bgColorPos_1;

        switch (v.getId()){
            case R.id.btn_FirstNum:
                Log.d(DEBUG_MESSAGE, "onClick: First");
                GetFirstBtnValue(question,numAnswer,colorAnswer,bgColorAnswer);
                break;
            case R.id.btn_SecondNum:
                Log.d(DEBUG_MESSAGE, "onClick: Second");
                GetSecondBtnValue(question,numAnswer,colorAnswer,bgColorAnswer);
                break;
            case R.id.btn_ThirdNum:
                Log.d(DEBUG_MESSAGE, "onClick: Third");
                GetThirdBtnValue(question,numAnswer,colorAnswer,bgColorAnswer);
                break;
        }
    }

    private void InitQuestions(int Question){

        switch (Question){
            case 0:
                tv_question.setText("Pick the Correct ");
                tv_question1.setText(arrayQuestions[Question]);
                tv_question2.setText(" ");
                break;
            case 1:
                tv_question.setText("Pick the ");
                ColorPickerTxt(colorPos_1);
                tv_question2.setText(" "+arrayQuestions[Question]);
                break;
            case 2:
                tv_question.setText("Pick the ");
                ColorPickerBox(bgColorPos_1);
                tv_question2.setText(" "+arrayQuestions[Question]);
                break;
        }
        Log.d(DEBUG_MESSAGE,"Question Index is "+Question);
    }

    private void GetFirstBtnValue(int Question,int numAnswer,int colorAnswer,int bgColorAnswer){

        switch (Question){
            case 0:
                AnsNum = btn_firstNum.getText().toString();
                Log.d(DEBUG_MESSAGE,"Answer is: "+AnsNum);
                GetAnswer(Integer.valueOf(AnsNum),numAnswer);
                break;
            case 1:
                ColorAns = btn_firstNum.getCurrentTextColor();
                Log.d(DEBUG_MESSAGE,"Text Color is: "+ColorAns);
                GetAnswer(ColorAns,colorAnswer);
                break;
            case 2:
                ColorDrawable btnColor = (ColorDrawable) btn_firstNum.getBackground();
                BGColorAns = btnColor.getColor();
                Log.d(DEBUG_MESSAGE,"BG Color is: "+BGColorAns);
                GetAnswer(BGColorAns,bgColorAnswer);
                break;
        }

    }

    private void GetSecondBtnValue(int Question,int numAnswer,int colorAnswer,int bgColorAnswer){
        switch (Question){
            case 0:
                AnsNum = btn_secondNum.getText().toString();
                Log.d(DEBUG_MESSAGE,"Answer is: "+AnsNum);
                GetAnswer(Integer.valueOf(AnsNum),numAnswer);
                break;
            case 1:
                ColorAns = btn_secondNum.getCurrentTextColor();
                Log.d(DEBUG_MESSAGE,"Text Color is: "+ColorAns);
                GetAnswer(ColorAns,colorAnswer);
                break;
            case 2:
                ColorDrawable btnColor = (ColorDrawable) btn_secondNum.getBackground();
                BGColorAns = btnColor.getColor();
                Log.d(DEBUG_MESSAGE,"BG Color is: "+BGColorAns);
                GetAnswer(BGColorAns,bgColorAnswer);
                break;
        }

    }

    private void GetThirdBtnValue(int Question,int numAnswer,int colorAnswer,int bgColorAnswer){
        switch (Question){
            case 0:
                AnsNum = btn_thirdNum.getText().toString();
                Log.d(DEBUG_MESSAGE,"Answer is: "+AnsNum);
                GetAnswer(Integer.valueOf(AnsNum),numAnswer);
                break;
            case 1:
                ColorAns = btn_thirdNum.getCurrentTextColor();
                Log.d(DEBUG_MESSAGE,"Text Color is: "+ColorAns);
                GetAnswer(ColorAns,colorAnswer);
                break;
            case 2:
                ColorDrawable btnColor = (ColorDrawable) btn_thirdNum.getBackground();
                BGColorAns = btnColor.getColor();
                Log.d(DEBUG_MESSAGE,"BG Color is: "+BGColorAns);
                GetAnswer(BGColorAns,bgColorAnswer);
                break;
        }

    }

    private void GetAnswer(int ansValue,int ans){
        Log.d(DEBUG_MESSAGE,"Your Choosen Answer: "+ansValue);
        Log.d(DEBUG_MESSAGE, "Correct Answer: "+ans);
        if (ans == ansValue){
            Log.d(DEBUG_MESSAGE,"Correct!");
            SharedPreferences pref_getScore = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
            int newScore = pref_getScore.getInt(TAG_KEY,0);
            newScore++;

            SharedPreferences pref_score = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
            SharedPreferences.Editor edit =pref_score.edit();
            edit.putInt(TAG_KEY,newScore);
            edit.apply();

            StoreNewNum(numAns);
            Intent restartActivity = getIntent();
            finish();
            startActivity(restartActivity);
            timer.cancel();
        }
        else {
            Log.d(DEBUG_MESSAGE,"Wrong!");

            SharedPreferences pref_score = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
            SharedPreferences.Editor edit =pref_score.edit();
            edit.putInt(TAG_KEY,0);
            edit.apply();
            Intent gameOver = new Intent(getApplicationContext(),StartGame.class);
            finish();
            StoreHighScore(score);
            startActivity(gameOver);
            timer.cancel();

        }
    }

    private void ShufflePositionNumber(int numPos_1,int numPos_2,int numPos_3){
        int finalNumber_1,finalNumber_2,finalNumber_3;
        List<Integer> numberList = Arrays.asList(new Integer[]{numPos_1,numPos_2,numPos_3});
        Collections.shuffle(numberList);
        finalNumber_1 = numberList.get(0);
        finalNumber_2 = numberList.get(1);
        finalNumber_3 = numberList.get(2);
        Log.d(DEBUG_MESSAGE,"finalNumber_1:"+finalNumber_1+" final_Number_2:"+finalNumber_2
                +" finalNumber_3:"+finalNumber_3);
        SetTxtNumber(finalNumber_1,finalNumber_2,finalNumber_3);
    }

    private void SetTxtNumber(int finalNumber_1,int finalNumber_2,int finalNumber_3){
        btn_firstNum.setText(String.valueOf(finalNumber_1));
        btn_secondNum.setText(String.valueOf(finalNumber_2));
        btn_thirdNum.setText(String.valueOf(finalNumber_3));
    }

    private void SetTxtColors(int colorPos_1,int colorPos_2,int colorPos_3,
                              int bgColorPos_1,int bgColorPos_2,int bgColorPos_3){
        btn_firstNum.setTextColor(colorPos_1);
        btn_secondNum.setTextColor(colorPos_2);
        btn_thirdNum.setTextColor(colorPos_3);

        btn_firstNum.setBackgroundColor(bgColorPos_1);
        btn_secondNum.setBackgroundColor(bgColorPos_2);
        btn_thirdNum.setBackgroundColor(bgColorPos_3);

    }

    private void ColorPickerTxt(int colorPos_1){
        switch (colorPos_1){

            case -65536:
                tv_question1.setTextColor(colorPos_1);
                tv_question1.setText("RED");
                break;
            case -16711936:
                tv_question1.setTextColor(colorPos_1);
                tv_question1.setText("GREEN");
                break;
            case -16776961:
                tv_question1.setTextColor(colorPos_1);
                tv_question1.setText("BLUE");
                break;

        }

    }

    private void ColorPickerBox(int bgColorPos_1){
        switch (bgColorPos_1){

            case -65536:
                tv_question1.setTextColor(bgColorPos_1);
                tv_question1.setText("RED");
                break;
            case -16711936:
                tv_question1.setTextColor(bgColorPos_1);
                tv_question1.setText("GREEN");
                break;
            case -16776961:
                tv_question1.setTextColor(bgColorPos_1);
                tv_question1.setText("BLUE");
                break;

        }

    }

    int x;
    private int GenerateNewNum(int score){

        Log.d(DEBUG_MESSAGE,"score is :"+score);
        if (score<1){
            x=2;
        }
        else {
            SharedPreferences getStoreNumAns = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
            x = getStoreNumAns.getInt(TAG_NUMBER,0);
            if (x>=100){
                x=10;
            }
        }
        return x;
    }

    private int GenerateSecondNum(int x){
        int xnum;
        Random rnd = new Random();
        xnum = rnd.nextInt(x/2)+1;
        Math.round(xnum);
        return xnum;
    }

    private void StoreNewNum(int numAns){
        SharedPreferences storeNumAns = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = storeNumAns.edit();
        editor.putInt(TAG_NUMBER,numAns);
        editor.apply();
    }

    private void StoreHighScore(int score){
        Log.d(DEBUG_MESSAGE,"checking highscore: "+score);
        dbHelper.UpdateDBScore(1,score);
    }

    private class RandomNumber{
       int numDecoy_1,
           numDecoy_2;
       public RandomNumber(int numDecoy_1,int numDecoy_2){
           this.numDecoy_1 = numDecoy_1;
           this.numDecoy_2 = numDecoy_2;
       }

       public int getNumDecoy_1(){
           return numDecoy_1;
       }

       public int getNumDecoy_2(){
           return numDecoy_2;
       }
   }

    public RandomNumber GenerateRndNum(int max){
        boolean i = true;
        Log.d(DEBUG_MESSAGE,"int max is "+max);
        int numDecoy_1,numDecoy_2;
        do
        {
            Random rnd = new Random();
            numDecoy_1 = rnd.nextInt(max);
            numDecoy_2 = rnd.nextInt(max-1);
            if(numDecoy_1!=numDecoy_2&&numDecoy_1!=0&numDecoy_2!=0&
                    numDecoy_2!=numDecoy_1){
                i = false;
            }
        }while (i);

        return new RandomNumber(numDecoy_1,numDecoy_2);
    }

    private class RandomColor{
        int txtColor,
            txtColorDecoy_1,
            txtColorDecoy_2;
        int bgColor,
            bgColorDecoy_1,
            bgColorDecoy_2;

        public RandomColor(int txtColor, int txtColorDecoy_1, int txtColorDecoy_2,int bgColor,
                           int bgColorDecoy_1,int bgColorDecoy_2) {
            this.txtColor = txtColor;
            this.txtColorDecoy_1 = txtColorDecoy_1;
            this.txtColorDecoy_2 = txtColorDecoy_2;
            this.bgColor = bgColor;
            this.bgColorDecoy_1 = bgColorDecoy_1;
            this.bgColorDecoy_2 = bgColorDecoy_2;

        }

        public int getTxtColor() {
            return txtColor;
        }

        public int getTxtColorDecoy_1() {
            return txtColorDecoy_1;
        }

        public int getTxtColorDecoy_2() {
            return txtColorDecoy_2;
        }

        public int getBgColor() {
            return bgColor;
        }

        public int getBgColorDecoy_1() {
            return bgColorDecoy_1;
        }

        public int getBgColorDecoy_2() {
            return bgColorDecoy_2;
        }
    }

    public RandomColor GenerateRndColor(){
        boolean i = true;
        int txtColor,txtColorDecoy_1,txtColorDecoy_2,
                bgColor,bgColorDecoy_1,bgColorDecoy_2;

        do {
            List<Integer> TxtcolorList = Arrays.asList(new Integer[]{textRed,textBlue,textGreen});
            Collections.shuffle(TxtcolorList);
            txtColor = TxtcolorList.get(0);
            txtColorDecoy_1 = TxtcolorList.get(1);
            txtColorDecoy_2=TxtcolorList.get(2);

            List<Integer> BGcolorList = Arrays.asList(new Integer[]{textRed,textBlue,textGreen});
            Collections.shuffle(BGcolorList);
            bgColor = BGcolorList.get(0);
            bgColorDecoy_1 = BGcolorList.get(1);
            bgColorDecoy_2 = BGcolorList.get(2);

            if(bgColor!=txtColor&txtColorDecoy_1!=bgColorDecoy_1&txtColorDecoy_2!=bgColorDecoy_2){
                i = false;
            }

        }while (i);

        return new RandomColor(txtColor,txtColorDecoy_1,txtColorDecoy_2,bgColor,
                bgColorDecoy_1,bgColorDecoy_2);
    }

}
