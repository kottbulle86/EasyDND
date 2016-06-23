package se.andreasmikaelsson.thesheetver4.nsd;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.nsd.NsdServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import se.andreasmikaelsson.thesheetver4.R;
import se.andreasmikaelsson.thesheetver4.fragments.InfoDialogFragment;

public class NsdChatActivity extends AppCompatActivity {
    NsdHelper mNsdHelper;
    private TextView mStatusView;
    private Handler mUpdateHandler;
    public static final String TAG = "NsdChat";
    ChatConnection mConnection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Creating chat activity");
        setContentView(R.layout.activity_nsd_chat);
        mStatusView = (TextView) findViewById(R.id.status);
        mUpdateHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String chatLine = msg.getData().getString("msg");
                addChatLine(chatLine);
            }
        };
        String titleKey = "title_key";
        String title = "Whisper Chat";
        String infoTextKey = "text_key";
        String infoText = getString(R.string.whisper_infotext);
        String whisperInfoCheckBoxKey = getString(R.string.saved_whisper_infocheckbox);
        Boolean whisperCheckBox = loadCharacterDataBoolean(whisperInfoCheckBoxKey);
        if (!whisperCheckBox) {
            showInfoFragment(titleKey, title, infoTextKey, infoText);
        }
    }

    private void showInfoFragment(String titleKey, String title, String infoTextKey, String infoText) {
        Bundle bundle = new Bundle();
        bundle.putString(titleKey, title);
        bundle.putString(infoTextKey, infoText);
        DialogFragment newFragment = new InfoDialogFragment();
        newFragment.setArguments(bundle);
        newFragment.show(getSupportFragmentManager(), "infoText");
    }

    public boolean loadCharacterDataBoolean(String key) {

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        return sharedPref.getBoolean(key, false);
    }

    public void clickAdvertise(View v) {
        Toast toast1 = Toast.makeText(this, "Device registered!", Toast.LENGTH_LONG);
        // Register service
        if (mConnection.getLocalPort() > -1) {
            mNsdHelper.registerService(mConnection.getLocalPort());
            toast1.show();
        } else {
            Log.d(TAG, "ServerSocket isn't bound.");
        }
    }

    public void clickDiscover(View v) {
        Toast toast2 = Toast.makeText(this, "Searching...", Toast.LENGTH_LONG);
        toast2.show();
        mNsdHelper.discoverServices();
    }

    public void clickConnect(View v) {
        NsdServiceInfo service = mNsdHelper.getChosenServiceInfo();
        if (service != null) {
            Log.d(TAG, "Connecting.");
            Toast toast3 = Toast.makeText(this, "Connecting...", Toast.LENGTH_LONG);
            toast3.show();
            mConnection.connectToServer(service.getHost(),
                    service.getPort());
        } else {
            Log.d(TAG, "No service to connect to!");
            Toast toast4 = Toast.makeText(this, "No service to connect to!", Toast.LENGTH_LONG);
            toast4.show();
        }
    }

    public void clickSend(View v) {
        EditText messageView = (EditText) this.findViewById(R.id.chatInput);
        if (messageView != null) {
            String messageString = messageView.getText().toString();
            if (!messageString.isEmpty()) {
                mConnection.sendMessage(messageString);
            }
            messageView.setText("");
        }
    }

    public void addChatLine(String line) {
        mStatusView.append("\n" + line);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "Starting.");
        mConnection = new ChatConnection(mUpdateHandler);
        mNsdHelper = new NsdHelper(this);
        mNsdHelper.initializeNsd();
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Pausing.");
        if (mNsdHelper != null) {
            mNsdHelper.stopDiscovery();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "Resuming.");
        super.onResume();
        if (mNsdHelper != null) {
            mNsdHelper.discoverServices();
        }
    }

    // For KitKat and earlier releases, it is necessary to remove the
    // service registration when the application is stopped.  There's
    // no guarantee that the onDestroy() method will be called (we're
    // killable after onStop() returns) and the NSD service won't remove
    // the registration for us if we're killed.
    // In L and later, NsdService will automatically unregister us when
    // our connection goes away when we're killed, so this step is
    // optional (but recommended).
    @Override
    protected void onStop() {
        Log.d(TAG, "Being stopped.");
        mNsdHelper.tearDown();
        mConnection.tearDown();
        mNsdHelper = null;
        mConnection = null;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Being destroyed.");
        super.onDestroy();
    }
}