package algonquin.cst2335.sing1489.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import algonquin.cst2335.R;
import algonquin.cst2335.sing1489.Adapter.OwlAdapter;
import algonquin.cst2335.sing1489.Model.ModelDictionary;
import algonquin.cst2335.sing1489.storage.LocalStorageDataBase;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //declare variable

    public DrawerLayout layout;  //declare variable
    public ActionBarDrawerToggle barDrawerToggle;  //declare variable
    private ArrayList<ModelDictionary.Definition> list;  //declare variable
    private EditText editText;  //declare variable
    private Button button;  //declare variable  //declare variable
    private OwlAdapter owlAdapter;
    private RecyclerView recyclerView;  //declare variable
    private ProgressBar pb;  //declare variable  //declare variable
    private LocalStorageDataBase dataBase;  //declare variable


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the database
        dataBase = new LocalStorageDataBase(getApplicationContext());


        layout = findViewById(R.id.my_drawer_layout); //initialize the drawerlayout
        pb = findViewById(R.id.progressBar);  //initialize progressbar
        barDrawerToggle = new ActionBarDrawerToggle(this, layout, R.string.nav_open, R.string.nav_close);  //initialize the bardrawertoggle

        editText = findViewById(R.id.editTextTextPersonName);  //initialize the editText
        button = findViewById(R.id.button);  //initialize the button

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        layout.addDrawerListener(barDrawerToggle);   ////add drawerToggle in drawerlayout
        NavigationView navigationView = findViewById(R.id.navigationVIew);  //initialize the navigation
        recyclerView = findViewById(R.id.recyclerView);  //initialize the recycler

        navigationView.setNavigationItemSelectedListener(this);   // initialize the navigation item onclickListener
        barDrawerToggle.syncState();
        //
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list = new ArrayList<>();  //initial the list of owlClass
        // The Adapter is also responsible for making a View for each item in the data set
        //Adapter provides access to the data items.

        owlAdapter = new OwlAdapter(list, this);  //initial the adapter
        recyclerView.setAdapter(owlAdapter);  //set the adapter in recyclerView


        button.setOnClickListener(new View.OnClickListener() {
            /**
             * @param view
             */
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);   //trim eliminates leading and trailing spaces
                save(editText.getText().toString().trim());
                String url = "https://owlbot.info/api/v4/dictionary/" + editText.getText().toString().trim();
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                //call the volley for api network
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pb.setVisibility(View.GONE);
                        try {
                            JSONArray businessesJson = response.getJSONArray("definitions");
                            ArrayList<ModelDictionary.Definition> definitions = ModelDictionary.Definition.fromJson(businessesJson);
                            list.clear();
                            list.addAll(definitions);
                            owlAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> error.printStackTrace()) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Authorization", "Token f5d24b5a7d6ef017d3a480d78919554e1b9c4a3a");
                        return params;
                    }
                };
                requestQueue.add(jsonObjectRequest);
            }
        });
        getData(editText);


    }

    //method for storing search text in sharedpreference
    private void getData(EditText etName) {
        //Indicates that Lint should ignore the specified warnings for the annotated element.
        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("OwlSharedPreferences", MODE_APPEND);
        String s1 = sh.getString("searchTerm", "");
        etName.setText(s1);
    }

    //method for storing search text in sharedpreference
    private void save(String trim) {
        SharedPreferences sharedPreferences = getSharedPreferences("OwlSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("searchTerm", trim);
        editor.commit();
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            startActivity(new Intent(MainActivity.this, OfflineActivity.class));
            //on a click of naviagation item to open offline activity activity
            return true;
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        if (barDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}