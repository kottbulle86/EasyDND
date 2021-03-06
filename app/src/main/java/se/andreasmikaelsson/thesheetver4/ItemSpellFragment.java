package se.andreasmikaelsson.thesheetver4;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import static se.andreasmikaelsson.thesheetver4.R.id.fragment_container;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemSpellFragment extends Fragment {


    public ItemSpellFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout linearview = (LinearLayout) inflater.inflate(R.layout.fragment_item_spell, container, false);

        //Create remove item button, generate ID and store with fragment tag
        String fragName = getArguments().getString("fragtag");
        FrameLayout removeButtonLayout = (FrameLayout) linearview.findViewById(R.id.remove_button_layout);
        Button removeActionButton = new Button(getActivity());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        removeActionButton.setBackgroundColor(Color.TRANSPARENT);
        params.gravity = Gravity.END;
        params.setMargins(0,0,20,0);
        removeActionButton.setText("X");
        removeActionButton.setTextSize(16);
        removeActionButton.setLayoutParams(params);
        int removeActionButtonID = View.generateViewId();
        removeActionButton.setId(removeActionButtonID);
        removeButtonLayout.addView(removeActionButton);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(String.valueOf(removeActionButtonID), fragName);
        editor.putInt(fragName, removeActionButtonID);
        editor.apply();

        String bundleName = getArguments().getString("spellName");
        TextView spellName = (TextView) linearview.findViewById(R.id.fragment_item_spell_name);
        spellName.setText(bundleName);
        String bundleAttack = getArguments().getString("spellAttack");
        TextView spellAttack = (TextView) linearview.findViewById(R.id.fragment_item_spell_attack);
        spellAttack.setText(bundleAttack);
        String bundleDC = getArguments().getString("spellDC");
        TextView spellDC = (TextView) linearview.findViewById(R.id.fragment_item_spell_dc);
        spellDC.setText(bundleDC);
        String bundleDesc = getArguments().getString("spellDescription");
        TextView spellDesc = (TextView) linearview.findViewById(R.id.fragment_item_spell_description);
        spellDesc.setText(bundleDesc);

        return linearview;
    }

    @Override
    public void onStart() {
        super.onStart();
        String fragName = getArguments().getString("fragtag");
        ((MainActivity)getActivity()).setupRemoveItemButton(fragName);
    }

}
