package uottawa.caleb.tipcup;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.prefs.Preferences;

public class home extends AppCompatActivity {
    private int[] requiredTextFields = {R.id.bill_field, R.id.tip_field, R.id.people_field};

    boolean billfieldEmpty, tipfieldEmpty, peopleFieldEmpty, peopleFieldZero;

    private int getPeople() {
        return Integer.parseInt(((TextInputLayout)findViewById(R.id.people_field)).getEditText().getText().toString());
    }

    private double getTip() {
        return Double.parseDouble(((TextInputLayout)findViewById(R.id.tip_field)).getEditText().getText().toString());
    }

    private double getBill() {
        return Double.parseDouble(((TextInputLayout)findViewById(R.id.bill_field)).getEditText().getText().toString());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        for(int requiredTextField : requiredTextFields) {
            setRequiredFieldHandler(requiredTextField);
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        EditText text = (EditText)findViewById(R.id.tip_edit_text);
        if(TextUtils.isEmpty(text.getText())) {
            String res = prefs.getString("pref_tip", "");
            if(!res.equals("")) {
                text.setText(res);
            }
        }


//        prefs.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
//            @Override
//            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//                if(key == "pref_currency") {
//                    ((EditText)findViewById(R.id.bill_edit_text)).setHint("Bill amount ( " + sharedPreferences.getString(key, "$") + ")");
//                }
//                if(key == "pref_tip") {
//                    EditText text = (EditText)findViewById(R.id.tip_edit_text);
//                    if(TextUtils.isEmpty(text.getText())) {
//                        String res = sharedPreferences.getString("pref_tip", "");
//                        if(!res.equals("")) {
//                            text.setText(res);
//                        }
//                    }
//                }
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public void onCalculate(View view) {
        String errmsg = this.validateAll();
        if(errmsg == null) {
            Intent intent = new Intent(this, SummaryActivity.class);
            intent.putExtra("people", getPeople())
                    .putExtra("bill", getBill())
                    .putExtra("tip", getTip());
            startActivity(intent);
        }
        else {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.top_layout), errmsg, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void onTipSuggestion(Integer tip) {
        EditText tipField = (EditText)findViewById(R.id.tip_edit_text);
        if(tip != null) {
            tipField.setText(tip.toString());
            findViewById(R.id.editText10).requestFocus();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent in = new Intent(this, SettingsActivity.class);
            startActivity(in);
        }

        return super.onOptionsItemSelected(item);
    }

    public void suggestTip(View view) {
        DialogFragment dialog = new TipSuggestionDialog();
        dialog.show(getFragmentManager(), "tag");
    }


    private void validate(int id) {
        TextInputLayout field = (TextInputLayout) findViewById(id);
        boolean fieldIsEmpty = TextUtils.isEmpty(field.getEditText().getText());
        switch(id) {
            case R.id.bill_field:
                final TextView helperText = (TextView) findViewById(R.id.bill_helper_text);
                if(fieldIsEmpty) {
                    helperText.setVisibility(View.INVISIBLE);
                    field.setError("Error: This is required");
                    billfieldEmpty = true;
                }
                else if(field.getError() != null) {
                    field.setError(null);
                    billfieldEmpty = false;
                    helperText.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            helperText.setVisibility(View.VISIBLE);
                        }
                    }, 300);
                }
                break;
            case R.id.tip_field:
                if(fieldIsEmpty) {
                    tipfieldEmpty = true;
                    field.setError("Error: This is required");
                }
                else {
                    tipfieldEmpty = false;
                    field.setError(null);
                }
                break;
            case R.id.people_field:
                peopleFieldEmpty = fieldIsEmpty;
                if(!fieldIsEmpty) {
                    if(Integer.parseInt(field.getEditText().getText().toString()) <= 0) {
                        peopleFieldZero = true;
                        field.setError("Error: This must be greater than zero");
                    }
                    else {
                        peopleFieldZero = false;
                        field.setError(null);
                    }
                }
                else {
                    field.setError("Error: This is required");
                }
                break;
        }
    }

    private String validateAll() {
        String errMessage = null;
        for(int id: requiredTextFields) {
            validate(id);
        }
        if(billfieldEmpty || tipfieldEmpty || peopleFieldEmpty) {
            errMessage = "Some fields still need to be filled out";
        }
        else if(peopleFieldZero) {
            errMessage = "The number of people needs to be greater than zero";
        }
        return errMessage;
    }

    private void setRequiredFieldHandler(final int id) {
        final TextInputLayout field = (TextInputLayout)findViewById(id);
        field.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    validate(id);
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
                validate(id);
            }
        });
    }
}
