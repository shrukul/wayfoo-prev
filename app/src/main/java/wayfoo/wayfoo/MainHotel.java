package wayfoo.wayfoo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainHotel extends AppCompatActivity {

    private Toolbar mToolbar;
    private static final String TAG = "Menu Card";
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainHotel.this);
        builder.setCancelable(true);
        builder.setMessage("Are you Sure??.");
        builder.setTitle("Leave Hotel");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(MainHotel.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        AlertDialog a=builder.create();
        a.show();
        Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bq2 = a.getButton(DialogInterface.BUTTON_POSITIVE);
        bq.setTextColor(ContextCompat.getColor(MainHotel.this, R.color.colorPrimary));
        bq2.setTextColor(ContextCompat.getColor(MainHotel.this, R.color.colorPrimary));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            startActivity(new Intent(MainHotel.this,Search.class));
        }
        if(id == R.id.cart){
            Intent it = new Intent(MainHotel.this,AdditionalInfo.class);
            it.putExtra("title",getIntent().getExtras().getString("title"));
            it.putExtra("table",getIntent().getExtras().getString("table"));
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_card);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_start(), "Starters");
        adapter.addFragment(new Fragment_main(), "Main Course");
        adapter.addFragment(new Fragment_deserts(), "Deserts");
        adapter.addFragment(new Fragments_drinks(), "Drinks");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
