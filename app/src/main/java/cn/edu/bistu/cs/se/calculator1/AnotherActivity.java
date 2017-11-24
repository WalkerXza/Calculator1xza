package cn.edu.bistu.cs.se.calculator1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

import bsh.EvalError;
import bsh.Interpreter;

public class AnotherActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editText = null;
    boolean isClear = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        Button button = (Button)findViewById(R.id.btn_back);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Intent intent = new Intent(AnotherActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        editText = (EditText)findViewById(R.id.editText);
        Button btn_cf = (Button)findViewById(R.id.btn_cf);
        Button btn_fc = (Button)findViewById(R.id.btn_fc);
        Button btn_rd = (Button)findViewById(R.id.btn_rd);
        Button btn_dr = (Button)findViewById(R.id.btn_dr);
        Button btn_cc = (Button)findViewById(R.id.btn_cc);

        Button btn_00 = (Button)findViewById(R.id.btn_00);
        Button btn_11 = (Button)findViewById(R.id.btn_11);
        Button btn_22 = (Button)findViewById(R.id.btn_22);
        Button btn_33 = (Button)findViewById(R.id.btn_33);
        Button btn_44 = (Button)findViewById(R.id.btn_44);
        Button btn_55 = (Button)findViewById(R.id.btn_55);
        Button btn_66 = (Button)findViewById(R.id.btn_66);
        Button btn_77 = (Button)findViewById(R.id.btn_77);
        Button btn_88 = (Button)findViewById(R.id.btn_88);
        Button btn_99 = (Button)findViewById(R.id.btn_99);
        Button btn_10to2 = (Button)findViewById(R.id.btn_10to2);


        btn_00.setOnClickListener(this);
        btn_11.setOnClickListener(this);
        btn_22.setOnClickListener(this);
        btn_33.setOnClickListener(this);
        btn_44.setOnClickListener(this);
        btn_55.setOnClickListener(this);
        btn_66.setOnClickListener(this);
        btn_77.setOnClickListener(this);
        btn_88.setOnClickListener(this);
        btn_99.setOnClickListener(this);
        btn_10to2.setOnClickListener(this);
        btn_cc.setOnClickListener(this);
        btn_cf.setOnClickListener(this);
        btn_fc.setOnClickListener(this);
        btn_dr.setOnClickListener(this);
        btn_rd.setOnClickListener(this);
    }

    @Override
    public void onClick(View e) {
        Button btn = (Button)e;
        String exp = editText.getText().toString();
        if(isClear &&(
                btn.getText().equals("0")
                        ||btn.getText().equals("1")
                        ||btn.getText().equals("2")
                        ||btn.getText().equals("3")
                        ||btn.getText().equals("4")
                        ||btn.getText().equals("5")
                        ||btn.getText().equals("6")
                        ||btn.getText().equals("7")
                        ||btn.getText().equals("8")
                        ||btn.getText().equals("9"))
                ||btn.getText().equals("算数公式错误")){
            editText.setText("");
            isClear = false;
        }
        if(btn.getText().equals("c")){
            editText.setText("");
        }
        //实现单位换算

        else if(btn.getText().equals("10→2")){
            TextView sart=(TextView) findViewById(R.id.editText);
            String s=Integer.toBinaryString(Integer.parseInt(sart.getText().toString()));
            editText.setText(s);
        }
        else if(btn.getText().equals("℃→℉")){
            TextView sart=(TextView) findViewById(R.id.editText);
            double a=Double.parseDouble(sart.getText().toString());
            double f=32+a*1.8;
            editText.setText(Double.toString(f));
        }
        else if(btn.getText().equals("℉→℃")){
            TextView sart=(TextView) findViewById(R.id.editText);
            double a=Double.parseDouble(sart.getText().toString());
            double c=(a-32)/1.8;
            editText.setText(Double.toString(c));
        }
        else if(btn.getText().equals("¥→$")){
            TextView sart=(TextView) findViewById(R.id.editText);
            double a=Double.parseDouble(sart.getText().toString());
            double D=a*0.1497;
            editText.setText(Double.toString(D));
        }
        else if(btn.getText().equals("$→¥")){
            TextView sart=(TextView) findViewById(R.id.editText);
            double a=Double.parseDouble(sart.getText().toString());
            double R=a*6.6798;
            editText.setText(Double.toString(R));
        }
        else{
            editText.setText(editText.getText()+""+btn.getText());
            isClear = false;
        }
        //操作完成后始终保持光标在最后一位
        editText.setSelection(editText.getText().length());
    }

    /***
     * @param  exp 算数表达式
     * @return 根据表达式返回结果
     */
    private String getRs(String exp){
        Interpreter bsh = new Interpreter();
        Number result = null;
        try {
            exp = filterExp(exp);
            result = (Number)bsh.eval(exp);
        } catch (EvalError e) {
            e.printStackTrace();
            isClear = true;
            return "算数公式错误";
        }
        exp = result.doubleValue()+"";
        if(exp.endsWith(".0"))
            exp = exp.substring(0, exp.indexOf(".0"));
        return exp;
    }


    /**
     * 因为计算过程中,全程需要有小数参与,所以需要过滤一下
     * @param exp 算数表达式
     * @return
     */
    private String filterExp(String exp) {
        String num[] = exp.split("");
        String temp = null;
        int begin=0,end=0;
        for (int i = 1; i < num.length; i++) {
            temp = num[i];
            if(temp.matches("[+-/()*]")){
                if(temp.equals(".")) continue;
                end = i - 1;
                temp = exp.substring(begin, end);
                if(temp.trim().length() > 0 && temp.indexOf(".")<0)
                    num[i-1] = num[i-1]+".0";
                begin = end + 1;
            }
        }
        return Arrays.toString(num).replaceAll("[\\[\\], ]", "");
    }

    /***
     * @param str
     * @return 字符串非空验证
     */
    private boolean isEmpty(String str){
        return (str==null || str.trim().length()==0);
    }

}

