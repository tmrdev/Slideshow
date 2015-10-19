package org.timreynolds.slideshow;

import com.squareup.picasso.Callback;

import org.timreynolds.slideshow.model.ImageItem;
import org.timreynolds.slideshow.utility.PicassoCache;
import org.timreynolds.slideshow.utility.SharedPreference;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Tim Reynolds
 * fragment for each image slide
 */
public class ScreenSlidePageFragment extends Fragment {

    //The argument key for the page number this fragment represents.

    public static final String ARG_PAGE = "page";

    // The fragment's page number through each iteration
    private int mPageNumber;

    public String[] imageUrls;

    SharedPreference sharedPreference;
    List<ImageItem> mImageItems;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber) {

        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);

        return fragment;
    }

    public ScreenSlidePageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_image_slide_page, container, false);
        ImageView largeImageView = (ImageView) rootView.findViewById(R.id.largeImage);
        // get saved Image items
        sharedPreference = new SharedPreference();
        mImageItems = sharedPreference.getItems(getActivity());
        Log.i("test", "image item ? -> " + mImageItems.get(mPageNumber).getThumbnail());
        String imageURL = mImageItems.get(mPageNumber).getThumbnail();

            PicassoCache.getPicassoInstance(getActivity()).load(imageURL)
                    .fit().placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder).into(largeImageView, new Callback() {
                @Override
                public void onSuccess() {
                    rootView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError() {
                    //rootView.setVisibility(View.INVISIBLE);
                    Log.i("picassoError", "picasso error : imageURL -> " + mImageItems.get(mPageNumber).getThumbnail());
                }
            });

            return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

}
