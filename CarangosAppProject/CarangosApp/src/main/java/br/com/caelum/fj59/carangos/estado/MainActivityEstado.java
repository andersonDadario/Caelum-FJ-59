package br.com.caelum.fj59.carangos.estado;

import android.app.Fragment;
import android.app.FragmentTransaction;

import java.util.List;

import br.com.caelum.fj59.carangos.MainActivity;
import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.adapter.BlogPostAdapter;
import br.com.caelum.fj59.carangos.fragment.ListaDePostsFragment;
import br.com.caelum.fj59.carangos.fragment.ProgressFragment;
import br.com.caelum.fj59.carangos.modelo.BlogPost;

/**
 * Created by android3827 on 9/24/13.
 */
public enum MainActivityEstado {
    INICIO {
        @Override
        public void executa(MainActivity activity, Object...params){
            this.colocaOuBuscaFragmentNaTela(activity
                    , R.id.fragment_principal
                    , ProgressFragment.class
                    , false);

            activity.buscaPrimeirosPosts();
        }
    }, PRIMEIROS_POSTS_RECEBIDOS {
        @Override
        public void executa(MainActivity activity, Object...params){
             ListaDePostsFragment posts = (ListaDePostsFragment) colocaOuBuscaFragmentNaTela(activity
                    , R.id.fragment_principal
                    , ListaDePostsFragment.class
                    , false);
            List<BlogPost> blogPosts = (List<BlogPost>) params[0];
            BlogPostAdapter adapter = new BlogPostAdapter(activity, blogPosts);
            posts.setListAdapter(adapter);
        }
    };

    Fragment colocaOuBuscaFragmentNaTela(MainActivity activity,
                                         int id,
                                         Class <? extends Fragment> fragmentClass,
                                         boolean backstack) {

        Fragment naTela = activity.getFragmentManager()
                .findFragmentByTag(fragmentClass.getCanonicalName());

        if(naTela != null){
            return naTela;
        }

        try{
            Fragment novoFragment = fragmentClass.newInstance();
            FragmentTransaction tx = activity.getFragmentManager().beginTransaction();
            tx.replace(id, novoFragment, fragmentClass.getCanonicalName());

            if(backstack){
                tx.addToBackStack(null);
            }

            tx.commit();

            return novoFragment;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void executa (MainActivity actvity, Object...params);
}
