package br.com.senaijandira.brechobernadete.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.model.Roupas;

public class ComprasAdapter extends ArrayAdapter<Roupas>{

    public ComprasAdapter(@NonNull Context context) {
        super(context, 0, new ArrayList<Roupas>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if (v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_compras, null);
        }

        Roupas item = getItem(position);

        ImageView img_roupa = v.findViewById(R.id.img_compra);
        TextView lbl_titulo_roupa = v.findViewById(R.id.lbl_titulo_compra);
        TextView lbl_descricao_roupa = v.findViewById(R.id.lbl_descricao_compra);

//        img_roupa.setImageBitmap(item.getTag());
        lbl_titulo_roupa.setText(item.getNome());
        lbl_descricao_roupa.setText(item.getDescricao());

        return v;
    }
}
