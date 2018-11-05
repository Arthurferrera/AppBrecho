package br.com.senaijandira.brechobernadete.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.model.Roupas;

public class RoupasAdapter extends ArrayAdapter<Roupas> {

//    construtor do adapter
    public RoupasAdapter(Context context, ArrayList<Roupas> lstRoupas) {
        super(context,0, lstRoupas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

//        inflando o layout do item da lista de categoria
        View v = convertView;
        if (v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_favoritos, null);
        }



//        Pegando o item que será carregado
        Roupas item = getItem(position);

//        finds dos elementos do layout inflado
        TextView lbl_titulo_favorito = v.findViewById(R.id.lbl_titulo_favorito);
        TextView lbl_status_roupas_favorito = v.findViewById(R.id.lbl_status_roupa_favorito);
        TextView lbl_cor = v.findViewById(R.id.lbl_cor_favorito);
        ImageView fav = v.findViewById(R.id.img_favorito_like);

//        setando os valores de cada elemento
        lbl_titulo_favorito.setText(item.getNome());
        lbl_status_roupas_favorito.setText(item.getStatus());
//        if (item.getFavorito()){
//            fav.setImageDrawable(R.drawable.ic_favoritos);
//        } else {
//            fav.setImageDrawable(R.drawable.ic_no_favorito);
//        }
//        todo: revisar a imagem do favorito
//        lbl_cor.setBackgroundColor();

        return v;
    }
}
