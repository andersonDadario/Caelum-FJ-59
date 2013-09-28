package br.com.caelum.fj59.carangostunning;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import br.com.caelum.fj59.carangos.modelo.BlogPost;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;

public class TunningActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tunning);

        BlogPost post;

        PullToRefreshAttacher attacher;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tunning, menu);
        return true;
    }
    
}
