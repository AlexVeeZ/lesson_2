package com.example.lesson_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private MediaPlayer audio_btn, audio_toast;

    private TextView txtField_result, txtField_operation;
    private Button btn_one, btn_two, btn_three, btn_four, btn_five, btn_six, btn_seven, btn_eight, btn_nine, btn_zero,
            btn_dot, btn_split, btn_multiply, btn_subtract, btn_augment,
            btn_discard, btn_delete, btn_equally;

    private double buffer = 0.0;
    private char act;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initSounds();
        initViews();
        setOnClickListener();
    }

    /*инициализация полей*/
    private void initViews() {
        //TextView
        txtField_result = findViewById(R.id.result);
        txtField_result.setText("0");
        txtField_operation = findViewById(R.id.operations);
        //Buttons
        btn_one = findViewById(R.id.btn_1);
        btn_two = findViewById(R.id.btn_2);
        btn_three = findViewById(R.id.btn_3);
        btn_four = findViewById(R.id.btn_4);
        btn_five = findViewById(R.id.btn_5);
        btn_six = findViewById(R.id.btn_6);
        btn_seven = findViewById(R.id.btn_7);
        btn_eight = findViewById(R.id.btn_8);
        btn_nine = findViewById(R.id.btn_9);
        btn_zero = findViewById(R.id.btn_0);

        btn_dot = findViewById(R.id.btn_dot);
        btn_split = findViewById(R.id.btn_split);
        btn_multiply = findViewById(R.id.btn_multiply);
        btn_augment = findViewById(R.id.btn_augment);
        btn_subtract = findViewById(R.id.btn_subtract);

        btn_discard = findViewById(R.id.btn_discard);
        btn_delete = findViewById(R.id.btn_delete);
        btn_equally = findViewById(R.id.btn_equally);

    }

    /*инициализация полей*/
    private void initSounds() {
        audio_btn = MediaPlayer.create(this, R.raw.btn_sound);
        audio_toast = MediaPlayer.create(this, R.raw.toast_sound);
    }

    /*метод реализует нажатие на цифру*/
    private void setValueOnClick(Button b) {
        audio_btn.start();
        txtField_operation.setText(String.format("%s%s", txtField_operation.getText().toString(), b.getText().toString()));
    }

    /*метод реализует нажатие на действие*/
    private void setActionOnClick(Button b) {

        try {
            audio_btn.start();
            if (Double.parseDouble(txtField_result.getText().toString()) != 0) {
                buffer = Double.parseDouble(txtField_result.getText().toString());
                act = b.getText().charAt(0);
                txtField_operation.setText("");
            } else {
                buffer = Double.parseDouble(txtField_operation.getText().toString());
                act = b.getText().charAt(0);
                txtField_operation.setText("");
            }
        } catch (NumberFormatException e) {
            audio_toast.start();
            Toast.makeText(getApplicationContext(), R.string.toast_help_2, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /*распределение методов нажатия по кнопкам*/
    @SuppressLint("DefaultLocale")
    private void setOnClickListener() {
        /*реализация нажатия на цифру*/
        btn_one.setOnClickListener(v -> setValueOnClick(btn_one));
        btn_two.setOnClickListener(v -> setValueOnClick(btn_two));
        btn_three.setOnClickListener(v -> setValueOnClick(btn_three));
        btn_four.setOnClickListener(v -> setValueOnClick(btn_four));
        btn_five.setOnClickListener(v -> setValueOnClick(btn_five));
        btn_six.setOnClickListener(v -> setValueOnClick(btn_six));
        btn_seven.setOnClickListener(v -> setValueOnClick(btn_seven));
        btn_eight.setOnClickListener(v -> setValueOnClick(btn_eight));
        btn_nine.setOnClickListener(v -> setValueOnClick(btn_nine));
        btn_zero.setOnClickListener(v -> setValueOnClick(btn_zero));
        /*реализация нажатия на знак*/
        btn_split.setOnClickListener(v -> setActionOnClick(btn_split));
        btn_multiply.setOnClickListener(v -> setActionOnClick(btn_multiply));
        btn_subtract.setOnClickListener(v -> setActionOnClick(btn_subtract));
        btn_augment.setOnClickListener(v -> setActionOnClick(btn_augment));
        btn_dot.setOnClickListener(v -> setValueOnClick(btn_dot));
        btn_discard.setOnClickListener(v -> {
            audio_btn.start();
            txtField_operation.setText("");
            txtField_result.setText("0");
            buffer = 0;
        });
        btn_delete.setOnClickListener(v -> {
            try {
                audio_btn.start();
                txtField_operation.setText(txtField_operation.getText().toString().substring(0, txtField_operation.getText().toString().length() - 1));
            } catch (StringIndexOutOfBoundsException e) {
                e.printStackTrace();
                audio_toast.start();
                Toast.makeText(getApplicationContext(), R.string.toast_help, Toast.LENGTH_SHORT).show();
            }

        });
        btn_equally.setOnClickListener(v -> {
            try {
                audio_btn.start();
                switch (act) {
                    case '+':
                        buffer += Double.parseDouble(txtField_operation.getText().toString());
                        break;
                    case '-':
                        buffer -= Double.parseDouble(txtField_operation.getText().toString());
                        break;
                    case '*':
                        buffer *= Double.parseDouble(txtField_operation.getText().toString());
                        break;
                    case '/':
                        buffer /= Double.parseDouble(txtField_operation.getText().toString());
                        break;
                }
                txtField_result.setText(String.valueOf(buffer));
                txtField_operation.setText("");
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState){
        super.onSaveInstanceState(instanceState);
        instanceState.putString("txt_result", String.valueOf(txtField_result.getText()));
        instanceState.putString("txt_operation", String.valueOf(txtField_operation.getText()));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState){
        super.onRestoreInstanceState(instanceState);
        txtField_operation.setText(instanceState.getString("txt_operation"));
        txtField_result.setText(instanceState.getString("txt_result"));
    }
}