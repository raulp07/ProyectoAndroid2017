package com.paucar.raul.chat.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.paucar.raul.chat.Mensajes.Mensajeria;
import com.paucar.raul.chat.R;

import java.util.Random;

/**
 * Created by Usuario on 1/05/2017.
 */

public class FireBaseServiceMensajes extends FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String mensaje = remoteMessage.getData().get("mensaje");
        String hora = remoteMessage.getData().get("hora");
        String cabezera = remoteMessage.getData().get("cabezera");
        String cuerpo = remoteMessage.getData().get("cuerpo");

        Mensaje(mensaje,hora);
        showNotificacion(cabezera,cuerpo);
    }

    private void Mensaje(String mensaje,String hora){
        Intent intent= new Intent(Mensajeria.MENSAJE);
        intent.putExtra("KEY_mensaje",mensaje);
        intent.putExtra("KEY_hora",hora);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }


    private void showNotificacion(String cabezera, String cuerpo){
        Intent i = new Intent(this,Mensajeria.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true);
        builder.setContentTitle(cabezera);
        builder.setContentText(cuerpo);
        builder.setSound(sound);
        builder.setSmallIcon(R.drawable.ic_action_key);
        builder.setTicker(cuerpo);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Random random= new Random();

        notificationManager.notify(random.nextInt(),builder.build());

    }
}
