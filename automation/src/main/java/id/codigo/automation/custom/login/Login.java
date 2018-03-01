package id.codigo.automation.custom.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import id.codigo.automation.R;
import id.codigo.seedroid_uikit.widget.SeedroidButton;
import id.codigo.seedroid_uikit.widget.SeedroidRelativeLayout;
import id.codigo.seedroid_uikit.widget.SeedroidTextView;


/**
 * Created by papahnakal on 01/03/18.
 */

public class Login extends RelativeLayout implements View.OnClickListener{
    private InputMethodManager inputMethodManager;
    private SeedroidTextView seedroidTextView;
    private SeedroidButton seedroidButton;
    private SeedroidRelativeLayout seedroidRelativeLayout;

    private String account = "";
    private String password;
    private String cachedAccount;
    public Login(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.login_layout, this);
        inputMethodManager = (InputMethodManager) context.getSystemService(Context
                .INPUT_METHOD_SERVICE);
        initView();
        initData();
        initListener();

        //setFocus(accountEdit, true);
    }
    private void initView(){
        seedroidRelativeLayout = findViewById(R.id.rel_login);
        seedroidTextView = (SeedroidTextView) findViewById(R.id.text_hello);
        seedroidButton = (SeedroidButton)findViewById(R.id.button_signin);
    }
    private void initData(){

    }
    private void initListener(){

    }
    @Override
    public void onClick(View view) {

    }
}
