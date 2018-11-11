package dgp.ugr.granaroutes.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import dgp.ugr.granaroutes.R;
import dgp.ugr.granaroutes.actividades.ActividadPrincipal;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificacionUtils {

    private static final String ACCION_QUITAR_NOTIFICACIONES = "quitar-notificaciones";
    private static final int NOTIFICATION_ID = 2090;
    private static final int ACTION_IGNORAR_INTENT_PENDIENTE_ID = 2091;
    private static final String NOTIFICATION_CANAL_ID = "granaroutes_canal_notificaciones";

    public static void notificar(Context context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //notificarOreo(context);
        }
        else {
            String notificationTitle = context.getString(R.string.app_name);

            String notificationText = context.getString(R.string.texto_notificaciones);

            /* getSmallArtResourceIdForWeatherCondition returns the proper art to show given an ID */
            int smallArtResourceId = R.drawable.ic_notificacion;

            /*
             * NotificationCompat Builder is a very convenient way to build backward-compatible
             * notifications. In order to use it, we provide a context and specify a color for the
             * notification, a couple of different icons, the title for the notification, and
             * finally the text of the notification, which in our case in a summary of today's
             * forecast.
             */
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(NOTIFICATION_SERVICE);

            
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(context, Integer.toString(NOTIFICATION_ID))
                            .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                            .setSmallIcon(smallArtResourceId)
                            .setContentTitle(notificationTitle)
                            .setContentText(notificationText)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText))
                            .addAction(ignoreReminderAction(context))
                            .setAutoCancel(true);

            /*
             * This Intent will be triggered when the user clicks the notification. In our case,
             * we want to open Sunshine to the DetailActivity to display the newly updated weather.
             */
            Intent detailIntentForToday = new Intent(context, ActividadPrincipal.class);

            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
            taskStackBuilder.addNextIntentWithParentStack(detailIntentForToday);
            PendingIntent resultPendingIntent = taskStackBuilder
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            notificationBuilder.setContentIntent(resultPendingIntent);


            /* WEATHER_NOTIFICATION_ID allows you to update or cancel the notification later on */
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        }

    }

    public static void quitarTodasLasNotificaciones(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private static NotificationCompat.Action ignoreReminderAction(Context context){
        Intent ignoreReminderIntent = new Intent(context, ActividadPrincipal.class);
        ignoreReminderIntent.setAction(ACCION_QUITAR_NOTIFICACIONES);

        PendingIntent pendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORAR_INTENT_PENDIENTE_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action
                (R.drawable.ic_favoritos_logo,
                        context.getString(R.string.action_sign_in),
                        pendingIntent);
        return ignoreReminderAction;
    }

}
