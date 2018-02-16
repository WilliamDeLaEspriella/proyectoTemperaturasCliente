package edu.temp.udc.proyectotempe.services;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import edu.temp.udc.proyectotempe.ApiRest.model.UserDevice;
import edu.temp.udc.proyectotempe.R;
import edu.temp.udc.proyectotempe.Vista.FullscreenActivity;
import edu.temp.udc.proyectotempe.tempo.Token;

import static android.R.attr.data;
import static android.R.attr.foreground;

public class FcmMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            //sendNotificationData(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle(), remoteMessage.getData());
            String id = "", message = "", title = "";

            JSONObject jsonObject = new JSONObject(remoteMessage.getData());
            verficarTempe(jsonObject);
            Token.getInstance().noty(jsonObject);

            ///  Toast.makeText(this,id+" "+message,Toast.LENGTH_SHORT).show();

        } else if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
        }
    }

    private void sendNotification(String messageBody, String title2) {


      /*  Intent intent = new Intent(this, MainNavigationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);*/
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.setBigContentTitle(title2);
        bigText.bigText(messageBody);
        bigText.setSummaryText(this.getResources().getString(R.string.app_name));
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title2);
        notificationBuilder.setContentText(messageBody);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(soundUri);
        notificationBuilder.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_ac_unit_blue_400_24dp));
       notificationBuilder.setSmallIcon(R.drawable.ic_notifications_white_24dp);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setStyle(bigText);
        // Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // v.vibrate(1000);
        //notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    public UserDevice buscarUser(String device) {

        for (UserDevice userDevice : Token.getInstance().getUserDevices()) {
            if (userDevice.getDevice().equals(device)) {
                return userDevice;
            }
        }
        return null;
    }

    public void verficarTempe(JSONObject object) {
        String message = "";
        String id = "";
        try {
            id = object.getString("data");
            message = object.getString("usuario");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        UserDevice user = buscarUser(message);
        double tempe=Double.parseDouble(id);
        double tempeUser= Double.parseDouble(user.getDato());
        if (user != null) {
            if (tempe>=38 && tempeUser<38){
                Intent i = new Intent(this, FullscreenActivity.class);

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("data",id);
                i.putExtra("sintomas","Temperatura "+user.getNombre()+" se ha elevado!");

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
                //startActivity(i);
            }else if (tempe<38 && tempeUser>=38){

                sendNotification("Temperatura normalizada","Actualizacion de estado");
            }
        }

    }

  /*  private void sendNotificationData(String messageBody, String title2, Map<String, String> data) {
        String id = "", message = "", title = "";
        try {
            JSONObject jsonObject = new JSONObject(data);
            id = jsonObject.getString("type");
            message = jsonObject.getString("message");

        } catch (JSONException e) {
            //            }
        }
        if (id.equalsIgnoreCase("audiencia")) {
            getNotiAud();
        } else if (id.equalsIgnoreCase("archivo")) {
            getNotiPro();
        }

        Intent intent = new Intent(this, MainNavigationActivity.class);
        intent.putExtra("type", id);
        intent.putExtra("message", message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.setBigContentTitle(title2);
        bigText.bigText(messageBody);
        bigText.setSummaryText("Portal Juridico");
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title2);
        notificationBuilder.setContentText(messageBody);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(soundUri);
        notificationBuilder.setSmallIcon(R.drawable.logonoti);
        notificationBuilder.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.logopng));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setStyle(bigText);
        // Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        // v.vibrate(1000);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

  */
}