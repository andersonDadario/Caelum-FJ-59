package br.com.caelum.fj59.carangos.application;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.android.gcm.GCMRegistrar;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.gcm.Constantes;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

/**
 * Created by erich on 9/12/13.
 */
public class CarangosApplication extends Application {
    private List<BlogPost> posts;
    private List<AsyncTask<?, ?, ?>> tasks;

    private static final String REGISTERED = "registeredInGCM";
    private SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        this.posts = new ArrayList<BlogPost>();
        this.tasks = new ArrayList<AsyncTask<?, ?, ?>>();

        preferences = getSharedPreferences("configs", Activity.MODE_PRIVATE);

//        this.initializeGCM();

    }

    public void initializeGCM() {
        if(usuarioNaoRegistrado()) {

            GCMRegistrar.checkDevice(this);
            GCMRegistrar.checkManifest(this);

            String registrationId = GCMRegistrar.getRegistrationId(this);

            if (registrationId.equals("")) {
                GCMRegistrar.register(this, Constantes.CGM_SERVER_ID);
                registrationId = GCMRegistrar.getRegistrationId(this);

                MyLog.i("ENVIANDO BROADCAST COM O ID " + registrationId);
            } else {
                MyLog.i("Device ja registrado");
            }
        }
    }

    private boolean usuarioNaoRegistrado() {
        return !preferences.getBoolean(REGISTERED, false);
    }

    public void lidaComRespostaDoRegistroNoServidor(Boolean registroOk) {
        if (registroOk) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(REGISTERED, true);
            editor.commit();
        }
    }

    public void registra(AsyncTask<?, ?, ?> task) {
        this.tasks.add(task);
    }

    public void desregistra(AsyncTask<?, ?, ?> task) {
        this.tasks.remove(task);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (AsyncTask task : this.tasks) {
            task.cancel(true);
        }
    }

    public List<BlogPost> getPosts() {
        return posts;
    }

}
