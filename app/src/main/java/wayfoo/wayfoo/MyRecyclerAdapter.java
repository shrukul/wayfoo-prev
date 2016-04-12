package wayfoo.wayfoo;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class MyRecyclerAdapter extends
		RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {

	private final Context mContext;
	private static Context mc;
	static String tag="Hotel List";

	public static class CustomViewHolder extends RecyclerView.ViewHolder
			implements OnClickListener {

		protected ImageView imageView;
		protected TextView textView, place;
		protected ToggleButton fav;
		CardView card;

		public CustomViewHolder(View view) {
			super(view);
			this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
			this.textView = (TextView) view.findViewById(R.id.title1);
			this.place = (TextView) view.findViewById(R.id.Place);
			this.fav = (ToggleButton) view.findViewById(R.id.fav);
			mc = view.getContext();
			card = (CardView) view.findViewById(R.id.YogaCard);
			card.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int i = getAdapterPosition();
			FeedItem feedItem = feedItemList.get(i);
			String pagenext;
			pagenext=Html.fromHtml(feedItem.getTitle()).toString();
			Intent intent = new Intent(mc, SimpleScannerActivity.class);
			intent.putExtra("name", pagenext);
			mc.startActivity(intent);
		}
	}

	private static List<FeedItem> feedItemList;

	public MyRecyclerAdapter(Context context, List<FeedItem> feedItemList) {
		MyRecyclerAdapter.feedItemList = feedItemList;
		this.mContext = context;
	}

	@Override
	public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.card_view_row, null);

		CustomViewHolder viewHolder = new CustomViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
		final FeedItem feedItem = feedItemList.get(i);

		Picasso.with(mContext).load(feedItem.getThumbnail())
				.error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher)
				.into(customViewHolder.imageView);

		Typeface font1 = Typeface.createFromAsset(mContext.getAssets(),
				"font/RobotoCondensed-Regular.ttf");
		SpannableStringBuilder SS = new SpannableStringBuilder(
				Html.fromHtml(feedItem.getTitle()));
		SS.setSpan(new CustomTypeFace("", font1), 0, SS.length(),
				Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		customViewHolder.textView.setText(SS);

		Typeface font = Typeface.createFromAsset(mContext.getAssets(),
				"font/RobotoCondensed-Regular.ttf");
		SS = new SpannableStringBuilder(Html.fromHtml(feedItem.getPlace()));
		SS.setSpan(new CustomTypeFace("", font), 0, SS.length(),
				Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		customViewHolder.place.setText(Html.fromHtml(feedItem.getPlace()));
		customViewHolder.fav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!customViewHolder.fav.isChecked()){
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					SharedPreferences pref = PreferenceManager
							.getDefaultSharedPreferences(mc);
					String email = pref.getString("KEY_E", null);
					nameValuePair.add(new BasicNameValuePair("CID", feedItem.getID()));
					nameValuePair.add(new BasicNameValuePair("mail", email));
					AsyncHttpPost async = new AsyncHttpPost(MyRecyclerAdapter.this, nameValuePair);
					async.execute("http://www.wayfoo.in/favadd.php");
				} else{
					List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
					SharedPreferences pref = PreferenceManager
							.getDefaultSharedPreferences(mc);
					String email = pref.getString("KEY_E", null);
					nameValuePair.add(new BasicNameValuePair("CID", feedItem.getID()));
					nameValuePair.add(new BasicNameValuePair("mail", email));
					AsyncHttpPost async = new AsyncHttpPost(MyRecyclerAdapter.this, nameValuePair);
					async.execute("http://www.wayfoo.in/favrem.php");
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return (null != feedItemList ? feedItemList.size() : 0);
	}

	public class AsyncHttpPost extends AsyncTask<String, String, String> {
		TextView t;
		MyRecyclerAdapter ma;
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();

		public AsyncHttpPost(MyRecyclerAdapter m, List<NameValuePair> nvp) {
			ma = m;
			nameValuePair = nvp;
		}

		@Override
		protected String doInBackground(String... params) {
			byte[] result = null;
			String str = "";
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(params[0]);
			try {
				post.setEntity(new UrlEncodedFormEntity(nameValuePair));
				HttpResponse response = client.execute(post);
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == HttpURLConnection.HTTP_OK) {
					result = EntityUtils.toByteArray(response.getEntity());
					str = new String(result, "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return str;
		}

		@Override
		protected void onPostExecute(String output) {
			// do something with the string returned earlier
			Toast.makeText(mc, "done with " + output,
				Toast.LENGTH_SHORT).show();
		}

	}
}
