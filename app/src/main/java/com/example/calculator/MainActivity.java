package com.example.calculator;
import java.util.Stack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView rs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rs=(TextView)findViewById(R.id.result);
    }
    public void number(View view){
        if(rs.getText().equals("Invalid Expression"))
            rs.setText("");
        Button btn=(Button)view;
        String text=rs.getText().toString()+btn.getText();
        rs.setText(text);
    }
    public void result(View view){

            String exp=rs.getText().toString().replace("%","*1/100*");
            try {
                rs.setText("" + evaluatePostfix(infixToPostfix(exp)));
            }
            catch (Exception e){
                rs.setText("Invalid Expression");

            }
    }
    public void clear(View view){
        rs.setText("");
    }

    public int Prec(char ch)
    {
        switch (ch)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;


        }
        return -1;
    }
    public String infixToPostfix(String exp)
    {
        String result = "";

          Stack<Character> stack = new Stack<>();

        for (int i = 0; i<exp.length(); ++i)
        {
            char c = exp.charAt(i);
            if (Character.isDigit(c))
                result+= c;

            else // an operator is encountered
            { 	result+=" ";
                while (!stack.isEmpty() && Prec(c)
                        <= Prec(stack.peek()))
                    result += stack.pop();


                stack.push(c);
            }

        }


        while (!stack.isEmpty()){
            result += stack.pop();
        }
        return result;
    }

    public  float evaluatePostfix(String exp)
    {
        //create a stack
        Stack<Float> stack = new Stack<>();
        String d="";
        // Scan all characters on e by one
        for(int i = 0; i < exp.length(); i++)
        {
            char c = exp.charAt(i);

            if(c == ' '){

                if(d.length()>0)
                    stack.push(Float.parseFloat(d));
                d="";

            }


            else if(Character.isDigit(c))
                d+=c;



          else
            {
                System.out.println(c);
                if(d.length()>0) {
                    stack.push(Float.parseFloat(d));
                    d="";
                }
                float val1 = stack.pop();
                float val2 = stack.pop();

                switch(c)
                {
                    case '+':
                        stack.push(val2+val1);
                        break;

                    case '-':
                        stack.push(val2- val1);
                        break;

                    case '/':
                        stack.push(val2/val1 );
                        break;

                    case '*':
                        stack.push(val2*val1);
                        break;

                }
            }
        }

        return stack.pop();
    }



}