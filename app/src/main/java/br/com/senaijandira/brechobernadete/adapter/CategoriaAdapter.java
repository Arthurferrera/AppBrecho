package br.com.senaijandira.brechobernadete.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.dao.CategoriaDAO;
import br.com.senaijandira.brechobernadete.model.Categoria;

public class CategoriaAdapter extends ArrayAdapter<Categoria>{

//    declarando as variaveis, elementos...
    CategoriaDAO dao;

//    construtor do adapter
    public CategoriaAdapter(Context ctx,CategoriaDAO dao, ArrayList<Categoria> lstCategorias) {
        super(ctx, 0, lstCategorias);
        this.dao = dao;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

//        inflando o layout do item da lista de categoria
        View v = convertView;
        if (v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_categoria, null);
        }

//        pegando o item da categoria
        Categoria categoria = getItem(position);

//        finds dos elementos do layout inflado
        TextView lbl_categoria_item = v.findViewById(R.id.lbl_categoria_item);
        TextView lbl_quantidade_categoria_item = v.findViewById(R.id.lbl_quantidade_roupa_categoria_item);
        CardView cardView = v.findViewById(R.id.card_view);
        ImageView img_bg_card = v.findViewById(R.id.img_bg_card);

//        setando os valores de cada elemento
        lbl_categoria_item.setText(categoria.getNome());
        lbl_quantidade_categoria_item.setText(categoria.getTotalPecas()+"");
        Picasso.get().load(R.drawable.blusas).into(img_bg_card);
//        retornando a view
        return v;

    }
}
