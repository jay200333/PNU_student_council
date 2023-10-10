package com.example.myapplication;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class  LoginRequest extends StringRequest{
    final static private String URL = "http://mmuyaho.dothome.co.kr/UserLogin.php";
    private Map<String, String> parameters;

    public LoginRequest(String userID, String userPassword ,Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);

    }

   // public LoginRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
     //   super(method, url, listener, errorListener);
   // }

    @Override
    public Map<String, String> getParams() {

        return parameters;
    }

}
