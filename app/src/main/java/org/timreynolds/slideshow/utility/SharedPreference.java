package org.timreynolds.slideshow.utility;

import com.google.gson.Gson;

import org.timreynolds.slideshow.model.ImageItem;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tim Reynolds
 */
public class SharedPreference {

    public static final String PREFS_NAME = "GALLERY_IMAGES";
    public static final String ITEMS = "IMAGE_ITEMS";

    public SharedPreference() {
        super();
    }

    public void saveItems(Context context, List<ImageItem> items) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonItems = gson.toJson(items);
        editor.putString(ITEMS, jsonItems);

        editor.commit();
    }

    public void addItem(Context context, ImageItem imageitem) {
        List<ImageItem> items = getItems(context);
        if (items == null)
            items = new ArrayList<ImageItem>();
        items.add(imageitem);
        saveItems(context, items);
    }

    //public void removeItem(Context context, ImageItem imageItem) {
    public void removeItem(Context context, int position, String imageToRemove) {
        ArrayList<ImageItem> items = getItems(context);

        Log.i("sharedpref", "image to check ->" + items.get(position).getThumbnail());
        //if( imageToRemove.equals(items.get(position).getThumbnail()) ) {
            if (items != null) {
                Log.i("sharedpref", "url being removed -> " + items.get(position).getThumbnail());
                items.remove(position);
                for (ImageItem myItem : items) {
                    Log.i("sharedpref", "each item after remove" + myItem.getThumbnail());
                }
                saveItems(context, items);
            }
        //}
        //else{
          //  Log.i("error", "error image url did not match on removal");
        //}
    }

    public ArrayList<ImageItem> getItems(Context context) {
        SharedPreferences settings;
        List<ImageItem> items;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(ITEMS)) {
            String jsonItems = settings.getString(ITEMS, null);
            Gson gson = new Gson();
            ImageItem[] myItems = gson.fromJson(jsonItems,
                    ImageItem[].class);

            items = Arrays.asList(myItems);
            items = new ArrayList<ImageItem>(items);
        } else
            return null;

        return (ArrayList<ImageItem>) items;
    }
}