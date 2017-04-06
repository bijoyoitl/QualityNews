package com.optimalbd.qualitynews;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.optimalbd.qualitynews.Utility.CurrentData;
import com.optimalbd.qualitynews.Utility.NewsSharePreference;

public class SettingsActivity extends AppCompatActivity {
    LinearLayout fontSizeLayout;
    public TextView textViewFontSize;
    Toolbar toolbar;
    LinearLayout ratingsLayout;
    NewsSharePreference sharePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fontSizeLayout = (LinearLayout) findViewById(R.id.fontSizeLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textViewFontSize = (TextView) findViewById(R.id.textViewFontSize);
        ratingsLayout = (LinearLayout) findViewById(R.id.ratingsLayout);
        sharePreference = new NewsSharePreference(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textViewFontSize.setText(sharePreference.getType());

        fontSizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                AlertDialogRadio alert = new AlertDialogRadio();
                alert.show(manager, "alert_dialog_radio");
            }
        });

        ratingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.optimalbd.qualitynews")));
            }
        });
    }

    public  class AlertDialogRadio extends DialogFragment {

        final String[] items = {"Small", "Normal", "Large"};


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

          AlertDialog.Builder b = new android.app.AlertDialog.Builder(getActivity());

            b.setTitle("News Font Size");

            b.setSingleChoiceItems(items, sharePreference.getPosition(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            sharePreference.saveTextSize(20, 0, "Small");
                            textViewFontSize.setText("Small");
                            break;
                        case 1:
                            sharePreference.saveTextSize(30, 1, "Normal");
                            textViewFontSize.setText("Normal");
                            break;
                        case 2:
                            sharePreference.saveTextSize(40, 2, "Large");
                            textViewFontSize.setText("Large");
                            break;
                        default:
                            break;
                    }


                    dialog.dismiss();
                }
            });


            b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            });

            AlertDialog d = b.create();

            return d;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
