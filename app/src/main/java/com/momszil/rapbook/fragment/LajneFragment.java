package com.momszil.rapbook.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.momszil.rapbook.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * Created by Vedran on 21.11.2014..
 */
public class LajneFragment extends Fragment {

    private ArrayList<String> mResults;
    OnLinijaSelectedListener mListener;

    @InjectView(R.id.list_lajne)
    ListView listLajne;

    @OnItemClick(R.id.list_lajne)
    public void rimujMe(AdapterView<?> aV, int position) {
        //View wantedView = listLajne.getChildAt(position - listLajne.getFirstVisiblePosition() + listLajne.getHeaderViewsCount());
        mListener.onLinijaSelected((String) aV.getItemAtPosition(position));
    }

    public interface OnLinijaSelectedListener {
        public void onLinijaSelected(String linijaString);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mListener = (OnLinijaSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnLinijaSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_lajne, container, false);

        ButterKnife.inject(this, rootView);

        mResults = getArguments().getStringArrayList("RESULTS");
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mResults);
        listLajne.setAdapter(listAdapter);
        for (int i = 0; i < mResults.size();i++ ) {
            Log.d("Speech", "result=" + mResults.get(i));
        }

        return rootView;
    }

}
