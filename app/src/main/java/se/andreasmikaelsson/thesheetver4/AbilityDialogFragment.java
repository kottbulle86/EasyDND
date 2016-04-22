package se.andreasmikaelsson.thesheetver4;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.StringRes;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static se.andreasmikaelsson.thesheetver4.R.array.Cha_Skills;
import static se.andreasmikaelsson.thesheetver4.R.array.Dex_Skills;
import static se.andreasmikaelsson.thesheetver4.R.array.Int_Skills;
import static se.andreasmikaelsson.thesheetver4.R.array.Str_Skills;
import static se.andreasmikaelsson.thesheetver4.R.array.Wis_Skills;

public class AbilityDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.ability_dialog, null);

        boolean[] checkedSkills = new boolean[0];
        final TextView abilityTitle = (TextView) view.findViewById(R.id.ability_title_text);
        final String ability = getArguments().getString("bundleKey");
        int abilityID = 0;
        String[] skillsNameArray = new String[0];
        switch (ability) {
            case "str":
                abilityID = getResources().getIdentifier("Str_Skills", "array", "se.andreasmikaelsson.thesheetver4");
                String Title1 = "STRENGTH";
                abilityTitle.setText(Title1);
                // Boolean array for initial selected items
                final boolean[] checkedSkillsStr = new boolean[]{
                        false // Athletics
                };
                checkedSkills = checkedSkillsStr;
                skillsNameArray = getActivity().getResources().getStringArray(Str_Skills);
                break;
            case "dex":
                abilityID = getResources().getIdentifier("Dex_Skills", "array", "se.andreasmikaelsson.thesheetver4");
                String Title2 = "DEXTERITY";
                abilityTitle.setText(Title2);
                // Boolean array for initial selected items
                final boolean[] checkedSkillsDex = new boolean[]{
                        false, // Acrobatics
                        false, // Sleight of Hand
                        false // Stealth
                };
                checkedSkills = checkedSkillsDex;
                skillsNameArray = getActivity().getResources().getStringArray(Dex_Skills);
                break;
            case "con":
                abilityID = getResources().getIdentifier("Con_Skills", "array", "se.andreasmikaelsson.thesheetver4");
                String Title3 = "CONSTITUTION";
                abilityTitle.setText(Title3);
                break;
            case "int":
                abilityID = getResources().getIdentifier("Int_Skills", "array", "se.andreasmikaelsson.thesheetver4");
                String Title4 = "INTELLIGENCE";
                abilityTitle.setText(Title4);
                // Boolean array for initial selected items
                final boolean[] checkedSkillsInt = new boolean[]{
                        false, // Arcane
                        false, // History
                        false, // Investigation
                        false, // Nature
                        false // Religion
                };
                checkedSkills = checkedSkillsInt;
                skillsNameArray = getActivity().getResources().getStringArray(Int_Skills);
                break;
            case "wis":
                abilityID = getResources().getIdentifier("Wis_Skills", "array", "se.andreasmikaelsson.thesheetver4");
                String Title5 = "WISDOM";
                abilityTitle.setText(Title5);
                // Boolean array for initial selected items
                final boolean[] checkedSkillsWis = new boolean[]{
                        false, // Animal Handling
                        false, // Insight
                        false, // Medicine
                        false, // Perception
                        false // Survival
                };
                checkedSkills = checkedSkillsWis;
                skillsNameArray = getActivity().getResources().getStringArray(Wis_Skills);
                break;
            case "cha":
                abilityID = getResources().getIdentifier("Cha_Skills", "array", "se.andreasmikaelsson.thesheetver4");
                String Title6 = "CHARISMA";
                abilityTitle.setText(Title6);
                // Boolean array for initial selected items
                final boolean[] checkedSkillsCha = new boolean[]{
                        false, // Deception
                        false, // Intimidation
                        false, // Performance
                        false // Persuasion
                };
                checkedSkills = checkedSkillsCha;
                skillsNameArray = getActivity().getResources().getStringArray(Cha_Skills);
                break;
        }

        for (int i = 0; i < checkedSkills.length; i++) {
            Boolean boxCheck = loadCharacterDataBoolean(skillsNameArray[i]);
            if (boxCheck == true) {
                checkedSkills[i] = true;
            }else {
                checkedSkills[i] = false;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final TextView abilityMod = (TextView) view.findViewById(R.id.ability_mod);
        final Spinner spinnerScore = (Spinner) view.findViewById(R.id.spinner_ability_score);
        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.Ability_Scores, R.layout.spinner_layout);
        adapter1.setDropDownViewResource(R.layout.spinner_layout);
        spinnerScore.setAdapter(adapter1);
        spinnerScore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int modScore = Integer.valueOf(spinnerScore.getSelectedItem().toString());
                int modScore2 = Math.round((modScore - 10) / 2);
                String modScoreString = String.valueOf(modScore2);
                abilityMod.setText(modScoreString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button randomRoll = (Button) view.findViewById(R.id.button_roll_abilityscore);
        randomRoll.setOnClickListener(new View.OnClickListener()
        {
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
        final String[] finalSkillsNameArray = skillsNameArray;
        builder.setView(view)
            .setTitle(R.string.dialog_skills_title)
            .setMultiChoiceItems(abilityID, checkedSkills,
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
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // OK press
                        for (int i = 0; i < finalCheckedSkills.length; i++) {
                            if (finalCheckedSkills[i] == true) {
                                saveCharacterDataBoolean(finalSkillsNameArray[i], true);
                            }else {
                                saveCharacterDataBoolean(finalSkillsNameArray[i], false);
                            }
                        }
                    }
                });
        return builder.create();
    }

    public void saveCharacterDataString(String key, String value) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public void saveCharacterDataBoolean(String key, Boolean value) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public String loadCharacterDataString(String key, String defaultValueString) {

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        return sharedPref.getString(key, defaultValueString);
    }
    public boolean loadCharacterDataBoolean(String key) {

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        return sharedPref.getBoolean(key, false);
    }

}