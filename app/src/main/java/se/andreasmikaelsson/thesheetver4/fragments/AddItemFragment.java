package se.andreasmikaelsson.thesheetver4.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import se.andreasmikaelsson.thesheetver4.R;

import static se.andreasmikaelsson.thesheetver4.R.id.fragment_container;

public class AddItemFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int[] k = new int[1];

        //Inflate fragment
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final LinearLayout linearview = (LinearLayout) inflater.inflate(R.layout.fragment_add_item, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final Spinner spinnerAddItem = (Spinner) linearview.findViewById(R.id.spinner_item_type);
        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.spinner_add_item, R.layout.character_spinner_layout);
        adapter1.setDropDownViewResource(R.layout.character_spinner_layout);
        spinnerAddItem.setAdapter(adapter1);

        spinnerAddItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout weaponLayout = (LinearLayout) linearview.findViewById(R.id.linearlayout_item_weapon);
                weaponLayout.setVisibility(View.GONE);
                LinearLayout spellLayout = (LinearLayout) linearview.findViewById(R.id.linearlayout_item_spell);
                spellLayout.setVisibility(View.GONE);
                LinearLayout actionLayout = (LinearLayout) linearview.findViewById(R.id.linearlayout_item_action);
                actionLayout.setVisibility(View.GONE);
                LinearLayout armorLayout = (LinearLayout) linearview.findViewById(R.id.linearlayout_item_armor);
                armorLayout.setVisibility(View.GONE);
                LinearLayout moneyLayout = (LinearLayout) linearview.findViewById(R.id.linearlayout_item_money);
                moneyLayout.setVisibility(View.GONE);
                switch (i) {
                    case 0:
                        weaponLayout.setVisibility(View.VISIBLE);
                        k[0] = i;
                        break;
                    case 1:
                        spellLayout.setVisibility(View.VISIBLE);
                        k[0] = i;
                        break;
                    case 2:
                        actionLayout.setVisibility(View.VISIBLE);
                        k[0] = i;
                        break;
                    case 3:
                        armorLayout.setVisibility(View.VISIBLE);
                        k[0] = i;
                        break;
                    case 4:
                        moneyLayout.setVisibility(View.VISIBLE);
                        k[0] = i;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        builder.setView(linearview)
                .setTitle("Add item menu")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int fragNumber = loadCharacterDataInt("fragNumber", 0);
                        switch (k[0]) {
                            case 0:
                                EditText weaponNameEdit = (EditText) linearview.findViewById(R.id.editText_weapon_name);
                                EditText weaponAttEdit = (EditText) linearview.findViewById(R.id.editText_weapon_attack_bonus);
                                EditText weaponDmgEdit = (EditText) linearview.findViewById(R.id.editText_weapon_damage);
                                EditText weaponDescEdit = (EditText) linearview.findViewById(R.id.editText_weapon_desc);

                                String weaponName = String.valueOf(weaponNameEdit.getText());
                                String weaponAtt = String.valueOf(weaponAttEdit.getText());
                                String weaponDmg = String.valueOf(weaponDmgEdit.getText());
                                String weaponDesc = String.valueOf(weaponDescEdit.getText());

                                newWeaponFragment(fragNumber, weaponName, weaponAtt, weaponDmg, weaponDesc);
                                break;
                            case 1:
                                EditText spellNameEdit = (EditText) linearview.findViewById(R.id.editText_spell_name);
                                EditText spellAttEdit = (EditText) linearview.findViewById(R.id.editText_spell_attack_bonus);
                                EditText spellDCEdit = (EditText) linearview.findViewById(R.id.editText_spell_DC);
                                EditText spellDescEdit = (EditText) linearview.findViewById(R.id.editText_spell_desc);

                                String spellName = String.valueOf(spellNameEdit.getText());
                                String spellAtt = String.valueOf(spellAttEdit.getText());
                                String spellDC = String.valueOf(spellDCEdit.getText());
                                String spellDesc = String.valueOf(spellDescEdit.getText());

                                newSpellFragment(fragNumber, spellName, spellAtt, spellDC, spellDesc);
                                break;
                            case 2:
                                EditText actionNameEdit = (EditText) linearview.findViewById(R.id.editText_action_name);
                                EditText actionAttEdit = (EditText) linearview.findViewById(R.id.editText_action_attack_bonus);
                                EditText actionDCEdit = (EditText) linearview.findViewById(R.id.editText_action_DC);
                                EditText actionDescEdit = (EditText) linearview.findViewById(R.id.editText_action_desc);

                                String actionName = String.valueOf(actionNameEdit.getText());
                                String actionAtt = String.valueOf(actionAttEdit.getText());
                                String actionDC = String.valueOf(actionDCEdit.getText());
                                String actionDesc = String.valueOf(actionDescEdit.getText());

                                newActionFragment(fragNumber, actionName, actionAtt, actionDC, actionDesc);
                                break;
                            case 3:
                                EditText armorNameEdit = (EditText) linearview.findViewById(R.id.editText_armor_name);
                                EditText armorACEdit = (EditText) linearview.findViewById(R.id.editText_armor_AC);
                                EditText armorDescEdit = (EditText) linearview.findViewById(R.id.editText_armor_desc);

                                String armorName = String.valueOf(armorNameEdit.getText());
                                String armorAC = String.valueOf(armorACEdit.getText());
                                String armorDesc = String.valueOf(armorDescEdit.getText());

                                newArmorFragment(fragNumber, armorName, armorAC, armorDesc);
                                break;
                            case 4:
                                EditText goldEdit = (EditText) linearview.findViewById(R.id.editText_gold);
                                EditText silverEdit = (EditText) linearview.findViewById(R.id.editText_silver);
                                EditText copperEdit = (EditText) linearview.findViewById(R.id.editText_copper);

                                String gold = String.valueOf(goldEdit.getText());
                                String silver = String.valueOf(silverEdit.getText());
                                String copper = String.valueOf(copperEdit.getText());

                                newMoneyFragment(fragNumber, gold, silver, copper);
                                break;
                        }
                        if (fragNumber > 20) {
                            fragNumber = 0;
                        }
                        fragNumber++;
                        saveCharacterDataInt("fragNumber", fragNumber);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }

    private void newWeaponFragment(int i, String name, String att, String dmg, String desc) {
        // Create new fragment and transaction
        String fragName = "fragName" + String.valueOf(i);
        Bundle bundle = new Bundle();
        bundle.putString("weaponName", name);
        bundle.putString("weaponAttack", att);
        bundle.putString("weaponDamage", dmg);
        bundle.putString("weaponDescription", desc);
        bundle.putString("fragtag", fragName);
        Fragment newFragment = new ItemWeaponFragment();
        newFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(fragment_container, newFragment, fragName);

        // Commit the transaction
        transaction.commit();
    }

    private void newSpellFragment(int i, String name, String att, String dc, String desc) {
        // Create new fragment and transaction
        String fragName = "fragName" + String.valueOf(i);
        Bundle bundle = new Bundle();
        bundle.putString("spellName", name);
        bundle.putString("spellAttack", att);
        bundle.putString("spellDC", dc);
        bundle.putString("spellDescription", desc);
        bundle.putString("fragtag", fragName);
        Fragment newFragment = new ItemSpellFragment();
        newFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(fragment_container, newFragment, fragName);

        // Commit the transaction
        transaction.commit();
    }

    private void newActionFragment(int i, String name, String att, String dc, String desc) {
        // Create new fragment and transaction
        String fragName = "fragName" + String.valueOf(i);
        Bundle bundle = new Bundle();
        bundle.putString("actionName", name);
        bundle.putString("actionAttack", att);
        bundle.putString("actionDC", dc);
        bundle.putString("actionDescription", desc);
        bundle.putString("fragtag", fragName);
        Fragment newFragment = new ItemActionFragment();
        newFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(fragment_container, newFragment, fragName);

        // Commit the transaction
        transaction.commit();
    }

    private void newArmorFragment(int i, String name, String AC, String desc) {
        // Create new fragment and transaction
        String fragName = "fragName" + String.valueOf(i);
        Bundle bundle = new Bundle();
        bundle.putString("armorName", name);
        bundle.putString("armorAC", AC);
        bundle.putString("armorDescription", desc);
        bundle.putString("fragtag", fragName);
        Fragment newFragment = new ItemArmorFragment();
        newFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(fragment_container, newFragment, fragName);

        // Commit the transaction
        transaction.commit();
    }

    private void newMoneyFragment(int i, String gold, String silver, String copper) {
        // Create new fragment and transaction
        String fragName = "fragName" + String.valueOf(i);
        Bundle bundle = new Bundle();
        bundle.putString("gold", gold);
        bundle.putString("silver", silver);
        bundle.putString("copper", copper);
        bundle.putString("fragtag", fragName);
        Fragment newFragment = new ItemMoneyFragment();
        newFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(fragment_container, newFragment, fragName);

        // Commit the transaction
        transaction.commit();
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

    public void saveCharacterDataString(String key, String value) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
