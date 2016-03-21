package com.ga.gachallenge;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by andrasta on 3/21/16.
 */
public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {
    private ArrayList<GroceryItem> mDataset;
    private Activity mParent;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemNameTV;
        public TextView itemQuantityTV;
        public TextView itemDescriptionTV;

        public ViewHolder(View itemView) {
            super(itemView);

            itemNameTV = (TextView) itemView.findViewById(R.id.itemName);
            itemQuantityTV = (TextView) itemView.findViewById(R.id.itemQuantity);
            itemDescriptionTV = (TextView) itemView.findViewById(R.id.itemDescription);
        }
    }

    public GroceryListAdapter(ArrayList<GroceryItem> dataset, Activity parent){
        mDataset = dataset;
        mParent = parent;
    }

    @Override
    public GroceryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item_view, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemNameTV.setText(mDataset.get(position).getItemName());
        holder.itemDescriptionTV.setText(mDataset.get(position).getItemDescription());
        holder.itemQuantityTV.setText("Quantity: "+mDataset.get(position).getItemQuantity());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
