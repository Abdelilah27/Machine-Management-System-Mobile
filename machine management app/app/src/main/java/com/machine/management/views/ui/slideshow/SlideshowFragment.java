package com.machine.management.views.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.machine.management.R;
import com.machine.management.adapters.MarqueAdapter;
import com.machine.management.databinding.FragmentSlideshowBinding;
import com.machine.management.models.Marque;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {
    EditText code, libelle;
    String codeText, libelleText;
    Button ajouterMarque;
    private FragmentSlideshowBinding binding;
    private static final String urlAddMarque = "http://10.0.2.2:8090/marques/save";
    private static final String getAllMarque = "http://10.0.2.2:8090/marques/all";
    private static final String deleteMarque = "http://10.0.2.2:8090/marques/";

    RecyclerView recycle_view_marque_fragment;
    MarqueAdapter marqueAdapterForRecycle;
    LinearLayoutManager linearLayoutManager;
    List<Marque> marques;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ajouterMarque = root.findViewById(R.id.button_marque_fragment);
        code = root.findViewById(R.id.editText_code_machine_fragment);
        libelle = root.findViewById(R.id.editText_libelle_machine_fragment);

        recycle_view_marque_fragment = (RecyclerView) root.findViewById(R.id.recycle_view_marque_fragment);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recycle_view_marque_fragment.setLayoutManager(linearLayoutManager);
        marques = new ArrayList<>();
        marqueAdapterForRecycle = new MarqueAdapter(marques, getContext());
        recycle_view_marque_fragment.setAdapter(marqueAdapterForRecycle);
        getMarqueList();

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final TextView id = viewHolder.itemView.findViewById(R.id.id_marque_fragment);
                int position = viewHolder.getAdapterPosition();
                int ide = Integer.parseInt((String) id.getText());
                deleteData(ide);
                recycle_view_marque_fragment.setAdapter(marqueAdapterForRecycle);
                marqueAdapterForRecycle.notifyItemRemoved(ide);
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recycle_view_marque_fragment);

        ajouterMarque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeText = code.getText().toString();
                libelleText = libelle.getText().toString();
                insertMarque(codeText, libelleText);
            }
        });

        return root;
    }


    private void deleteData(int ide) {
        StringRequest request = new StringRequest(Request.Method.DELETE, deleteMarque + ide,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Bien Supprimé",
                                Toast.LENGTH_SHORT).show();
                        marques.remove(marques.get(findPositionById(marques, ide)));
                        marqueAdapterForRecycle.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", "onErrorResponse: " + error.getMessage());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    private int findPositionById(List<Marque> marqueList, int position) {
        int identifiant = -1;
        for (Marque e : marqueList) {
            if (e.getId().equalsIgnoreCase(String.valueOf(position))) {
                identifiant = marqueList.indexOf(e);
            }
        }
        return identifiant;
    }

    private void getMarqueList() {
        StringRequest request = new StringRequest(Request.Method.GET, getAllMarque,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonarray = new JSONArray(response);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String id = jsonobject.getString("id");
                                String libelle = jsonobject.getString("libelle");
                                String code = jsonobject.getString("code");
                                Marque marque = new Marque(id, code, libelle);
                                marques.add(marque);
                                marqueAdapterForRecycle.notifyDataSetChanged();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onErrorResponse: " + error.getMessage());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void insertMarque(String codeText, String libelleText) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JSONObject postData = new JSONObject();
        try {
            postData.put("code", codeText);
            postData.put("libelle", libelleText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlAddMarque,
                postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), "Bien Ajouté",
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.toString().contains("End")) {
                    marques.clear();
                    code.setText("");
                    code.setText("");
                    getMarqueList();
                    Toast.makeText(getContext(), "Bien Ajouté",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), error.toString(),
                            Toast.LENGTH_SHORT).show();
                }
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}