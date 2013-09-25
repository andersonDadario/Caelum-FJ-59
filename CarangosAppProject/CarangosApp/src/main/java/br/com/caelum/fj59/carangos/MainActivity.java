package br.com.caelum.fj59.carangos;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.delegate.BuscaMaisPostsDelegate;
import br.com.caelum.fj59.carangos.estado.MainActivityEstado;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPostsTask;

public class MainActivity extends Activity implements BuscaMaisPostsDelegate {
    private List<BlogPost> posts;
    private MainActivityEstado estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.posts = new ArrayList<BlogPost>();
        this.estado = MainActivityEstado.INICIO;
        this.estado.executa(this);
    }

    @Override
    public void lidaComRetorno(List<BlogPost> resultado){
        this.posts.clear();
        this.posts.addAll(resultado);

        this.estado = MainActivityEstado.PRIMEIROS_POSTS_RECEBIDOS;
        this.estado.executa(this, this.posts);
    }

    @Override
    public void lidaComErro(Exception e){
        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public CarangosApplication getCarangosApplication(){
        return (CarangosApplication) getApplication();
    }

    public void buscaPrimeirosPosts(){
        new BuscaMaisPostsTask(this).execute();
    }
    
}
