package org.timreynolds.slideshow.adapter;

import com.squareup.picasso.Callback;

import org.timreynolds.slideshow.model.ImageItem;
import org.timreynolds.slideshow.utility.PicassoCache;
import org.timreynolds.slideshow.R;
import org.timreynolds.slideshow.ScreenSlideActivity;
import org.timreynolds.slideshow.utility.SharedPreference;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Created by Tim Reynolds
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<ImageListRowHolder> {

    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 768;

    private List<ImageItem> mImageItemList;
    private Context mContext;
    private List<ImageItem> savedImages;
    private SharedPreference mSharedPreference;

    public MyRecyclerAdapter(Context context, List<ImageItem> imageItemList) {
        this.mImageItemList = imageItemList;
        this.mContext = context;
    }

    @Override
    public ImageListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        ImageListRowHolder mh = new ImageListRowHolder(v);

        return mh;
    }

    @Override
    public void onBindViewHolder(final ImageListRowHolder imageListRowHolder, final int position) {
        final ImageItem imageItem = mImageItemList.get(position);
        //Log.i("test", "thumbnail -> " + imageItem.getThumbnail());
        mSharedPreference = new SharedPreference();
        // get existing list that was stored
        savedImages = mSharedPreference.getItems(mContext);

        // NOTE: error exception handled in PicassoCache class, basic error implemented here
        PicassoCache.getPicassoInstance(mContext).load(imageItem.getThumbnail())
                .resize(0, 125).placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder).into(imageListRowHolder.thumbnail, new Callback() {
            @Override
            public void onSuccess() {
                imageListRowHolder.itemView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                // remove from prefs
                mSharedPreference.removeItem(mContext, position, imageItem.getThumbnail());
                remove(imageItem);
                notifyDataSetChanged();
                Log.i("picassoError","picasso error : imageURL -> "+imageItem.getThumbnail());
            }
        });

        imageListRowHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ScreenSlideActivity.class);
                intent.putExtra("pid", position);
                intent.putExtra("imageCount", getItemCount());
                mContext.startActivity(intent);
            }
        });

    }

    public void remove(ImageItem item) {
        int position = mImageItemList.indexOf(item);
        mImageItemList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return (null != mImageItemList ? mImageItemList.size() : 0);
    }

}