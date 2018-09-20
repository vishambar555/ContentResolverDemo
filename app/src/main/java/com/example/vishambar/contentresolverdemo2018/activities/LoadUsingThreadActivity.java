package com.example.vishambar.contentresolverdemo2018.activities;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishambar.contentresolverdemo2018.R;

public class LoadUsingThreadActivity extends AppCompatActivity {
    private String[] mProjections = {ContactsContract.Contacts.DISPLAY_NAME_PRIMARY, ContactsContract.Contacts.HAS_PHONE_NUMBER};

    private String mSelectionSpecific = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = 'Father'";

    private String mSelectionQuestionMark = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = ?";
    private String[] mSelectionArguments = new String[]{"Priyanka"};

    private String sortOrder = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;
    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_without_loader);
        tvText = findViewById(R.id.tv_text);
    }

    public void read(int i) {
        final int num = i;
        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = null;
                switch (num) {
                    case 1:
                        cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, mProjections,
                                null, null, null);
                        break;

                    case 2:
                        cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, mProjections,
                                mSelectionSpecific, null, null);
                        break;
                    case 3:
                        cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, mProjections,
                                mSelectionQuestionMark, mSelectionArguments, null);
                        break;

                    case 4:
                        cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, mProjections,
                                null, null, sortOrder);
                        break;
                }


                if (cursor != null && cursor.getCount() > 0) {
                    final StringBuilder stringBuilder = new StringBuilder();
                    while (cursor.moveToNext()) {
                        stringBuilder.append(cursor.getString(0) + " " + cursor.getString(1) + "\n");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvText.setText(stringBuilder.toString());

                            }
                        });

                    }

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvText.setText("No contact to display");
                        }
                    });

                }
            }
        }).start();

    }

    public void readAll(View view) {
        read(1);
    }

    public void readWithSelectionClauseOnly(View view) {
        read(2);
    }

    public void readWithSelectionClauseAndSelectionArgumentsOnly(View view) {
        read(3);
    }

    public void readAllInSortingOrder(View view) {
        read(4);
    }


}
