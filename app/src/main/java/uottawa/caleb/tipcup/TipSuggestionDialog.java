package uottawa.caleb.tipcup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.media.Rating;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Caleb on 2017-06-05.
 */

public class TipSuggestionDialog extends DialogFragment {
    Integer tip = null;
    @Override
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View v = inflater.inflate(R.layout.fragment_tip_recommendation, null);
        RatingBar r = (RatingBar) v.findViewById(R.id.ratingBar);
        r.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tip = (int)(rating*2 + 10);
                String tipText = tip + "%";
                ((TextView)v.findViewById(R.id.suggested_tip)).setText(tipText);

                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.findViewById(R.id.rate).setVisibility(View.INVISIBLE);
                        v.findViewById(R.id.suggest).setVisibility(View.VISIBLE);
                    }
                }, 200);
            }
        });
        builder.setView(v)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((home)getActivity()).onTipSuggestion(tip);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TipSuggestionDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }



}
