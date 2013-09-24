package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;

import java.util.List;

import br.com.caelum.fj59.carangos.converter.BlogPostConverter;
import br.com.caelum.fj59.carangos.delegate.BuscaMaisPostsDelegate;
import br.com.caelum.fj59.carangos.modelo.BlogPost;
import br.com.caelum.fj59.carangos.webservice.Pagina;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by android3827 on 9/23/13.
 */
public class BuscaMaisPostsTask extends AsyncTask <Pagina, Void, List<BlogPost>> {
    private BuscaMaisPostsDelegate delegate;
    private Exception erro;

    public BuscaMaisPostsTask(BuscaMaisPostsDelegate delegate){
        this.delegate = delegate;
    }

    @Override
    protected List <BlogPost> doInBackground (Pagina...paginas){
        try{
            Pagina paginaParaBuscar = (paginas.length > 1)? paginas[0] : new Pagina();
            String jsonDeResposta = new WebClient("post/list?" + paginaParaBuscar).get();

            List<BlogPost> postsRecebidos = new BlogPostConverter().toPosts(jsonDeResposta);

            return postsRecebidos;
        } catch (Exception e){
            this.erro = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<BlogPost> retorno){
        if(retorno != null){
            this.delegate.lidaComRetorno(retorno);
        } else {
            this.delegate.lidaComErro(this.erro);
        }
    }
}
