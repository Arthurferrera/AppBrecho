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

public class TagAdapter extends ArrayAdapter<Tag> {

//    construtor do adapter
    public TagAdapter(Context context, ArrayList<Tag> tags) {
        super(context, 0, tags);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

//        inflando o layout do item da lista de categoria
        View v = convertView;
        if (v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_tag,  null);
        }

//        Pegando o item que será carregado
        Tag tag = getItem(position);

//        finds dos elementos do layout inflado
        TextView lbl_tag_nome = v.findViewById(R.id.lbl_tag_item);

//        setando os valores de cada elemento
        lbl_tag_nome.setText(tag.getNomeTag());

        return v;
    }
}
