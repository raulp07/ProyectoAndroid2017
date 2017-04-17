package com.paucar.raul.chat.Mensajes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.paucar.raul.chat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raul on 10/04/2017.
 */

public class Mensajeria extends AppCompatActivity {
    private RecyclerView rv;
    private Button btEnviarMensaje;

    private EditText etEscribirmensaje;
    private List<MensajeDeTexto> mensajeDeTextos ;
    private MensajesAdapter adapter;
    private int TEXT_LINES =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajeria);
        mensajeDeTextos =  new ArrayList<>();
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        btEnviarMensaje = (Button)findViewById(R.id.btnaceptar);
        etEscribirmensaje = (EditText)findViewById(R.id.txtmensaje);
        rv = (RecyclerView)findViewById(R.id.RVvista);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        int tipomensaje= 1;
        for (int i = 0; i < 20; i++) {

            MensajeDeTexto mensajedetextoauxiliar = new MensajeDeTexto();
            mensajedetextoauxiliar.setId(""+i);
            mensajedetextoauxiliar.setMensaje("Emisor "+ i);
            mensajedetextoauxiliar.setTipoMensaje(tipomensaje);
            mensajedetextoauxiliar.setHoraMensaje("10:3"+i);
            mensajeDeTextos.add(mensajedetextoauxiliar);
            if (tipomensaje == 1) {tipomensaje =2;}else{tipomensaje=1;}
        }

        etEscribirmensaje.addTextChangedListener(new TextWatcher() {
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
        });

        adapter = new MensajesAdapter(mensajeDeTextos,this);

        rv.setAdapter(adapter);

        btEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateMensaje(etEscribirmensaje.getText().toString());
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void CreateMensaje(String mensaje){
        MensajeDeTexto mensajedetextoauxiliar = new MensajeDeTexto();
        mensajedetextoauxiliar.setId("0");
        mensajedetextoauxiliar.setMensaje(mensaje);
        mensajedetextoauxiliar.setTipoMensaje(1);
        mensajedetextoauxiliar.setHoraMensaje("10:30");
        mensajeDeTextos.add(mensajedetextoauxiliar);
        adapter.notifyDataSetChanged();
        etEscribirmensaje.setText("");
        setScrollbarChat();
    }
    public void setScrollbarChat(){
        rv.scrollToPosition(adapter.getItemCount()-1);

    }
}
