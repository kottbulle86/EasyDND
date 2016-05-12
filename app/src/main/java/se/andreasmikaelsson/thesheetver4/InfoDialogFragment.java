package se.andreasmikaelsson.thesheetver4;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfoDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String Title = getArguments().getString("title_key");
        final String infoText = getArguments().getString("text_key");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_info_dialog, null);

        TextView infoTextView = (TextView) view.findViewById(R.id.info_fragement_textview);
        infoTextView.setText(infoText);
        final CheckBox infoCheckBox = (CheckBox) view.findViewById(R.id.info_fragment_checkbox);
        String infoCheckBoxKey = null;

        switch (Title) {
            case "Welcome!":
                infoCheckBoxKey = getString(R.string.saved_mainmenu_infocheckbox);
                break;
            case "Character information":
                infoCheckBoxKey = getString(R.string.saved_expand1_infocheckbox);
                break;
            case "Character statistics":
                infoCheckBoxKey = getString(R.string.saved_expand2_infocheckbox);
                break;
            case "Dice simulator":
                infoCheckBoxKey = getString(R.string.saved_expand3_infocheckbox);
                break;
            case "Unknown":
                infoCheckBoxKey = getString(R.string.saved_expand4_infocheckbox);
                break;
            case "Whisper Chat":
                infoCheckBoxKey = getString(R.string.saved_whisper_infocheckbox);
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String finalInfoCheckBoxKey = infoCheckBoxKey;
        builder.setView(view)
                .setTitle(Title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // OK press
                        if (infoCheckBox.isChecked()) {
                            saveCharacterDataBoolean(finalInfoCheckBoxKey, true);
                        }
                    }
                });
        return builder.create();
    }

    public void saveCharacterDataBoolean(String key, Boolean value) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
