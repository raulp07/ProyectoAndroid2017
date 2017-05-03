package com.paucar.raul.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.paucar.raul.chat.Mensajes.Mensajeria;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    private EditText txtusuario,txtpassword;
    private Button btningresar;

    private static final String ip ="http://raulandroid.pe.hu/ArchivosPHP/Login_GETID.php?id=";
    private static final String IP_Token="http://raulandroid.pe.hu/ArchivosPHP/Token_INSERT.php";
    private RequestQueue requestQueue;
    private VolleyRP volleyRP;
    String USER,PWD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        volleyRP = VolleyRP.getInstance(this);
        requestQueue = volleyRP.getRequestQueue();

        txtusuario = (EditText)findViewById(R.id.txtusuario);
        txtpassword = (EditText)findViewById(R.id.txtpassword);
        btningresar = (Button)findViewById(R.id.btningresar);

        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar(txtusuario.getText().toString().toLowerCase(),txtpassword.getText().toString());
            }
        });

    }

    public  void verificar (String user,String passwrod){
        USER = user;
        PWD = passwrod;
        solicitudjson(ip+ user);
    }
    public void solicitudjson(String url){
        JsonObjectRequest solicitud = new JsonObjectRequest(url,null,new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                verificar_login(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,"Ocrrio un error con la coneccion",Toast.LENGTH_LONG).show();
            }
        });
        VolleyRP.addToQueue(solicitud,requestQueue,this,volleyRP);
    }

    public void verificar_login(JSONObject datos){
        //Toast.makeText(this,"los datos son "+ datos.toString(),Toast.LENGTH_LONG).show();
        try {
            String estado = datos.getString("Resultado");
            //Toast.makeText(this,estado,Toast.LENGTH_LONG).show();
            if (estado.equals("CC")){
                //Toast.makeText(this,"Contraseña y usuario correcto",Toast.LENGTH_LONG).show();
                //String usuariopassword = datos.getString("datos");
                JSONObject jsondatos = new JSONObject(datos.getString("datos"));
                String id = jsondatos.getString("id");
                String pwd = jsondatos.getString("password");
                if (id.equals(USER) && pwd.equals(PWD)){
                    String Token =FirebaseInstanceId.getInstance().getToken();
                    if (Token != null) {
                        //JSONObject js =new JSONObject(Token);
                        //String token_ = js.getString("token");
                        subirToken(Token);
                    }else Toast.makeText(this,"No se genero el token",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(this,"La contraseña es incorrecta",Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(this,usuariopassword,Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,estado,Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void subirToken(String token){
        HashMap<String,String> hashMapToekn = new HashMap<>();
        hashMapToekn.put("id",USER);
        hashMapToekn.put("token", token);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,IP_Token,new JSONObject(hashMapToekn),new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                //verificar_login(response);
                try {
                    Toast.makeText(Login.this,response.getString("resultado"),Toast.LENGTH_LONG).show();
                }catch (JSONException e){}

                Intent i = new Intent(Login.this,Mensajeria.class);
                i.putExtra("key_emisor",USER);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,"El token no se pudo subir a la base de datos",Toast.LENGTH_LONG).show();
            }
        });
        VolleyRP.addToQueue(solicitud,requestQueue,this,volleyRP);

    }
}
