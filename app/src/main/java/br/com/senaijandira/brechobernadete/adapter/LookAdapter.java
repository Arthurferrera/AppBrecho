package br.com.senaijandira.brechobernadete.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import br.com.senaijandira.brechobernadete.dao.RoupasDAO;
import br.com.senaijandira.brechobernadete.model.Roupas;

public class LookAdapter extends ArrayAdapter<Roupas> {

    ImageView fav;
    RoupasDAO dao;

    public LookAdapter (Context context, ArrayList<Roupas> lstRoupas){
        super(context, 0, lstRoupas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;
        if (v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_look, null);
        }

        dao = RoupasDAO.getInstance();
        final Roupas item = getItem(position);
        String foto = buscarFoto(item.getId());

        ImageView img_look = v.findViewById(R.id.imageView_look);
        TextView lbl_titulo = v.findViewById(R.id.lbl_titulo_look);
        TextView lbl_descricao_look = v.findViewById(R.id.lbl_descricao_look);

        Bitmap imgBit = BitmapFactory.decodeFile(foto);
        img_look.setImageBitmap(imgBit);
        lbl_titulo.setText(item.getNome());
        lbl_descricao_look.setText(item.getDescricao());

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
