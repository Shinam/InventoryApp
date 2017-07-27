package com.example.android.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.android.inventoryapp.data.ProductContract.ProductEntry;

/**
 * Created by Shinam on 27/07/2017.
 */

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        Button button = (Button) view.findViewById(R.id.sell);
        ImageView imageImageView = (ImageView) view.findViewById(R.id.image);


        final int productId = cursor.getInt(cursor.getColumnIndex(ProductEntry._ID));
        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        int imageColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_IMAGE);


        String productName = cursor.getString(nameColumnIndex);
        Double productPrice = cursor.getDouble(priceColumnIndex);
        final int productQuantity = cursor.getInt(quantityColumnIndex);
        String productImage = cursor.getString(imageColumnIndex);


        nameTextView.setText(productName);
        priceTextView.setText(productPrice.toString());
        quantityTextView.setText(Integer.toString(productQuantity));
        imageImageView.setImageURI(Uri.parse(productImage));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri productUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, productId);
                reduceQuantity(context, productUri, productQuantity);
            }
        });
    }

    private void reduceQuantity(Context context, Uri productUri, int currentQuantity) {

        int newStock = (currentQuantity >= 1) ? currentQuantity - 1 : 0;

        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, newStock);
        context.getContentResolver().update(productUri, contentValues, null, null);
    }
}
