package com.machine.management.views.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.machine.management.adapters.MachineAdapter;
import com.machine.management.databinding.FragmentGalleryBinding;
import com.machine.management.models.Machine;
import com.machine.management.models.Marque;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    Button ajouterMachine;
    EditText reference, prix, dateAchat;
    String referenceText, prixText, dateAchatText, idMarqueText;
    Spinner marqueSpinner;
    ArrayList<String> marqueList = new ArrayList<>();
    ArrayAdapter<String> marqueAdapter;
    List<String> sIds = new ArrayList<String>();
    private static final String urlGetMarque = "http://10.0.2.2:8090/marques/all";
    private static final String urlGetMachines = "http://10.0.2.2:8090/machines/all";
    private static final String urlAddMachine = "http://10.0.2.2:8090/machines/save";
    private static final String deleteMachine = "http://10.0.2.2:8090/machines/";

    private Marque MarqueByID;


    RecyclerView recycle_view_machine_fragment;
    MachineAdapter machineAdapter;
    LinearLayoutManager linearLayoutManager;
    List<Machine> machines;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ajouterMachine = root.findViewById(R.id.button_machine_fragment);
        reference = root.findViewById(R.id.editText_reference_machine_fragment);
        prix = root.findViewById(R.id.editText_prix_machine_fragment);
        dateAchat = root.findViewById(R.id.editText_date_machine_fragment);
        marqueSpinner = (Spinner) root.findViewById(R.id.spinner_marque_machine_fragment);
        getAllMarqueInSpinner();

        marqueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String idd = sIds.get(pos);
                idMarqueText = idd;
                findMarqueByID(idd);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                Log.i("Message", "Nothing is selected");

            }
        });


        ajouterMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                referenceText = reference.getText().toString();
                prixText = prix.getText().toString();
                dateAchatText = dateAchat.getText().toString();
                insert(referenceText, prixText, dateAchatText, MarqueByID);
            }
        });

        recycle_view_machine_fragment = (RecyclerView) root.findViewById(R.id.recycle_view_machine_fragment);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recycle_view_machine_fragment.setLayoutManager(linearLayoutManager);
        machines = new ArrayList<>();
        machineAdapter = new MachineAdapter(machines, getContext());
        recycle_view_machine_fragment.setAdapter(machineAdapter);
        getMachineList();

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final TextView id = viewHolder.itemView.findViewById(R.id.id_machine_fragment);
                int position = viewHolder.getAdapterPosition();
                int ide = Integer.parseInt((String) id.getText());
                deleteData(ide);
                recycle_view_machine_fragment.setAdapter(machineAdapter);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recycle_view_machine_fragment);
        return root;
    }

    private void deleteData(int ide) {
        StringRequest request = new StringRequest(Request.Method.DELETE, deleteMachine + ide,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Bien Supprimé",
                                Toast.LENGTH_SHORT).show();
                        machines.remove(machines.get(findPositionById(machines, ide)));
                        machineAdapter.notifyDataSetChanged();

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

    private int findPositionById(List<Machine> machineList, int position) {
        int identifiant = -1;
        for (Machine e : machineList) {
            if (e.getId().equalsIgnoreCase(String.valueOf(position))) {
                identifiant = machineList.indexOf(e);
            }
        }
        return identifiant;
    }

    private void getMachineList() {
        StringRequest request = new StringRequest(Request.Method.GET, urlGetMachines,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonarray = new JSONArray(response);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String id = jsonobject.getString("id");
                                String ref = jsonobject.getString("reference");
                                String dateAchat = jsonobject.getString("dateAchat");
                                String prix = jsonobject.getString("prix");
                                String marqueArray = jsonobject.getString("marque");
                                Log.d("A", "onResponse: " + id + ref + dateAchat + prix);

                                JSONObject jsonobjectMarque = new JSONObject(marqueArray);
                                Log.d("c", "onResponse: " + jsonobjectMarque);
                                String idMarque = jsonobjectMarque.getString("id");
                                String codeMarque = jsonobjectMarque.getString("code");
                                String libelleMarque = jsonobjectMarque.getString("libelle");
                                Log.d("B", "onResponse: " + id + codeMarque + libelleMarque);
                                Marque marque = new Marque(idMarque, codeMarque, libelleMarque);
                                Machine machine = new Machine(id, ref,
                                        prix,
                                        dateAchat,
                                        marque);
                                machines.add(machine);
                                machineAdapter.notifyDataSetChanged();
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

    private Marque findMarqueByID(String idd) {
        final String urlFindMarque = "http://10.0.2.2:8090/marques/" + idd;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlFindMarque,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject Jarray = new JSONObject(response);
                            String code = Jarray.getString("code");
                            String libelle = Jarray.getString("libelle");
                            MarqueByID = new Marque(idd, code, libelle);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Log.d("TAG", "onErrorResponse: " + error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        return MarqueByID;
    }

    private void insert(String referenceText, String prixText, String dateAchatText,
                        Marque marque) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject postData = new JSONObject();
        JSONObject postDataMarque = new JSONObject();
        try {
            postDataMarque.put("id", marque.getId());
            postDataMarque.put("code", marque.getCode());
            postDataMarque.put("libelle", marque.getLibelle());
            postData.put("dateAchat", dateAchatText);
            postData.put("prix", prixText);
            postData.put("reference", referenceText);
            postData.put("marque", postDataMarque);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlAddMachine,
                postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), "Bien Ajouté",
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", error.toString());
                if (error.toString().contains("End of input at character 0")) {
                    machines.clear();
                    reference.setText("");
                    prix.setText("");
                    dateAchat.setText("");
                    Toast.makeText(getContext(), "Bien Ajouté",
                            Toast.LENGTH_SHORT).show();
                    getMachineList();
                } else {
                    Toast.makeText(getContext(), error.toString(),
                            Toast.LENGTH_SHORT).show();
                }
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    private void getAllMarqueInSpinner() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlGetMarque,
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
                                sIds.add(id);
                                marqueList.add(libelle);
                                marqueAdapter = new ArrayAdapter<>(getContext(),
                                        android.R.layout.simple_spinner_item, marqueList);
                                marqueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                marqueSpinner.setAdapter(marqueAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Log.d("TAG", "onErrorResponse: " + error.getMessage());
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}