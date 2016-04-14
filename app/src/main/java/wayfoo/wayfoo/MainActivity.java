package wayfoo.wayfoo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;

    private static final String TAG = "RecyclerViewExample";
    private List<FeedItem> feedsList;
    private MyRecyclerAdapter adapter;
    private ProgressBar progressBar;
    AsyncHttpTask a;
    ImageView b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        b=(ImageView)findViewById(R.id.qqq);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(this);
        String email = pref.getString("KEY_E", null);
        String loc = pref.getString("LOC","Mangalore");
        final String url = "http://wayfoo.com/hotellist.php?Location="+loc;
        a = new AsyncHttpTask();
        a.execute(url);
/*        ImageView icon = new ImageView(this);
        icon.setImageResource(R.mipmap.fab);*/

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(
                            new InputStreamReader(
                                    urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1;
                } else {
                    result = 0;
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new MyRecyclerAdapter(getApplicationContext(), feedsList);
                mRecyclerView.setAdapter(adapter);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setMessage("Something seems to be wrong with the internet.");
                builder.setTitle("Oops!!");
                builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                AlertDialog a=builder.create();
                a.show();
                Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
                Button bq2 = a.getButton(DialogInterface.BUTTON_POSITIVE);
                bq.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                bq2.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("output");
            feedsList = new ArrayList<FeedItem>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setTitle(post.optString("Name"));
                item.setPlace(post.optString("Place"));
                item.setThumbnail(post.optString("Image"));
                item.setID(post.optString("ID"));
                feedsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        if (a != null) a.cancel(true);
        super.onDestroy();
    }

    public void onStop() {
        if (a != null) a.cancel(true);
        super.onStop();
    }

}
