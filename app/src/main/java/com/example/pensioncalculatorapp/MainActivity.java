package com.example.pensioncalculatorapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.lang.Math;
import java.util.Currency;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{
    boolean date1Flag  = false;
    boolean date2Flag =  false;
    boolean date3Flag = false;
    double grossPension = 0;
    double gradutyPension = 0;
    double commutedPension = 0;
    double netPension = 0;
    Button btn1,btn2,btn3;
    Button calculateBtn;
    Calendar c1,c2,c3, currentDate;
    TextView  totalService,totalAge, lastSalary;
    TextView grossPensionText, gradutyPensionText, commutedPensionText, netPensionText;
    int month,day, year;
    long totalAgeVal, totalServiceVal;
    double  ageRateVal;
    int currentBtn;
    int lastBasicPay = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        calculateBtn = (Button) findViewById(R.id.calculateBtn);
        c1 = Calendar.getInstance();
        c2 = Calendar.getInstance();
        c3 = Calendar.getInstance();
        currentDate = Calendar.getInstance();
        totalAge = findViewById(R.id.totalAge);
        totalService = findViewById(R.id.totalService);
        lastSalary = findViewById(R.id.lastSalary);
        grossPensionText = findViewById(R.id.grossPension);
        gradutyPensionText = findViewById(R.id.gradutyPension);
        commutedPensionText = findViewById(R.id.commutedPension);
        netPensionText = findViewById(R.id.netPension);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBtn = 1;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBtn = 2;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentBtn = 3;
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(isValidData())
                {
                    totalAgeVal = currentDate.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
                    totalServiceVal = c3.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
                    totalAge.setText(Long.toString(totalAgeVal));
                    totalService.setText(Long.toString(totalServiceVal));
                    ageRateVal = findAgeRate((int) totalAgeVal);
                    lastBasicPay = Integer.parseInt(lastSalary.getText().toString());
                    grossPension = Math.round((totalServiceVal * lastBasicPay * 7) / 300.0);
                    gradutyPension = Math.round(grossPension * 0.35);
                    commutedPension = Math.round(gradutyPension * 12 * ageRateVal);
                    netPension = Math.round(grossPension * 0.65);
                    grossPensionText.setText(Double.toString(grossPension));
                    gradutyPensionText.setText(Double.toString(gradutyPension));
                    commutedPensionText.setText(Double.toString(commutedPension));
                    netPensionText.setText(Double.toString(netPension));
                }


            }
        });

    }
    public double findAgeRate(int age)
    {
        double[] ageRateArray =
                {
                        40.5043,39.7341,38.9653,38.1974, 37.4307, 36.6651, 35.9006, 35.1372, 34.375, 33.6143,
                        32.8071,32.0974, 31.3412, 30.5869,29.8343,29.0841,28.3362,27.5908,26.8482,26.1009,
                        25.3728,24.6406,23.9126,23.184,22.4713,21.7592,21.0538,20.3555,19.6653,18.9841,
                        18.3129, 17.6526,17.005,16.371,15.7517,15.1478,14.5602,13.9888,13.434,12.8953,12.3719
                };
        double x = 0;
        if(age<20)
        {
            x = 40.503;
        }
        else if(age>=20 && age<=60)
        {
            x = ageRateArray[age-20];
        }
        return x;
    }
    boolean isValidData()
    {
        if(date1Flag == false  ||date2Flag == false || date3Flag == false) {
            return false;
        }
        long days1= c1.getTime().getTime();
        long days2 = c2.getTime().getTime();
        long days3 = c3.getTime().getTime();
//        if(days1>days2 || days1>days3 || days2 > days3)
//        {
//            return false;
//        }
        return true;

    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public long getDays(Calendar sd, Calendar ed)
//    {
//        LocalDate startDate = LocalDate.parse(getDatePattern(sd), DateTimeFormatter.ISO_LOCAL_DATE);
//        LocalDate lastDate = LocalDate.parse(getDatePattern(ed), DateTimeFormatter.ISO_LOCAL_DATE);
//        Duration diff = Duration.between(startDate.atStartOfDay(), lastDate.atStartOfDay());
//        long diffDays = diff.toDays();
//        return diffDays;
//    }

    public String getDatePattern(Calendar d)
    {
        return Integer.toString(d.get(Calendar.YEAR)) + "-" +  Integer.toString(d.get(Calendar.MONTH)) + "-" + Integer.toString(d.get(Calendar.DAY_OF_MONTH));
        //        return "YYYY-MM-YY";
    }

    @Override
    public void onDateSet(DatePicker view, int year,int month,int day)
    {
       month++;
        String currentDateString;
        currentDateString = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
        if(currentBtn == 1)
        {

            c1.set(Calendar.YEAR, year);
            c1.set(Calendar.MONTH, month);
            c1.set(Calendar.DAY_OF_MONTH, day);
            btn1.setText(currentDateString);
            date1Flag = true;
        }
        else if(currentBtn == 2)
        {
            c2.set(Calendar.YEAR, year);
            c2.set(Calendar.MONTH, month);
            c2.set(Calendar.DAY_OF_MONTH, day);
            btn2.setText(currentDateString);
            date2Flag = true;
        }
        else if(currentBtn == 3)
        {
            c3.set(Calendar.YEAR, year);
            c3.set(Calendar.MONTH, month);
            c3.set(Calendar.DAY_OF_MONTH, day);
            btn3.setText(currentDateString);
            date3Flag = true;
        }
    }



}