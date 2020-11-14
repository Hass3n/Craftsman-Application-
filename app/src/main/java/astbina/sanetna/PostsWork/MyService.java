package astbina.sanetna.PostsWork;/*package astbina.sanetna.PostsWork;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import astbina.sanetna.R;
import astbina.sanetna.Nav_Home.Navhome;

public class MyService extends Service {
    static final int NOTIFICATION_ID = 543;
    public static boolean isServiceRunning = false;
    public static final String ACTION_START_SERVICE = "com.mind.simplelogin.MyService";
    private DatabaseReference databaseReference;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // startServiceWithNotification();
        DBChanged();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction().equals(ACTION_START_SERVICE)) {
            DBChanged();
        } else stopMyService();
        return START_STICKY;
    }

    // In case the service is deleted or crashes some how
    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent startIntent = new Intent(getApplicationContext(), MyService.class);
        startIntent.setAction(ACTION_START_SERVICE);
        startService(startIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }


    void startServiceWithNotification() {
        if (isServiceRunning) return;
        isServiceRunning = true;
        Intent notificationIntent = new Intent(this, Navhome.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("New Post");
        builder.setSmallIcon(R.drawable.uv);
        builder.setContentIntent(pendingIntent);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        //Vibration
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        //LED
        builder.setLights(Color.RED, 3000, 3000);
        builder.setOnlyAlertOnce(true);
        Notification noti = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }

    void stopMyService() {
       /* stopForeground(true);
        stopSelf();
        isServiceRunning = false;*/
    //}

   /* @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Intent startIntent = new Intent(getApplicationContext(), MyService.class);
        startIntent.setAction(ACTION_START_SERVICE);
        startService(startIntent);
    }

    public void DBChanged() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (!dataSnapshot.exists()) {
                    }
                    startServiceWithNotification();
                } catch (Exception e) {
                    Log.e("emptyViewHome", "onDataChange: ", e);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}*/
