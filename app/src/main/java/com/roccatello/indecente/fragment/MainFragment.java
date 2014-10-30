package com.roccatello.indecente.fragment;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.roccatello.indecente.MainActivity;
import com.roccatello.indecente.R;

public class MainFragment extends Fragment {

    private ImageView imageViewBarattolo;
    private ImageView imageViewBg;
    private ImageView imageViewTitle;
    private Button buttonCopione;
    private Button buttonCast;
    private Button buttonGallery;
    private Button buttonNews;
    private Button buttonTrailer;
    private Button buttonPlay;
    private LinearLayout menuGroup;

    private TextView creditsTextView;

    private MainFragmentCallback callback;

    private Boolean firstLoad;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        this.imageViewBarattolo = (ImageView)rootView.findViewById(R.id.imageViewBarattolo);
//        this.imageViewBarattolo.setImageResource(R.drawable.indecente_render);
//        this.imageViewBarattolo.setScaleType(ImageView.ScaleType.FIT_START);

        this.imageViewBg = (ImageView)rootView.findViewById(R.id.imageViewBg);
//        this.imageViewBg.setImageResource(R.drawable.indecente_bg);
//        this.imageViewBg.setScaleType(ImageView.ScaleType.MATRIX);

        this.imageViewTitle = (ImageView)rootView.findViewById(R.id.imageViewTitle);
        //this.imageViewTitle.setImageResource(R.drawable.indecente_text);
        //this.imageViewTitle.setScaleType(ImageView.ScaleType.MATRIX);

        this.menuGroup = (LinearLayout)rootView.findViewById(R.id.menuGroup);
        this.buttonCopione = (Button)rootView.findViewById(R.id.buttonCopione);
        this.buttonCopione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onIndecenteMenuButtonClick(1);
            }
        });

        this.buttonCast = (Button)rootView.findViewById(R.id.buttonCast);
        this.buttonCast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onIndecenteMenuButtonClick(2);
            }
        });

        this.buttonGallery = (Button)rootView.findViewById(R.id.buttonGallery);
        this.buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onIndecenteMenuButtonClick(3);
            }
        });

        this.buttonNews = (Button)rootView.findViewById(R.id.buttonNews);
        this.buttonNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onIndecenteMenuButtonClick(4);
            }
        });

        this.buttonTrailer = (Button)rootView.findViewById(R.id.buttonTrailer);
        this.buttonTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onIndecenteMenuButtonClick(5);
            }
        });

        this.buttonPlay = (Button)rootView.findViewById(R.id.buttonPlay);
        this.buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onIndecenteMenuButtonClick(6);
            }
        });

        this.firstLoad = ((MainActivity)getActivity()).hasToAnimateMainFragment();

        this.creditsTextView = (TextView)rootView.findViewById(R.id.textViewCredits);
        this.creditsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onIndecenteMenuButtonClick(100);
            }
        });
        this.creditsTextView.setPaintFlags(this.creditsTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.firstLoad) {
            // title alpha
            if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                this.imageViewTitle.setAlpha(0.0f);
            } else {
                this.imageViewTitle.setAlpha(0);
            }
            // out of the screen
            FrameLayout.LayoutParams barattoloLayout = (FrameLayout.LayoutParams) this.imageViewBarattolo.getLayoutParams();
            barattoloLayout.bottomMargin = -barattoloLayout.height;

            FrameLayout.LayoutParams menuLayout = (FrameLayout.LayoutParams) this.menuGroup.getLayoutParams();
            menuLayout.rightMargin = -menuLayout.width;


            FrameLayout.LayoutParams giocaLayout = (FrameLayout.LayoutParams) this.buttonPlay.getLayoutParams();
            giocaLayout.leftMargin = -menuLayout.width;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.firstLoad) {
            // barattolo animation
            final FrameLayout.LayoutParams barattoloLayout = (FrameLayout.LayoutParams) this.imageViewBarattolo.getLayoutParams();
            final int originalBottomMargin = barattoloLayout.bottomMargin;
            ValueAnimator barattoloAnim = ValueAnimator.ofInt(-originalBottomMargin);
            barattoloAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    barattoloLayout.bottomMargin = originalBottomMargin + (Integer) valueAnimator.getAnimatedValue();
                    imageViewBarattolo.setLayoutParams(barattoloLayout);
                }
            });
            barattoloAnim.setDuration(700);
            barattoloAnim.setStartDelay(500);
            barattoloAnim.start();

            // title opacity
            ObjectAnimator titleOpacityAnim = ObjectAnimator.ofFloat(imageViewTitle, "alpha", 0.0f, 1.0f);
            titleOpacityAnim.setDuration(1200);
            titleOpacityAnim.setStartDelay(200);
            titleOpacityAnim.start();

            // menu animation
            final FrameLayout.LayoutParams menuGroupLayout = (FrameLayout.LayoutParams) this.menuGroup.getLayoutParams();
            final int originalRightMargin = menuGroupLayout.rightMargin;
            ValueAnimator menuAnim = ValueAnimator.ofInt(-originalRightMargin);
            menuAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    menuGroupLayout.rightMargin = originalRightMargin + (Integer) valueAnimator.getAnimatedValue();
                    menuGroup.setLayoutParams(menuGroupLayout);
                }
            });
            menuAnim.setDuration(700);
            menuAnim.setStartDelay(1000);
            menuAnim.start();

            final FrameLayout.LayoutParams playLayout = (FrameLayout.LayoutParams) this.buttonPlay.getLayoutParams();
            final int originalLeftMargin = playLayout.leftMargin;
            ValueAnimator playAnim = ValueAnimator.ofInt(-originalLeftMargin);
            playAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    playLayout.leftMargin = originalLeftMargin + (Integer) valueAnimator.getAnimatedValue();
                    buttonPlay.setLayoutParams(playLayout);
                }
            });
            playAnim.setDuration(700);
            playAnim.setStartDelay(1000);
            playAnim.start();
        }
        this.firstLoad = false;
    }

    public MainFragmentCallback getCallback() {
        return callback;
    }

    public void setCallback(MainFragmentCallback callback) {
        this.callback = callback;
    }
}