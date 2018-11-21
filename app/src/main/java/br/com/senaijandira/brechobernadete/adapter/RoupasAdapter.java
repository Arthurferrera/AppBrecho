package br.com.senaijandira.brechobernadete.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.dao.RoupasDAO;
import br.com.senaijandira.brechobernadete.model.Roupas;

public class RoupasAdapter extends ArrayAdapter<Roupas> {

    ArrayList<Roupas> itens;
    ArrayList<Roupas> itens_exibicao;
    ImageView fav;
    RoupasDAO dao;

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

        dao = RoupasDAO.getInstance();

//        Pegando o item que será carregado
        final Roupas item = getItem(position);

//        finds dos elementos do layout inflado
        TextView lbl_titulo_favorito = v.findViewById(R.id.lbl_titulo_favorito);
        TextView lbl_status_roupas_favorito = v.findViewById(R.id.lbl_status_roupa_favorito);
        TextView lbl_cor = v.findViewById(R.id.lbl_cor_favorito);
        fav = v.findViewById(R.id.img_favorito_like);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getFavorito() == 1){
                    fav.setImageResource(R.drawable.ic_no_favorito);
//                    Toast.makeText(getContext(), "favorito", Toast.LENGTH_SHORT).show();
                } else {
                    fav.setImageResource(R.drawable.ic_favoritos);
//                    Toast.makeText(getContext(), "não favorito", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(getContext(), item.getId()+" - "+item.getCor()+" - "+item.getFavorito(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), item.getCor()+"", Toast.LENGTH_SHORT).show();
                Boolean sucesso = setarFavorito(item);
                if (sucesso){
                    Toast.makeText(getContext(), "opaopa", Toast.LENGTH_SHORT).show();
                 }
//                fav.setVisibility(View.GONE);
            }
        });

//        setando os valores de cada elemento
        lbl_titulo_favorito.setText(item.getNome());
        lbl_status_roupas_favorito.setText(item.getStatus());
        lbl_cor.setBackgroundTintList(ColorStateList.valueOf(item.getCor()));
        if (item.getFavorito() == 0){
            fav.setImageResource(R.drawable.ic_no_favorito);
        } else {
            fav.setImageResource(R.drawable.ic_favoritos);
        }
//        todo: revisar a imagem do favorito

        return v;
    }

    public boolean setarFavorito(Roupas roupa){
        Boolean teste = dao.atualizarFavorito(getContext(), roupa.getId(), roupa.getFavorito());
        Log.d("teste", roupa.getId()+"");
        return teste;
    }
}
