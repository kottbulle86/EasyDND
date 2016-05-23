package se.andreasmikaelsson.thesheetver4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
}
