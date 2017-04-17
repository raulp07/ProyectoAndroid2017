package com.paucar.raul.chat.Mensajes;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.paucar.raul.chat.R;

import java.util.List;

/**
 * Created by raul on 16/04/2017.
 */

public class MensajesAdapter extends RecyclerView.Adapter<MensajesAdapter.MensajesVierHolder> {

    private List<MensajeDeTexto> mensajeDeTextos;
    private Context context;
    public MensajesAdapter(List<MensajeDeTexto> mensajeDeTextos, Context context) {
        this.mensajeDeTextos = mensajeDeTextos;
        this.context= context;
    }

    @Override
    public MensajesVierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_mensajes,parent,false);
        return new MensajesVierHolder(view);
    }

    @Override
    public void onBindViewHolder(MensajesVierHolder holder, int position) {

            RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) holder.cardView.getLayoutParams();
            FrameLayout.LayoutParams fl =(FrameLayout.LayoutParams)holder.mensajeBG.getLayoutParams();
            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams)holder.txtHora.getLayoutParams();
            LinearLayout.LayoutParams llm = (LinearLayout.LayoutParams)holder.txtmensaje.getLayoutParams();

            if (mensajeDeTextos.get(position).getTipoMensaje() ==1){
                holder.mensajeBG.setBackgroundResource(R.drawable.in_message_bg);

                rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT,0);
                fl.gravity = Gravity.RIGHT;
                ll.gravity = Gravity.RIGHT;
                llm.gravity = Gravity.RIGHT;
                holder.txtmensaje.setGravity(Gravity.RIGHT);
            }else{
                holder.mensajeBG.setBackgroundResource(R.drawable.out_message_bg);
                rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,0);
                fl.gravity= Gravity.LEFT;
                ll.gravity = Gravity.LEFT;
                llm.gravity = Gravity.LEFT;
                holder.txtmensaje.setGravity(Gravity.LEFT);
            }
            holder.cardView.setLayoutParams(rl);
            holder.mensajeBG.setLayoutParams(fl);
            holder.txtmensaje.setLayoutParams(llm);
            holder.txtHora.setLayoutParams(ll);

            holder.txtmensaje.setText(mensajeDeTextos.get(position).getMensaje());
            holder.txtHora.setText(mensajeDeTextos.get(position).getHoraMensaje());
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                holder.cardView.getBackground().setAlpha(0);
            }else{
                holder.cardView.setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));
            }



    }

    @Override
    public int getItemCount() {
        return mensajeDeTextos.size();
    }

    static class MensajesVierHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView txtmensaje;
        TextView txtHora;
        LinearLayout mensajeBG;

        MensajesVierHolder(View view){
            super(view);
            cardView = (CardView)view.findViewById(R.id.cvMensaje);
            mensajeBG = (LinearLayout)view.findViewById(R.id.mensajeBG);
            txtmensaje = (TextView)view.findViewById(R.id.msTexto);
            txtHora = (TextView)view.findViewById(R.id.msHora);



        }

    }
}
