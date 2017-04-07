package com.androidprojects.greggy.funmath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class Subtraction extends AppCompatActivity implements View.OnClickListener{

    TextView tv_score,tv_questionNum,tv_question1,tv_question2,
                tv_question3;
    Button btn_sub1,btn_sub2,btn_sub3;

    String TAG_MESSAGE= "DEBUG_MESSAGE";

    int numAns,numPos_1,numPos_2,numPos_3;

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

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_sub_1:
                Log.d(TAG_MESSAGE,"clicked "+btn_sub1.getText().toString());
                break;
            case R.id.btn_sub_2:
                Log.d(TAG_MESSAGE,"clicked "+btn_sub2.getText().toString());
                break;
            case R.id.btn_sub_3:
                Log.d(TAG_MESSAGE,"clicked "+btn_sub3.getText().toString());
                break;
        }
    }

    private int GenerateSecondNum(int x){
        int y;
        Random rnd = new Random();
        y= rnd.nextInt(x/2)+1;
        return y;
    }

    private int[] RandomNumber(int numAns){
        int[] numberArray = new int[2];
        boolean i = true;
        Random rnd = new Random();
        do {
            numberArray[0] = rnd.nextInt(numAns);
            numberArray[1] = rnd.nextInt(numAns-1)+1;
            if (numberArray[0]!=numberArray[1]&numberArray[0]!=0&
                    numberArray[1]!=0){
                i=false;
            }
        }
        while (i);
        return  numberArray;
    }
}
