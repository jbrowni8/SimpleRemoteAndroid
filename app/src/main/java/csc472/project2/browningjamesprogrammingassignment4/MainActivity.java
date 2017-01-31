package csc472.project2.browningjamesprogrammingassignment4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "MainActivity";
    private final int MAXCHANNELS = 15;
    private boolean powerOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch powerSwitch = (Switch) findViewById(R.id.powerSwitch);
        powerSwitch.setOnCheckedChangeListener(this);

        final ArrayList<String> buttons = new ArrayList<String>();
        final ArrayList<Button> buttonButtons = new ArrayList<Button>();

        for (int idx = 0; idx < MAXCHANNELS; idx++) {
            int resID = getResources().getIdentifier("button" + idx,
                    "id", getPackageName());
            buttonButtons.add((Button) findViewById(resID));
        }

        final TextView powerTv = (TextView) findViewById(R.id.textView);
        final TextView speakerVolume = (TextView) findViewById(R.id.textView2);
        final TextView currentChannel = (TextView) findViewById(R.id.textView6);
        final SeekBar volumeSeekBar = (SeekBar) findViewById(R.id.volumeSeekBar);

        powerTv.setText("Off");
        speakerVolume.setText("");
        currentChannel.setText("0");
        volumeSeekBar.setVisibility(View.INVISIBLE);

        View.OnClickListener listener =
                new View.OnClickListener() {
                    String number = "";
                    int idx = 0;

                    public void onClick(View v) {
                        if (powerOn) {
                            String buttonText = ((Button) v).getText() + "";
                            try {
                                int buttonInt = Integer.parseInt(buttonText);
                                number += buttonText;
                                idx++;
                                if (idx == 3) {
                                    if (number.equals("000")) {
                                        currentChannel.setText("1");
                                    } else {
                                        currentChannel.setText(number);
                                        idx = 0;
                                        number = "";
                                    }
                                }
                            } catch (Exception e) {
                                if (buttonText.equals("CNN")
                                        || buttonText.equals("Sling")
                                        || buttonText.equals("Youtube")) {
                                    currentChannel.setText(buttonText);
                                } else if ((buttonText.equals("+")
                                        || buttonText.equals("-"))) {
                                    try {
                                        int channel = Integer.parseInt(currentChannel.getText().toString());
                                        if (channel <= 999) {
                                            channel = buttonText.equals("+") ? channel + 1 : channel - 1;
                                            if (channel == 0) {
                                                channel = 999;
                                            }
                                            if (channel == 1000) {
                                                channel = 1;
                                            }
                                            currentChannel.setText(channel + "");
                                        }
                                    } catch (Exception e2) {
                                        currentChannel.setText("1");
                                    }
                                }
                            }


                            //TODO: buttonText is an int, currentChannel is an int.
                        }
                    }
                };

        for (Button button : buttonButtons) {
            button.setOnClickListener(listener);
        }


        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (powerOn) {
                    Log.d(TAG, "onProgressChanged");
                    speakerVolume.setText("" + i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch");
            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
        TextView powerTv = (TextView) findViewById(R.id.textView);
        SeekBar volumeSeekBar = (SeekBar) findViewById(R.id.volumeSeekBar);
        TextView volume = (TextView) findViewById(R.id.textView2);
        TextView channel = (TextView) findViewById(R.id.textView6);

        powerOn = !powerOn;
        if (powerOn) {
            powerTv.setText("On");
            volumeSeekBar.setVisibility(View.VISIBLE);
        } else {
            volumeSeekBar.setVisibility(View.INVISIBLE);
            powerTv.setText("Off");
            volume.setText("");
            channel.setText("");
        }

        Toast.makeText(this, button.getTag() + " is " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
        Log.d(TAG, isChecked ? "isChecked is on" : "isChecked is off");
        Log.d(TAG, powerOn ? "powerOn is on" : "powerOn is off");
    }

    public void onToggleClicked(View view) {
        ToggleButton toggleButton = (ToggleButton) view;
        Log.d(TAG, "onToggleClicked() " + toggleButton.getTag() + " " + (toggleButton.isChecked() ? "on" : "off"));
    }

    public void onSwitchClicked(View view) {
        Switch sw = (Switch) view;
        Log.d(TAG, "onSwitchClicked() " + sw.getTag() + " " + (sw.isChecked() ? "on" : "off"));
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


}
