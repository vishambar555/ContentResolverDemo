package com.example.vishambar.contentresolverdemo2018.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishambar.contentresolverdemo2018.R;
import com.example.vishambar.contentresolverdemo2018.models.MyModel;

public class ItemDetailsActivity extends BaseActivity implements View.OnClickListener {
    private TextView nameTv, statusTv;
    private Button updateBtn, deleteBtn;
    private MyModel myModel;
    private EditText addItemToUpdateEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

    }

    @Override
    public void getIds() {
        nameTv = findViewById(R.id.tv_name);
        statusTv = findViewById(R.id.tv_status);
        addItemToUpdateEt = findViewById(R.id.et_add_item_to_update);
        updateBtn = findViewById(R.id.btn_update_item);
        deleteBtn = findViewById(R.id.btn_delete_item);
    }

    @Override
    public void setData() {
        Bundle bundle = getIntent().getExtras();

        myModel = bundle.getParcelable("parcelable");

        nameTv.setText(myModel.getName());
        statusTv.setText(myModel.getStatus());
    }

    @Override
    public void setListeners() {
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_update_item:
                updateItem();
                break;
            case R.id.btn_delete_item:
                deleteItem();
                break;
        }

    }

    private void deleteItem() {
        String whereClause = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = ?";
        String[] selectionArgs = new String[]{myModel.getName()};
        getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, whereClause, selectionArgs);

        Intent intent = new Intent();
        setResult(26, intent);
        Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();

        finish();

    }

    private void updateItem() {
        if(validate()){
            ContentValues contentValues = new ContentValues();
            contentValues.put(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY, "Test");
            String whreeClause = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " =?";
            String[] mSelectionArguments = new String[]{myModel.getName()};
            int value = getContentResolver().update(ContactsContract.RawContacts.CONTENT_URI, contentValues, whreeClause, mSelectionArguments);
            if (value > 0) {
                Intent intent = new Intent();
                setResult(27, intent);
                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validate() {
        if(addItemToUpdateEt.getText().toString().isEmpty()){
            Toast.makeText(this, "Add new item first", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }


}
