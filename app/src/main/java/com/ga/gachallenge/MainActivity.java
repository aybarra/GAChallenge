package com.ga.gachallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.grocery_recycler_view);

        // Starting dataset
        ArrayList<GroceryItem> dataset = new ArrayList<GroceryItem>();
        dataset.add(new GroceryItem("Apples","", 2));
        dataset.add(new GroceryItem("Oranges","", 3));

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create the adapter
        mAdapter = new GroceryListAdapter(dataset, this);

        // Apply the adapter
        mRecyclerView.setAdapter(mAdapter);
    }
}
