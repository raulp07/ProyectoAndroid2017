package com.paucar.raul.chat.Mensajes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.paucar.raul.chat.Login;
import com.paucar.raul.chat.R;
import com.paucar.raul.chat.VolleyRP;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by raul on 10/04/2017.
 */

public class Mensajeria extends AppCompatActivity {

    public static final String MENSAJE= "MENSAJE";
    private BroadcastReceiver bR;

    private RecyclerView rv;
    private Button btEnviarMensaje;

    private EditText etEscribirmensaje,etReceptor;
    private List<MensajeDeTexto> mensajeDeTextos ;
    private MensajesAdapter adapter;
    private int TEXT_LINES =1;

    private String MENSAJE_ENVIAR = "";
    private String EMISOR="";
    private String RECEPTOR ="";

    private RequestQueue requestQueue;
    private VolleyRP volleyRP;

    private static final String IP_MENSAJE  ="http://raulandroid.pe.hu/ArchivosPHP/Enviar_Mensaje.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajeria);
        mensajeDeTextos =  new ArrayList<>();

        Intent extras = getIntent();
        Bundle bundle = extras.getExtras();
        if (bundle!= null){
            EMISOR = bundle.getString("key_emisor");
        }

        volleyRP = VolleyRP.getInstance(this);
        requestQueue = volleyRP.getRequestQueue();

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        btEnviarMensaje = (Button)findViewById(R.id.btnaceptar);
        etEscribirmensaje = (EditText)findViewById(R.id.txtmensaje);



        etReceptor = (EditText)findViewById(R.id.receptor);
        rv = (RecyclerView)findViewById(R.id.RVvista);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        rv.setLayoutManager(lm);
        /*int tipomensaje= 1;

        for (int i = 0; i < 20; i++) {

            MensajeDeTexto mensajedetextoauxiliar = new MensajeDeTexto();
            mensajedetextoauxiliar.setId(""+i);
            mensajedetextoauxiliar.setMensaje("Emisor "+ i);
            mensajedetextoauxiliar.setTipoMensaje(tipomensaje);
            mensajedetextoauxiliar.setHoraMensaje("10:3"+i);
            mensajeDeTextos.add(mensajedetextoauxiliar);
            if (tipomensaje == 1) {tipomensaje =2;}else{tipomensaje=1;}
        }*/

        /*etEscribirmensaje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etEscribirmensaje.getLayout().getLineCount() != TEXT_LINES){
                    setScrollbarChat();
                    TEXT_LINES=etEscribirmensaje.getLayout().getLineCount();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        adapter = new MensajesAdapter(mensajeDeTextos,this);

        rv.setAdapter(adapter);

        btEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MENSAJE_ENVIAR = etEscribirmensaje.getText().toString();
                RECEPTOR = etReceptor.getText().toString();
                if (!MENSAJE_ENVIAR.isEmpty() && !RECEPTOR.isEmpty()){
                    Date d= new Date();
                    String hora = d.getHours()+":"+d.getMinutes();
                    MandarMensaje();
                    CreateMensaje(MENSAJE_ENVIAR,hora,1);
                    etEscribirmensaje.setText("");
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bR= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String mensaje = intent.getStringExtra("KEY_mensaje");
                String hora = intent.getStringExtra("KEY_hora");

                Toast.makeText(Mensajeria.this,"El bR funciona",Toast.LENGTH_LONG).show();
                CreateMensaje(mensaje,hora,2);
            }
        };
    }

    private void MandarMensaje(){
        HashMap<String,String> hashMapToekn = new HashMap<>();
        hashMapToekn.put("emisor",EMISOR);
        hashMapToekn.put("receptor", RECEPTOR);
        hashMapToekn.put("mensaje", MENSAJE_ENVIAR);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,IP_MENSAJE,new JSONObject(hashMapToekn),new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                //verificar_login(response);
                try {
                    Toast.makeText(Mensajeria.this,response.getString("resultado"),Toast.LENGTH_LONG).show();
                }catch (JSONException e){}

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mensajeria.this,"Ocurrio un error",Toast.LENGTH_LONG).show();
            }
        });
        VolleyRP.addToQueue(solicitud,requestQueue,this,volleyRP);
    }

    public void CreateMensaje(String mensaje,String hora,int tipoMensaje){
        MensajeDeTexto mensajedetextoauxiliar = new MensajeDeTexto();
        mensajedetextoauxiliar.setId("0");
        mensajedetextoauxiliar.setMensaje(mensaje);
        mensajedetextoauxiliar.setTipoMensaje(tipoMensaje);
        mensajedetextoauxiliar.setHoraMensaje(hora);
        mensajeDeTextos.add(mensajedetextoauxiliar);
        adapter.notifyDataSetChanged();

        setScrollbarChat();
    }

    @Override
    protected void onPause(){
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bR);
    }

    @Override
    protected void onResume(){
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(bR,new IntentFilter(MENSAJE));
    }

    public void setScrollbarChat(){
        rv.scrollToPosition(adapter.getItemCount()-1);

    }
}
