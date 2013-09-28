package br.com.caelum.fj59.carangos;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.android.gcm.GCMBaseIntentService;

import java.util.List;

import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.gcm.Constantes;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.tasks.RegistraDeviceTask;

/**
 * Created by erich on 9/12/13.
 */
public class GCMIntentService extends GCMBaseIntentService {



    public GCMIntentService() {
        super(Constantes.CGM_SERVER_ID);
    }

    @Override
    protected void onError(Context ctx, String message) {
        MyLog.i("ERROR:" + message);
    }

    private static boolean isApplicationNotRunning(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onMessage(Context ctx, Intent intent) {
        MyLog.i("CHEGOU AI SIM HEIN!!!!"+intent.getExtras());

        MyLog.i("PASSOU AQUI $$$$$$$$$$$$$$$$!"+ ctx.getClass());
//        boolean naoRodando = isApplicationNotRunning(getApplication());
//
//        if (naoRodando) {
//            Intent notificationIntent = new Intent(getApplication(), LeilaoActivity.class);
//
//            PendingIntent contentIntent = PendingIntent.getActivity(getApplication(), 0,
//                    notificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            notificationIntent.putExtra("idDaNotificacao", Constantes.ID_NOTIFICACAO);
//
//            Notification notificacao = new NotificationCompat.Builder(getApplication())
//                    .setContentTitle("Um novo leilao comecou!")
//                    .setContentText("Leilao do " + intent.getExtras().getSerializable("message"))
//                    .setSmallIcon(R.drawable.ic_launcher)
//                    .setContentIntent(contentIntent)
////                .setLargeIcon(BitmapFactory.decodeResource(R.drawable.loading))
//                    .build();
//
//            NotificationManager manager =
//                    (NotificationManager)
//                            getApplication().getSystemService
//                                    (Context.NOTIFICATION_SERVICE);
//
//            manager.notify(Constantes.ID_NOTIFICACAO, notificacao);
//        } else {
//            EventoLeilaoIniciado.notificaSucesso(getApplication(), (String)intent.getSerializableExtra("message"));
//        }


    }

    @Override
    protected void onRegistered(Context ctx, String message) {
        MyLog.i("REGISTERED:"+message);

        CarangosApplication app = (CarangosApplication) getApplication();

        new RegistraDeviceTask(app).execute(message);
    }

    @Override
    protected void onUnregistered(Context ctx, String message) {
        MyLog.i("UNREGISTERED:"+message);
    }
}