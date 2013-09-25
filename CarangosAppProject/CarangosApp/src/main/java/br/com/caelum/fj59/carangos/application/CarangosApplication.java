package br.com.caelum.fj59.carangos.application;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android3827 on 9/24/13.
 */
public class CarangosApplication extends Application {
    private List<AsyncTask<?,?,?>> tasks = new ArrayList<AsyncTask<?,?,?>>();

    public void onTerminate(){
        super.onTerminate();

        for (AsyncTask task : this.tasks){
            this.desregistra(task);
        }
    }

    public void registra(AsyncTask<?,?,?> task){
        tasks.add(task);
    }

    public void desregistra(AsyncTask<?,?,?> task){
        tasks.remove(task);
    }
}
