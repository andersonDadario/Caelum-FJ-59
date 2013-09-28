package br.com.caelum.fj59.carangos.evento;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPostsDelegate;

/**
 * Created by erich on 9/12/13.
 */
public class EventoBlogPostsRecebidos extends BroadcastReceiver {
    private static final String BLOGPOSTS_RECEBIDOS = "Posts foram recebidos";
    private static final String RESULTADO_POSTS = "Posts";
    private static final String SUCESSO = "Sucesso";
    private BuscaMaisPostsDelegate delegate;

    @Override
    public void onReceive(Context context, Intent intent) {
        MyLog.i("RECEBI O EVENTO!!! DEU CERTO?" +intent.getBooleanExtra(SUCESSO, false));

        this.delegate.lidaComRetorno((List<BlogPost>) intent.getSerializableExtra(RESULTADO_POSTS));
    }

    public static void notificaPostsRecebidos(Context context, List<BlogPost> resultado, boolean sucesso) {
        Intent intent = new Intent(BLOGPOSTS_RECEBIDOS);

        intent.putExtra(RESULTADO_POSTS, (ArrayList<BlogPost>)resultado);
        intent.putExtra(SUCESSO, sucesso);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static EventoBlogPostsRecebidos registraObservador(BuscaMaisPostsDelegate delegate){
        EventoBlogPostsRecebidos receiver = new EventoBlogPostsRecebidos();
        receiver.delegate = delegate;

        LocalBroadcastManager.getInstance(delegate.getCarangosApplication())
                .registerReceiver(receiver, new IntentFilter(BLOGPOSTS_RECEBIDOS));

        return receiver;
    }

    public void desregistra(CarangosApplication application) {
        LocalBroadcastManager.getInstance(application).unregisterReceiver(this);
    }

}
