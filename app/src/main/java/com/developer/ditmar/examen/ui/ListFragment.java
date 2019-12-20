package com.developer.ditmar.examen.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.developer.ditmar.examen.Data.StructList;
import com.developer.ditmar.examen.Data.Utils;
import com.developer.ditmar.examen.R;
import com.developer.ditmar.examen.adapters.CustomAdapter;
import com.loopj.android.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private View root;
    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_list, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final ListView listView = root.findViewById(R.id.listview);
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<StructList> listdata = new ArrayList<>();
        client.get(Utils.SERVICE, new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        StructList item = new StructList();
                        item.setId(obj.getString("_id"));
                        item.setDescription(obj.getString("description"));
                        item.setDirections(obj.getString("directions"));
                        item.setDisclaimer(obj.getString("disclaimer"));
                        item.setLat(obj.getDouble("lat"));
                        item.setListPrice(obj.getDouble("listPrice"));
                        item.setLng(obj.getDouble("listPrice"));
                        item.setPrimary_photo(obj.getString("primary_photo"));
                        listdata.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                CustomAdapter adapter = new CustomAdapter(ListFragment.this.getContext(), listdata);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

}
