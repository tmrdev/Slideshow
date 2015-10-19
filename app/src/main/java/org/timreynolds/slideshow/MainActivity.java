package org.timreynolds.slideshow;

import org.timreynolds.slideshow.model.ImageItem;
import org.timreynolds.slideshow.adapter.MyRecyclerAdapter;
import org.timreynolds.slideshow.utility.SharedPreference;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<ImageItem> mImageItemList = new ArrayList<ImageItem>();

    private RecyclerView mRecyclerView;

    private MyRecyclerAdapter adapter;

    private Toolbar toolbar;

    private SharedPreference mSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();

        //Initialize recyclerview - set GridLayoutManager
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        Log.i("main-activity-oncreate", "check list");
        mSharedPreference = new SharedPreference();
        mImageItemList = mSharedPreference.getItems(this);
        // basic test for state of mImageItemsList from SharedPreference class
        checkSavedImageState(mImageItemList);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setTitle("Image List");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.i("test", "MainActivity onResume hit");
        mSharedPreference = new SharedPreference();
        mImageItemList = mSharedPreference.getItems(this);
        // basic test for state of mImageItemsList from SharedPreference class
        checkSavedImageState(mImageItemList);
    }


    /**
     * buildResult
     * @param init - if true builds out the intial list of image urls, false and the list is loaded from saved preferences
     */
    private void buildResult(boolean init){
        // always initialize ImageItem
        mImageItemList = new ArrayList<ImageItem>();

        if(init == true) {
        /*
         * Use This to Initialize on First Loop
         */
            setImageItem("http://photojournal.jpl.nasa.gov/jpegMod/PIA19981_modest.jpg");
            setImageItem("http://photojournal.jpl.nasa.gov/jpegMod/PIA20010_modest.jpg");
            setImageItem("http://photojournal.jpl.nasa.gov/jpegMod/PIA19973_modest.jpg");
            setImageItem("http://photojournal.jpl.nasa.gov/jpegMod/PIA18339_modest.jpg");
            setImageItem("http://photojournal.jpl.nasa.gov/jpegMod/PIA19813_modest.jpg");
            setImageItem("http://photojournal.jpl.nasa.gov/jpegMod/PIA19811_modest.jpg");
            setImageItem("http://photojournal.jpl.nasa.gov/jpegMod/PIA19719_modest.jpg");
            setImageItem("http://photojournal.jpl.nasa.gov/jpegMod/PIA19598_modest.jpg");
            setImageItem("http://photojournal.jpl.nasa.gov/jpegMod/PIA19703_modest.jpg");

        }
        else if(init == false){
            // retrieve images from SharedPreference custom class if not init
            mImageItemList = mSharedPreference.getItems(this);

        }
        // set MyRecyclerAdapter
        adapter = new MyRecyclerAdapter(MainActivity.this, mImageItemList);
        mRecyclerView.setAdapter(adapter);
    }

    public void checkSavedImageState(List<ImageItem> imageList){
        if(imageList == null){
            // is null so either build initial results or trap for any errors
            //Log.i("test", "image item list is null");
            buildResult(true);
        }
        else{
            if(imageList.size() == 0){
                //Log.i("test", "image items list has been created but now has no entries");
                // If result was built and all images have been delted then rebuild from static list
                buildResult(true);
            }

            if(imageList.size() > 0){
                // there is existing saved date so load without init
                buildResult(false);

                // dump out all image items for review
                for( ImageItem element : imageList ) {
                    //Log.i("dump", "list of image urls -> " + element.getThumbnail());
                }
            }
        }
    }

    private void setImageItem(String imageURL) {
        ImageItem item = new ImageItem();
        //item.setTitle(title);
        item.setThumbnail(imageURL);
        // save the list of images to display with custom class
        mSharedPreference.addItem(this, item);
        mImageItemList.add(item);

    }
}
