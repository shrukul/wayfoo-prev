package wayfoo.wayfoo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Axle on 04/02/2016.
 */
public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private String TAG="QRCode";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString());
        Toast.makeText(getApplicationContext(),"QRCOde is :"+ rawResult.getText(),Toast.LENGTH_LONG).show();
        /*if(!rawResult.getText().contains("wf")){

        }*/
        String[] parts = rawResult.getText().split("-");
        String part1 = parts[0];
        String part2 = parts[1];
        Intent i=new Intent(this,Intermediate.class);
        i.putExtra("title",part1 );
        i.putExtra("table",part2);
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString("hotel", part1);
        editor.putString("table", part2);
        mScannerView.stopCamera();
        startActivity(i);
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }

}