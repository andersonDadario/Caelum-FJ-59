package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;
import android.os.Build;

import java.util.List;

import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.converter.BlogPostConverter;
import br.com.caelum.fj59.carangos.evento.EventoBlogPostsRecebidos;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.webservice.Pagina;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by erich on 7/16/13.
 */
public class BuscaMaisPostsTask extends AsyncTask<Pagina, Void, List<BlogPost>> {
    private Exception erro;
    private CarangosApplication application;

    public BuscaMaisPostsTask(CarangosApplication application) {
        this.application = application;
        this.application.registra(this);
    }

    @Override
    protected List<BlogPost> doInBackground(Pagina... paginas) {
        try {
            Pagina paginaParaBuscar = paginas.length > 1? paginas[0] : new Pagina();

            String jsonDeResposta = new WebClient("post/list?" + paginaParaBuscar, this.application).get();

            List<BlogPost> postsRecebidos = new BlogPostConverter().toPosts(jsonDeResposta);

            return postsRecebidos;
        } catch (Exception e) {
            this.erro = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<BlogPost> retorno) {
        this.application.desregistra(this);

        MyLog.i("RETORNO OBTIDO! E AGORA?" + retorno);

        if (retorno!=null) {
            EventoBlogPostsRecebidos.notificaPostsRecebidos(this.application, retorno, true);
        } else {
            EventoBlogPostsRecebidos.notificaPostsRecebidos(this.application, retorno, false);
        }
    }

    public void executeCustomizado(Pagina... paginas) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, paginas);
        } else {
            this.execute(paginas);
        }
    }
}
