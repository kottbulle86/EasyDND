package se.andreasmikaelsson.thesheetver4.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import se.andreasmikaelsson.thesheetver4.R;

public class RemoveDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String Title = getArguments().getString("title_key");
        final String infoText = getArguments().getString("text_key");
        final int buttonID = getArguments().getInt("buttonID");

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        final String fragName = sharedPref.getString(String.valueOf(buttonID), getArguments().getString("fragtag"));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_remove_dialog, null);

        TextView infoTextView = (TextView) view.findViewById(R.id.remove_fragement_textview);
        infoTextView.setText(infoText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(view)
                .setTitle(Title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag(fragName)).commit();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Cancel press
                    }
                });
        return builder.create();
    }
}