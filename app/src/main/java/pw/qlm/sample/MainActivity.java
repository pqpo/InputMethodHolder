package pw.qlm.sample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import pw.qlm.softinputmethodholder.OnInputMethodListener;
import pw.qlm.softinputmethodholder.SoftInputMethodHolder;

public class MainActivity extends AppCompatActivity {

    OnInputMethodListener onInputMethodListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onInputMethodListener = new OnInputMethodListener() {
            @Override
            public void onShow(boolean result) {
                Toast.makeText(MainActivity.this, "Show input method! " + result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onHide(boolean result) {
                Toast.makeText(MainActivity.this, "Hide input method! " + result, Toast.LENGTH_SHORT).show();
            }
        };
        SoftInputMethodHolder.registerListener(onInputMethodListener);
        Button btn = (Button) findViewById(R.id.btn);
        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoftInputMethodHolder.unregisterListener(onInputMethodListener);
        SoftInputMethodHolder.release();
    }
}
