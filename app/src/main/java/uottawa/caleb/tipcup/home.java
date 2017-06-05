package uottawa.caleb.tipcup;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.stream.Stream;

public class home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int[] requiredTextFields = {R.id.bill_field, R.id.tip_field, R.id.people_field};
        for(int requiredTextField : requiredTextFields) {
            setRequiredFieldHandler(requiredTextField);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    private void setRequiredFieldHandler(final int id) {
        final TextInputLayout field = (TextInputLayout)findViewById(id);
        field.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(TextUtils.isEmpty(field.getEditText().getText())) {
                        if(id == R.id.bill_field) {
                            TextView helperText = (TextView) findViewById(R.id.bill_helper_text);
                            helperText.setVisibility(View.INVISIBLE);
                        }
                        field.setError("Error: This field is required");
                    }
                }
            }
        });

        field.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if(!TextUtils.isEmpty(s)) {
                    field.setError(null);
                }
                if(id == R.id.bill_field) {
                    final TextView helperText = (TextView) findViewById(R.id.bill_helper_text);
                    helperText.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            helperText.setVisibility(View.VISIBLE);
                        }
                    }, 500);
                }
            }
        });
    }
}
