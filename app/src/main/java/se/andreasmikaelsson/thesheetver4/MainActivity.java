package se.andreasmikaelsson.thesheetver4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vstechlab.easyfonts.EasyFonts;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //information dialogfragment
        String titleKey = "title_key";
        String title = "Welcome!";
        String infoTextKey = "text_key";
        String infoText = getString(R.string.main_menu_infotext);
        String mainMenuInfoCheckBoxKey = getString(R.string.saved_mainmenu_infocheckbox);
        Boolean mainMenuCheckBox = loadCharacterDataBoolean(mainMenuInfoCheckBoxKey);
        if (!mainMenuCheckBox) {
            showInfoFragment(titleKey, title, infoTextKey, infoText);
        }

        //fab for NsdChatActivity (whisper)
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NsdChatActivity.class);
                startActivity(intent);
            }
        });

        // Setup for shake method (dice roll)
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        //TEMP CLEAR BUTTON
        Button clearShardPref = (Button) findViewById(R.id.button_clear_sharedpref);
        assert clearShardPref != null;
        clearShardPref.setOnClickListener(this);

        // Spinners in expand-layout1
        setupSpinner();

        // Calculates screen height and sets expand-layouts to that height.
        setupExpandHeight();

        //Main menu buttons and hide buttons
        setupImageButtons();

        //Expand1 - roll buttons
        setupRollButtons();

        //Expand2 XP, level and PB
        levelCalculator();

        //Expand2 - ability buttons
        setupAbilityButtons();

        //Expand3 - dice simulator
        setupExpand3();

        //Set fonts for textviews, edittexts etc. (saved in notes)
        //setupFonts();

        loadCharacterDataSetup();
    }

    private void showInfoFragment(String titleKey, String title, String infoTextKey, String infoText) {
        Bundle bundle = new Bundle();
        bundle.putString(titleKey, title);
        bundle.putString(infoTextKey, infoText);
        DialogFragment newFragment = new InfoDialogFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "infoText");
    }

    private void levelCalculator() {
        final TextView lvlValue = (TextView) findViewById(R.id.lvl_textview);
        final TextView pbValue = (TextView) findViewById(R.id.pb_textview);
        final EditText xpValue = (EditText) findViewById(R.id.xp_value);
        assert xpValue != null;
        xpValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!xpValue.getEditableText().toString().equals("")) {
                    int xpInt = Integer.valueOf(xpValue.getEditableText().toString());
                    String lvlString = null;
                    String pbString = null;
                    if (xpInt < 300) {
                        lvlString = "1";
                        pbString = "2";
                    }
                    else if (xpInt < 900) {
                        lvlString = "2";
                        pbString = "2";
                    }
                    else if (xpInt < 2700) {
                        lvlString = "3";
                        pbString = "2";
                    }
                    else if (xpInt < 6500) {
                        lvlString = "4";
                        pbString = "2";
                    }
                    else if (xpInt < 14000) {
                        lvlString = "5";
                        pbString = "3";
                    }
                    else if (xpInt < 23000) {
                        lvlString = "6";
                        pbString = "3";
                    }
                    else if (xpInt < 34000) {
                        lvlString = "7";
                        pbString = "3";
                    }
                    else if (xpInt < 48000) {
                        lvlString = "8";
                        pbString = "3";
                    }
                    else if (xpInt < 64000) {
                        lvlString = "9";
                        pbString = "4";
                    }
                    else if (xpInt < 85000) {
                        lvlString = "10";
                        pbString = "4";
                    }
                    else if (xpInt < 100000) {
                        lvlString = "11";
                        pbString = "4";
                    }
                    else if (xpInt < 120000) {
                        lvlString = "12";
                        pbString = "4";
                    }
                    else if (xpInt < 140000) {
                        lvlString = "13";
                        pbString = "5";
                    }
                    else if (xpInt < 165000) {
                        lvlString = "14";
                        pbString = "5";
                    }
                    else if (xpInt < 195000) {
                        lvlString = "15";
                        pbString = "5";
                    }
                    else if (xpInt < 225000) {
                        lvlString = "16";
                        pbString = "5";
                    }
                    else if (xpInt < 265000) {
                        lvlString = "17";
                        pbString = "6";
                    }
                    else if (xpInt < 305000) {
                        lvlString = "18";
                        pbString = "6";
                    }
                    else if (xpInt < 355000) {
                        lvlString = "19";
                        pbString = "6";
                    }else if (xpInt >= 355000) {
                        lvlString = "20";
                        pbString = "6";
                    }
                    assert lvlValue != null;
                    lvlValue.setText(lvlString);
                    assert pbValue != null;
                    pbValue.setText(pbString);

                    String xpKey = getString(R.string.saved_xp);
                    saveCharacterDataInt(xpKey, xpInt);
                    String pbKey = getString(R.string.saved_pb);
                    saveCharacterDataString(pbKey, pbString);
                }
            }
        });
    }

    //Setup shake method
    private SensorManager mSensorManager;
    private float mAccel; // Used in method onSensorChanged(), acceleration apart from gravity
    private float mAccelCurrent; // Used in method onSensorChanged(), current acceleration including gravity
    private float mAccelLast; // Used in method onSensorChanged(), last acceleration including gravity
    int rollCount = 0; //Used in method diceRoll()
    int sumCount = 0; //Used in method diceRoll()

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
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Dice rolled!", Toast.LENGTH_LONG);
                toast.show();
                ImageView animationTarget = (ImageView) findViewById(R.id.dice_roll_button);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.rotate_around_center_point_dice);
                animationTarget.startAnimation(animation);
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
        int randomRoll;
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
                R.array.dice, R.layout.ability_spinner_layout);
        spinnerDice.setAdapter(adapterdice);

        ImageButton rollDice = (ImageButton) findViewById(R.id.dice_roll_button);
        rollDice.setOnClickListener(this);

        Button clearRolls = (Button) findViewById(R.id.clear_rolls_button);
        clearRolls.setOnClickListener(this);
    }

    private void setupAbilityButtons() {
        ImageButton buttonStr = (ImageButton) findViewById(R.id.button_str);
        ImageButton buttonDex = (ImageButton) findViewById(R.id.button_dex);
        ImageButton buttonCon = (ImageButton) findViewById(R.id.button_con);
        ImageButton buttonInt = (ImageButton) findViewById(R.id.button_int);
        ImageButton buttonWis = (ImageButton) findViewById(R.id.button_wis);
        ImageButton buttonCha = (ImageButton) findViewById(R.id.button_cha);

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

    public void abilityView(String bundleString, String pb) {
        Bundle bundle = new Bundle();
        bundle.putString("bundleKey", bundleString);
        bundle.putString("pbBundleKey", pb);
        DialogFragment newFragment = new AbilityDialogFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "abilityinfo");
    }

    public void setupSpinner() {
        final Spinner spinnerRace = (Spinner) findViewById(R.id.spinnerRace);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Races, R.layout.character_spinner_layout);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRace.setAdapter(adapter1);
        Spinner spinnerClass = (Spinner) findViewById(R.id.spinnerClass);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Classes, R.layout.character_spinner_layout);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClass.setAdapter(adapter2);
        Spinner spinnerBackground = (Spinner) findViewById(R.id.spinnerBackground);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.Backgrounds, R.layout.character_spinner_layout);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBackground.setAdapter(adapter3);

        spinnerRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinnerSubRace = (Spinner) findViewById(R.id.spinnerSubrace);
                switch (i) {
                    case 0:
                        ArrayAdapter<CharSequence> adapter10 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.Subraces_Dwarf, R.layout.character_spinner_layout);
                        adapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSubRace.setAdapter(adapter10);
                        break;
                    case 1:
                        ArrayAdapter<CharSequence> adapter11 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.Subraces_Elf, R.layout.character_spinner_layout);
                        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSubRace.setAdapter(adapter11);
                        break;
                    case 2:
                        ArrayAdapter<CharSequence> adapter12 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.Subraces_Halfling, R.layout.character_spinner_layout);
                        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSubRace.setAdapter(adapter12);
                        break;
                    case 3:
                        ArrayAdapter<CharSequence> adapter13 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.Subraces_Human, R.layout.character_spinner_layout);
                        adapter13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSubRace.setAdapter(adapter13);
                        break;
                    case 4:
                        ArrayAdapter<CharSequence> adapter14 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.Subraces_Dragonborn, R.layout.character_spinner_layout);
                        adapter14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSubRace.setAdapter(adapter14);
                        break;
                    case 5:
                        ArrayAdapter<CharSequence> adapter15 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.Subraces_Gnome, R.layout.character_spinner_layout);
                        adapter15.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSubRace.setAdapter(adapter15);
                        break;
                    case 6:
                        ArrayAdapter<CharSequence> adapter16 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.Subraces_HalfElf, R.layout.character_spinner_layout);
                        adapter16.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSubRace.setAdapter(adapter16);
                        break;
                    case 7:
                        ArrayAdapter<CharSequence> adapter17 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.Subraces_HalfOrc, R.layout.character_spinner_layout);
                        adapter17.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSubRace.setAdapter(adapter17);
                        break;
                    case 8:
                        ArrayAdapter<CharSequence> adapter18 = ArrayAdapter.createFromResource(getApplicationContext(),
                                R.array.Subraces_Tiefling, R.layout.character_spinner_layout);
                        adapter18.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSubRace.setAdapter(adapter18);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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
        Spinner charSubrace = (Spinner) findViewById(R.id.spinnerSubrace);
        Spinner charClass = (Spinner) findViewById(R.id.spinnerClass);
        Spinner charBackground = (Spinner) findViewById(R.id.spinnerBackground);

        int raceValue = charRace.getSelectedItemPosition();
        int subraceValue = charSubrace.getSelectedItemPosition();
        int classValue = charClass.getSelectedItemPosition();
        int backgroundValue = charBackground.getSelectedItemPosition();

        String nameKey = getString(R.string.saved_name);
        String raceKey = getString(R.string.saved_race);
        String subraceKey = getString(R.string.saved_subrace);
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
        saveCharacterDataInt(subraceKey, subraceValue);
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
        String subraceKey = getString(R.string.saved_subrace);
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
        int subraceValue = loadCharacterDataInt(subraceKey, 0);
        int classValue = loadCharacterDataInt(classKey, 0);
        int backgroundValue = loadCharacterDataInt(backgroundKey, 0);

        EditText charName = (EditText) findViewById(R.id.editTextName);
        EditText charTraits = (EditText) findViewById(R.id.editTextTraits);
        EditText charIdeals = (EditText) findViewById(R.id.editTextIdeals);
        EditText charBonds = (EditText) findViewById(R.id.editTextBonds);
        EditText charFlaws = (EditText) findViewById(R.id.editTextFlaws);

        Spinner charRace = (Spinner) findViewById(R.id.spinnerRace);
        Spinner charSubrace = (Spinner) findViewById(R.id.spinnerSubrace);
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
        if (charSubrace != null) {
            charSubrace.setSelection(subraceValue);
        }
        if (charClass != null) {
            charClass.setSelection(classValue);
        }
        if (charBackground != null) {
            charBackground.setSelection(backgroundValue);
        }

        //Expand2
        String xpKey = getString(R.string.saved_xp);
        int xpValue = loadCharacterDataInt(xpKey, 0);
        EditText xpEditText = (EditText) findViewById(R.id.xp_value);
        if (xpValue != 0) {
            assert xpEditText != null;
            xpEditText.setText(String.valueOf(xpValue));
        }else {
            assert xpEditText != null;
            xpEditText.setText("");
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

    public boolean loadCharacterDataBoolean(String key) {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        return sharedPref.getBoolean(key, false);
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

    private void clearSharedPref() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }

    @Override
    public void onClick(View view) {

        Handler mHandler = new Handler();
        ImageView animationTarget;
        Animation animation;

        ImageButton button1 = (ImageButton) findViewById(R.id.button1);
        ImageButton button2 = (ImageButton) findViewById(R.id.button2);
        ImageButton button3 = (ImageButton) findViewById(R.id.button3);
        ImageButton button4 = (ImageButton) findViewById(R.id.button4);
        if (button1 != null) {
            button1.setEnabled(false);
        }
        if (button2 != null) {
            button2.setEnabled(false);
        }
        if (button3 != null) {
            button3.setEnabled(false);
        }
        if (button4 != null) {
            button4.setEnabled(false);
        }
        TextView result = (TextView) findViewById(R.id.dice_result);
        TextView rolls = (TextView) findViewById(R.id.dice_rolls);
        TextView sum = (TextView) findViewById(R.id.dice_sum);
        Toast toast = Toast.makeText(getApplicationContext(), "Information saved!", Toast.LENGTH_SHORT);
        //Toast toast2 = Toast.makeText(getApplicationContext(), "Dice rolled!", Toast.LENGTH_LONG);
        Toast toast3 = Toast.makeText(getApplicationContext(), "Cleared!", Toast.LENGTH_LONG);
        String pbKey = getString(R.string.saved_pb);
        final String pbString = loadCharacterDataString(pbKey, "");

        final String titleKey = "title_key";
        String title = null;
        final String infoTextKey = "text_key";
        String infoText = null;

        //Main menu switch
        switch (view.getId()) {
            case R.id.button1:
                expand(findViewById(R.id.expandlayout1), findViewById(R.id.linearlayout_screen));
                title = "Character information";
                infoText = getString(R.string.expand1_infotext);
                String Expand1InfoCheckBoxKey = getString(R.string.saved_expand1_infocheckbox);
                Boolean Expand1CheckBox = loadCharacterDataBoolean(Expand1InfoCheckBoxKey);
                if (!Expand1CheckBox) {
                    final String finalTitle = title;
                    final String finalInfoText = infoText;
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            showInfoFragment(titleKey, finalTitle, infoTextKey, finalInfoText);
                        }
                    }, 500);
                }
                break;
            case R.id.button2:
                expand(findViewById(R.id.expandlayout2), findViewById(R.id.linearlayout_screen));
                title = "Character statistics";
                infoText = getString(R.string.expand2_infotext);
                String Expand2InfoCheckBoxKey = getString(R.string.saved_expand2_infocheckbox);
                Boolean Expand2CheckBox = loadCharacterDataBoolean(Expand2InfoCheckBoxKey);
                if (!Expand2CheckBox) {
                    final String finalTitle = title;
                    final String finalInfoText = infoText;
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            showInfoFragment(titleKey, finalTitle, infoTextKey, finalInfoText);
                        }
                    }, 500);
                }
                break;
            case R.id.button3:
                expand(findViewById(R.id.expandlayout3), findViewById(R.id.linearlayout_screen));
                title = "Dice simulator";
                infoText = getString(R.string.expand3_infotext);
                String Expand3InfoCheckBoxKey = getString(R.string.saved_expand3_infocheckbox);
                Boolean Expand3CheckBox = loadCharacterDataBoolean(Expand3InfoCheckBoxKey);
                if (!Expand3CheckBox) {
                    final String finalTitle = title;
                    final String finalInfoText = infoText;
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            showInfoFragment(titleKey, finalTitle, infoTextKey, finalInfoText);
                        }
                    }, 500);
                }
                break;
            case R.id.button4:
                expand(findViewById(R.id.expandlayout4), findViewById(R.id.linearlayout_screen));
                title = "Unknown";
                infoText = getString(R.string.expand4_infotext);
                String Expand4InfoCheckBoxKey = getString(R.string.saved_expand4_infocheckbox);
                Boolean Expand4CheckBox = loadCharacterDataBoolean(Expand4InfoCheckBoxKey);
                if (!Expand4CheckBox) {
                    final String finalTitle = title;
                    final String finalInfoText = infoText;
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            showInfoFragment(titleKey, finalTitle, infoTextKey, finalInfoText);
                        }
                    }, 500);
                }
                break;
        }
        //Expand1 switch
        switch (view.getId()) {
            case R.id.button_roll_race:
                String[] stringArrayRaces = getResources().getStringArray(R.array.Races);
                int randomRace = randomItemStringArray(stringArrayRaces);
                Spinner spinnerRace = (Spinner) findViewById(R.id.spinnerRace);
                if (spinnerRace != null) {
                    spinnerRace.setSelection(randomRace);
                }
                break;
            case R.id.button_roll_class:
                String[] stringArrayClass = getResources().getStringArray(R.array.Classes);
                int randomClass = randomItemStringArray(stringArrayClass);
                Spinner spinnerClass = (Spinner) findViewById(R.id.spinnerClass);
                if (spinnerClass != null) {
                    spinnerClass.setSelection(randomClass);
                }
                break;
            case R.id.button_roll_background:
                String[] stringArrayBackground = getResources().getStringArray(R.array.Backgrounds);
                int randomBackground = randomItemStringArray(stringArrayBackground);
                Spinner spinnerBackground = (Spinner) findViewById(R.id.spinnerBackground);
                if (spinnerBackground != null) {
                    spinnerBackground.setSelection(randomBackground);
                }
                break;
            case R.id.button_hide1:
                collapse(findViewById(R.id.expandlayout1));
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
                toast.show();
                break;
        }
        //Expand2 switch
        switch (view.getId()) {
            case R.id.button_str:
                animationTarget = (ImageView) this.findViewById(R.id.button_str);
                animation = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point_dice);
                if (animationTarget != null) {
                    animationTarget.startAnimation(animation);
                }
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        abilityView("str", pbString);
                    }
                }, 500);
                break;
            case R.id.button_dex:
                animationTarget = (ImageView) this.findViewById(R.id.button_dex);
                animation = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point_dice);
                if (animationTarget != null) {
                    animationTarget.startAnimation(animation);
                }
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        abilityView("dex", pbString);
                    }
                }, 500);
                break;
            case R.id.button_con:
                animationTarget = (ImageView) this.findViewById(R.id.button_con);
                animation = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point_dice);
                if (animationTarget != null) {
                    animationTarget.startAnimation(animation);
                }
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        abilityView("con", pbString);
                    }
                }, 500);
                break;
            case R.id.button_int:
                animationTarget = (ImageView) this.findViewById(R.id.button_int);
                animation = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point_dice);
                if (animationTarget != null) {
                    animationTarget.startAnimation(animation);
                }
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        abilityView("int", pbString);
                    }
                }, 500);
                break;
            case R.id.button_wis:
                animationTarget = (ImageView) this.findViewById(R.id.button_wis);
                animation = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point_dice);
                if (animationTarget != null) {
                    animationTarget.startAnimation(animation);
                }
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        abilityView("wis", pbString);
                    }
                }, 500);
                break;
            case R.id.button_cha:
                animationTarget = (ImageView) this.findViewById(R.id.button_cha);
                animation = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point_dice);
                if (animationTarget != null) {
                    animationTarget.startAnimation(animation);
                }
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        abilityView("cha", pbString);
                    }
                }, 500);
                break;
            case R.id.button_hide2:
                collapse(findViewById(R.id.expandlayout2));
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
                toast.show();
                break;
        }
        //Expand3 switch
        switch (view.getId()) {
            case R.id.dice_roll_button:
                animationTarget = (ImageView) this.findViewById(R.id.dice_roll_button);
                animation = AnimationUtils.loadAnimation(this, R.anim.rotate_around_center_point_dice);
                animationTarget.startAnimation(animation);
                diceRoll();
                break;
            case R.id.clear_rolls_button:
                rollCount = 0;
                sumCount = 0;
                if (result != null) {
                    result.setText(String.valueOf(0));
                }
                if (rolls != null) {
                    rolls.setText(String.valueOf(0));
                }
                if (sum != null) {
                    sum.setText(String.valueOf(0));
                }
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
        }
        //Expand4 switch
        switch (view.getId()) {
            case R.id.button_hide4:
                collapse(findViewById(R.id.expandlayout4));
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
                toast.show();
                break;
            case R.id.button_clear_sharedpref:
                clearSharedPref();
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
