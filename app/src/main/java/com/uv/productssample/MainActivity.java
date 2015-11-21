package com.uv.productssample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    SQLiteHelper dbHelper;

    private AutoCompleteTextView actv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SQLiteHelper(this);


        dbHelper.insertProduct("HP Printer", "The printer");
        dbHelper.insertProduct("Logitech keyboard", "The keyboard");
        dbHelper.insertProduct("ViewSonic Monitor", "The monitor");
        dbHelper.insertProduct("Another Monitor", "The monitor");


        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        String[] products = dbHelper.getAllProducts();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,products);
        actv.setAdapter(adapter);
    }
}
