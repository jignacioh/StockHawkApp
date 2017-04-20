package com.udacity.stockhawk.data;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;

/**
 * Created by Juan PC on 16/04/2017.
 */

public class StockWidgetProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Cursor cursor;
    private Intent intent;

    //For obtaining the activity's context and intent
    public StockWidgetProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    private void initCursor(){
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        /**This is done because the widget runs as a separate thread
         when compared to the current app and hence the app's data won't be accessible to it
         because I'm using a content provided **/
        cursor = context.getContentResolver().query(Contract.Quote.URI,
                new String[]{Contract.Quote._ID, Contract.Quote.COLUMN_SYMBOL, Contract.Quote.COLUMN_PRICE,
                        Contract.Quote.COLUMN_PERCENTAGE_CHANGE, Contract.Quote.COLUMN_ABSOLUTE_CHANGE},
                null,
                null,null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onCreate() {
        initCursor();
        if (cursor != null) {
            cursor.moveToFirst();
        }
    }

    @Override
    public void onDataSetChanged() {
        /** Listen for data changes and initialize the cursor again **/
        initCursor();
    }

    @Override
    public void onDestroy() {
        cursor.close();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        /** Populate your widget's single list item **/
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_item_quote);
        cursor.moveToPosition(i);

        //remoteViews.setTextViewText(R.id.stock_symbol,cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_SYMBOL)));
        //remoteViews.setTextViewText(R.id.price,cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_PRICE)));
        //remoteViews.setTextViewText(R.id.change,cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_PERCENTAGE_CHANGE)));


        Log.i("STOCK HAWK PRUEBA","JFJEJWJFJEWJFE");

        remoteViews.setTextViewText(R.id.stock_symbol,cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_SYMBOL)));
        remoteViews.setTextViewText(R.id.price,cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_PRICE)));
        remoteViews.setTextViewText(R.id.change,cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_PERCENTAGE_CHANGE)));

        /*if (cursor.getString(cursor.getColumnIndex(QuoteColumns.ISUP)).equals("1")) {
            remoteViews.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_green);
        } else {
            remoteViews.setInt(R.id.change, "setBackgroundResource", R.drawable.percent_change_pill_red);
        }*/
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
