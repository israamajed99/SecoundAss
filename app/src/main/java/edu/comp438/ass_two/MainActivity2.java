package edu.comp438.ass_two;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    private EditText InputText;
    private TextView textView;
    private Button set, start, reset,stop;
    private CountDownTimer Cont_Down;
    private boolean Running;
    private long StartTime, TimeLeftI, EndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        InputText = findViewById(R.id.input);
        textView = findViewById(R.id.text_view_countdown);
        set = findViewById(R.id.button_set);
        start = findViewById(R.id.button_start_pause);
        reset = findViewById(R.id.button_reset);
        stop=findViewById(R.id.stop);
//        checkSavedInstance(savedInstanceState);

    }

    private void setTime ( long milliseconds){
        StartTime = milliseconds;
        TimeLeftI = StartTime;
        update();

    }
    private void start () {
        EndTime = System.currentTimeMillis() + TimeLeftI;
        Cont_Down = new CountDownTimer(TimeLeftI, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeLeftI = millisUntilFinished;
                update();
            }
            @Override
            public void onFinish() {
                Running = false;

            }
        }

                .start();
        Running = true;

    }

    private void update () {
        int hours = (int) (TimeLeftI / 1000) / 3600,  minutes = (int) ((TimeLeftI / 1000) % 3600) / 60,seconds = (int) (TimeLeftI / 1000) % 60;

        String timeLeft;
        if (hours > 0) {
            timeLeft = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeft = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        textView.setText(timeLeft);
    }

    @Override
    protected void onStop () {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("startTimeInMillis", StartTime);
        editor.putLong("millisLeft", TimeLeftI);
        editor.putBoolean("timerRunning", Running);
        editor.putLong("endTime", EndTime);
        editor.apply();
        if (Cont_Down != null) {
            Cont_Down.cancel();
        }
    }
    public void ButtonSetOnClick (View view){
        String input = InputText.getText().toString();
        if (input.length() == 0) {
            Toast.makeText(MainActivity2.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        long millisInput = Long.parseLong(input) * 60000;
        if (millisInput == 0) {
            Toast.makeText(MainActivity2.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
            return;
        }
        setTime(millisInput);
        InputText.setText("");
    }

    public void startAct (View view){

            start();
        }


    public void reset (View view){
        TimeLeftI = StartTime;
        update();

    }

    public void StopActiv(View view) {
        onStop();
    }

    public void bakeOnFirstActiv(View view) {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
    }


//private void checkSavedInstance(Bundle saveInstanceState){
//        if(saveInstanceState!=null){
//           StartTime = saveInstanceState.getLong("start");
//           EndTime = saveInstanceState.getLong("end");
//           TimeLeftI = saveInstanceState.getLong("left");
//           Running = saveInstanceState.getBoolean("running");
//        }
//}
//
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putLong("start",StartTime);
//        outState.putLong("end",EndTime);
//        outState.putLong("left",TimeLeftI);
//        outState.putBoolean("running",Running);
//    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
       outState.putString("text",textView.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText(savedInstanceState.getString("text"));

    }
}



