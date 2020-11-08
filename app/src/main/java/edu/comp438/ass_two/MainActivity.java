package edu.comp438.ass_two;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static  final String NAME= "NAME";
    public static  final String HIGHT= "HIGHT";
    public static  final String WEIGHT= "WEIGHT";
    public static  final String GENDER= "GENDER";
    public static final String FLAG="FLAG";
    private EditText name,hight,weight;
    private CheckBox checkBox;
    private Button save ,BMI,open;
    private Spinner Spinner_gender;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private TextView res;
    String cal , bmiRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res= findViewById(R.id.res);
        open=findViewById(R.id.add);
        setupViews();
        setupShareedPrefs();
        checkPrefs();

    }
    private void checkPrefs(){
        boolean flag = prefs.getBoolean(FLAG, false);

        if(flag){
            String names = prefs.getString(NAME,"");
            String hights = prefs.getString(HIGHT,"");
            String weights = prefs.getString(WEIGHT,"");
            String gendes = prefs.getString(GENDER,"");
            name.setText(names);
            hight.setText(hights);
            weight.setText(weights);
            Spinner_gender.getSelectedItem();
            checkBox.setChecked(true);
        }
    }
    private  void setupShareedPrefs(){
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }
    private void  setupViews(){
        name = findViewById(R.id.MyName);
        hight = findViewById(R.id.MyHight);
        weight = findViewById(R.id.MyWeight);
        checkBox = findViewById(R.id.checkBox2);
        Spinner_gender=findViewById(R.id.spinner);

    }

    public void SaveMy_Infor(View view) {
        String names =name.getText().toString();
        String hights= hight.getText().toString();
        String  weights = weight.getText().toString();
        String genders = Spinner_gender.getSelectedItem().toString();
        if (checkBox.isChecked()){
            editor.putString(NAME,names);
            editor.putString(HIGHT,hights);
            editor.putString(WEIGHT,weights);
            editor.putString(GENDER,genders);
            editor.putBoolean(FLAG, true);
            editor.commit();

        }
    }


    public void calculateBmi(View view) {
        String s1 =hight.getText().toString();
        String s2 =weight.getText().toString();

        float hightVal =Float.parseFloat(s1)/100;
        float weightval=Float.parseFloat(s2);

        float bmi = weightval/(hightVal*hightVal);
        if (bmi<16){
            bmiRes= "Under Weight";
        }else if (bmi<18){
            bmiRes="Under Weight";
        }else if (bmi>=18.5 && bmi<=24.5){
            bmiRes="Normal Weight";
        }else if (bmi >25){
            bmiRes="Overweight";
        }else {
            bmiRes ="Sorry";
        }
        cal = "Reasult: \n " + bmi +"\n" + bmiRes;
        res.setText(cal);
    }

    public void OpenNewActi(View view) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }
}