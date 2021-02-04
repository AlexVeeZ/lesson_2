package com.example.lesson_2;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private final String KEY_COUNTERS = "key_counters";
    /*объявление необходимых полей*/
    private MediaPlayer audio_btn, audio_toast;



    private TextView result, operation;
    private Button one, two, three, four, five, six, seven, eight, nine, zero,
            dot, split, multiply, subtract, augment,
            discard, delete, equally;

    private double buffer = 0.0;
    private char act;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSounds();
        initViews();
        setOnClickListener();
    }

    /*инициализация полей*/
    private void initViews() {
        //TextView
        result = findViewById(R.id.result);
        result.setText("0");
        operation = findViewById(R.id.operations);
        //Buttons
        one = findViewById(R.id.btn_1);
        two = findViewById(R.id.btn_2);
        three = findViewById(R.id.btn_3);
        four = findViewById(R.id.btn_4);
        five = findViewById(R.id.btn_5);
        six = findViewById(R.id.btn_6);
        seven = findViewById(R.id.btn_7);
        eight = findViewById(R.id.btn_8);
        nine = findViewById(R.id.btn_9);
        zero = findViewById(R.id.btn_0);

        dot = findViewById(R.id.btn_dot);
        split = findViewById(R.id.btn_split);
        multiply = findViewById(R.id.btn_multiply);
        augment = findViewById(R.id.btn_augment);
        subtract = findViewById(R.id.btn_subtract);

        discard = findViewById(R.id.btn_discard);
        delete = findViewById(R.id.btn_delete);
        equally = findViewById(R.id.btn_equally);

    }

    /*метод реализует нажатие на цифру*/
    private void setValueOnClick(Button b) {
        audio_btn.start();
        operation.setText(String.format("%s%s", operation.getText().toString(), b.getText().toString()));
    }

    /*метод реализует нажатие на действие*/
    private void setActionOnClick(Button b) {

        try {
            audio_btn.start();
            if (Double.parseDouble(result.getText().toString()) != 0) {
                buffer = Double.parseDouble(result.getText().toString());
                act = b.getText().charAt(0);
                operation.setText("");
            } else {
                buffer = Double.parseDouble(operation.getText().toString());
                act = b.getText().charAt(0);
                operation.setText("");
            }
        } catch (NumberFormatException e) {
            audio_toast.start();
            Toast.makeText(getApplicationContext(), R.string.toast_help_2, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /*распределение методов нажатия по кнопкам*/
    private void setOnClickListener() {
        /*реализация нажатия на цифру*/
        one.setOnClickListener(v -> setValueOnClick(one));
        two.setOnClickListener(v -> setValueOnClick(two));
        three.setOnClickListener(v -> setValueOnClick(three));
        four.setOnClickListener(v -> setValueOnClick(four));
        five.setOnClickListener(v -> setValueOnClick(five));
        six.setOnClickListener(v -> setValueOnClick(six));
        seven.setOnClickListener(v -> setValueOnClick(seven));
        eight.setOnClickListener(v -> setValueOnClick(eight));
        nine.setOnClickListener(v -> setValueOnClick(nine));
        zero.setOnClickListener(v -> setValueOnClick(zero));
        /*реализация нажатия на знак*/
        split.setOnClickListener(v -> setActionOnClick(split));
        multiply.setOnClickListener(v -> setActionOnClick(multiply));
        subtract.setOnClickListener(v -> setActionOnClick(subtract));
        augment.setOnClickListener(v -> setActionOnClick(augment));
        dot.setOnClickListener(v -> setValueOnClick(dot));
        discard.setOnClickListener(v -> {
            audio_btn.start();
            operation.setText("");
            result.setText("0");
            buffer = 0;
        });
        delete.setOnClickListener(v -> {
            try {
                audio_btn.start();
                operation.setText(operation.getText().toString().substring(0, operation.getText().toString().length() - 1));
            } catch (StringIndexOutOfBoundsException e) {
                e.printStackTrace();
                audio_toast.start();
                Toast.makeText(getApplicationContext(), R.string.toast_help, Toast.LENGTH_SHORT).show();
            }

        });
        equally.setOnClickListener(v -> {
            try {
                audio_btn.start();
                switch (act) {
                    case '+':
                        buffer += Double.parseDouble(operation.getText().toString());
                        break;
                    case '-':
                        buffer -= Double.parseDouble(operation.getText().toString());
                        break;
                    case '*':
                        buffer *= Double.parseDouble(operation.getText().toString());
                        break;
                    case '/':
                        buffer /= Double.parseDouble(operation.getText().toString());
                        break;
                }
                result.setText(String.valueOf(buffer));
                operation.setText("");
            } catch (NumberFormatException e) {
                e.printStackTrace();
                audio_toast.start();
                Toast.makeText(getApplicationContext(), R.string.toast_help, Toast.LENGTH_SHORT).show();
            } catch (ArithmeticException e) {
                e.printStackTrace();
                audio_toast.start();
                Toast.makeText(getApplicationContext(), R.string.toast_help_3, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initSounds() {
        audio_btn = MediaPlayer.create(this, R.raw.btn_sound);
        audio_toast = MediaPlayer.create(this, R.raw.toast_sound);
    }



}