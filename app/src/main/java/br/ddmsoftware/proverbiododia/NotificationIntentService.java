package br.ddmsoftware.proverbiododia;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by dmoraes on 23/09/2016.
 */
public class NotificationIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";

    public NotificationIntentService(){
        super(NotificationIntentService.class.getSimpleName());
    }

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context,NotificationIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotificationService(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, start handling a notification service");
        try {
            String action = intent.getAction();

            if(ACTION_START.equals(action)) {
                processStartNotification();
            }

            if (ACTION_DELETE.equals(action)) {
                processDeleteNotification();
            }
        }finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification() {
    }

    private void processStartNotification() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        String hint = getResources().getString(R.string.notification_hint) ;

        builder.setContentTitle(getResources().getString(R.string.app_name))
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentText(hint)
                .setSmallIcon(R.mipmap.icone);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID,
                new Intent(this, NavigationMain2Activity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }
}
