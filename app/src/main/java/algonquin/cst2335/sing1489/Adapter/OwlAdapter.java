package algonquin.cst2335.sing1489.Adapter;

/**
 * @author Darshan Singh
 * @version 1.0
 */
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import algonquin.cst2335.R;
import algonquin.cst2335.sing1489.Model.ModelDictionary;
import algonquin.cst2335.sing1489.storage.Entity;
import algonquin.cst2335.sing1489.storage.LocalStorageDataBase;

public class OwlAdapter extends RecyclerView.Adapter<OwlAdapter.viewHolder> {
//deleration of arraylist
    private ArrayList<ModelDictionary.Definition> getOwlDetails;   //declare the list variables
    private Activity activity;
    LocalStorageDataBase dbHelper;  //declare database variable to delete the data from database

    /**
     * @param getOwlDetails used to get the details about the Definition
     * @param activity used for having activity
     */
    //create the constructor for add data in adapter class
    public OwlAdapter(ArrayList<ModelDictionary.Definition> getOwlDetails, Activity activity) {
        this.getOwlDetails = getOwlDetails;
        this.activity = activity;
    }


    /**
     * @param parent  parent
     * @param viewType used to know about the type of the view
     * @return will return new view to hold in owlAdapter
     */
    @Override
//onCreateViewHelper function with parameter parent and viewType
    //this function helps to create a new view holder when there are no exsiting view holders
    //which the recycler view can REUSE.
    public OwlAdapter.viewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.list_item_view, parent, false);
        dbHelper = new LocalStorageDataBase(activity);
        return new OwlAdapter.viewHolder(view);

    }

    /**
     * @param holder to hold the view in recycle view
     * @param position used to hold view at specific pisition
     */
    //onBindViewHolder function with parameters holder and position
    //this function is called by the Recyclerview to display the data at the sepecific position.
    @Override
    public void onBindViewHolder( OwlAdapter.viewHolder holder, int position) {
        ModelDictionary.Definition definition = getOwlDetails.get(position);

        if (getOwlDetails.get(position).getImageUrl() != null) {
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(activity).load(definition.getImageUrl())
                    .apply(new RequestOptions())
                    .into(holder.imageView);
        }

//funtion for save the records
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //database db to get data from the LocalDataAdapter class
                //getWritableDatabase() Create open a database that will be used for reading and writing
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //ContentValues is a maplike class that matches a value to a String key
                //ContentValues: put(String key, Byte value) void put(String key, Integer value)
                ContentValues values = new ContentValues();
                values.put(Entity.OwlDataBase.COLUMN_NAME_DEFINITION, definition.getDefinition());
                values.put(Entity.OwlDataBase.COLUMN_NAME_EXAMPLE, definition.getExample());
                values.put(Entity.OwlDataBase.COLUMN_NAME_TYPE, definition.getType());
                values.put(Entity.OwlDataBase.COLUMN_NAME_IMAGE_URL, definition.getImageUrl());

                long newrow = db.insert(Entity.OwlDataBase.TABLE_NAME, null, values);

                if (newrow > 0) {
                    Toast.makeText(activity, "Saved Locally", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, "Something wrong! try again", Toast.LENGTH_SHORT).show();
                }

            }
        });
        holder.definition.setText("Definition:" + definition.getDefinition());
        holder.example.setText("Example : " + definition.getExample());
        holder.type.setText("Type :" + definition.getType());
    }


    @Override
    public int getItemCount() {
        return getOwlDetails.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        TextView definition, type, example, imageView2;

        public viewHolder( View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            definition = itemView.findViewById(R.id.textView);
            type = itemView.findViewById(R.id.textView3);
            example = itemView.findViewById(R.id.textView2);
            imageView2 = itemView.findViewById(R.id.imageView2);
        }
    }
}
