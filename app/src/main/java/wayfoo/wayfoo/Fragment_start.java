package wayfoo.wayfoo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Axle on 09/02/2016.
 */
public class Fragment_start extends Fragment {
    private RecyclerView rv;

    private static final String TAG = "Starters";
    private List<FeedItemHotel> persons;
    private DatabaseHandler db;
    private Cursor c;
    private MyRecyclerAdapterHotel adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_card_view_hotel,
                container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        rv.setHasFixedSize(true);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        db = new DatabaseHandler(getActivity());
        initializeData();
        adapter = new MyRecyclerAdapterHotel(getActivity(), persons);
        rv.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        db.close();
        return rootView;
    }

    private void initializeData() {

        persons = new ArrayList<FeedItemHotel>();
        FeedItemHotel feed = new FeedItemHotel();
        List<FeedItemHotel> contacts = db.getAllContacts();

        for (FeedItemHotel cn : contacts) {
            if (cn.getType().equals("Starters")) {
                FeedItemHotel item = new FeedItemHotel();
                item.setTitle(cn.getTitle());
                item.setType(cn.getType());
                item.setPrice(cn.getPrice());
                item.setVeg(cn.getVeg());
                item.setAmt(cn.getAmt());
                persons.add(item);
            }
        }
    }
}
