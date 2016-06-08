package se.andreasmikaelsson.thesheetver4;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static se.andreasmikaelsson.thesheetver4.R.id.fragment_container;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemActionFragment extends Fragment {


    public ItemActionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout linearview = (LinearLayout) inflater.inflate(R.layout.fragment_item_action, container, false);

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

        String bundleName = getArguments().getString("actionName");
        TextView actionName = (TextView) linearview.findViewById(R.id.fragment_item_action_name);
        actionName.setText(bundleName);
        String bundleAttack = getArguments().getString("actionAttack");
        TextView actionAttack = (TextView) linearview.findViewById(R.id.fragment_item_action_attack);
        actionAttack.setText(bundleAttack);
        String bundleDC = getArguments().getString("actionDC");
        TextView actionDC = (TextView) linearview.findViewById(R.id.fragment_item_action_dc);
        actionDC.setText(bundleDC);
        String bundleDesc = getArguments().getString("actionDescription");
        TextView actionDesc = (TextView) linearview.findViewById(R.id.fragment_item_action_description);
        actionDesc.setText(bundleDesc);

        return linearview;
    }

    @Override
    public void onStart() {
        super.onStart();
        String fragName = getArguments().getString("fragtag");
        ((MainActivity)getActivity()).setupRemoveItemButton(fragName);
    }
}
