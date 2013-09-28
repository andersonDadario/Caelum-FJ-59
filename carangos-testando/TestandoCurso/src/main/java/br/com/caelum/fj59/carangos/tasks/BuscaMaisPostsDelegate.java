package br.com.caelum.fj59.carangos.tasks;

import android.content.Context;

import java.util.List;

import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

/**
 * Created by erich on 9/11/13.
 */
public interface BuscaMaisPostsDelegate {
    void lidaComRetorno(List<BlogPost> retorno);

    void lidaComErro();

    CarangosApplication getCarangosApplication();
}
