package com.example.aditya.contacts;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etName, etCell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etCell = findViewById(R.id.etCell);
    }

    public void btnSubmit(View view)
    {
        //trim is just to aavoid spaces
        String name = etName.getText().toString().trim();
        String cell = etCell.getText().toString().trim();

        try
        {
            ContactsDB db = new ContactsDB(this);
            db.open();
            db.createEntry(name, cell);
            db.close();
            Toast.makeText(this, "Contact Successfully Entered!!", Toast.LENGTH_SHORT).show();
            etName.setText("");
            etCell.setText("");
        }

        catch (android.database.SQLException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void btnShowData(View view)

    {
        startActivity(new Intent(this, Data.class));
    }

    /**public void btnEditData(View view)
    {
        try
        {
            ContactsDB db = new ContactsDB(this);
             db.open();
             db.udateEntry("1" , "John" , ("81958123574"));
             db.close();
            Toast.makeText(this, "Successfully Updated!!!", Toast.LENGTH_SHORT).show();
        }

        catch (SQLException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }  **/

    public void btnDeleteData(View view)
    {
        try
        {
            ContactsDB db = new ContactsDB(this);
            db.open();

                String top = db.TopId();
                db.deleteEntry(top);
                db.close();
                Toast.makeText(this, "Successfully Deleted!!!", Toast.LENGTH_SHORT).show();
            }
        catch (android.database.SQLException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
