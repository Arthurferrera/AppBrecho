package br.com.senaijandira.brechobernadete;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CategoriaAdapter extends ArrayAdapter<Categoria>{

    public CategoriaAdapter(Context ctx, ArrayList<Categoria> lstCategorias) {
        super(ctx, 0, lstCategorias);
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
        Categoria caegoria = getItem(position);

//        finds dos elementos do layout inflado
        TextView lbl_categoria_item = v.findViewById(R.id.lbl_categoria_item);
        TextView lbl_quantidade_categoria_item = v.findViewById(R.id.lbl_quantidade_roupa_categoria_item);

//        setando os valores de cada elemento
        lbl_categoria_item.setText(caegoria.getNome());
//        lbl_quantidade_categoria_item.setText(caegoria.getId());

//        retornando a view
        return v;

    }
}
