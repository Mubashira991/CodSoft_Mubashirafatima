package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import  org.mozilla.javascript.Scriptable;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialButton BtnClear,BtnOpenBrac,BtnCloseBrac,BtnBacksp,BtnEquals;
    MaterialButton Btn0,Btn1,Btn2,Btn3,Btn4,Btn5,Btn6,Btn7,Btn8,Btn9;
    MaterialButton BtnAdd,BtnSub,BtnMul,BtnDiv,BtnDot;

    public TextView answerField,inputField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Assigning ids to buttons
        BtnClear = findViewById(R.id.btnC);
        BtnOpenBrac = findViewById(R.id.btn_openbrac);
        BtnCloseBrac = findViewById(R.id.btn_closebrac);
        BtnBacksp = findViewById(R.id.btnbacksp);
        Btn0 = findViewById(R.id.btn0);
        Btn1 = findViewById(R.id.btn1);
        Btn2 = findViewById(R.id.btn2);
        Btn3 = findViewById(R.id.btn3);
        Btn4 = findViewById(R.id.btn4);
        Btn5 = findViewById(R.id.btn5);
        Btn6 = findViewById(R.id.btn6);
        Btn7 = findViewById(R.id.btn7);
        Btn8 = findViewById(R.id.btn8);
        Btn9 = findViewById(R.id.btn9);
        BtnAdd = findViewById(R.id.btn_plus);
        BtnSub = findViewById(R.id.btn_minus);
        BtnMul = findViewById(R.id.btn_mul);
        BtnDiv = findViewById(R.id.btn_divide);
        BtnDot = findViewById(R.id.btn_dot);
        BtnEquals = findViewById(R.id.btn_equal);

//        Initializing text fields
        inputField = findViewById(R.id.input);
        answerField = findViewById(R.id.answer);

//        Defining click Events
        Btn0.setOnClickListener(this);
        Btn1.setOnClickListener(this);
        Btn2.setOnClickListener(this);
        Btn3.setOnClickListener(this);
        Btn4.setOnClickListener(this);
        Btn5.setOnClickListener(this);
        Btn6.setOnClickListener(this);
        Btn7.setOnClickListener(this);
        Btn8.setOnClickListener(this);
        Btn9.setOnClickListener(this);
        BtnDot.setOnClickListener(this);
        BtnAdd.setOnClickListener(this);
        BtnSub .setOnClickListener(this);
        BtnMul.setOnClickListener(this);
        BtnDiv.setOnClickListener(this);
        BtnOpenBrac.setOnClickListener(this);
        BtnCloseBrac.setOnClickListener(this);

        BtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerField.setText("");
                inputField.setText("");
            }
        });

        BtnBacksp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String n  = inputField.getText().toString();
                if(n.isEmpty()){
                    Toast.makeText(MainActivity.this, "Nothing to erase", Toast.LENGTH_SHORT).show();
                }
                else{
                    inputField.setText(n.subSequence(0,n.length()-1));
                    answerField.setText(n.subSequence(0,n.length()-1));

                }

            }
        });

        BtnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputField.setText(answerField.getText());
                return;
            }
        });





    }

    String getResult(String data){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"Javascript",1,null).toString();

            return finalResult;
        }
        catch (Exception e){
            return "Error";
        }
    }

    @Override
    public void onClick(View view) {
        MaterialButton btn = (MaterialButton)view;
        String text = btn.getText().toString();
        inputField.setText(inputField.getText() + text);
        String inputText = inputField.getText().toString();
        String finalResult = getResult(inputText);
        if(finalResult.endsWith(".0")){
            finalResult=finalResult.replace(".0","");}
        if(!finalResult.equals("Error")){
//            inputField.setText(finalResult);
            answerField.setText(finalResult);


        }

    }
}