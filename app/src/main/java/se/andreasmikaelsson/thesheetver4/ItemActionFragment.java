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
public class ItemActionFragment extends Fragment {


    public ItemActionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout linearview = (LinearLayout) inflater.inflate(R.layout.fragment_item_action, container, false);

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

}
