package com.example.pleasework;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;
import static android.app.Notification.VISIBILITY_PUBLIC;
import static android.graphics.Bitmap.createBitmap;

public class NotificationsActivity extends AppCompatActivity {

    private int messageCount = 25;
    //choose your own
    private final String CHANNEL_ID = "CHANNEL_ID";
    private final String CHANNEL_ID_EXPANDABLE = "CHANNEL_ID_EXPANDABLE";
    private final String CHANNEL_ID_GROUPED = "CHANNEL_ID_GROUPED";
    private final int NOTIFICATION_ID = 420;
    private final int NOTIFICATION_ID_EXPANDABLE = 421;
    public final String CHANNEL_ID_REPLAY = "personal_notifications";
    public static final int NOTIFICATION_ID_REPLAY = 101;
    public static final String TXT_REPLAY = "text replay";


//    Zadanie 1
//      Stwórz proste powiadomienie wyświetlające się na pasku powiadomień. Pamiętaj, że każde powiadomienie musi zawierać ikonkę. Ustawia się ją za pomocą setSmallIcon().
//      (wskazówka) Aby stworzyć nową ikonkę możesz kliknąć w dowolnym miejscu project tree prawym przyciskiem myszy i wybrać Vector Asset
//      Spraw aby powiadomienie po kliknięciu przenosiło cię w wybrane miejsce w aplikacji
//      Spraw aby powiadomienie wyświetlało się na wygaszaczu ekranu
//      (dodatkowe) Dodaj przyciski w powiadomieniu

    public void generateSimpleNotification(View view) {

        createNotificationChannel(getString(R.string.channel_name),CHANNEL_ID);
        //this part indicates what will happen after tapping a notification
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        //this part creates notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.abc1)    //R.drawable = app/res/drawable/image
                .setContentTitle("Simple Notification")
                .setContentText("This is a simple notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)       //different types of priority have different options like vibration, sound or led signal
                .setContentIntent(pendingIntent)                        //sets tap action
                .setAutoCancel(true)                                    //notification disappear after being tapped
                .setVisibility(VISIBILITY_PUBLIC)                       //permits to show all content of notification on a lock screen
                .setNumber(messageCount)                                 //indicates number of notifications this one contains
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);  //displays small icon instead of big one in Badge

        //this part below actually shows the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
//    Zadanie 2.
//    Aby stwórz rozwijane powiadomienie wystarczy wykorzystać funkcję setStyle() podczas kreowania powiadomienia za pomocą NotificationCompat.Builder
//    Umożliwia ona dołączenie do powiadomienia m.in. obrazu bmp, długiego tekstu czy pojedynczych linijek wiadomości
//    Twoim zadaniem będzie stworzenie rozszerzanego powiadomienia na każdy z wymienionych sposobów

    public void generateExpandableNotification(View view){
        createNotificationChannel(getString(R.string.ex_channel_name),CHANNEL_ID_EXPANDABLE);


        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID_EXPANDABLE);
        builder.setSmallIcon(R.drawable.abc1)    //R.drawable = app/res/drawable/image
                .setContentTitle("Expandable Notification")
                .setContentText("This is an expandable notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVisibility(VISIBILITY_PUBLIC);
        ;


//Choose one of the possible option below and uncomment to see effects (ctrl+/)

//        //Option 1 bitmap
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bestofthebest);
        builder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(myBitmap)
                .bigLargeIcon(null));

//        //Option 2 bigtext
        String subject = getString(R.string.longmsg);
//        builder.setStyle(new NotificationCompat.BigTextStyle()
//               .bigText(subject));

//        //Option 3 line message
//        String messageSnippet1 = "First line";
//        String messageSnippet2 = "Second one";
//        String messageSnippet3 = "You can add a lot of new lines";
//        String messageSnippet4 = "But only up to 6 will be displayed";
//        builder.setStyle(new NotificationCompat.InboxStyle()
//                .addLine(messageSnippet1)           //you can add only up to 6 lines
//                .addLine(messageSnippet2)
//                .addLine(messageSnippet3)
//                .addLine(messageSnippet4));

//******************
        //this part below actually shows the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID_EXPANDABLE, builder.build());
    }
//    Zadanie 3.
//    Utwórz grupowanie powiadomień
//    Aby to osiągnąć musisz stworzyć co najmniej 3 powiadomienia. Każde z nich powinno należeć do tej samej grupy oraz mieć inne ID.
//    Grupę ustala się za pomocą funkcji .setGroup("nazwagrupy").
//    Trzecie powiadomienie niech posłuży Ci za "powiadomienie sumujące". Poinformuj o tym przez ustawienie .setGroupSummary(true).
//    Do zsumowania powiadomień w grupie wykorzystaj InBoxStyle. Dodaj .setBigContentTitle("You have 2 message") oraz .setSummaryText("user@example.com")
//    Na koniec wywołaj po kolei każde powiadomienie w 2s odstępach czasu

    public void createGroupedNotification(View view){
        //use constant ID for notification used as group summary
        int SUMMARY_ID = 50;
        String GROUP_KEY = "com.android.example.WORK_EMAIL";
        int emailNotificationId1 = 51;
        int emailNotificationId2 = 52;

        Notification newMessageNotification1 =
                new NotificationCompat.Builder(this, CHANNEL_ID_GROUPED)
                        .setSmallIcon(R.drawable.ic_mail_outline_black_24dp)
                        .setContentTitle("Check this out")
                        .setContentText("You will not believe...")
                        .setGroup(GROUP_KEY)
                        .build();

        Notification newMessageNotification2 =
                new NotificationCompat.Builder(this, CHANNEL_ID_GROUPED)
                        .setSmallIcon(R.drawable.ic_mail_outline_black_24dp)
                        .setContentTitle("Launch Party")
                        .setContentText("Please join us to celebrate the...")
                        .setGroup(GROUP_KEY)
                        .build();

        Notification summaryNotification =
                new NotificationCompat.Builder(this, CHANNEL_ID_GROUPED)
                        .setContentTitle("Check your post")
                        //set content text to support devices running API level < 24
                        .setContentText("Two new messages")
                        .setSmallIcon(R.drawable.ic_mail_outline_black_24dp)
                        //build summary info into InboxStyle template
                        .setStyle(new NotificationCompat.InboxStyle()
                                .addLine("Alex Faarborg  Check this out")
                                .addLine("Jeff Chang    Launch Party")
                                .setBigContentTitle("2 new messages")
                                .setSummaryText("user@example.com"))
                        //specify which group this notification belongs to
                        .setGroup(GROUP_KEY)
                        //set this notification as the summary for the group
                        .setGroupSummary(true)
                        .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        SystemClock.sleep(2000);
        notificationManager.notify(emailNotificationId1, newMessageNotification1);
        SystemClock.sleep(2000);
        notificationManager.notify(emailNotificationId2, newMessageNotification2);
        SystemClock.sleep(2000);
        notificationManager.notify(SUMMARY_ID, summaryNotification);
    }

//    Zadanie 5.
//    Stwórz powiadomienie na które można odpowiadać

    public void replayNotification(View view) {

        createNotificationChannel(getString(R.string.re_channel_name),CHANNEL_ID_REPLAY,NotificationManager.IMPORTANCE_MAX);

        Intent landingIntent = new Intent(this, LandingActivity.class);
        landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent landingPendingIntent = PendingIntent.getActivity(this, 0,landingIntent ,PendingIntent.FLAG_ONE_SHOT);


/*   Adding action buttons to notification
        Intent YesIntent = new Intent(this, YesActivity.class);
        YesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent YesPendingIntent = PendingIntent.getActivity(this,0,YesIntent,PendingIntent.FLAG_ONE_SHOT);
        Intent NoIntent = new Intent(this, NoActivity.class);
        NoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent NoPendingIntent = PendingIntent.getActivity(this,0,NoIntent,PendingIntent.FLAG_ONE_SHOT);
*/

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_REPLAY);
        builder.setSmallIcon(R.drawable.ic_insert_comment_black_24dp);
        builder.setContentTitle("Simple Notification");
        builder.setContentText("This is a simple notification");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);
        builder.setContentIntent(landingPendingIntent);
/*
        builder.addAction(R.drawable.ic_mms_notification,"yes",YesPendingIntent );
        builder.addAction(R.drawable.ic_mms_notification,"no",NoPendingIntent );
*/

        /*   Add a direct reply action   */
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            RemoteInput remoteInput = new RemoteInput.Builder(TXT_REPLAY).setLabel("Replay").build();

            Intent replayIntent = new Intent(this, RemoteReceiver.class);
            replayIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent replayPendingIntent = PendingIntent.getActivity(this,0,replayIntent,PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_insert_comment_black_24dp,"Replay", replayPendingIntent).addRemoteInput(remoteInput).build();

            builder.addAction(action);


        }



        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID_REPLAY,builder.build());
    }



//    Zadanie 0.
//    Od Androida N aby wyświetlić powiadomienie niezbędny jest do tego kanał, dlatego aby zobaczyć efekty przyszłej pracy zacznij od stworzenia go
//    do tego celu wykorzystaj następujące linijki
//      NotificationChannel channel = new NotificationChannel(id, name, importance);
//      NotificationManager notificationManager = getSystemService(NotificationManager.class);
//      notificationManager.createNotificationChannel(channel);

    //start this code as soon as possible. Executing this at start of the app would be the best
    private void createNotificationChannel(CharSequence n, String id, int importanc) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = n;
            String description = getString(R.string.channel_description);
            int importance = importanc;
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(description);
            //channel.setShowBadge(false);                                        //Turning off a Notification Badge
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void createNotificationChannel(CharSequence n, String id){
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        createNotificationChannel(n, id, importance);
    }

//   Zadanie 6.
//   Notification Badge jest domyślnie jest zawsze włączone choć nie zawsze jest to potrzebne.
//   Aby wyłączyć pokazywanie powiadomienia po przytrzymaniu ikonki aplikacji ustaw dla danego NotificationChannelu opcję .setShowBadge(false)
//   Możesz też w pewnym stopniu manipulować Badge np. ustawiając dla danego powiadomienia  .setNumber(int) (sprawia że jedno powiadomienie liczy się jako kilka)
//   lub .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL) (zmienia ikonkę)
//   W tym zadaniu wyłącz Notification Badge oraz ustaw wskazane metody.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
    }


}
