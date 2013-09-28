package br.com.caelum.fj59.carangos.tasks;

import android.os.AsyncTask;

import br.com.caelum.fj59.carangos.application.CarangosApplication;
import br.com.caelum.fj59.carangos.gcm.InformacoesDoUsuario;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by erich on 9/12/13.
 */
public class RegistraDeviceTask extends AsyncTask<String, Void, Boolean> {

    private CarangosApplication app;

    public RegistraDeviceTask(CarangosApplication app) {
        this.app = app;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String resgistrationId = params[0];

            String email = InformacoesDoUsuario.getEmail(app);

            new WebClient("device/register/"+email+"/"+resgistrationId, this.app).post();
        } catch (Exception e) {
            MyLog.e("Problema no registro do device no server!" + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        //O CarangosApplication deverá lidar com o registro do usuário
        app.lidaComRespostaDoRegistroNoServidor(result);
    }
}