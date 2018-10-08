package br.com.senaijandira.brechobernadete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PromocaoAdapter extends ArrayAdapter<Promocao> {


    public PromocaoAdapter(Context ctx) {
        super(ctx, 0, new ArrayList<Promocao>());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_promocoes, null);
        }

        //Pegando o item que será carregado
        Promocao item = getItem(position);

        //ViewHolder
        TextView lbl_valorAntigo_promo = v.findViewById(R.id.lbl_valorAntigo_promo);
        TextView lbl_valorNovo_promo = v.findViewById(R.id.lbl_valorNovoPromo);

        //Atualizando a UI
        lbl_valorAntigo_promo.setText(item.getPrecoAntigo());
        lbl_valorNovo_promo.setText(item.getPrecoNovo());

        return v;
    }
}
