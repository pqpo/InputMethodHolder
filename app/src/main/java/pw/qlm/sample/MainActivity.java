package pw.qlm.sample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pw.qlm.inputmethodholder.OnInputMethodListener;
import pw.qlm.inputmethodholder.InputMethodHolder;

public class MainActivity extends AppCompatActivity {

    OnInputMethodListener onInputMethodListener;
    EditText et;
    Button btnHook;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.et);
        btnHook = (Button) findViewById(R.id.btn_init);
        btnClose = (Button) findViewById(R.id.btn_close_input);

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

        InputMethodHolder.registerListener(onInputMethodListener);

        //FIXME 在这初始化会失败，目前只能在Application中
//        btnHook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InputMethodHolder.init(getApplication().getBaseContext());
//            }
//        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InputMethodHolder.unregisterListener(onInputMethodListener);
        InputMethodHolder.release();
    }
}
