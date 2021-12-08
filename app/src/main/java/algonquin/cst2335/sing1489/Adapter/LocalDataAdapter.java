package algonquin.cst2335.sing1489.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import algonquin.cst2335.R;
import algonquin.cst2335.sing1489.Model.ModelDictionary;
import algonquin.cst2335.sing1489.storage.Entity;
import algonquin.cst2335.sing1489.storage.LocalStorageDataBase;

/**
 * @author Darshan Singh
 * @version 1.0
 */
public class LocalDataAdapter extends RecyclerView.Adapter<LocalDataAdapter.VH> {
    private ArrayList<ModelDictionary.Definition> getOwlDetails;  //declare the list variables


    //create the constructor for add data in adapter class
    public LocalDataAdapter(ArrayList<ModelDictionary.Definition> getOwlDetails, Activity activity) {
        this.getOwlDetails = getOwlDetails;
        this.activity = activity;
    }

    private Activity activity;
    LocalStorageDataBase dbHelper;  //declare database varibale to delete the data from database


//onCreateViewHelper function with parameter parent and viewType
    //this function helps to create a new view holder when there are no exsiting view holders
    //which the recycler view can REUSE.
    @Override
    public VH onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.list_item_view, parent, false);
        dbHelper = new LocalStorageDataBase(activity);
        return new LocalDataAdapter.VH(view);
    }

    /**
     * @param holder to hold the view in recycle view
     * @param position used to hold view at specific pisition
     */
    //onBindViewHolder function with parameters holder and position
    //this function is called by the Recyclerview to display the data at the sepecific position.
    @Override                             //Indicates that Lint should ignore the specified warnings for the annotated element.
    public void onBindViewHolder( VH holder, @SuppressLint("RecyclerView") int position) {
        ModelDictionary.Definition definition = getOwlDetails.get(position);

        if (getOwlDetails.get(position).getImageUrl() != null) {
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(activity).load(definition.getImageUrl())
                    .apply(new RequestOptions())
                    .into(holder.imageView);
        }
        //holding image view for deleting view and setting text "Delete"
        holder.imageView2.setText("Delete");
//applying setOnClickListener on delete imageView
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            /**
             * @param v onClick function with parameter v
             */
            @Override
            public void onClick(View v) {

// alertDialog to make a option to ask for deleting or not
                //it is used to ask weather you want to delete view or not.
                new AlertDialog.Builder(activity)
                        .setTitle("Deleted")
                        .setMessage("You wanna Delete it")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteTitle(definition, holder.constraintLayout, dbHelper);
                                getOwlDetails.remove(position);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        //using holder for setting definition in the saved view
        holder.definition.setText("Definition:" + definition.getDefinition());
        //using holder for setting example in the saved view
        holder.example.setText("Example : " + definition.getExample());
        //using holder for setting type in the saved view
        holder.type.setText("Type :" + definition.getType());
    }
//deleteTittle() function for delete the records from local database
    public boolean deleteTitle(ModelDictionary.Definition definition, ConstraintLayout constraintLayout, LocalStorageDataBase dbHelper) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        return db.delete(Entity.OwlDataBase.TABLE_NAME, Entity.OwlDataBase._ID + "=" + Entity.OwlDataBase._ID, null) > 0;
    }

    /**
     * @return the details of the Definition
     */
//
    @Override
    public int getItemCount() {
        return getOwlDetails.size();
    }
// VH means view Holder class which is extending RecyclerVie.ViewHolder
    //A subclass of RecyclerView.ViewHolder responsible for holding views in a data set.
    public class VH extends RecyclerView.ViewHolder {
        public ImageView imageView;
        //delaration of textviews
        TextView definition, type, example, imageView2;
        ConstraintLayout constraintLayout;

    /**
     * @param itemView
     */
    //constructor of VH class with parameter itemView
        public VH( View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            definition = itemView.findViewById(R.id.textView);
            constraintLayout = itemView.findViewById(R.id.constraint);
            type = itemView.findViewById(R.id.textView3);
            example = itemView.findViewById(R.id.textView2);
            imageView2 = itemView.findViewById(R.id.imageView2);

        }
    }
}
