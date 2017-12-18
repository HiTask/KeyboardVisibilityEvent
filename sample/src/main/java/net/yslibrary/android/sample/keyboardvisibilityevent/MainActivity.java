package net.yslibrary.android.sample.keyboardvisibilityevent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView mKeyboardStatus;

    EditText mTextField;

    Button mButtonUnregister;

    Unregistrar mUnregistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mKeyboardStatus = (TextView) findViewById(R.id.keyboard_status);
        mTextField = (EditText) findViewById(R.id.text_field);
        mButtonUnregister = (Button) findViewById(R.id.btn_unregister);

        /*
          You can also use {@link KeyboardVisibilityEvent#setEventListener(Activity, KeyboardVisibilityEventListener)}
          if you don't want to unregister the event manually.
         */
        mUnregistrar = KeyboardVisibilityEvent.registerEventListener(this, new KeyboardVisibilityEventListener() {
            @Override
            public void onKeyboardOpened(int keyboardHeight) {
                updateKeyboardStatusText(true, keyboardHeight);
            }

            @Override
            public void onKeyboardClosed() {
                updateKeyboardStatusText(false);
            }
        });

        updateKeyboardStatusText(KeyboardVisibilityEvent.isKeyboardVisible(this));

        mButtonUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregister();
            }
        });
    }

    private void updateKeyboardStatusText(boolean isOpen) {
        updateKeyboardStatusText(isOpen, 0);
    }

    private void updateKeyboardStatusText(boolean isOpen, int keyboardHeight) {
        String keyboardStatusText;
        if (isOpen) {
            keyboardStatusText = String.format(
                    Locale.getDefault(), "Keyboard is visible. Its height = %d", keyboardHeight);
        } else {
            keyboardStatusText = "Keyboard is hidden";
        }
        mKeyboardStatus.setText(keyboardStatusText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void unregister() {
        mUnregistrar.unregister();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mUnregistrar.unregister();
    }
}
