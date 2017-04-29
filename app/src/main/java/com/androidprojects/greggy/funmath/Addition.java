package com.androidprojects.greggy.funmath;


import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import com.transitionseverywhere.TransitionManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


public class Addition extends AppCompatActivity implements View.OnClickListener {

    Button btn_firstNum, btn_secondNum, btn_thirdNum, btn_score;
    TextView tv_questionNum, tv_question, tv_question1, tv_question2, tv_FUN, tv_MATH,
            tv_scoredpoints, tv_secremaining;
    GifDrawable gifDrawable;
    GifImageView gif_timer;

    String DEBUG_MESSAGE = "MESSAGE";

    long milliLeft;
    int score;
    int numAns, numPos_1, numPos_2, numPos_3;

    int textRed = Color.rgb(255, 82, 82),
            textGreen = Color.rgb(76, 175, 80),
            textBlue = Color.rgb(68, 138, 255);

    int colorPos_1, colorPos_2, colorPos_3;

    int bgColorPos_1, bgColorPos_2, bgColorPos_3;

    String arrayQuestions[] = new String[]{"Answer", "Number", "Box"};

    int Question;

    String AnsNum;
    int ColorAns, BGColorAns;

    public static final String PREFS_NAME = "User_Score";
    String TAG_KEY = "score",
            TAG_NUMBER = "number";

    final int TimerValue = 3000;
    CountDownTimer timer;

    private DBHelper dbHelper;

    String TAG_BUNDLE_SCORE = "score";
    String TAG_BUNDLE_PK = "pk";

    Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        tv_FUN = (TextView) findViewById(R.id.tv_addFUN);
        tv_MATH = (TextView) findViewById(R.id.tv_addMATH);
        tv_secremaining = (TextView) findViewById(R.id.tv_addsecremaining);
        tv_scoredpoints = (TextView) findViewById(R.id.tv_addscoredpoints);
        tv_questionNum = (TextView) findViewById(R.id.tv_addNumQuestion);
        tv_question = (TextView) findViewById(R.id.tv_addQuestion);
        tv_question1 = (TextView) findViewById(R.id.tv_addQuestion_1);
        tv_question2 = (TextView) findViewById(R.id.tv_addQuestion_2);
        btn_score = (Button) findViewById(R.id.btn_addscore);
        gif_timer = (GifImageView) findViewById(R.id.iv_addtimer);
        gif_timer.setImageResource(R.drawable.timer);
        gifDrawable = (GifDrawable) gif_timer.getDrawable();

        font = Typeface.createFromAsset(getAssets(), "fonts/URW Gothic L Book.ttf");
        tv_FUN.setTypeface(font);
        tv_MATH.setTypeface(font);
        tv_scoredpoints.setTypeface(font);
        tv_secremaining.setTypeface(font);
        tv_questionNum.setTypeface(font);
        tv_question.setTypeface(font);
        tv_question1.setTypeface(font);
        tv_question2.setTypeface(font);
        btn_score.setTypeface(font);

        dbHelper = new DBHelper(this);

        btn_firstNum = (Button) findViewById(R.id.btn_FirstNum);
        btn_firstNum.setOnClickListener(this);

        btn_secondNum = (Button) findViewById(R.id.btn_SecondNum);
        btn_secondNum.setOnClickListener(this);

        btn_thirdNum = (Button) findViewById(R.id.btn_ThirdNum);
        btn_thirdNum.setOnClickListener(this);

        SharedPreferences getScore = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        score = getScore.getInt(TAG_KEY, 0);
        Log.d(DEBUG_MESSAGE, "score is: " + score);
        btn_score.setText(String.valueOf(score));

        int x, y;
        x = GenerateNewNum(score);
        y = GenerateSecondNum(x);
        Log.d(DEBUG_MESSAGE, "Value of x is :" + x + " Value of y is:" + y);
        tv_questionNum.setText(String.valueOf(x) + " + " + String.valueOf(y) + " = ?");
        numAns = x + y;

        Log.d(DEBUG_MESSAGE, "value of numAns is :" + numAns);
        RandomNumber rndNumber = GenerateRndNum(numAns);
        numPos_1 = numAns;
        numPos_2 = rndNumber.getNumDecoy_1();
        numPos_3 = rndNumber.getNumDecoy_2();
        ShufflePositionNumber(numPos_1, numPos_2, numPos_3);

        RandomColor rndColor = GenerateRndColor();
        colorPos_1 = rndColor.getTxtColor();
        colorPos_2 = rndColor.getTxtColorDecoy_1();
        colorPos_3 = rndColor.getTxtColorDecoy_2();
        Log.d(DEBUG_MESSAGE, "colorPos_1:" + colorPos_1 + " colorPos_2:" + colorPos_2 + " colorPos_3:" + colorPos_3);

        bgColorPos_1 = rndColor.getBgColor();
        bgColorPos_2 = rndColor.getBgColorDecoy_1();
        bgColorPos_3 = rndColor.getBgColorDecoy_2();
        Log.d(DEBUG_MESSAGE, "BGcolorPos_1:" + bgColorPos_1 + " BGcolorPos_2:" + bgColorPos_2
                + " BGcolorPos_3:" + bgColorPos_3);
        SetTxtColors(colorPos_1, colorPos_2, colorPos_3, bgColorPos_1, bgColorPos_2, bgColorPos_3);

        Random rndquestion = new Random();
        Question = rndquestion.nextInt(3);
        InitQuestions(Question);

        timer = new CountDownTimer(TimerValue, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                Log.d(DEBUG_MESSAGE, "TIMER IS:" + millisUntilFinished / 1000);
                milliLeft = millisUntilFinished;
            }

            @Override
            public void onFinish() {

                Log.d(DEBUG_MESSAGE, "TIMES UP!");
                Log.d(DEBUG_MESSAGE,"Category in Timer: "+Question);
                int answer_array[]=new int[]{numAns,colorPos_1,bgColorPos_1};
                Log.d(DEBUG_MESSAGE,"Timer answer is:"+answer_array[Question]);
                SharedPreferences pref_score = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor edit = pref_score.edit();
                edit.putInt(TAG_KEY, 0);
                edit.apply();
                cancel();
                AnimateShake(answer_array[Question],Question);
                DelayActivity();

            }
        }.start();
    }

    @Override
    public void onClick(View v) {

        int question = Question;
        int numAnswer = numPos_1,
                colorAnswer = colorPos_1,
                bgColorAnswer = bgColorPos_1;

        switch (v.getId()) {
            case R.id.btn_FirstNum:
                Log.d(DEBUG_MESSAGE, "onClick: First");
                GetFirstBtnValue(question, numAnswer, colorAnswer, bgColorAnswer);
                break;
            case R.id.btn_SecondNum:
                Log.d(DEBUG_MESSAGE, "onClick: Second");
                GetSecondBtnValue(question, numAnswer, colorAnswer, bgColorAnswer);
                break;
            case R.id.btn_ThirdNum:
                Log.d(DEBUG_MESSAGE, "onClick: Third");
                GetThirdBtnValue(question, numAnswer, colorAnswer, bgColorAnswer);
                break;
        }
    }

    private void InitQuestions(int Question) {

        switch (Question) {
            case 0:
                tv_question.setText("Pick the Correct ");
                tv_question1.setText(arrayQuestions[Question]);
                tv_question2.setText(" ");
                break;
            case 1:
                tv_question.setText("Pick the ");
                ColorPickerTxt(colorPos_1);
                tv_question2.setText(" " + arrayQuestions[Question]);
                break;
            case 2:
                tv_question.setText("Pick the ");
                ColorPickerBox(bgColorPos_1);
                tv_question2.setText(" " + arrayQuestions[Question]);
                break;
        }
        Log.d(DEBUG_MESSAGE, "Question Index is " + Question);
    }

    private void GetFirstBtnValue(int Question, int numAnswer, int colorAnswer, int bgColorAnswer) {

        switch (Question) {
            case 0:
                AnsNum = btn_firstNum.getText().toString();
                Log.d(DEBUG_MESSAGE, "Answer is: " + AnsNum);
                GetAnswer(Integer.valueOf(AnsNum), numAnswer, 0);
                break;
            case 1:
                ColorAns = btn_firstNum.getCurrentTextColor();
                Log.d(DEBUG_MESSAGE, "Text Color is: " + ColorAns);
                GetAnswer(ColorAns, colorAnswer, 1);
                break;
            case 2:
                ColorDrawable btnColor = (ColorDrawable) btn_firstNum.getBackground();
                BGColorAns = btnColor.getColor();
                Log.d(DEBUG_MESSAGE, "BG Color is: " + BGColorAns);
                GetAnswer(BGColorAns, bgColorAnswer, 2);
                break;
        }

    }

    private void GetSecondBtnValue(int Question, int numAnswer, int colorAnswer, int bgColorAnswer) {

        switch (Question) {
            case 0:
                AnsNum = btn_secondNum.getText().toString();
                Log.d(DEBUG_MESSAGE, "Answer is: " + AnsNum);
                GetAnswer(Integer.valueOf(AnsNum), numAnswer, 0);
                break;
            case 1:
                ColorAns = btn_secondNum.getCurrentTextColor();
                Log.d(DEBUG_MESSAGE, "Text Color is: " + ColorAns);
                GetAnswer(ColorAns, colorAnswer, 1);
                break;
            case 2:
                ColorDrawable btnColor = (ColorDrawable) btn_secondNum.getBackground();
                BGColorAns = btnColor.getColor();
                Log.d(DEBUG_MESSAGE, "BG Color is: " + BGColorAns);
                GetAnswer(BGColorAns, bgColorAnswer, 2);
                break;
        }

    }

    private void GetThirdBtnValue(int Question, int numAnswer, int colorAnswer, int bgColorAnswer) {

        switch (Question) {

            case 0:
                AnsNum = btn_thirdNum.getText().toString();
                Log.d(DEBUG_MESSAGE, "Answer is: " + AnsNum);
                GetAnswer(Integer.valueOf(AnsNum), numAnswer, 0);
                break;
            case 1:
                ColorAns = btn_thirdNum.getCurrentTextColor();
                Log.d(DEBUG_MESSAGE, "Text Color is: " + ColorAns);
                GetAnswer(ColorAns, colorAnswer, 1);
                break;
            case 2:
                ColorDrawable btnColor = (ColorDrawable) btn_thirdNum.getBackground();
                BGColorAns = btnColor.getColor();
                Log.d(DEBUG_MESSAGE, "BG Color is: " + BGColorAns);
                GetAnswer(BGColorAns, bgColorAnswer, 2);
                break;
        }

    }

    private void GetAnswer(int ansValue, int ans, int category) {

        Log.d(DEBUG_MESSAGE, "Your Choosen Answer: " + ansValue);
        Log.d(DEBUG_MESSAGE, "Correct Answer: " + ans);
        Log.d(DEBUG_MESSAGE, "Question category: " + category);
        if (ans == ansValue) {
            Log.d(DEBUG_MESSAGE, "Correct!");
            SharedPreferences pref_getScore = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            int newScore = pref_getScore.getInt(TAG_KEY, 0);
            newScore++;
            SharedPreferences pref_score = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor edit = pref_score.edit();
            edit.putInt(TAG_KEY, newScore);
            edit.apply();
            SlideRevealCorrect();
            StoreNewNum(numAns);
            Intent restartActivity = getIntent();
            finish();
            startActivity(restartActivity);
            timer.cancel();
        } else {

            Log.d(DEBUG_MESSAGE, "Wrong!");
            SharedPreferences pref_score = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor edit = pref_score.edit();
            edit.putInt(TAG_KEY, 0);
            edit.apply();
            timer.cancel();
            AnimateShake(ans,category);
            DelayActivity();

        }
    }

    private void ShufflePositionNumber(int numPos_1, int numPos_2, int numPos_3) {
        int finalNumber_1, finalNumber_2, finalNumber_3;
        List<Integer> numberList = Arrays.asList(new Integer[]{numPos_1, numPos_2, numPos_3});
        Collections.shuffle(numberList);
        finalNumber_1 = numberList.get(0);
        finalNumber_2 = numberList.get(1);
        finalNumber_3 = numberList.get(2);
        Log.d(DEBUG_MESSAGE, "finalNumber_1:" + finalNumber_1 + " final_Number_2:" + finalNumber_2
                + " finalNumber_3:" + finalNumber_3);
        SetTxtNumber(finalNumber_1, finalNumber_2, finalNumber_3);
    }

    private void SetTxtNumber(int finalNumber_1, int finalNumber_2, int finalNumber_3) {
        btn_firstNum.setText(String.valueOf(finalNumber_1));
        btn_secondNum.setText(String.valueOf(finalNumber_2));
        btn_thirdNum.setText(String.valueOf(finalNumber_3));
    }

    private void SetTxtColors(int colorPos_1, int colorPos_2, int colorPos_3,
                              int bgColorPos_1, int bgColorPos_2, int bgColorPos_3) {
        btn_firstNum.setTextColor(colorPos_1);
        btn_secondNum.setTextColor(colorPos_2);
        btn_thirdNum.setTextColor(colorPos_3);

        btn_firstNum.setBackgroundColor(bgColorPos_1);
        btn_secondNum.setBackgroundColor(bgColorPos_2);
        btn_thirdNum.setBackgroundColor(bgColorPos_3);

    }

    private void ColorPickerTxt(int colorPos_1) {
        switch (colorPos_1) {

            case -44462:
                tv_question1.setTextColor(colorPos_1);
                tv_question1.setText("RED");
                break;
            case -11751600:
                tv_question1.setTextColor(colorPos_1);
                tv_question1.setText("GREEN");
                break;
            case -12285185:
                tv_question1.setTextColor(colorPos_1);
                tv_question1.setText("BLUE");
                break;
        }
    }

    private void ColorPickerBox(int bgColorPos_1) {
        switch (bgColorPos_1) {

            case -44462:
                tv_question1.setTextColor(bgColorPos_1);
                tv_question1.setText("RED");
                break;
            case -11751600:
                tv_question1.setTextColor(bgColorPos_1);
                tv_question1.setText("GREEN");
                break;
            case -12285185:
                tv_question1.setTextColor(bgColorPos_1);
                tv_question1.setText("BLUE");
                break;
        }
    }

    int x;

    private int GenerateNewNum(int score) {

        Log.d(DEBUG_MESSAGE, "score is :" + score);
        if (score < 1) {
            x = 2;
        } else {
            SharedPreferences getStoreNumAns = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            x = getStoreNumAns.getInt(TAG_NUMBER, 0);
            if (x >= 100) {
                x = 10;
            }
        }
        return x;
    }

    private int GenerateSecondNum(int x) {
        int xnum;
        Random rnd = new Random();
        xnum = rnd.nextInt(x / 2) + 1;
        Math.round(xnum);
        return xnum;
    }

    private void StoreNewNum(int numAns) {
        SharedPreferences storeNumAns = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = storeNumAns.edit();
        editor.putInt(TAG_NUMBER, numAns);
        editor.apply();
    }

    private void StoreHighScore(int score) {
        Log.d(DEBUG_MESSAGE, "checking highscore: " + score);
        dbHelper.UpdateDBScore(1, score);
    }

    private class RandomNumber {
        int numDecoy_1,
                numDecoy_2;

        public RandomNumber(int numDecoy_1, int numDecoy_2) {
            this.numDecoy_1 = numDecoy_1;
            this.numDecoy_2 = numDecoy_2;
        }

        public int getNumDecoy_1() {
            return numDecoy_1;
        }

        public int getNumDecoy_2() {
            return numDecoy_2;
        }
    }

    public RandomNumber GenerateRndNum(int max) {
        boolean i = true;
        Log.d(DEBUG_MESSAGE, "int max is " + max);
        int numDecoy_1, numDecoy_2;
        do {
            Random rnd = new Random();
            numDecoy_1 = rnd.nextInt(max);
            numDecoy_2 = rnd.nextInt(max - 1);
            if (numDecoy_1 != numDecoy_2 && numDecoy_1 != 0 & numDecoy_2 != 0 &
                    numDecoy_2 != numDecoy_1) {
                i = false;
            }
        } while (i);

        return new RandomNumber(numDecoy_1, numDecoy_2);
    }

    private class RandomColor {
        int txtColor,
                txtColorDecoy_1,
                txtColorDecoy_2;
        int bgColor,
                bgColorDecoy_1,
                bgColorDecoy_2;

        public RandomColor(int txtColor, int txtColorDecoy_1, int txtColorDecoy_2, int bgColor,
                           int bgColorDecoy_1, int bgColorDecoy_2) {
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

    public RandomColor GenerateRndColor() {
        boolean i = true;
        int txtColor, txtColorDecoy_1, txtColorDecoy_2,
                bgColor, bgColorDecoy_1, bgColorDecoy_2;

        do {
            List<Integer> TxtcolorList = Arrays.asList(new Integer[]{textRed, textBlue, textGreen});
            Collections.shuffle(TxtcolorList);
            txtColor = TxtcolorList.get(0);
            txtColorDecoy_1 = TxtcolorList.get(1);
            txtColorDecoy_2 = TxtcolorList.get(2);

            List<Integer> BGcolorList = Arrays.asList(new Integer[]{textRed, textBlue, textGreen});
            Collections.shuffle(BGcolorList);
            bgColor = BGcolorList.get(0);
            bgColorDecoy_1 = BGcolorList.get(1);
            bgColorDecoy_2 = BGcolorList.get(2);

            if (bgColor != txtColor & txtColorDecoy_1 != bgColorDecoy_1 & txtColorDecoy_2 != bgColorDecoy_2) {
                i = false;
            }

        } while (i);

        return new RandomColor(txtColor, txtColorDecoy_1, txtColorDecoy_2, bgColor,
                bgColorDecoy_1, bgColorDecoy_2);
    }

    private void SlideRevealGameOver() {
        final ViewGroup view = (ViewGroup) findViewById(R.id.activity_addition);
        TransitionManager.beginDelayedTransition(view, new com.transitionseverywhere.Slide(Gravity.LEFT));
        view.setVisibility(View.GONE);
    }

    private void SlideRevealCorrect() {
        final ViewGroup view = (ViewGroup) findViewById(R.id.activity_addition);
        TransitionManager.beginDelayedTransition(view, new com.transitionseverywhere.Slide(Gravity.RIGHT));
        view.setVisibility(View.GONE);
    }

    int backpress_count = 2;

    @Override
    public void onBackPressed() {
        backpress_count--;
        Log.d(DEBUG_MESSAGE,"Backpress Category:"+Question);
        int answer_array[]=new int[]{numAns,colorPos_1,bgColorPos_1};
        Log.d(DEBUG_MESSAGE,"Backpress Correct Answer:"+answer_array[Question]);
        BackPressCounter(backpress_count,answer_array[Question],Question);
    }

    private void BackPressCounter(int count, final int ans, final int category) {

        Log.d(DEBUG_MESSAGE, "BACK PRESS COUNT: " + count);

        if (count == 0) {
            //do nothing...
            Log.d(DEBUG_MESSAGE, "DO NOTHING...");
        } else if (count < 0) {
            ClosingActivity();
        } else {
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
                            TimerLeft(milliLeft - 1000,ans,category);
                            gifDrawable.start();
                        }
                    });
            AlertDialog dialog = alertDialog.create();
            dialog.show();
        }
    }

    private void TimerPause() {

        timer.cancel();
    }

    private void TimerLeft(long milliLeft, final int ans, final int category) {
        timer = new CountDownTimer(milliLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(DEBUG_MESSAGE, "TIMER IS: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                Log.d(DEBUG_MESSAGE, "TIMES UP!");
                SharedPreferences pref_score = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor edit = pref_score.edit();
                edit.putInt(TAG_KEY, 0);
                edit.apply();
                cancel();
                AnimateShake(ans,category);
                DelayActivity();
            }
        }.start();
    }

    private void HideActivity() {

        View view = findViewById(R.id.activity_addition);
        view.setVisibility(View.INVISIBLE);
    }

    private void RevealActivity() {

        View view = findViewById(R.id.activity_addition);
        view.setVisibility(View.VISIBLE);
    }

    public void ClosingActivity() {

        timer.cancel();
        SharedPreferences pref_score = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = pref_score.edit();
        edit.putInt(TAG_KEY, 0);
        edit.apply();
        Intent startGame = new Intent(this, StartGame.class);
        finish();
        startActivity(startGame);
    }

    private void AnimateShake(int ans, int category) {

        switch (category) {
            case 0:
                Log.d(DEBUG_MESSAGE, "Correct Answer");
                FindBtnAnswer(ans);
                break;
            case 1:
                Log.d(DEBUG_MESSAGE, "Correct Color");
                FindBtnColor(ans);
                break;
            case 2:
                Log.d(DEBUG_MESSAGE, "Correct background Color");
                FindBtnBGColor(ans);
                break;
        }
    }

    private void FindBtnAnswer(int ans) {
        int answer;
        boolean i = true;
        Button[] btn_array = new Button[]{btn_firstNum, btn_secondNum, btn_thirdNum};
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        int index = 0;
        do {
            answer = Integer.valueOf(btn_array[index].getText().toString());
            if (answer == ans) {
                i = false;
                btn_array[index].setAnimation(shake);
            } else {
                index++;
            }
        } while (i);
    }

    private void FindBtnColor(int ans) {
        int answer;
        boolean i = true;
        Button[] btn_array = new Button[]{btn_firstNum, btn_secondNum, btn_thirdNum};
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        int index = 0;
        do {
            answer = btn_array[index].getCurrentTextColor();
            if (answer == ans) {
                i = false;
                btn_array[index].setAnimation(shake);
            } else {
                index++;
            }
        } while (i);

    }

    private void FindBtnBGColor(int ans) {
        int answer;
        boolean i = true;
        ColorDrawable BG_btn1 = (ColorDrawable) btn_firstNum.getBackground();
        ColorDrawable BG_btn2 = (ColorDrawable) btn_secondNum.getBackground();
        ColorDrawable BG_btn3 = (ColorDrawable) btn_thirdNum.getBackground();
        ColorDrawable[] BG_btnarray = new ColorDrawable[]{BG_btn1, BG_btn2, BG_btn3};
        Button[] btn_array = new Button[]{btn_firstNum, btn_secondNum, btn_thirdNum};
        Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        int index = 0;
        do {
            answer = BG_btnarray[index].getColor();
            if (answer == ans) {
                i = false;
                btn_array[index].setAnimation(shake);
            } else {
                index++;
            }
        } while (i);
    }

    private void DelayActivity() {

        for (int counter = 0;counter<=20000;counter++){
            Log.d(DEBUG_MESSAGE,String.valueOf(counter));
            if (counter==3000){
                CallGameOver();
            }
        }

    }

    private void CallGameOver(){
        SlideRevealGameOver();
        Intent gameOver = new Intent(getApplicationContext(), GameOver.class);
        finish();
        StoreHighScore(score);
        gameOver.putExtra(TAG_BUNDLE_SCORE, score);
        gameOver.putExtra(TAG_BUNDLE_PK, 1);
        startActivity(gameOver);
    }


}
