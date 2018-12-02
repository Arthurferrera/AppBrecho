package br.com.senaijandira.brechobernadete.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

//    DECLARANDO VARIAVEIS, CLASSES...
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

//        PEGANDO A INSTANCIA DO DAO
        dao = RoupasDAO.getInstance();
//        Pegando o item que será carregado
        final Roupas item = getItem(position);
        String foto = buscarFoto(item.getId());

//        finds dos elementos do layout inflado
        TextView lbl_titulo_favorito = v.findViewById(R.id.lbl_titulo_favorito);
        TextView lbl_status_roupas_favorito = v.findViewById(R.id.lbl_status_roupa_favorito);
        TextView lbl_cor = v.findViewById(R.id.lbl_cor_favorito);
        ImageView img_roupa = v.findViewById(R.id.img_favorito);
        Bitmap imgBit = BitmapFactory.decodeFile(foto);
        img_roupa.setImageBitmap(imgBit);
        fav = v.findViewById(R.id.img_favorito_like);
//        CLICK DO ICONE DE FAVORITO DA ROUPA
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg;
                if (item.getFavorito() == 1){
                    fav.setImageResource(R.drawable.ic_no_favorito);
                    msg = "Não Favorito";
                } else {
                    fav.setImageResource(R.drawable.ic_favoritos);
                    msg = "Favorito";
                }
                Boolean sucesso = setarFavorito(item);
                if (sucesso){
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                 }
            }
        });

//        setando os valores de cada elemento
        lbl_titulo_favorito.setText(item.getNome());
        lbl_status_roupas_favorito.setText(item.getStatus());
        lbl_cor.setBackgroundTintList(ColorStateList.valueOf(item.getCor()));
        if (item.getFavorito() == 1){
            fav.setImageResource(R.drawable.ic_favoritos);
        } else {
            fav.setImageResource(R.drawable.ic_no_favorito);
        }

        return v;
    }

//    MÉTODO QUE MUDA O STATUS "FAVORITO" DA ROUPA
    public boolean setarFavorito(Roupas roupa){
        Boolean teste = dao.atualizarFavorito(getContext(), roupa.getId(), roupa.getFavorito());
        return teste;
    }

    public String buscarFoto(int id){
        return dao.buscarUmaFoto(getContext(), id);
    }
}
