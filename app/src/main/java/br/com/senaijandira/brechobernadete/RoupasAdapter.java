package br.com.senaijandira.brechobernadete;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RoupasAdapter extends ArrayAdapter<Roupas> {


    public RoupasAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;
        if (v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_favoritos, null);
        }

        Roupas item = getItem(position);

        TextView lbl_titulo_favorito = v.findViewById(R.id.lbl_titulo_favorito);
        TextView lbl_status_roupas_favorito = v.findViewById(R.id.lbl_status_roupa_favorito);

        lbl_titulo_favorito.setText(item.getNome());
        lbl_status_roupas_favorito.setText(item.getIdStatus());

        return v;
    }
}
