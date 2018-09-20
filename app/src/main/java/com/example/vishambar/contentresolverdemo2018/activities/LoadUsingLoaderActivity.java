package com.example.vishambar.contentresolverdemo2018.activities;


import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishambar.contentresolverdemo2018.R;
import com.example.vishambar.contentresolverdemo2018.adapters.MyAdapter;
import com.example.vishambar.contentresolverdemo2018.interfaces.OnClickInterface;
import com.example.vishambar.contentresolverdemo2018.models.MyModel;

import java.util.ArrayList;

public class LoadUsingLoaderActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>, OnClickInterface, View.OnClickListener {


    private String[] mProjections = new String[]{ContactsContract.Contacts.DISPLAY_NAME_PRIMARY, ContactsContract.Contacts.HAS_PHONE_NUMBER};

    private String sortingOrder = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;
    private boolean isLoadedFirstTime = true;
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private ArrayList<MyModel> arrayList;
    private int clicketPosition;
    private Button addBtn;
    private EditText itemToAddEt;

    @Override
    public void getIds() {
        recyclerView = findViewById(R.id.recycler_view);
        addBtn = findViewById(R.id.btn_add_item);
    }

    @Override
    public void setData() {
        if (isLoadedFirstTime) {
            getSupportLoaderManager().initLoader(1, null, this);
            isLoadedFirstTime = false;
        } else {
            getSupportLoaderManager().restartLoader(1, null, this);
        }
    }

    @Override
    public void setListeners() {
        itemToAddEt = findViewById(R.id.et_add_item);
        addBtn.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_using_loader);

    }


    private void setAdapter() {
        myAdapter = new MyAdapter(this, this, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        if (i == 1) {
            return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI, mProjections, null, null, sortingOrder);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            arrayList = new ArrayList<>();
            while (cursor.moveToNext()) {
                MyModel myModel = new MyModel(cursor.getString(0), cursor.getString(1));
                arrayList.add(myModel);
            }
            if (arrayList != null && !arrayList.isEmpty()) {
                setAdapter();
            } else {
                Toast.makeText(this, "No contacts to display", Toast.LENGTH_SHORT).show();
            }
            cursor.moveToFirst();

        }
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {

    }


    public void deleteContact(View view) {
        String whereClause = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = 'Abdul Haseeb'";
        getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, whereClause, null);
    }

    public void addContact(View view) {
    }

    public void upadateContact(View view) {
    }

    @Override
    public void handleClick(int position) {
        clicketPosition = position;
        Intent i = new Intent(this, ItemDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("parcelable", arrayList.get(position));
        i.putExtras(bundle);
        startActivityForResult(i, 25);
    }


    private void addItem() {
        if (validate()) {
//            int newPhoneNumberStatus = 1;
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY, itemToAddEt.getText().toString());


//            getContentResolver().insert(ContactsContract.Contacts.CONTENT_URI, contentValues);


            ArrayList<ContentProviderOperation> ops =
                    new ArrayList<ContentProviderOperation>();
            ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

            // Adding insert operation to operations list
            // to insert display name in the table ContactsContract.Data
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, itemToAddEt.getText().toString())
                    .build());

            try {
                getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (OperationApplicationException e) {
                e.printStackTrace();
            }

        }

    }

    private boolean validate() {
        if (itemToAddEt.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please add item first", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getSupportLoaderManager().restartLoader(1, null, this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_item:
                addItem();
                break;
        }
    }


}
