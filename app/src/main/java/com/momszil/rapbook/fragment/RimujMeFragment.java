package com.momszil.rapbook.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.momszil.rapbook.R;
import com.momszil.rapbook.network.RimujMeService;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RestAdapter;

/**
 * Created by Vedran on 4.10.2014..
 */
public class RimujMeFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<String>> {

    private static final int THE_LOADER = 0x01;
    private String mUpit;

    @InjectView(R.id.spinner)
    Spinner spinner;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_rimuj_me, container, false);

        ButterKnife.inject(this, rootView);

        mUpit = getArguments().getString("WORD");

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(THE_LOADER, null, this).forceLoad();
    }

    @Override
    public Loader<List<String>> onCreateLoader(int i, Bundle bundle) {
        return new Rimuj(getActivity(), mUpit);
    }

    @Override
    public void onLoadFinished(Loader<List<String>> listLoader, List<String> strings) {
        Log.v("RIJECI", " " + strings.toString() + " KOLICINA: " + strings.size());
        if (strings.size() > 0) {
            spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, strings);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);
        } else {
            Toast.makeText(getActivity(), "NEMA PRONALASKA", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<String>> listLoader) {
        spinner.setAdapter(null);
    }

    private static class Rimuj extends AsyncTaskLoader<List<String>> {

        Scanner s = null;
        private String mUpit;
        String html = "";
        List<String> lista;

        public Rimuj(Context context, String upit) {
            super(context);
            mUpit = upit;
        }

        @Override
        public List<String> loadInBackground() {
            RestAdapter restAdapter = new RestAdapter.Builder().
                    setLogLevel(RestAdapter.LogLevel.BASIC).
                    setEndpoint("http://rimuj.me").
                    build();
            RimujMeService service = restAdapter.create(RimujMeService.class);

            try {
                s = new Scanner(service.testiramo(mUpit).getBody().in()).useDelimiter("\\A");
            } catch (IOException e) {
                e.printStackTrace();
            }
            html = s.hasNext() ? s.next() : "";

            lista = new ArrayList<String>();
            int start = html.indexOf("<table id=\"searchResults\">");
            if (start > 0) {
                html = html.substring(start, html.indexOf("</table>", start) + 8);
                html = Jsoup.parseBodyFragment(html).getElementById("searchResults").getElementsByTag("td").text() + " zadnjica";
                //Document doc = Jsoup.parseBodyFragment(html);
                //Elements table = doc.getElementById("searchResults").getElementsByTag("td");
                //html = table.text() + " zadnjica";

                for (String rimovana : html.split(" × ")) {
                    lista.add(rimovana);
                }
                lista.remove(lista.size()-1);
            }

            return lista;
        }

        /**
         * Handles a request to start the Loader.
         */
        @Override
        protected void onStartLoading() {
            if (lista != null) {
                // If we currently have a result available, deliver it
                // immediately.
                deliverResult(lista);
            }

            if (takeContentChanged() || lista == null) {
                // If the data has changed since the last time it was loaded
                // or is not currently available, start a load.
                forceLoad();
            }

        }

        @Override
        public void deliverResult(List<String> lista) {

            this.lista = lista;

            if (isStarted()) {
                // If the Loader is currently started, we can immediately
                // deliver its results.
                super.deliverResult(lista);
            }

        }
    }
}
