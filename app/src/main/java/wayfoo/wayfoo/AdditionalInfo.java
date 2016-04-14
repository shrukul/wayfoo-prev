package wayfoo.wayfoo;


/**
 * Created by shrukul on 12/4/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AdditionalInfo extends AppCompatActivity {

    EditText addr,phone;
    Button btn;
    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additional_info);

        addr = (EditText)findViewById(R.id.p_addr);
        phone = (EditText)findViewById(R.id.p_phone);
        btn = (Button)findViewById(R.id.btn_setup);
        parentLayout = findViewById(android.R.id.content);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validate()) {
                    Snackbar snackbar = Snackbar.make( parentLayout, "Enter Valid Details.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;
                }

                setup();
            }
        });


    }

    private void setup() {
        String saddr,sphone;

        saddr = addr.getText().toString();
        sphone = phone.getText().toString();

        Intent it = new Intent(AdditionalInfo.this,Cart.class);
        it.putExtra("title",getIntent().getExtras().getString("title"));
        it.putExtra("table",getIntent().getExtras().getString("table"));
        it.putExtra("addr",saddr);
        it.putExtra("phone",sphone);
        startActivity(it);
        finish();
    }

    public boolean validate() {
        boolean valid = true;

        String idText = addr.getText().toString();
        String phoneText = phone.getText().toString();

        if (idText.isEmpty() || idText.length() < 5) {
            addr.setError("At least 5 characters");
            valid = false;
        } else {
            addr.setError(null);
        }

        if (phoneText.length() != 10) {
            phone.setError("Enter a valid Phone Number");
            valid = false;
        } else {
            phone.setError(null);
        }
        return valid;
    }
}