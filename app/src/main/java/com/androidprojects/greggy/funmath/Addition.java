package com.androidprojects.greggy.funmath;

import android.graphics.Color;
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
    TextView tv_score,tv_timer,tv_questionNum,tv_question;

    String DEBUG_MESSAGE = "MESSAGE";

    int x = 2,
        y = x,
        numAns,
        numPos_1,
        numPos_2,
        numPos_3;

        int textRed = Color.rgb(255,0,0),
            textGreen = Color.rgb(0,255,0),
            textBlue = Color.rgb(0,0,255);
        int colorPos_1,colorPos_2,colorPos_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);

        tv_score = (TextView) findViewById(R.id.tv_addscore);
        tv_timer = (TextView) findViewById(R.id.tv_addtimer);
        tv_questionNum = (TextView) findViewById(R.id.tv_addNumQuestion);
        tv_question = (TextView) findViewById(R.id.tv_addQuestion);

        btn_firstNum =(Button) findViewById(R.id.btn_FirstNum);
        btn_firstNum.setOnClickListener(this);

        btn_secondNum =(Button) findViewById(R.id.btn_SecondNum);
        btn_secondNum.setOnClickListener(this);

        btn_thirdNum =(Button) findViewById(R.id.btn_ThirdNum);
        btn_thirdNum.setOnClickListener(this);

        numAns = x+y;
        RandomNumber rndNumber = GenerateRndNum(numAns);
        numPos_1 = numAns;
        numPos_2 = rndNumber.getNumDecoy_1();
        numPos_3 = rndNumber.getNumDecoy_2();
        Log.d(DEBUG_MESSAGE,"numPos_1:"+numAns+" numPos_2:"+numPos_2+" numPos_3:"+numPos_3);

        RandomTxtColor rndTxtColor = GenerateRndTxtColor();
        colorPos_1 = rndTxtColor.getTxtColor();
        colorPos_2 = rndTxtColor.getTxtColorDecoy_1();
        colorPos_3 = rndTxtColor.getTxtColorDecoy_2();
        Log.d(DEBUG_MESSAGE,"colorPos_1:"+colorPos_1+" colorPos_2:"+colorPos_2+" colorPos_3:"+colorPos_3);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_FirstNum:
                Log.d(DEBUG_MESSAGE, "onClick: First");
                break;
            case R.id.btn_SecondNum:
                Log.d(DEBUG_MESSAGE, "onClick: Second");
                break;
            case R.id.btn_ThirdNum:
                Log.d(DEBUG_MESSAGE, "onClick: Third");
                break;
        }
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
            numDecoy_1 = rnd.nextInt(max-1);
            numDecoy_2 = rnd.nextInt(max-1)+1;
            if(numDecoy_1!=numDecoy_2&&numDecoy_1!=0){
                i = false;
            }
        }while (i);

        return new RandomNumber(numDecoy_1,numDecoy_2);
    }

    private class RandomTxtColor{
        int txtColor,
            txtColorDecoy_1,
            txtColorDecoy_2;

        public RandomTxtColor(int txtColor, int txtColorDecoy_1, int txtColorDecoy_2) {
            this.txtColor = txtColor;
            this.txtColorDecoy_1 = txtColorDecoy_1;
            this.txtColorDecoy_2 = txtColorDecoy_2;
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
    }

    public RandomTxtColor GenerateRndTxtColor(){
        int txtColor,txtColorDecoy_1,txtColorDecoy_2;
        List<Integer> colorList = Arrays.asList(new Integer[]{textRed,textBlue,textGreen});
        Collections.shuffle(colorList);
        txtColor = colorList.get(0);
        txtColorDecoy_1 = colorList.get(1);
        txtColorDecoy_2=colorList.get(2);
        return new RandomTxtColor(txtColor,txtColorDecoy_1,txtColorDecoy_2);
    }

}
