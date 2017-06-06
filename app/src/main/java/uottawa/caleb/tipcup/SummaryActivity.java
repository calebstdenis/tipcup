package uottawa.caleb.tipcup;

import android.icu.text.NumberFormat;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);

        String currency = PreferenceManager.getDefaultSharedPreferences(this).getString("pref_currency", "$");

        int numPeople = getIntent().getIntExtra("people", 1);
        double tip = getIntent().getDoubleExtra("tip", 0);
        double bill = getIntent().getDoubleExtra("bill", 0);
        double tipAmount = bill*tip/100;
        double tipPerPerson = tipAmount/numPeople;
        double totalAmount = bill + tipAmount;
        double grandTotalNum = totalAmount/numPeople;

        NumberFormat f = NumberFormat.getCurrencyInstance();
        String billText = currency + f.format(bill).substring(1);
        String tipText = currency + f.format(tipAmount).substring(1);
        String grandTotalText = currency + f.format(grandTotalNum).substring(1);
        String tipPerPersonText = currency + f.format(tipPerPerson).substring(1);
        String totalText = currency + f.format(totalAmount).substring(1);

        ((TextView)findViewById(R.id.grand_total_value)).setText(grandTotalText);
        ((TextView)findViewById(R.id.tip_per_person_value)).setText(tipPerPersonText);
        ((TextView)findViewById(R.id.total_amount_value)).setText(totalText);
        ((TextView)findViewById(R.id.tip_amount_value)).setText(tipText);
        ((TextView)findViewById(R.id.bill_amount_value)).setText(billText);

        TextView grandTotal = (TextView)findViewById(R.id.grandtotal);

        if(numPeople > 1) {
            grandTotal.setText("Grand total per person");
        }
        else {
            grandTotal.setText("Grand total");
            findViewById(R.id.tip_per_person_label).setVisibility(View.GONE);
            findViewById(R.id.tip_per_person_value).setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
