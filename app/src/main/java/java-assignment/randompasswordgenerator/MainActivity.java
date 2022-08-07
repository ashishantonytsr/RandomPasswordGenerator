package com.example.randompasswordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editPasswordSize;
    private TextView textPasswordGenerated, textErrorMessage;
    private CheckBox checkLower, checkUpper, checkSpecialChar, checkNumeric;
    private Button btnGenerate, btnCopy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        clickListeners();
        }

    private void clickListeners(){
        btnGenerate.setOnClickListener(view -> {
            int passwordSize = Integer.parseInt( editPasswordSize.getText().toString() );

            textErrorMessage.setText("");
            if(passwordSize < 8) {
                textErrorMessage.setText("Password size must be greater than 8");
                return;
            }

            PasswordGenerator.clear();
            if(checkLower.isChecked()) PasswordGenerator.add(new LowerCaseGenerator());
            if(checkUpper.isChecked()) PasswordGenerator.add(new UpperCaseGenerator());
            if(checkNumeric.isChecked()) PasswordGenerator.add(new NumericGenerator());
            if(checkSpecialChar.isChecked()) PasswordGenerator.add(new SpecialCharGenerator());

            if(PasswordGenerator.isEmpty()){
                textErrorMessage.setText("Please select at least one password content type");
                return;
            }

            String password = PasswordGenerator.generatePassword(passwordSize);
            textPasswordGenerated.setText(password);
        });

        btnCopy.setOnClickListener(view -> {
            ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            manager.setPrimaryClip((ClipData.newPlainText("password", textPasswordGenerated.getText().toString())) );
            Toast.makeText(this, "Password Copied", Toast.LENGTH_SHORT).show();
        });
    }

    private void initViews(){
        editPasswordSize = findViewById(R.id.edit_pwd_size);
        textPasswordGenerated = findViewById(R.id.text_password_result);
        textErrorMessage = findViewById(R.id.text_error);
        checkUpper = findViewById(R.id.check_upper);
        checkLower = findViewById(R.id.check_lower);
        checkNumeric = findViewById(R.id.check_numeric);
        checkSpecialChar = findViewById(R.id.check_special_char);
        btnCopy = findViewById(R.id.btn_copy);
        btnGenerate = findViewById(R.id.btn_generate);
    }

}