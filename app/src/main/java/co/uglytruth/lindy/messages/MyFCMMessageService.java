package co.uglytruth.lindy.messages;

/**
 * Created by tjw127 on 11/8/17.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;

import co.uglytruth.lindy.LindyHomeActivity;
import co.uglytruth.lindy.R;

public class MyFCMMessageService  {
//
//    private static final String TAG = "MyAndroidFCMService";
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        //Log data to Log Cat
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
//        //create notification
//        createNotification(remoteMessage.getNotification().getBody());
//    }
//
//    private void createNotification( String messageBody) {
//        Intent intent = new Intent( this , LindyHomeActivity.class );
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
//                .setSmallIcon(R.mipmap.ic_shop_black_24dp)
//                .setContentTitle("Check out Foxy Lou!")
//                .setContentText(messageBody)
//                .setAutoCancel( true )
//                .setSound(notificationSoundURI)
//                .setContentIntent(resultIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, mNotificationBuilder.build());
//    }
}
