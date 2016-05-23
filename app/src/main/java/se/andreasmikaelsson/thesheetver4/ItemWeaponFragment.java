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
public class ItemWeaponFragment extends Fragment {


    public ItemWeaponFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout linearview = (LinearLayout) inflater.inflate(R.layout.fragment_item_weapon, container, false);

        String bundleName = getArguments().getString("weaponName");
        TextView weaponName = (TextView) linearview.findViewById(R.id.fragment_item_weapon_name);
        weaponName.setText(bundleName);
        String bundleAttack = getArguments().getString("weaponAttack");
        TextView weaponAttack = (TextView) linearview.findViewById(R.id.fragment_item_weapon_attack);
        weaponAttack.setText(bundleAttack);
        String bundleDamage = getArguments().getString("weaponDamage");
        TextView weaponDamage = (TextView) linearview.findViewById(R.id.fragment_item_weapon_damage);
        weaponDamage.setText(bundleDamage);
        String bundleDesc = getArguments().getString("weaponDescription");
        TextView weaponDesc = (TextView) linearview.findViewById(R.id.fragment_item_weapon_description);
        weaponDesc.setText(bundleDesc);

        return linearview;
    }


}
