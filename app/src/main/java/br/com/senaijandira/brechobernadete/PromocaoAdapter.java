package br.com.senaijandira.brechobernadete;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class PromocaoAdapter extends ArrayAdapter<Promocao> {

//    declarando as variaveis, elementos...
    NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "br"));

//    construtor do adapter
    public PromocaoAdapter(Context ctx) {
        super(ctx, 0, new ArrayList<Promocao>());
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        inflando o layout do item da lista de categoria
        View v = convertView;
        if (v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.list_view_item_promocoes, null);
        }

        //Pegando o item que será carregado
        Promocao item = getItem(position);

//        finds dos elementos do layout inflado
        TextView lbl_valorAntigo_promo = v.findViewById(R.id.lbl_valorAntigo_promo);
        TextView lbl_valorNovo_promo = v.findViewById(R.id.lbl_valorNovoPromo);
        TextView lbl_nome_produto = v.findViewById(R.id.lbl_titulo_promo);
        Button btn_conferir_promo = v.findViewById(R.id.btn_conferir_promo);

//        setando os valores de cada elemento
        lbl_nome_produto.setText(item.getNomeProduto());
        lbl_valorAntigo_promo.setText(nf.format(item.getPrecoAntigo()));
        lbl_valorNovo_promo.setText(nf.format(item.getPrecoNovo()));
//        setando a ação do botão de conferir
        btn_conferir_promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String endereco = "http://www.uou.com.br";
                Uri uri = Uri.parse(endereco);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        });
        return v;
    }

}
