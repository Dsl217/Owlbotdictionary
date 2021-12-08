package algonquin.cst2335.sing1489.Activity;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2335.R;
import algonquin.cst2335.sing1489.Adapter.LocalDataAdapter;
import algonquin.cst2335.sing1489.Model.ModelDictionary;
import algonquin.cst2335.sing1489.storage.Entity;
import algonquin.cst2335.sing1489.storage.LocalStorageDataBase;


public class OfflineActivity extends AppCompatActivity {

    private RecyclerView recyclerView; //declare variable recyclerView
    private LocalDataAdapter adapter; //declare variable adapter
    private ArrayList<ModelDictionary.Definition> dataList;  //declare variable List
    private SQLiteDatabase db; //declare variable

    private ProgressBar progressBar; //declare variable progressBar


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerView);  //initialize the recyclerView
        progressBar = findViewById(R.id.progressBar);  //initialize the progressBar

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL)); //initialize the recyclerView for showing
        // horizontal line between the list in recyclerview


        dataList = new ArrayList<>();  //initialize the dataList
        // The Adapter is also responsible for making a View for each item in the data set
        //Adapter provides access to the data items
        adapter = new LocalDataAdapter(dataList, this); //initialize the adapter
        recyclerView.setAdapter(adapter); //set the adapter in recyclerView
        LocalStorageDataBase dbHelper = new LocalStorageDataBase(getApplicationContext());  //initialize the Database Sqlite


        progressBar.setVisibility(View.VISIBLE);  //set visibility to visible of progressBar

        db = dbHelper.getReadableDatabase();  //initialize the SQLiteDatabase


        //cursor of using to run query of SqliteDatabase
        Cursor cursor = db.query(Entity.OwlDataBase.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {  //loop for get all the row DataList
            ModelDictionary.Definition definition = new ModelDictionary.Definition();  //initialize the bean class
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(Entity.OwlDataBase._ID));
            //set the values from cursor and set in beans class
            definition.setDefinition(String.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(Entity.OwlDataBase.COLUMN_NAME_DEFINITION))));
            definition.setExample(cursor.getString(cursor.getColumnIndexOrThrow(Entity.OwlDataBase.COLUMN_NAME_EXAMPLE)));
            definition.setType(cursor.getString(cursor.getColumnIndexOrThrow(Entity.OwlDataBase.COLUMN_NAME_TYPE)));
            definition.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(Entity.OwlDataBase.COLUMN_NAME_IMAGE_URL)));
            dataList.add(definition); // add the value in our list
        }

        if (dataList.size() > 0) {
            progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "no data found", Toast.LENGTH_SHORT).show();
        }
        cursor.close();

    }


}