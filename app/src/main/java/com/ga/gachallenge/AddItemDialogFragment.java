package com.ga.gachallenge;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.ga.gachallenge.AddItemDialogFragment.OnCompleteListener} interface
 * to handle interaction events.
 * Use the {@link AddItemDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 * @author Andy Ybarra
 */
public class AddItemDialogFragment extends DialogFragment {

    private OnCompleteListener mListener;

    public static interface OnCompleteListener {
        void onComplete(String itemName, String itemDesc, int itemQuantity);
        void dismiss();
    }

    // Make sure the Activity implemented it
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static AddItemDialogFragment newInstance() {
        AddItemDialogFragment f = new AddItemDialogFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.getDialog().setTitle("Add grocery item");
        View v = inflater.inflate(R.layout.fragment_add_grocery_dialog, container, false);

        final EditText itemNameET = (EditText) v.findViewById(R.id.groceryItemName);
        final EditText itemDescET = (EditText) v.findViewById(R.id.groceryItemDescription);
        final EditText itemQuantET = (EditText) v.findViewById(R.id.groceryItemQuantity);

        Button save = (Button) v.findViewById(R.id.saveGroceryItemButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Item name and quantity are required....description is optional
                if(itemNameET.getText().toString().equals("")){
                    itemNameET.setHintTextColor(Color.RED);
                    itemNameET.setHint("Item name required");
                }
                if(itemQuantET.getText().toString().equals("")){
                    itemQuantET.setHintTextColor(Color.RED);
                    itemQuantET.setHint("Item quantity required");
                }

                // Cover case when they enter 0 for the quantity
                if(!itemQuantET.getText().toString().equals("") && Integer.parseInt(itemQuantET.getText().toString()) == 0){
                    itemQuantET.setText("");
                    itemQuantET.setHintTextColor(Color.RED);
                    itemQuantET.setHint("Quantity must be > 0");
                }

                if(!itemNameET.getText().toString().equals("") && !itemQuantET.getText().toString().equals("")){
                    mListener.onComplete(itemNameET.getText().toString(),
                            itemDescET.getText().toString(),
                            Integer.parseInt(itemQuantET.getText().toString()));
                }
            }
        });

        // If they cancel out then do nothing, just dismiss the dialog
        Button cancel = (Button) v.findViewById(R.id.cancelAddGroceryButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.dismiss();
            }
        });

        return v;
    }
}
