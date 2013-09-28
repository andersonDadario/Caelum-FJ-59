package br.com.caelum.fj59.carangos.activity;

import android.app.Application;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity.*;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.adapter.BlogPostAdapter;
import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.evento.EventoBlogPostsRecebidos;
import br.com.caelum.fj59.carangos.fragments.ListaDePostsFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPostsDelegate;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPostsTask;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class MainActivity extends Activity implements BuscaMaisPostsDelegate {
    private EstadoMainActivity estado;
    private  static  final String ESTADO_ATUAL = "ESTADO_ATUAL";
    private EventoBlogPostsRecebidos observador;
    private PullToRefreshAttacher attacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.attacher = PullToRefreshAttacher.get(this);

        this.observador = EventoBlogPostsRecebidos.registraObservador(this);

        this.estado = INICIO;
    }

    @Override
    protected void onResume() {
        MyLog.i("EXECUTANDO ESTADO:" + this.estado);
        super.onResume();
        this.estado.executa(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        MyLog.i("RESTAURANDO ESTADO!");
        super.onRestoreInstanceState(savedInstanceState);
        this.estado = (EstadoMainActivity) savedInstanceState.getSerializable(ESTADO_ATUAL);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyLog.i("SALVANDO ESTADO!");

        outState.putSerializable(ESTADO_ATUAL, this.estado);
    }

    @Override
    public void lidaComRetorno(List<BlogPost> resultado) {
        CarangosApplication application = (CarangosApplication) getApplication();

        application.getPosts().clear();
        application.getPosts().addAll(resultado);

        this.estado = PRIMEIROS_POSTS_RECEBIDOS;
        this.estado.executa(this);
        this.attacher.setRefreshComplete();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem compras = menu.add("Compras");
        compras.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        compras.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setData(Uri.parse("busao://localhost//acao/customizada/*"));

                startActivity(intent);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void lidaComErro() {
        //TODO
    }

    @Override
    public CarangosApplication getCarangosApplication() {
        return (CarangosApplication) getApplication();
    }

    public List<BlogPost> getPosts() {
        return ((CarangosApplication) getApplication()).getPosts();
    }

    public void buscaPrimeirosPosts() {
        new BuscaMaisPostsTask(getCarangosApplication()).executeCustomizado();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.observador.desregistra(getCarangosApplication());
    }

    public PullToRefreshAttacher getAttacher() {
        return attacher;
    }

    public void alteraEstadoPara(EstadoMainActivity estado ) {
        this.estado = estado;
        this.estado.executa(this);
    }
}
