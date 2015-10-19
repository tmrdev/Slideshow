package org.timreynolds.slideshow;

import org.timreynolds.slideshow.model.ImageItem;
import org.timreynolds.slideshow.utility.SharedPreference;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

/**
 * Created by Tim Reynolds
 * Activity that manages ViewPager for Fragment slideshow
 * Transitions are done through a runnable thread with init and slide delay settings
 * Users can select any image in the previous RecylerView Activity and the image index postion and total
 * through from the intent in the setImageIndexAndCount method
 */
public class ScreenSlideActivity extends AppCompatActivity {

    //Init number of pages in slideshow to zero, gets set when user selected image
    private int NUM_PAGES = 0;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    // int for moving the index to the next image in slideshow
    // when the user selectes an image, the relevant index will be set from there
    private int moveToIndex = 0;

    // provide image transition duration with runnable delay
    private Handler handler = new Handler();

    private Toolbar toolbar;
    SharedPreference mSharedPreference;
    private ArrayList<ImageItem> mImageItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slide);
        setImageIndexAndCount();
        initToolbar();
        mSharedPreference = new SharedPreference();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        // NOTE: setting the moveToIndex value here, corresponds to the image the user selected and will load it
        mPager.setCurrentItem(moveToIndex, true);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // reset menu as images transition
                invalidateOptionsMenu();
            }
        });

        // Set intial delay
        handler.postDelayed(runnable, 4000);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setTitle("Gallery");
        }
    }

    /*
     * Set slideshow 2 second transition
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            moveToIndex++;
            mPager.setCurrentItem(moveToIndex);
            handler.postDelayed(this, 2000);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_image_slide_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // set images order in top toolbar
        String imageNumber = mPager.getCurrentItem() + 1 + "/" + NUM_PAGES;
        menu.findItem(R.id.image_number).setTitle(imageNumber);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_image:
                // delete image and  then go back to MainActivity and rebuilt Grid
                mImageItems = mSharedPreference.getItems(getApplicationContext());
                mSharedPreference.removeItem(getApplicationContext(), mPager.getCurrentItem(), mImageItems.get(mPager.getCurrentItem()).getThumbnail());
                Intent myIntent = new Intent(ScreenSlideActivity.this, MainActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onResume(){
        super.onResume();
        // set if needed
        Log.i("ScreenOnResume", "screen on resume hit");
        // will set zero defaults
        setImageIndexAndCount();
    }

    public void onPause(){
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    public void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private void setImageIndexAndCount(){
        Intent incomingIntent = getIntent();
        // might need to store moveToIndex and NUM_PAGES to sharedprefs
        // as onResume action or some other issue might not have these values
        this.moveToIndex = incomingIntent.getIntExtra("pid", 0);
        this.NUM_PAGES = incomingIntent.getIntExtra("imageCount", 0);
    }

    /**
     * pager adapter
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
