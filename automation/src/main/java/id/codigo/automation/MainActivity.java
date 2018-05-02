package id.codigo.automation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.codigo.seedroid_uikit.widget.SeedroidButton;
import id.codigo.seedroid_uikit.widget.SeedroidRelativeLayout;
import id.codigo.seedroid_uikit.widget.SeedroidTextView;

public class MainActivity extends AppCompatActivity {
    private SeedroidTextView seedroidTextView;
    private SeedroidButton seedroidButton;
    private SeedroidRelativeLayout seedroidRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        seedroidRelativeLayout = findViewById(R.id.rel_login);
        seedroidTextView = (SeedroidTextView) findViewById(R.id.text_hello);
        seedroidButton = (SeedroidButton)findViewById(R.id.button_signin);
    }
}
