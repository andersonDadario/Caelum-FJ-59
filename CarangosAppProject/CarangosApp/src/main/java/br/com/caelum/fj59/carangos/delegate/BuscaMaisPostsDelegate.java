package br.com.caelum.fj59.carangos.delegate;

import java.util.List;

import br.com.caelum.fj59.carangos.modelo.BlogPost;

/**
 * Created by android3827 on 9/23/13.
 */
public interface BuscaMaisPostsDelegate {
    void lidaComRetorno(List<BlogPost> retorno);
    void lidaComErro(Exception e);
}
