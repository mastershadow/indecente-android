package com.roccatello.indecente;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.roccatello.indecente.fragment.CastFragment;
import com.roccatello.indecente.fragment.CopioneFragment;
import com.roccatello.indecente.fragment.CreditsFragment;
import com.roccatello.indecente.fragment.GalleryFragment;
import com.roccatello.indecente.fragment.GameFragment;
import com.roccatello.indecente.fragment.MainFragment;
import com.roccatello.indecente.fragment.MainFragmentCallback;
import com.roccatello.indecente.fragment.NewsFragment;


public class MainActivity extends FragmentActivity implements MainFragmentCallback {
    public static final String WEB_URL = "http://app.roccatello.com/indecente/";
    public static final String GALLERY_DATA_URL = "http://app.roccatello.com/indecente/gallery/";
    private boolean animateMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Init Universal Image Loader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        this.animateMainFragment = true;
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        MainFragment mainFragment = new MainFragment();
        mainFragment.setCallback(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, mainFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onIndecenteMenuButtonClick(Integer buttonId) {
        this.animateMainFragment = false;

        if (buttonId == 5) { // trailer
            String video_id = "eqI0LE9UpsE";
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + video_id));
                startActivity(intent);
            } catch (Exception ex) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Hai installato l'app di Youtube? Se no, fallo subito :)").setTitle("Attenzione!");
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            } finally {
                return;
            }
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment f = null;
        switch (buttonId) {
            case 1: // copione
                f = new CopioneFragment();
                break;
            case 2: // cast
                f = new CastFragment();
                break;
            case 3: { // gallery
                // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                f = new GalleryFragment();
                break;
            }
            case 4: // news
                f = new NewsFragment();
                break;
            case 5: // trailer
                break;
            case 6: // game
                f = new GameFragment();
                break;
            case 100: // cast
                f = new CreditsFragment();
                break;
        }
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.container, f).addToBackStack("Subview" + buttonId);
        ft.commit();
    }

    public boolean hasToAnimateMainFragment() {
        return animateMainFragment;
    }
}
