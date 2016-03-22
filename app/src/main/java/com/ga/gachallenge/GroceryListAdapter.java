package com.ga.gachallenge;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by andrasta on 3/21/16.
 */
public class GroceryListAdapter extends ArrayAdapter<GroceryItem> {

    public static class ViewHolder {

        public TextView itemNameTV;
        public TextView itemQuantityTV;
        public TextView itemDescriptionTV;

    }

    public GroceryListAdapter(Context context, ArrayList<GroceryItem> dataset){
        super(context, R.layout.grocery_item_view, dataset);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        GroceryItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.grocery_item_view, parent, false);
            viewHolder.itemNameTV = (TextView) convertView.findViewById(R.id.itemName);
            viewHolder.itemDescriptionTV = (TextView) convertView.findViewById(R.id.itemDescription);
            viewHolder.itemQuantityTV = (TextView) convertView.findViewById(R.id.itemQuantity);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.itemNameTV.setText(item.getItemName());
        viewHolder.itemDescriptionTV.setText("Description: " + item.getItemDescription());
        viewHolder.itemQuantityTV.setText("Quantity: " + item.getItemQuantity());

        // Return the completed view to render on screen
        return convertView;

    }
}
