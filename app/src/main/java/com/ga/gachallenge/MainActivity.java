package com.ga.gachallenge;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mGroceryListView;
    private ArrayAdapter<GroceryItem> mAdapter;

    private LinearLayout mEmptyListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGroceryListView = (ListView) findViewById(R.id.groceryListView);

        mEmptyListLayout = (LinearLayout) findViewById(R.id.emptyListLayout);

        // Starting dataset
        ArrayList<GroceryItem> dataset = new ArrayList<GroceryItem>();
        dataset.add(new GroceryItem("Apples","", 2));
        dataset.add(new GroceryItem("Oranges", "", 3));


        // Create the adapter
        mAdapter = new GroceryListAdapter(this, dataset);

        // Apply the adapter
        mGroceryListView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
