package dgp.ugr.granaroutes.utils;

import android.content.Context;

/**
 * Created by Guillermo on 25/08/2017.
 */

public class NotificacionTareas {
    private static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";
    private static final String ACTION_VIEW_ARTICLES = "view-articles";

    public static void executeTask(Context context, String action) {
        if (ACTION_DISMISS_NOTIFICATION.equals(action)) {
            NotificacionUtils.quitarTodasLasNotificaciones(context);
        }

    }

}
