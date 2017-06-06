package uottawa.caleb.tipcup;

import android.support.v4.app.NavUtils;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);

        int numPeople = getIntent().getIntExtra("people", 1);

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
