package com.roccatello.indecente.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.roccatello.indecente.MainActivity;
import com.roccatello.indecente.R;
import com.roccatello.indecente.model.Photo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Eduard on 07/09/2014.
 */
public class GalleryAdapter extends PagerAdapter {
    private Context context;
    private List<Photo> photos;
    private LayoutInflater inflater;
    private DisplayImageOptions options;

    public GalleryAdapter(Context ctx, List<Photo> items) {
        this.context = ctx;
        this.photos = items;
        inflater = LayoutInflater.from(this.context);

        this.options = new DisplayImageOptions.Builder()
                //.showImageForEmptyUri(R.drawable.ic_empty)
                //.showImageOnFail(R.drawable.ic_error)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }

    @Override
    public int getCount() {
        return this.photos.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View imageLayout = inflater.inflate(R.layout.item_pager_image, container, false);
        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);

        ImageLoader.getInstance().displayImage(MainActivity.GALLERY_DATA_URL + this.photos.get(position).getUrl(), imageView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                spinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "Input/Output error";
                        break;
                    case DECODING_ERROR:
                        message = "Image can't be decoded";
                        break;
                    case NETWORK_DENIED:
                        message = "Downloads are denied";
                        break;
                    case OUT_OF_MEMORY:
                        message = "Out Of Memory error";
                        break;
                    case UNKNOWN:
                        message = "Unknown error";
                        break;
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                spinner.setVisibility(View.GONE);
                new PhotoViewAttacher(imageView).update();
            }
        });

        container.addView(imageLayout, 0);
        return imageLayout;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view.equals(o);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
