package com.androidprojects.greggy.funmath;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.io.IOException;

import pl.droidsonroids.gif.GifImageView;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class Tutorial extends AppCompatActivity implements View.OnClickListener{

    TextView tv_FUN,tv_MATH,tv_questionNum,tv_question,tv_question1,tv_question2;
    Button btn_firstnum,btn_secondnum,btn_thirdnum,imgbtn_score;
    GifImageView gif_timer;

    String TAG_MESSAGE = "DEBUG MESSAGE: ";

    int textRed = Color.rgb(255,82,82),
            textGreen = Color.rgb(76,175,80),
            textBlue = Color.rgb(68,138,255);
    public static final String PREFS_NAME = "User_Demo";
    String TAG_KEY="demo_number",
            TAG_SCORE="demo_score";

    SharedPreferences demoNum;
    int demonum,score;

    String SHOWCASE_ID= "sequence showcase";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);


        tv_FUN=(TextView)findViewById(R.id.tv_tutFUN);
        tv_MATH=(TextView)findViewById(R.id.tv_tutMATH);
        tv_questionNum=(TextView)findViewById(R.id.tv_tutNumQuestion);
        tv_question=(TextView)findViewById(R.id.tv_tutQuestion);
        tv_question1=(TextView)findViewById(R.id.tv_tutQuestion_1);
        tv_question2=(TextView)findViewById(R.id.tv_tutQuestion_2);
        imgbtn_score=(Button)findViewById(R.id.btn_tutscore);
        gif_timer=(GifImageView)findViewById(R.id.iv_tuttimer);

        btn_firstnum=(Button)findViewById(R.id.btn_tutFirstNum);
        btn_firstnum.setOnClickListener(this);
        btn_secondnum=(Button)findViewById(R.id.btn_tutSecondNum);
        btn_secondnum.setOnClickListener(this);
        btn_thirdnum=(Button)findViewById(R.id.btn_tutThirdNum);
        btn_thirdnum.setOnClickListener(this);

        ShowCaseDemo();

        SetupDemo();
        demoNum = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        demonum = demoNum.getInt(TAG_KEY,0);
        score=demoNum.getInt(TAG_SCORE,0);
        imgbtn_score.setText(String.valueOf(score));

        Log.d(TAG_MESSAGE,"demo number is "+demonum);
        DemoCaller(demonum);
    }

    @Override
    public void onClick(View v) {

        Log.d(TAG_MESSAGE,"demo number is now "+demonum);
        switch (v.getId()){

            case R.id.btn_tutFirstNum:
                Log.d(TAG_MESSAGE,"Please pick the correct answer...");
                DemoAnimationCaller(demonum);
                break;
            case R.id.btn_tutSecondNum:
                if (demonum==0){
                    GetCorrectAnswer();
                }
                else if(demonum ==2){
                    Log.d(TAG_MESSAGE,"finished tutorial...");
                    OpenAlertDialog();
                }
                else {
                    Log.d(TAG_MESSAGE,"Please pick the correct answer...");
                    DemoAnimationCaller(demonum);
                }
                break;
            case R.id.btn_tutThirdNum:
                if (demonum==1){
                    GetCorrectColor();
                }
                else {
                    Log.d(TAG_MESSAGE,"Please pick the correct answer...");
                    DemoAnimationCaller(demonum);
                }
                break;
        }
    }

    private void SetupDemo(){
        btn_firstnum.setText("1");
        btn_firstnum.setTextColor(textBlue);
        btn_firstnum.setBackgroundColor(textRed);

        btn_secondnum.setText("4");
        btn_secondnum.setTextColor(textGreen);
        btn_secondnum.setBackgroundColor(textBlue);

        btn_thirdnum.setText("3");
        btn_thirdnum.setTextColor(textRed);
        btn_thirdnum.setBackgroundColor(textGreen);
    }

    private void DemoCaller(int demonum){

        switch (demonum){

            case 0:
                DemoCorrectAnswer();
                break;
            case 1:
                DemoCorrectColor();
                break;
            case 2:
                DemoCorrectBackgroundColor();
                break;
        }
    }

    private void DemoAnimationCaller(int demonum){

        switch (demonum){

            case 0:
                SwingCorrectAnswer();
                break;
            case 1:
                SwingCorrectColor();
                break;
            case 2:
                SwingCorrectAnswer();
                break;
        }
    }

    private void DemoCorrectAnswer(){
        tv_question.setText("Pick the ");
        tv_question1.setText("CORRECT ");
        tv_question2.setText("Answer ");
    }

    private void GetCorrectAnswer(){
        demoNum = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = demoNum.edit();
        edit.putInt(TAG_KEY,1);
        edit.putInt(TAG_SCORE,1);
        edit.apply();
        RefreshActivity();
    }

    private void DemoCorrectColor(){
        tv_question.setText("Pick the ");
        tv_question1.setText("RED ");
        tv_question1.setTextColor(textRed);
        tv_question2.setText("Number ");

    }

    private void GetCorrectColor(){
        demoNum = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = demoNum.edit();
        edit.putInt(TAG_KEY,2);
        edit.putInt(TAG_SCORE,2);
        edit.apply();
        RefreshActivity();
    }

    private void DemoCorrectBackgroundColor(){
        tv_question.setText("Pick the ");
        tv_question1.setText("BLUE ");
        tv_question1.setTextColor(textBlue);
        tv_question2.setText("Box ");
    }

    private void RefreshActivity(){
        Intent reset = getIntent();
        finish();
        startActivity(reset);
    }

    public void RestartPrefs(){
        demoNum = getApplicationContext().getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor edit = demoNum.edit();
        edit.putInt(TAG_KEY,0);
        edit.putInt(TAG_SCORE,0);
        edit.apply();
    }

    private void OpenAlertDialog(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Congratulations!");
        alertDialog.setMessage("You have just finished the tutorial.");
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RestartPrefs();
                CloseActivity();
                ResetShowCaseView();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    private void ShowCaseDemo() {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500);

        MaterialShowcaseSequence materialShowcaseSequence = new MaterialShowcaseSequence(this,SHOWCASE_ID);

        materialShowcaseSequence.setConfig(config);

        materialShowcaseSequence.addSequenceItem(
          new MaterialShowcaseView.Builder(this)
                .setTarget(gif_timer)
                .setDismissText("GOT IT")
                .setContentText("This is the timer, when it ends GAME OVER but for now just a display.")
                .setDismissOnTargetTouch(true)
                .withCircleShape()
                .build()
        );

        materialShowcaseSequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                .setTarget(imgbtn_score)
                .setDismissText("GOT IT")
                .setDismissOnTargetTouch(true)
                .setContentText("This is your score...")
                .withCircleShape()
                .build()

        );

        materialShowcaseSequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                .setTarget(tv_question)
                .setDismissText("GOT IT")
                .setContentText("Pay attention to the indicated questions. Sometimes they can be tricky.")
                .withRectangleShape(true)
                .build()
        );

        materialShowcaseSequence.start();
    }

    private void ShowDialogEndTut(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage("Do you want to quit tutorial?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ResetShowCaseView();
                CloseActivity();
                RestartPrefs();

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        ShowDialogEndTut();
    }


    public void ResetShowCaseView(){
        MaterialShowcaseView.resetSingleUse(getApplicationContext(),SHOWCASE_ID);
    }

    private void CloseActivity(){
        this.finish();
    }

    private void SwingCorrectAnswer(){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        btn_secondnum.startAnimation(animation);
    }

    private void SwingCorrectColor(){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        btn_thirdnum.startAnimation(animation);

    }
}
