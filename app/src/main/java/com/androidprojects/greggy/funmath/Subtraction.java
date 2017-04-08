package com.androidprojects.greggy.funmath;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Subtraction extends AppCompatActivity implements View.OnClickListener{

    TextView tv_score,tv_questionNum,tv_question1,tv_question2,
                tv_question3;
    Button btn_sub1,btn_sub2,btn_sub3;

    String TAG_MESSAGE= "TAG_MESSAGE";

    int numAns,numPos_1,numPos_2,numPos_3,
        colorPos_1,colorPos_2,colorPos_3,
        bgColorPos_1,bgColorPos_2,bgColorPos_3;

    int colorRed = Color.RED,
        colorGreen = Color.GREEN,
        colorBlue = Color.BLUE;

    int Questions;
    String arrayQuestions[] = new String[]{"Answer","Number","Box"};

    String AnsNum;
    int ColorAns,BGColorAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtraction);

        tv_score = (TextView)findViewById(R.id.tv_subscore);
        tv_questionNum = (TextView)findViewById(R.id.tv_subquestionNum);
        tv_question1 = (TextView)findViewById(R.id.tv_subquestion_1);
        tv_question2 = (TextView)findViewById(R.id.tv_subquestion_2);
        tv_question3 = (TextView)findViewById(R.id.tv_subquestion_3);

        btn_sub1 = (Button)findViewById(R.id.btn_sub_1);
        btn_sub1.setOnClickListener(this);
        btn_sub2 = (Button)findViewById(R.id.btn_sub_2);
        btn_sub2.setOnClickListener(this);
        btn_sub3 = (Button)findViewById(R.id.btn_sub_3);
        btn_sub3.setOnClickListener(this);

        int x = 4,
            y = GenerateSecondNum(x);
        numAns = x-y;
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
        y= rnd.nextInt(x/2)+1;
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

            case -65536:
                tv_question2.setTextColor(colorPos_1);
                tv_question2.setText("RED");
                break;
            case -16711936:
                tv_question2.setTextColor(colorPos_1);
                tv_question2.setText("GREEN");
                break;
            case -16776961:
                tv_question2.setTextColor(colorPos_1);
                tv_question2.setText("BLUE");
                break;

        }

    }

    private void ColorPickerBox(int bgColorPos_1){
        switch (bgColorPos_1){

            case -65536:
                tv_question2.setTextColor(bgColorPos_1);
                tv_question2.setText("RED");
                break;
            case -16711936:
                tv_question2.setTextColor(bgColorPos_1);
                tv_question2.setText("GREEN");
                break;
            case -16776961:
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
                break;
            case 1:
                ColorAns = btn_sub1.getCurrentTextColor();
                Log.d(TAG_MESSAGE,"Text Color is: "+ColorAns);
                break;
            case 2:
                ColorDrawable btnColor = (ColorDrawable) btn_sub1.getBackground();
                BGColorAns = btnColor.getColor();
                Log.d(TAG_MESSAGE,"BG Color is: "+BGColorAns);
                break;
        }

    }

    private void GetSecondBtnValue(int Question,int numAnswer,int colorAnswer,int bgColorAnswer){
        switch (Question){
            case 0:
                AnsNum = btn_sub2.getText().toString();
                Log.d(TAG_MESSAGE,"Answer is: "+AnsNum);
                break;
            case 1:
                ColorAns = btn_sub2.getCurrentTextColor();
                Log.d(TAG_MESSAGE,"Text Color is: "+ColorAns);
                break;
            case 2:
                ColorDrawable btnColor = (ColorDrawable) btn_sub2.getBackground();
                BGColorAns = btnColor.getColor();
                Log.d(TAG_MESSAGE,"BG Color is: "+BGColorAns);
                break;
        }

    }

    private void GetThirdBtnValue(int Question,int numAnswer,int colorAnswer,int bgColorAnswer){
        switch (Question){
            case 0:
                AnsNum = btn_sub3.getText().toString();
                Log.d(TAG_MESSAGE,"Answer is: "+AnsNum);

                break;
            case 1:
                ColorAns = btn_sub3.getCurrentTextColor();
                Log.d(TAG_MESSAGE,"Text Color is: "+ColorAns);
                break;
            case 2:
                ColorDrawable btnColor = (ColorDrawable) btn_sub3.getBackground();
                BGColorAns = btnColor.getColor();
                Log.d(TAG_MESSAGE,"BG Color is: "+BGColorAns);
                break;
        }

    }


    private void TestFinish(){

        Intent restart = getIntent();
        finish();
        startActivity(restart);
    }
}
