package wayfoo.wayfoo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        KenBurnsView kbv = (KenBurnsView)findViewById(R.id.kbv_splash_image);
        kbv.setImageResource(R.drawable.splash2);
        DecelerateInterpolator DECELERATE = new DecelerateInterpolator();
        AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        LinearInterpolator lip = new LinearInterpolator();
        lip.getInterpolation(100);
        RandomTransitionGenerator generator = new RandomTransitionGenerator(2800, lip);
        //duration = 10000ms = 10s and interpolator = ACCELERATE_DECELERATE
        kbv.setTransitionGenerator(generator); //set new transition on kbv
//        RandomTransitionGenerator localRandomTransitionGenerator = new RandomTransitionGenerator(3000L, new LinearInterpolator());
//        kbv.setTransitionGenerator(localRandomTransitionGenerator);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences pref = PreferenceManager
                        .getDefaultSharedPreferences(SplashScreen.this);
                if (pref.contains("KEY_E")) {
                    startActivity(new Intent(SplashScreen.this,
                            MainActivity.class));
                    finish();
                } else {
                    Intent i = new Intent(SplashScreen.this, IntroVIewPager.class);
                    startActivity(i);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }

}