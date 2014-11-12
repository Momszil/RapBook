package com.momszil.rapbook.Network;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.momszil.rapbook.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RestAdapter;

/**
 * Created by Vedran on 4.10.2014..
 */
public class RimujMeFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<String>> {

    private static final int THE_LOADER = 0x01;
    private String mUpit;

    @InjectView(R.id.linearLayoutRimujMe)
    LinearLayout llRimuj;
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

        // TODO ovo je bila greska
        //mUpit = getArguments().getString("WORD");

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(THE_LOADER, null, this).forceLoad();
    }

    @Override
    public Loader<List<String>> onCreateLoader(int i, Bundle bundle) {
        return new RimujMe(getActivity(), mUpit);
    }

    @Override
    public void onLoadFinished(Loader<List<String>> listLoader, List<String> strings) {
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_rimuj_me, R.id.spinner, strings);
        //spinner.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<List<String>> listLoader) {
        spinner.setAdapter(null);
    }

    private static class RimujMe extends AsyncTaskLoader<List<String>> {

        private String mUpit;
        List<String> lista;

        public RimujMe(Context context, String upit) {
            super(context);
            mUpit = upit;
        }

        @Override
        public List<String> loadInBackground() {
            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://rimuj.me").build();
            RimujMeService service = restAdapter.create(RimujMeService.class);
            String html = "x"; //service.testiramo(mUpit);
            lista = new ArrayList<String>();
            lista.add(html);
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
