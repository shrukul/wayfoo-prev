package wayfoo.wayfoo;

/**
 * Created by Axle on 26/03/2016.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static wayfoo.wayfoo.Constants.FIRST_COLUMN;
import static wayfoo.wayfoo.Constants.THIRD_COLUMN;
import static wayfoo.wayfoo.Constants.SECOND_COLUMN;

public class PerOrder extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> list;
    private ProgressBar progressBar;
    AsyncHttpTask a;
    private Toolbar mToolbar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perorder);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        listView=(ListView)findViewById(R.id.listView1);
        Bundle b= getIntent().getExtras();
        String oid=b.getString("oid");
        final String url = "http://wayfoo.com/perOrder.php?OID="+oid;
        a = new AsyncHttpTask();
        a.execute(url);
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
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                PerOrderAdapter adapter=new PerOrderAdapter(PerOrder.this, list);
                listView.setAdapter(adapter);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(PerOrder.this);
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
                bq.setTextColor(ContextCompat.getColor(PerOrder.this, R.color.colorPrimary));
                bq2.setTextColor(ContextCompat.getColor(PerOrder.this, R.color.colorPrimary));
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("output");
            list=new ArrayList<HashMap<String,String>>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                HashMap<String,String> temp=new HashMap<String, String>();
                temp.put(FIRST_COLUMN, post.optString("Name"));
                temp.put(SECOND_COLUMN,post.optString("Quantity"));
                temp.put(THIRD_COLUMN, String.valueOf(Float.parseFloat(post.optString("Amount"))*Float.parseFloat(post.optString("Quantity"))));
                list.add(temp);
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