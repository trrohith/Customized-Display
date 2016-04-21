package cheeseproducts.customizeddisplay;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView)findViewById(R.id.textView);
        final TextView textView2 = (TextView)findViewById(R.id.textView2);
        final TextView textView3 = (TextView)findViewById(R.id.textView3);

        view = this.getWindow().getDecorView();

        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        });



        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String currentTime = (String) DateFormat.format("hh:mm:ss AA", new Date());
                                currentTime=currentTime.toUpperCase();
                                textView.setText(currentTime);
                                String dateSet = (String) DateFormat.format("dd/MM/yyyy", new Date());
                                textView2.setText(dateSet);
                                String monthSet = (String) DateFormat.format("MMM, EEE", new Date());
                                textView3.setText(monthSet);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };






        /*ValueAnimator colorAnim = ObjectAnimator.ofInt(view, "backgroundColor", Color.GREEN,Color.WHITE,Color.RED,Color.BLUE,Color.MAGENTA,Color.YELLOW);

        colorAnim.setDuration(30000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();*/
        t.start();

        generateNew(Color.WHITE);
    }

    public void colorAnimations(int color, int color1, int color2){
        final ValueAnimator anim = ObjectAnimator.ofInt(view, "backgroundColor", color,color1,color2);
        anim.setDuration(30000);
        anim.setEvaluator(new ArgbEvaluator());
        anim.start();

        final int previous = color2;
        anim.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                generateNew(previous);
            }
        });
    }

    public void generateNew(int previous)
    {
        Random random = new Random();


        Log.e("Generator","Called");
        final int color = Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256));
        final int color1 = Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256));
        final int color2 = Color.rgb(random.nextInt(256),random.nextInt(256),random.nextInt(256));


        final ValueAnimator anim = ObjectAnimator.ofInt(view, "backgroundColor", previous,color);
        anim.setDuration(5000);
        anim.setEvaluator(new ArgbEvaluator());
        anim.start();

        anim.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                colorAnimations(color,color1,color2);
            }
        });


    }

}
