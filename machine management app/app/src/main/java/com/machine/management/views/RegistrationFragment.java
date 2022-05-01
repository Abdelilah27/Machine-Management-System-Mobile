package com.machine.management.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.machine.management.R;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationFragment extends Fragment {

    EditText username, password, verificationPassword;
    Button RegistrationButton;
    private static final String urlAddUser = "http://10.0.2.2:8090/users/";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_registration, container, false);

        username = root.findViewById(R.id.editText_username_registration_fragment);
        password = root.findViewById(R.id.editText_password_registration_fragment);
        verificationPassword = root.findViewById(R.id.editText_password_verification_registration_fragment);
        RegistrationButton = root.findViewById(R.id.button_registration_fragment);

        username.setTranslationX(800);
        password.setTranslationX(800);
        verificationPassword.setTranslationX(800);
        RegistrationButton.setTranslationX(800);

        username.setAlpha(0);
        password.setAlpha(0);
        verificationPassword.setAlpha(0);
        RegistrationButton.setAlpha(0);


        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        verificationPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        RegistrationButton.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        RegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
                String verificationPasswordText = verificationPassword.getText().toString();
                if (usernameText.isEmpty() || passwordText.isEmpty() || verificationPasswordText.isEmpty()) {
                    Toast.makeText(getContext(), "Veuillez remplir tout les champs",
                            Toast.LENGTH_SHORT).show();
                } else if (passwordText.length() < 6 || verificationPasswordText.length() < 6) {
                    Toast.makeText(getContext(), "Veuillez entrez un mot de pass contient 6 " +
                                    "caracteres au minimum"
                            ,
                            Toast.LENGTH_SHORT).show();
                } else if (!passwordText.equals(verificationPasswordText)) {
                    Toast.makeText(getContext(), "Les mots de pass sont differents ",
                            Toast.LENGTH_SHORT).show();
                } else {
                    insertUser(usernameText, passwordText);
                }
            }
        });


        return root;
    }


    private void insertUser(String usernameText, String passwordText) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JSONObject postData = new JSONObject();
        try {
            postData.put("username", usernameText);
            postData.put("password", passwordText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlAddUser,
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
                if (error.toString().contains("com.android.volley.ParseError: org.json.JSONException: " +
                        "End of input at character 0 of")) {
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
}