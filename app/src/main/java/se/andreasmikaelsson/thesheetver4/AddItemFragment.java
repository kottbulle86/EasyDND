package se.andreasmikaelsson.thesheetver4;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import static se.andreasmikaelsson.thesheetver4.R.id.fragment_container;

public class AddItemFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final int[] k = new int[8];

        //Inflate fragment
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final LinearLayout linearview = (LinearLayout) inflater.inflate(R.layout.fragment_add_item, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final Spinner spinnerAddItem = (Spinner) linearview.findViewById(R.id.spinner_item_type);
        final ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.spinner_add_item, R.layout.ability_spinner_layout);
        adapter1.setDropDownViewResource(R.layout.ability_spinner_layout);
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
                switch (i) {
                    case 0:
                        weaponLayout.setVisibility(View.VISIBLE);
                        k[0] = i;
                        break;
                    case 1:
                        spellLayout.setVisibility(View.VISIBLE);
                        k[1] = i;
                        break;
                    case 2:
                        actionLayout.setVisibility(View.VISIBLE);
                        k[2] = i;
                        break;
                    case 3:
                        armorLayout.setVisibility(View.VISIBLE);
                        k[3] = i;
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
                        switch (k[0]) {
                            case 0:
                                newFragment();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }

    private void newFragment() {
        // Create new fragment and transaction
        Fragment newFragment = new ItemFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.add(fragment_container, newFragment);
                //(R.id.fragment_container, newFragment);
        //transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    public void saveCharacterDataString(String key, String value) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
