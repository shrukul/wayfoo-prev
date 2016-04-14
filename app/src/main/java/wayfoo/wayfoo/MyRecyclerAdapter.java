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
			Intent intent = new Intent(mc, Intermediate.class);
			intent.putExtra("title", pagenext);
			intent.putExtra("table", "1");
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
				.error(R.mipmap.logo).placeholder(R.mipmap.logo)
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

	}
	@Override
	public int getItemCount() {
		return (null != feedItemList ? feedItemList.size() : 0);
	}

}
