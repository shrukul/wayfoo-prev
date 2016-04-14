package wayfoo.wayfoo.gcmservice;

/**
 * Created by shrukul on 18/3/16.
 */

import com.google.android.gms.gcm.GcmListenerService;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import wayfoo.wayfoo.R;

public class GcmMessageHandler extends GcmListenerService {
    public static final int MESSAGE_NOTIFICATION_ID = 435345;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        String balance = data.getString("balance");
        String typ="yay";
        String type = data.getString("type");
        int typeInt = Integer.parseInt(type);
        System.out.println("Type"+typeInt);
        if (typeInt == 0) {
            message = "Your Order will be delivered. The amount payable is ₹"+message;
            typ = "Done";
        } else if(typeInt == 1){
            message = "Your Order has been confirmed. The amount payable is ₹"+message;
            typ = "Confirmed";
        }

        createNotification(typ, message);
    }

    // Creates notification based on title and body received
    private void createNotification(String typ, String body) {
        Context context = getBaseContext();
/*        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.notify).setContentTitle(title)
                .setContentText(body);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());*/
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        android.support.v7.app.NotificationCompat.Builder mBuilder = (android.support.v7.app.NotificationCompat.Builder) new android.support.v7.app.NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.food)
                .setContentTitle("Wayfoo")
                .setContentText(typ)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.app_icon))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body));
        ;
        notificationManager.notify(1, mBuilder.build());
    }

}
