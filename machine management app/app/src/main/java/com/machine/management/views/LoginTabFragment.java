package com.machine.management.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.machine.management.R;
import com.machine.management.models.User;
import com.machine.management.utils.SessionManagment;

import java.util.HashMap;
import java.util.Map;

public class LoginTabFragment extends Fragment {

    EditText username, password;
    Button loginButton;
    private static final String urlLoginUser = "http://10.0.2.2:8090/login";
    SessionManagment sessionManagment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_login_tab, container, false);
        sessionManagment = new SessionManagment(getContext());
        checkLogin();
        username = root.findViewById(R.id.editText_username_login_fragment);
        password = root.findViewById(R.id.editText_password_login_fragment);
        loginButton = root.findViewById(R.id.button_login_fragment);
        username.setTranslationX(800);
        password.setTranslationX(800);
        loginButton.setTranslationX(800);

        username.setAlpha(0);
        password.setAlpha(0);
        loginButton.setAlpha(0);

        username.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        loginButton.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
                login(usernameText, passwordText);

            }
        });


        return root;
    }

    private void checkLogin() {
        if (sessionManagment.getLogin()) {
            Activity currentActivity = (Activity) getContext();
            Intent i = new Intent(currentActivity, NavigationDrawerActivity.class);
            currentActivity.startActivity(i);
            getActivity().finish();
        }
    }


    private void login(String usernameText, String passwordText) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlLoginUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        User user = new User(usernameText, passwordText);
                        sessionManagment.setLogin(true);
                        sessionManagment.setUsername(usernameText);
                        Activity currentActivity = (Activity) getContext();
                        Intent i = new Intent(currentActivity, NavigationDrawerActivity.class);
                        currentActivity.startActivity(i);
                        getActivity().finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Mot de pass ou username incorrect",
                                Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", usernameText);
                params.put("password", passwordText);
                return params;
            }
        };
        {

        }
        requestQueue.add(stringRequest);
    }
}

