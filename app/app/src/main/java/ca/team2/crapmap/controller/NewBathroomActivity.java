package ca.team2.crapmap.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import ca.team2.crapmap.R;
import ca.team2.crapmap.fragment.TimePickerFragment;
import ca.team2.crapmap.service.BathroomService;
import ca.team2.crapmap.service.RequestHandler;

public class NewBathroomActivity extends AppCompatActivity {
    private EditText name;
    private CheckBox requiresPurchase;
    private CheckBox[] days;
    private TextView[] startTimes;
    private TextView[] endTimes;
    private Button submit;
    private double latitude, longitude;
    private String baseApiUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bathroom);
        name = (EditText)findViewById(R.id.new_bathroom_name);
        requiresPurchase = (CheckBox)findViewById(R.id.new_bathroom_requires_purchase);
        submit = (Button)findViewById(R.id.new_bathroom_submit);

        days = new CheckBox[7];
        startTimes = new TextView[7];
        endTimes = new TextView[7];

        days[0] = (CheckBox)findViewById(R.id.monday);
        startTimes[0] = (TextView) findViewById(R.id.mondayStart);
        startTimes[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, startTimes[0]);
            }
        });
        endTimes[0] = (TextView) findViewById(R.id.mondayEnd);
        endTimes[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, endTimes[0]);
            }
        });

        days[1] = (CheckBox)findViewById(R.id.tuesday);
        startTimes[1] = (TextView) findViewById(R.id.tuesdayStart);
        startTimes[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, startTimes[1]);
            }
        });
        endTimes[1] = (TextView) findViewById(R.id.tuesdayEnd);
        endTimes[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, endTimes[1]);
            }
        });

        days[2] = (CheckBox)findViewById(R.id.wednesday);
        startTimes[2] = (TextView) findViewById(R.id.wednesdayStart);
        startTimes[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, startTimes[2]);
            }
        });
        endTimes[2] = (TextView) findViewById(R.id.wednesdayEnd);
        endTimes[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, endTimes[2]);
            }
        });

        days[3] = (CheckBox)findViewById(R.id.thursday);
        startTimes[3] = (TextView) findViewById(R.id.thursdayStart);
        startTimes[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, startTimes[3]);
            }
        });
        endTimes[3] = (TextView) findViewById(R.id.thursdayEnd);
        endTimes[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, endTimes[3]);
            }
        });

        days[4] = (CheckBox)findViewById(R.id.friday);
        startTimes[4] = (TextView) findViewById(R.id.fridayStart);
        startTimes[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, startTimes[4]);
            }
        });
        endTimes[4] = (TextView) findViewById(R.id.fridayEnd);
        endTimes[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, endTimes[4]);
            }
        });

        days[5] = (CheckBox)findViewById(R.id.saturday);
        startTimes[5] = (TextView) findViewById(R.id.saturdayStart);
        startTimes[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, startTimes[5]);
            }
        });
        endTimes[5] = (TextView) findViewById(R.id.saturdayEnd);
        endTimes[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, endTimes[5]);
            }
        });

        days[6] = (CheckBox)findViewById(R.id.sunday);
        startTimes[6] = (TextView) findViewById(R.id.sundayStart);
        startTimes[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, startTimes[6]);
            }
        });
        endTimes[6] = (TextView) findViewById(R.id.sundayEnd);
        endTimes[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view, endTimes[6]);
            }
        });

        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);
        baseApiUrl = getIntent().getStringExtra("baseApiUrl");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewBathroom();
            }
        });
    }

    public void createNewBathroom(){
        //TODO: check for invalid times
        ArrayList<String> arrayListTimes = new ArrayList<>();
        for(int i=0; i<7; i++){
            if(days[i].isChecked()){
                arrayListTimes.add(days[i].getText().toString() + " "
                    + startTimes[i].getText().toString() + " "
                    + endTimes[i].getText().toString());
            }
        }

        String[] times =  arrayListTimes.toArray(new String[arrayListTimes.size()]);
        BathroomService.addBathroom(name.getText().toString(), latitude, longitude, requiresPurchase.isChecked(), times, new RequestHandler() {
            @Override
            public void callback(Object result) {
                String response = (String) result;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("response", response);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    public void showTimePickerDialog(View v, TextView textView) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setTextView(textView);
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
