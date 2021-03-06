package com.udacity.stockhawk;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.udacity.stockhawk.sync.StockWidgetService;
import com.udacity.stockhawk.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class StockHawkWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {

            String sTitleWidget = context.getString(R.string.appwidget_text);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.stock_hawk_widget);

            // Create intent to launch MainActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            views.setTextViewText(R.id.tvTittleWidget, sTitleWidget);
            views.setRemoteAdapter(R.id.widget_listView,
                    new Intent(context, StockWidgetService.class));


            views.setOnClickPendingIntent(R.id.widget_toolbar, pendingIntent);

            // Set up collection
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            //    setRemoteAdapter(context, views);
            //} else {
            //    setRemoteAdapterV11(context, views);
            //}



            // Set up collection items
            Intent clickIntentTemplate = new Intent(context, MainActivity.class);
            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntentTemplate)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.widget_listView, clickPendingIntentTemplate);




            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    /**
     *
     * Sets the remote adapter used to fill in the list items
     * @param context the context used to launch the intent
     * @param views RemoteViews to set the RemoteAdapter
     *
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_listView,
                new Intent(context, StockWidgetService.class));
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param context the context to launch the intent
     * @param views RemoteViews to set the RemoteAdapter
     */
    @SuppressWarnings("deprecation")
    private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widget_listView,
                new Intent(context, StockWidgetService.class));
    }
}