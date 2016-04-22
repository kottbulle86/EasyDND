package se.andreasmikaelsson.thesheetver4;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewDebug;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* do this in onCreate */
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        //TEMP CLEAR BUTTON
        Button clearShardPref = (Button) findViewById(R.id.button_clear_sharedpref);
        assert clearShardPref != null;
        clearShardPref.setOnClickListener(this);

        setupSpinner();

        setupExpandHeight();

        setupImageButtons();

        //Expand1
        setupRollButtons();

        //Expand2
        setupAbilityButtons();

        //Expand3
        setupExpand3();

        loadCharacterDataSetup();
    }

    //Setup shake method
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    int rollCount = 0;
    int sumCount = 0;

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter

            if (mAccel > 12) {
                Toast toast = Toast.makeText(getApplicationContext(), "Dice rolled!", Toast.LENGTH_LONG);
                toast.show();

                diceRoll();
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void diceRoll() {
        Spinner spinnerDice = (Spinner) findViewById(R.id.spinner_dice);
        TextView result = (TextView) findViewById(R.id.dice_result);
        TextView rolls = (TextView) findViewById(R.id.dice_rolls);
        TextView sum = (TextView) findViewById(R.id.dice_sum);
        rollCount++;
        int randomRoll = 0;
        switch (spinnerDice.getSelectedItemPosition()) {
            case 0:
                randomRoll = new Random().nextInt(20) + 1;
                result.setText(String.valueOf(randomRoll));
                sumCount = sumCount + randomRoll;
                rolls.setText(String.valueOf(rollCount));
                sum.setText(String.valueOf(sumCount));
                break;
            case 1:
                randomRoll = new Random().nextInt(12) + 1;
                result.setText(String.valueOf(randomRoll));
                sumCount = sumCount + randomRoll;
                rolls.setText(String.valueOf(rollCount));
                sum.setText(String.valueOf(sumCount));
                break;
            case 2:
                randomRoll = new Random().nextInt(10) + 1;
                result.setText(String.valueOf(randomRoll));
                sumCount = sumCount + randomRoll;
                rolls.setText(String.valueOf(rollCount));
                sum.setText(String.valueOf(sumCount));
                break;
            case 3:
                randomRoll = new Random().nextInt(8) + 1;
                result.setText(String.valueOf(randomRoll));
                sumCount = sumCount + randomRoll;
                rolls.setText(String.valueOf(rollCount));
                sum.setText(String.valueOf(sumCount));
                break;
            case 4:
                randomRoll = new Random().nextInt(6) + 1;
                result.setText(String.valueOf(randomRoll));
                sumCount = sumCount + randomRoll;
                rolls.setText(String.valueOf(rollCount));
                sum.setText(String.valueOf(sumCount));
                break;
            case 5:
                randomRoll = new Random().nextInt(4) + 1;
                result.setText(String.valueOf(randomRoll));
                sumCount = sumCount + randomRoll;
                rolls.setText(String.valueOf(rollCount));
                sum.setText(String.valueOf(sumCount));
                break;
        }
    }

    private void setupExpand3() {
        Spinner spinnerDice = (Spinner) findViewById(R.id.spinner_dice);
        ArrayAdapter<CharSequence> adapterdice = ArrayAdapter.createFromResource(this,
                R.array.dice, android.R.layout.simple_spinner_item);
        spinnerDice.setAdapter(adapterdice);

        ImageButton rollDice = (ImageButton) findViewById(R.id.dice_roll_button);
        rollDice.setOnClickListener(this);

        Button clearRolls = (Button) findViewById(R.id.clear_rolls_button);
        clearRolls.setOnClickListener(this);
    }

    private void setupAbilityButtons() {
        Button buttonStr = (Button) findViewById(R.id.button_str);
        Button buttonDex = (Button) findViewById(R.id.button_dex);
        Button buttonCon = (Button) findViewById(R.id.button_con);
        Button buttonInt = (Button) findViewById(R.id.button_int);
        Button buttonWis = (Button) findViewById(R.id.button_wis);
        Button buttonCha = (Button) findViewById(R.id.button_cha);

        assert buttonStr != null;
        assert buttonDex != null;
        assert buttonCon != null;
        assert buttonInt != null;
        assert buttonWis != null;
        assert buttonCha != null;
        buttonStr.setOnClickListener(this);
        buttonDex.setOnClickListener(this);
        buttonCon.setOnClickListener(this);
        buttonInt.setOnClickListener(this);
        buttonWis.setOnClickListener(this);
        buttonCha.setOnClickListener(this);
    }

    public void abilityView(String bundleString) {
        Bundle bundle = new Bundle();
        bundle.putString("bundleKey", bundleString);
        DialogFragment newFragment = new AbilityDialogFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "abilityinfo");
    }

    public void setupSpinner() {
        Spinner spinnerRace = (Spinner) findViewById(R.id.spinnerRace);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Races, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRace.setAdapter(adapter1);
        Spinner spinnerClass = (Spinner) findViewById(R.id.spinnerClass);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Classes, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(adapter2);
        Spinner spinnerBackground = (Spinner) findViewById(R.id.spinnerBackground);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.Backgrounds, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBackground.setAdapter(adapter3);
    }

    private void setupRollButtons() {
        Button rollRace = (Button) findViewById(R.id.button_roll_race);
        Button rollClass = (Button) findViewById(R.id.button_roll_class);
        Button rollBackground = (Button) findViewById(R.id.button_roll_background);

        assert rollRace != null;
        assert rollClass != null;
        assert rollBackground != null;
        rollRace.setOnClickListener(this);
        rollClass.setOnClickListener(this);
        rollBackground.setOnClickListener(this);
    }

        public int randomItemStringArray(String[] s) {
        int idx = new Random().nextInt(s.length);
        String random = (s[idx]);
        return idx;
    }

    public void setupExpandHeight() {
        int displayHeight = getWindowManager().getDefaultDisplay().getHeight();

        FrameLayout expand_layout1 = (FrameLayout)findViewById(R.id.expandlayout1);
        FrameLayout expand_layout2 = (FrameLayout)findViewById(R.id.expandlayout2);
        FrameLayout expand_layout3 = (FrameLayout)findViewById(R.id.expandlayout3);
        FrameLayout expand_layout4 = (FrameLayout)findViewById(R.id.expandlayout4);

        expand_layout1.getLayoutParams().height = displayHeight;
        expand_layout2.getLayoutParams().height = displayHeight;
        expand_layout3.getLayoutParams().height = displayHeight;
        expand_layout4.getLayoutParams().height = displayHeight;

        ImageView expand_background1 = (ImageView)findViewById(R.id.expand1_background);
        ImageView expand_background2 = (ImageView)findViewById(R.id.expand2_background);
        ImageView expand_background3 = (ImageView)findViewById(R.id.expand3_background);
        ImageView expand_background4 = (ImageView)findViewById(R.id.expand4_background);

        expand_background1.getLayoutParams().height = displayHeight;
        expand_background2.getLayoutParams().height = displayHeight;
        expand_background3.getLayoutParams().height = displayHeight;
        expand_background4.getLayoutParams().height = displayHeight;
    }

    public void setupImageButtons() {
        ImageButton button1 = (ImageButton) findViewById(R.id.button1);
        ImageButton button2 = (ImageButton) findViewById(R.id.button2);
        ImageButton button3 = (ImageButton) findViewById(R.id.button3);
        ImageButton button4 = (ImageButton) findViewById(R.id.button4);

        Button hidebutton1 = (Button) findViewById(R.id.button_hide1);
        Button hidebutton2 = (Button) findViewById(R.id.button_hide2);
        Button hidebutton3 = (Button) findViewById(R.id.button_hide3);
        Button hidebutton4 = (Button) findViewById(R.id.button_hide4);

        assert button1 != null;
        assert button2 != null;
        assert button3 != null;
        assert button4 != null;
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        assert hidebutton1 != null;
        assert hidebutton2 != null;
        assert hidebutton3 != null;
        assert hidebutton4 != null;
        hidebutton1.setOnClickListener(this);
        hidebutton2.setOnClickListener(this);
        hidebutton3.setOnClickListener(this);
        hidebutton4.setOnClickListener(this);
    }

    public void saveCharacterDataSetup() {

        EditText charName = (EditText) findViewById(R.id.editTextName);
        EditText charTraits = (EditText) findViewById(R.id.editTextTraits);
        EditText charIdeals = (EditText) findViewById(R.id.editTextIdeals);
        EditText charBonds = (EditText) findViewById(R.id.editTextBonds);
        EditText charFlaws = (EditText) findViewById(R.id.editTextFlaws);

        String nameValue = String.valueOf(charName.getText());
        String traitsValue = String.valueOf(charTraits.getText());
        String idealsValue = String.valueOf(charIdeals.getText());
        String bondsValue = String.valueOf(charBonds.getText());
        String flawsValue = String.valueOf(charFlaws.getText());

        Spinner charRace = (Spinner) findViewById(R.id.spinnerRace);
        Spinner charClass = (Spinner) findViewById(R.id.spinnerClass);
        Spinner charBackground = (Spinner) findViewById(R.id.spinnerBackground);

        int raceValue = charRace.getSelectedItemPosition();
        int classValue = charClass.getSelectedItemPosition();
        int backgroundValue = charBackground.getSelectedItemPosition();

        String nameKey = getString(R.string.saved_name);
        String raceKey = getString(R.string.saved_race);
        String classKey = getString(R.string.saved_class);
        String backgroundKey = getString(R.string.saved_background);
        String traitsKey = getString(R.string.saved_traits);
        String idealsKey = getString(R.string.saved_ideals);
        String bondsKey = getString(R.string.saved_bonds);
        String flawsKey = getString(R.string.saved_flaws);

        saveCharacterDataString(nameKey, nameValue);
        saveCharacterDataString(traitsKey, traitsValue);
        saveCharacterDataString(idealsKey, idealsValue);
        saveCharacterDataString(bondsKey, bondsValue);
        saveCharacterDataString(flawsKey, flawsValue);

        saveCharacterDataInt(raceKey, raceValue);
        saveCharacterDataInt(classKey, classValue);
        saveCharacterDataInt(backgroundKey, backgroundValue);

    }

        public void saveCharacterDataString(String key, String value) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

        public void saveCharacterDataInt(String key, int value) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void loadCharacterDataSetup() {
        String nameKey = getString(R.string.saved_name);
        String raceKey = getString(R.string.saved_race);
        String classKey = getString(R.string.saved_class);
        String backgroundKey = getString(R.string.saved_background);
        String traitsKey = getString(R.string.saved_traits);
        String idealsKey = getString(R.string.saved_ideals);
        String bondsKey = getString(R.string.saved_bonds);
        String flawsKey = getString(R.string.saved_flaws);

        //No default value needed with Hint instead... Set to null value instead.
        String nameDefaultValue = getResources().getString(R.string.charactername_null);
        //int raceDefaultValue = Integer.parseInt(getResources().getString(R.string.characterrace_null));
        //int classDefaultValue = Integer.parseInt(getResources().getString(R.string.characterclass_null));
        //int backgroundDefaultValue = Integer.parseInt(getResources().getString(R.string.characterbackground_null));
        String traitsDefaultValue = getResources().getString(R.string.charactertraits_null);
        String idealsDefaultValue = getResources().getString(R.string.characterideals_null);
        String bondsDefaultValue = getResources().getString(R.string.characterbonds_null);
        String flawsDefaultValue = getResources().getString(R.string.characterflaws_null);

        String nameValue = loadCharacterDataString(nameKey, nameDefaultValue);
        String traitsValue = loadCharacterDataString(traitsKey, traitsDefaultValue);
        String idealsValue = loadCharacterDataString(idealsKey, idealsDefaultValue);
        String bondsValue = loadCharacterDataString(bondsKey, bondsDefaultValue);
        String flawsValue = loadCharacterDataString(flawsKey, flawsDefaultValue);

        int raceValue = loadCharacterDataInt(raceKey, 0);
        int classValue = loadCharacterDataInt(classKey, 0);
        int backgroundValue = loadCharacterDataInt(backgroundKey, 0);

        EditText charName = (EditText) findViewById(R.id.editTextName);
        EditText charTraits = (EditText) findViewById(R.id.editTextTraits);
        EditText charIdeals = (EditText) findViewById(R.id.editTextIdeals);
        EditText charBonds = (EditText) findViewById(R.id.editTextBonds);
        EditText charFlaws = (EditText) findViewById(R.id.editTextFlaws);

        Spinner charRace = (Spinner) findViewById(R.id.spinnerRace);
        Spinner charClass = (Spinner) findViewById(R.id.spinnerClass);
        Spinner charBackground = (Spinner) findViewById(R.id.spinnerBackground);

        if (charName != null) {
            charName.setText(nameValue);
        }
        if (charTraits != null) {
            charTraits.setText(traitsValue);
        }
        if (charIdeals != null) {
            charIdeals.setText(idealsValue);
        }
        if (charBonds != null) {
            charBonds.setText(bondsValue);
        }
        if (charFlaws != null) {
            charFlaws.setText(flawsValue);
        }

        if (charRace != null) {
            charRace.setSelection(raceValue);
        }
        if (charClass != null) {
            charClass.setSelection(classValue);
        }
        if (charBackground != null) {
            charBackground.setSelection(backgroundValue);
        }
    }

        public String loadCharacterDataString(String key, String defaultValueString) {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        return sharedPref.getString(key, defaultValueString);
    }

        public int loadCharacterDataInt(String key, int defaultValueInt) {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        return sharedPref.getInt(key, defaultValueInt);
    }

    public static void expand(final View v, final View p) {
        p.measure(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        final int targetHeight = p.getHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ActionBar.LayoutParams.WRAP_CONTENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);

    }

    private void ClearSharedPref() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }

    @Override
    public void onClick(View view) {

        ImageButton button1 = (ImageButton) findViewById(R.id.button1);
        ImageButton button2 = (ImageButton) findViewById(R.id.button2);
        ImageButton button3 = (ImageButton) findViewById(R.id.button3);
        ImageButton button4 = (ImageButton) findViewById(R.id.button4);
        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        button4.setEnabled(false);
        TextView result = (TextView) findViewById(R.id.dice_result);
        TextView rolls = (TextView) findViewById(R.id.dice_rolls);
        TextView sum = (TextView) findViewById(R.id.dice_sum);
        Toast toast = Toast.makeText(getApplicationContext(), "Information saved!", Toast.LENGTH_LONG);
        Toast toast2 = Toast.makeText(getApplicationContext(), "Dice rolled!", Toast.LENGTH_LONG);
        Toast toast3 = Toast.makeText(getApplicationContext(), "Cleared!", Toast.LENGTH_LONG);

        switch (view.getId()) {
            case R.id.button1:
                expand(findViewById(R.id.expandlayout1), findViewById(R.id.linearlayout_screen));
                break;
            case R.id.button2:
                expand(findViewById(R.id.expandlayout2), findViewById(R.id.linearlayout_screen));
                break;
            case R.id.button3:
                expand(findViewById(R.id.expandlayout3), findViewById(R.id.linearlayout_screen));
                break;
            case R.id.button4:
                expand(findViewById(R.id.expandlayout4), findViewById(R.id.linearlayout_screen));
                break;
            case R.id.button_hide1:
                collapse(findViewById(R.id.expandlayout1));
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
                toast.show();
                break;
            case R.id.button_str:
                abilityView("str");
                break;
            case R.id.button_dex:
                abilityView("dex");
                break;
            case R.id.button_con:
                abilityView("con");
                break;
            case R.id.button_int:
                abilityView("int");
                break;
            case R.id.button_wis:
                abilityView("wis");
                break;
            case R.id.button_cha:
                abilityView("cha");
                break;
            case R.id.button_hide2:
                collapse(findViewById(R.id.expandlayout2));
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
                toast.show();
                break;
            case R.id.dice_roll_button:
                diceRoll();
                toast2.show();
                break;
            case R.id.clear_rolls_button:
                rollCount = 0;
                sumCount = 0;
                result.setText(String.valueOf(0));
                rolls.setText(String.valueOf(0));
                sum.setText(String.valueOf(0));
                toast3.show();
                break;
            case R.id.button_hide3:
                collapse(findViewById(R.id.expandlayout3));
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
                toast.show();
                break;
            case R.id.button_hide4:
                collapse(findViewById(R.id.expandlayout4));
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
                toast.show();
                break;
            case R.id.button_roll_race:
                String[] stringArrayRaces = getResources().getStringArray(R.array.Races);
                int randomRace = randomItemStringArray(stringArrayRaces);
                Spinner spinnerRace = (Spinner) findViewById(R.id.spinnerRace);
                spinnerRace.setSelection(randomRace);
                break;
            case R.id.button_roll_class:
                String[] stringArrayClass = getResources().getStringArray(R.array.Classes);
                int randomClass = randomItemStringArray(stringArrayClass);
                Spinner spinnerClass = (Spinner) findViewById(R.id.spinnerClass);
                spinnerClass.setSelection(randomClass);
                break;
            case R.id.button_roll_background:
                String[] stringArrayBackground = getResources().getStringArray(R.array.Backgrounds);
                int randomBackground = randomItemStringArray(stringArrayBackground);
                Spinner spinnerBackground = (Spinner) findViewById(R.id.spinnerBackground);
                spinnerBackground.setSelection(randomBackground);
                break;
            case R.id.button_clear_sharedpref:
                ClearSharedPref();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveCharacterDataSetup();
        mSensorManager.unregisterListener(mSensorListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCharacterDataSetup();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveCharacterDataSetup();
    }
}
