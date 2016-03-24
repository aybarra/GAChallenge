package com.ga.gachallenge;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Simple enum that will tell the edit activity whether to dismiss when back or the the back
 * arrow is pressed or to send a payload of information back to the main activity
 */
enum ItemChanged {
    NONE, ALL
}

/**
 * Activity that allows the user to view the extended content description of an item
 * as well as modify its fields. The user can delete an item using the actionbar trash icon.
 * @author Andy Ybarra
 */
public class EditActivity extends AppCompatActivity {

    private static final String TAG = "EditActivity";
    private String mName;
    private String mDesc;
    private int mQuant;
    private int mPosition;

    private EditText nameLabel;
    private EditText descLabel;
    private EditText quantityLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Need to setup the containers to display
        nameLabel = (EditText) findViewById(R.id.itemName);
        descLabel = (EditText) findViewById(R.id.itemDescription);
        quantityLabel = (EditText) findViewById(R.id.itemQuantity);

        // Fetch intent items
        mName = getIntent().getExtras().getString("ItemName");
        mDesc = getIntent().getExtras().getString("ItemDesc");
        mQuant = getIntent().getExtras().getInt("ItemQuantity");
        mPosition = getIntent().getExtras().getInt("position");

        nameLabel.setText(mName);
        descLabel.setText(mDesc);
        quantityLabel.setText(""+mQuant);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_delete_grocery_item:
                launchConfirmDeleteDialog();
                return true;
            case android.R.id.home:
                // Overriding this behavior because I want to keep the main activity from calling onCreate
                if(checkDifference() != ItemChanged.NONE){
                    setResult(Activity.RESULT_OK,
                            new Intent()
                                    .putExtra("position", mPosition)
                                    .putExtra("deleteItem", false)
                                    .putExtra("ItemName", nameLabel.getText().toString())
                                    .putExtra("ItemDesc", descLabel.getText().toString())
                                    .putExtra("ItemQuantity", Integer.parseInt(quantityLabel.getText().toString())));
                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void launchConfirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                setResult(Activity.RESULT_OK,
                        new Intent()
                                .putExtra("position", mPosition)
                                .putExtra("deleteItem", true));
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });

        builder.setTitle(R.string.delete_item_title);
        builder.setMessage(R.string.delete_item_message);

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
//        Log.d(TAG, "BACK WAS PRESSED");
        if(checkDifference() != ItemChanged.NONE){
            setResult(Activity.RESULT_OK,
                new Intent().putExtra("position", mPosition)
                        .putExtra("deleteItem", false)
                        .putExtra("ItemName", nameLabel.getText().toString())
                        .putExtra("ItemDesc", descLabel.getText().toString())
                        .putExtra("ItemQuantity", Integer.parseInt(quantityLabel.getText().toString())));

        }
        finish();
    }

    /**
     * Helper method to check if any of the displayed edit texts have been updated since
     * they were first loaded.
     * @return ItemChanged enum either NONE or ALL
     */
    public ItemChanged checkDifference() {
        if(!mName.equals(nameLabel.getText().toString())
                || !mDesc.equals(descLabel.getText().toString())
                || mQuant != Integer.parseInt(quantityLabel.getText().toString())) {
            return ItemChanged.ALL;
        }
        return ItemChanged.NONE;
    }
}
