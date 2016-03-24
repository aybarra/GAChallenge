package com.ga.gachallenge;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;


import java.util.ArrayList;

/**
 * Created by Andy Ybarra on 3/21/16.
 */
public class GroceryListAdapter extends ArrayAdapter<GroceryItem> {

    public static final int EDIT_ITEM_CODE = 1;
    private Context mContext;
    private Activity mParent;


    public static class ViewHolder {
        public TextView itemNameOverview;
        public TextView itemNameDetail;
        public TextView itemQuantityTV;
        public TextView itemDescriptionTV;
        public RelativeLayout itemContainer;
        public ImageButton editItemButton;
        public ViewFlipper itemFlipper;
        public CheckBox itemCheckbox;
        public int originalFlags;
    }

    public GroceryListAdapter(Context context, ArrayList<GroceryItem> dataset, Activity parent){
        super(context, R.layout.grocery_item_view, dataset);
        mContext = context;
        mParent = parent;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        final GroceryItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.grocery_item_view, parent, false);
            viewHolder.itemNameOverview = (TextView) convertView.findViewById(R.id.itemNameLarge);
            viewHolder.itemNameDetail = (TextView) convertView.findViewById(R.id.itemName);
            viewHolder.itemDescriptionTV = (TextView) convertView.findViewById(R.id.itemDescription);
            viewHolder.itemQuantityTV = (TextView) convertView.findViewById(R.id.itemQuantity);
            viewHolder.itemContainer = (RelativeLayout) convertView.findViewById(R.id.itemContainer);
            viewHolder.editItemButton = (ImageButton) convertView.findViewById(R.id.editItemButton);
            viewHolder.itemFlipper = (ViewFlipper) convertView.findViewById(R.id.itemFlipper);
            viewHolder.itemCheckbox = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.itemNameOverview.setText(item.getItemName());
        viewHolder.itemNameDetail.setText(item.getItemName());
        viewHolder.itemDescriptionTV.setText("Description: " + item.getItemDescription());
        viewHolder.itemQuantityTV.setText("Quantity: " + item.getItemQuantity());

        // Launches the edit activity for that particular row
        viewHolder.editItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("ItemName", item.getItemName());
                intent.putExtra("ItemQuantity", item.getItemQuantity());
                intent.putExtra("ItemDesc", item.getItemDescription());
                intent.putExtra("position", position);
                mParent.startActivityForResult(intent, EDIT_ITEM_CODE);
            }
        });

        // Swaps the overview and detail if anywhere on the row container is clicked
        viewHolder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView groceryList = (ListView) mParent.findViewById(R.id.groceryListView);
                ViewGroup.LayoutParams params = groceryList.getLayoutParams();
                viewHolder.itemFlipper.setInAnimation(mContext, R.anim.fade_in);
                viewHolder.itemFlipper.setOutAnimation(mContext, R.anim.fade_out);
                viewHolder.itemFlipper.showNext();

                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                groceryList.setLayoutParams(params);
            }
        });

        // Performs action to cross out the text of a grocery item
        viewHolder.itemCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    viewHolder.originalFlags = viewHolder.itemNameOverview.getPaintFlags();
                    viewHolder.itemNameOverview.setPaintFlags(viewHolder.itemNameOverview.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    viewHolder.itemNameOverview.setPaintFlags(viewHolder.originalFlags);
                }
            }
        });

        // Return the completed view to render on screen
        return convertView;

    }
}
