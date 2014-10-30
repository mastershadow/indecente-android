package com.roccatello.indecente.fragment;



import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roccatello.indecente.MainActivity;
import com.roccatello.indecente.R;
import com.roccatello.indecente.adapter.GalleryAdapter;
import com.roccatello.indecente.model.Photo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class GalleryFragment extends Fragment {

    private Context context;
    private ViewPager viewPager;

    public GalleryFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        v.setBackgroundColor(Color.argb(255, 0, 0, 0));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.viewPager = (ViewPager)getView().findViewById(R.id.viewPager);
        this.loadResources();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity.getApplicationContext();
    }

    public void loadResources() {
        new AsyncTask<Void, Integer, List<Photo>>(){

            @Override
            protected List doInBackground(Void... voids) {
                ObjectMapper mapper = new ObjectMapper();
                List<Photo> galleryPics = null;
                try {
                    galleryPics = mapper.readValue(new URL(MainActivity.GALLERY_DATA_URL + "gallery.json"), new TypeReference<List<Photo>>() { });
                } catch (Exception e) {
                    Log.e("GalleryFragment", e.getMessage());
                }
                return galleryPics;
            }

            @Override
            protected void onPostExecute(List<Photo> items) {
                super.onPostExecute(items);
                if (items != null && GalleryFragment.this.context != null) {
                    GalleryAdapter adapter = new GalleryAdapter(GalleryFragment.this.context, items);
                    viewPager.setAdapter(adapter);
                }
            }
        }.execute();
    }
}
