package se.andreasmikaelsson.thesheetver4.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;

import se.andreasmikaelsson.thesheetver4.R;

import static se.andreasmikaelsson.thesheetver4.R.array.Cha_Skills;
import static se.andreasmikaelsson.thesheetver4.R.array.Dex_Skills;
import static se.andreasmikaelsson.thesheetver4.R.array.Int_Skills;
import static se.andreasmikaelsson.thesheetver4.R.array.Save_Cha_SkillsB;
import static se.andreasmikaelsson.thesheetver4.R.array.Save_Dex_SkillsB;
import static se.andreasmikaelsson.thesheetver4.R.array.Save_Int_SkillsB;
import static se.andreasmikaelsson.thesheetver4.R.array.Save_Str_SkillsB;
import static se.andreasmikaelsson.thesheetver4.R.array.Save_Wis_SkillsB;
import static se.andreasmikaelsson.thesheetver4.R.array.Str_Skills;
import static se.andreasmikaelsson.thesheetver4.R.array.Wis_Skills;

public class AbilityDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Declaration of variables and fetching saved data
        String Title = null;
        boolean[] checkedSkills = new boolean[0];
        String[] skillsScores = new String[0];
        final String ability = getArguments().getString("bundleKey");
        final String pb = getArguments().getString("pbBundleKey");
        int pbInt = 0;
        if (!Objects.equals(pb, "")) {
            pbInt = Integer.valueOf(pb);
        }
        String[] skillsNameArray = new String[0];
        String[] skillsKeyArrayB = new String[0];
        String abilityKey = null;
        String modKey = null;
        int skillScoreInt;

        //Switch that determines which ability button was pushed and declares variables
        switch (ability) {
            case "str":
                Title = "Set skill proficiencies - STRENGTH";
                // Boolean array for initial selected items
                checkedSkills = new boolean[]{
                        false // Athletics
                };
                // Int array for skill score for each item
                skillsScores = new String[]{
                        "x" // Athletics
                };
                skillsNameArray = getActivity().getResources().getStringArray(Str_Skills);
                skillsKeyArrayB = getActivity().getResources().getStringArray(Save_Str_SkillsB);
                //Load ability score
                abilityKey = getString(R.string.saved_str);
                modKey = getString(R.string.saved_mod_str);
                break;
            case "dex":
                Title = "Set skill proficiencies - DEXTERITY";
                // Boolean array for initial selected items
                checkedSkills = new boolean[]{
                        false, // Acrobatics
                        false, // Sleight of Hand
                        false // Stealth
                };
                // Int array for skill score for each item
                skillsScores = new String[]{
                        "x", // Acrobatics
                        "x", // Sleight of Hand
                        "x" // Stealth
                };
                skillsNameArray = getActivity().getResources().getStringArray(Dex_Skills);
                skillsKeyArrayB = getActivity().getResources().getStringArray(Save_Dex_SkillsB);
                //Load spinner ability score
                abilityKey = getString(R.string.saved_dex);
                modKey = getString(R.string.saved_mod_dex);
                break;
            case "con":
                Title = "CONSTITUTION";
                //Load spinner ability score
                abilityKey = getString(R.string.saved_con);
                modKey = getString(R.string.saved_mod_con);
                break;
            case "int":
                Title = "Set skill proficiencies - INTELLIGENCE";
                // Boolean array for initial selected items
                checkedSkills = new boolean[]{
                        false, // Arcane
                        false, // History
                        false, // Investigation
                        false, // Nature
                        false // Religion
                };
                skillsScores = new String[]{
                        "x", // Arcane
                        "x", // History
                        "x", // Investigation
                        "x", // Nature
                        "x" // Religion
                };
                skillsNameArray = getActivity().getResources().getStringArray(Int_Skills);
                skillsKeyArrayB = getActivity().getResources().getStringArray(Save_Int_SkillsB);
                //Load spinner ability score
                abilityKey = getString(R.string.saved_int);
                modKey = getString(R.string.saved_mod_int);
                break;
            case "wis":
                Title = "Set skill proficiencies - WISDOM";
                // Boolean array for initial selected items
                checkedSkills = new boolean[]{
                        false, // Animal Handling
                        false, // Insight
                        false, // Medicine
                        false, // Perception
                        false // Survival
                };
                skillsScores = new String[]{
                        "x", // Animal Handling
                        "x", // Insight
                        "x", // Medicine
                        "x", // Perception
                        "x" // Survival
                };
                skillsNameArray = getActivity().getResources().getStringArray(Wis_Skills);
                skillsKeyArrayB = getActivity().getResources().getStringArray(Save_Wis_SkillsB);
                //Load spinner ability score
                abilityKey = getString(R.string.saved_wis);
                modKey = getString(R.string.saved_mod_wis);
                break;
            case "cha":
                Title = "Set skill proficiencies - CHARISMA";
                // Boolean array for initial selected items
                checkedSkills = new boolean[]{
                        false, // Deception
                        false, // Intimidation
                        false, // Performance
                        false // Persuasion
                };
                skillsScores = new String[]{
                        "x", // Deception
                        "x", // Intimidation
                        "x", // Performance
                        "x" // Persuasion
                };
                skillsNameArray = getActivity().getResources().getStringArray(Cha_Skills);
                skillsKeyArrayB = getActivity().getResources().getStringArray(Save_Cha_SkillsB);
                //Load spinner ability score
                abilityKey = getString(R.string.saved_cha);
                modKey = getString(R.string.saved_mod_cha);
                break;
        }

        String[] skillsNameArrayMod = new String[skillsNameArray.length];

        for (int i = 0; i < checkedSkills.length; i++) {
            Boolean boxCheck = loadCharacterDataBoolean(skillsKeyArrayB[i]);
            skillScoreInt = Integer.valueOf(loadCharacterDataString(modKey, "0"));
            if (boxCheck) {
                checkedSkills[i] = true;
                skillScoreInt += pbInt;
                skillsScores[i] = String.valueOf(skillScoreInt);
                skillsNameArrayMod[i] = skillsNameArray[i].replace("x", skillsScores[i]);
            } else {
                checkedSkills[i] = false;
                skillsScores[i] = String.valueOf(skillScoreInt);
                skillsNameArrayMod[i] = skillsNameArray[i].replace("x", skillsScores[i]);
            }
        }

        //Inflate fragment
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final LinearLayout view = (LinearLayout) inflater.inflate(R.layout.ability_dialog, null);
        //final TextView abilityTitle = (TextView) view.findViewById(R.id.ability_title_text);
        //abilityTitle.setText(Title);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final TextView abilityMod = (TextView) view.findViewById(R.id.ability_mod);
        final Spinner spinnerScore = (Spinner) view.findViewById(R.id.spinner_ability_score);
        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.Ability_Scores, R.layout.ability_spinner_layout);
        adapter1.setDropDownViewResource(R.layout.ability_spinner_layout);
        spinnerScore.setAdapter(adapter1);

        //Set loaded spinner value
        int abilityValue = loadCharacterDataInt(abilityKey, 0);
        spinnerScore.setSelection(abilityValue);

        spinnerScore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int modScore = Integer.valueOf(spinnerScore.getSelectedItem().toString());
                int modScore2 = Math.round((modScore - 10) / 2);
                String modScoreString = String.valueOf(modScore2);
                abilityMod.setText(modScoreString);

                final String ability = getArguments().getString("bundleKey");
                String abilityKey;
                String modKey;
                switch (ability) {
                    case "str":
                        abilityKey = getString(R.string.saved_str);
                        saveCharacterDataInt(abilityKey, i);
                        modKey = getString(R.string.saved_mod_str);
                        saveCharacterDataString(modKey, modScoreString);
                        break;
                    case "dex":
                        abilityKey = getString(R.string.saved_dex);
                        saveCharacterDataInt(abilityKey, i);
                        modKey = getString(R.string.saved_mod_dex);
                        saveCharacterDataString(modKey, modScoreString);
                        break;
                    case "con":
                        abilityKey = getString(R.string.saved_con);
                        saveCharacterDataInt(abilityKey, i);
                        modKey = getString(R.string.saved_mod_con);
                        saveCharacterDataString(modKey, modScoreString);
                        break;
                    case "int":
                        abilityKey = getString(R.string.saved_int);
                        saveCharacterDataInt(abilityKey, i);
                        modKey = getString(R.string.saved_mod_int);
                        saveCharacterDataString(modKey, modScoreString);
                        break;
                    case "wis":
                        abilityKey = getString(R.string.saved_wis);
                        saveCharacterDataInt(abilityKey, i);
                        modKey = getString(R.string.saved_mod_wis);
                        saveCharacterDataString(modKey, modScoreString);
                        break;
                    case "cha":
                        abilityKey = getString(R.string.saved_cha);
                        saveCharacterDataInt(abilityKey, i);
                        modKey = getString(R.string.saved_mod_cha);
                        saveCharacterDataString(modKey, modScoreString);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button randomRoll = (Button) view.findViewById(R.id.button_roll_abilityscore);
        randomRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idx1 = new Random().nextInt(6);
                int idx2 = new Random().nextInt(6);
                int idx3 = new Random().nextInt(6);
                int idx4 = new Random().nextInt(6);
                int idx = idx1 + idx2 + idx3 + idx4;
                spinnerScore.setSelection(idx + 1);
                int modScore = Integer.valueOf(spinnerScore.getSelectedItem().toString());
                int modScore2 = Math.round((modScore - 10) / 2);
                String modScoreString = String.valueOf(modScore2);
                abilityMod.setText(modScoreString);
            }
        });

        final boolean[] finalCheckedSkills = checkedSkills;
        final String[] finalSkillsKeyArrayB = skillsKeyArrayB;
        builder.setView(view)
                .setTitle(Title)
                .setMultiChoiceItems(skillsNameArrayMod, checkedSkills,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    finalCheckedSkills[which] = true;
                                } else if (!isChecked) {
                                    finalCheckedSkills[which] = false;
                                }
                            }
                        })
                .setPositiveButton(R.string.ok_update, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // OK press
                        for (int i = 0; i < finalCheckedSkills.length; i++) {
                            if (finalCheckedSkills[i]) {
                                saveCharacterDataBoolean(finalSkillsKeyArrayB[i], true);
                            } else {
                                saveCharacterDataBoolean(finalSkillsKeyArrayB[i], false);
                            }
                        }
                        final TextView abilityMod = (TextView) view.findViewById(R.id.ability_mod);
                        String modScoreString = String.valueOf(abilityMod.getText());
                        final String ability = getArguments().getString("bundleKey");
                        String modKey;
                        switch (ability) {
                            case "str":
                                modKey = getString(R.string.saved_mod_str);
                                saveCharacterDataString(modKey, modScoreString);
                                break;
                            case "dex":
                                modKey = getString(R.string.saved_mod_dex);
                                saveCharacterDataString(modKey, modScoreString);
                                break;
                            case "con":
                                modKey = getString(R.string.saved_mod_con);
                                saveCharacterDataString(modKey, modScoreString);
                                break;
                            case "int":
                                modKey = getString(R.string.saved_mod_int);
                                saveCharacterDataString(modKey, modScoreString);
                                break;
                            case "wis":
                                modKey = getString(R.string.saved_mod_wis);
                                saveCharacterDataString(modKey, modScoreString);
                                break;
                            case "cha":
                                modKey = getString(R.string.saved_mod_cha);
                                saveCharacterDataString(modKey, modScoreString);
                                break;
                        }
                    }
                });
        return builder.create();
    }

    //Save data
    @Override
    public void onPause() {
        super.onPause();
    }

    //Load data
    @Override
    public void onResume() {
        super.onResume();
    }

    //Save data
    @Override
    public void onStop() {
        super.onStop();
    }

    public void saveCharacterDataBoolean(String key, Boolean value) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean loadCharacterDataBoolean(String key) {

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        return sharedPref.getBoolean(key, false);
    }

    public void saveCharacterDataInt(String key, int value) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int loadCharacterDataInt(String key, int defaultValueInt) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getInt(key, defaultValueInt);
    }

    public String loadCharacterDataString(String key, String defaultValueString) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(key, defaultValueString);
    }

    public void saveCharacterDataString(String key, String value) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

}