package com.ga.gachallenge;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

/**
 * Main activity for grocery list activity,
 * 1. Allows users to view their grocery list
 * 2. Allows them to see more details by tapping the row elements
 * 3. They can add more by selecting the FAB at the bottom right corner.
 * 4. This activity implements onCompleteListener methods for the add item dialog
 * @author Andy Ybarra
 */
public class MainActivity extends AppCompatActivity implements AddItemDialogFragment.OnCompleteListener {

    private static final String TAG = "MainActivity";

    private ArrayAdapter<GroceryItem> mAdapter;
    private ArrayList<GroceryItem> mDataset;
    private LinearLayout mEmptyLayout;
    private ListView mGroceryListView;
    private DialogFragment addGroceryItemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEmptyLayout = (LinearLayout) findViewById(R.id.emptyListLayout);

        mGroceryListView = (ListView) findViewById(R.id.groceryListView);

        // Starting dataset
        mDataset = new ArrayList<GroceryItem>();
        mDataset.add(new GroceryItem("Apples", "", 2));
        mDataset.add(new GroceryItem("Oranges", "This is a long description to test out the layout on startup", 3));

        // Create the adapter
        mAdapter = new GroceryListAdapter(this, mDataset, this);

        // Apply the adapter
        mGroceryListView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog();
            }
        });

        Log.d(TAG, "onCreate called");
    }


    public void showAddItemDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("groceryDialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        addGroceryItemFragment = AddItemDialogFragment.newInstance();
        addGroceryItemFragment.show(ft, "groceryDialog");
    }

    /**
     * Implemented interface method to handle the addItem dialog
     * If the size of the list is 1 (ie: we are transitioning from 0 to 1) I hide the empty layout
     * @param itemName
     * @param itemDesc
     * @param itemQuantity
     */
    @Override
    public void onComplete(String itemName, String itemDesc, int itemQuantity) {
        mDataset.add(new GroceryItem(itemName, itemDesc, itemQuantity));
        mAdapter.notifyDataSetChanged();
        if(mDataset.size() == 1){
            mEmptyLayout.setVisibility(View.GONE);
            mGroceryListView.setVisibility(View.VISIBLE);
        }
        addGroceryItemFragment.dismiss();
        Snackbar.make(mGroceryListView, "Grocery item added", Snackbar.LENGTH_LONG).show();
    }

    /**
     * Implemented interface method to handle addItemDialog dismiss, pretty much just closes the dialog
     */
    @Override
    public void dismiss() {
        addGroceryItemFragment.dismiss();
    }

    /**
     * To make this simple I handle two cases....
     * 1. The user deleted the grocery item, and if so I will delete it using the passed position
     * 2. The user updated the grocery item, so I will look up the item by position and then make
     * a call to notifydatasetchanged
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GroceryListAdapter.EDIT_ITEM_CODE){
            if(resultCode == RESULT_OK){
                int position = data.getExtras().getInt("position");
                boolean deleteItem = data.getExtras().getBoolean("deleteItem");
                if(position >= 0 && deleteItem){
                    mDataset.remove(position);
                    mAdapter.notifyDataSetChanged();
                    Snackbar.make(mGroceryListView, "Grocery item removed", Snackbar.LENGTH_LONG).show();

                    if(mDataset.size() == 0){
                        mEmptyLayout.setVisibility(View.VISIBLE);
                        mGroceryListView.setVisibility(View.GONE);
                    }
                } else {
                    String newName = data.getExtras().getString("ItemName");
                    String newDesc = data.getExtras().getString("ItemDesc");
                    int newQuant = data.getExtras().getInt("ItemQuantity");
                    mDataset.get(position).setItemName(newName);
                    mDataset.get(position).setItemDescription(newDesc);
                    mDataset.get(position).setItemQuantity(newQuant);
                    mAdapter.notifyDataSetChanged();
                    Snackbar.make(mGroceryListView, "Grocery item updated", Snackbar.LENGTH_LONG).show();
                }
            }
        }
    }
}