package com.example.sninkovic_ns.vezba1.async;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.sninkovic_ns.vezba1.tools.ReviewerTools;

/**
 * Created by SNinkovic_ns on 18.3.2017.
 */

public class SimpleSyncTask extends AsyncTask<Integer, Void, Integer> {

    private Context context;

    public SimpleSyncTask(Context context) {this.context = context;}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Integer kojiNet) {
        Toast.makeText(context, "Koristite "+ ReviewerTools.getConnectionType(kojiNet), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return params[0];
    }
}
