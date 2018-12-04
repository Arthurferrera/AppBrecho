package br.com.senaijandira.brechobernadete.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.model.Promocao;

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
        final Promocao item = getItem(position);

//        finds dos elementos do layout inflado
        ImageView img_promo = v.findViewById(R.id.img_promocao);
        TextView lbl_valorAntigo_promo = v.findViewById(R.id.lbl_valorAntigo_promo);
        TextView lbl_valorNovo_promo = v.findViewById(R.id.lbl_valorNovoPromo);
        TextView lbl_nome_produto = v.findViewById(R.id.lbl_titulo_promo);
        LinearLayout btn_conferir_promo = v.findViewById(R.id.btn_conferir_promo);

//        setando os valores de cada elemento
//        Picasso.get().load("http://www.brechobernadete.com.br/cms/view/arquivos/"+item.getFoto()).resize(150, 150).centerInside().into(img_promo);
//        Picasso.get().load("http://192.168.1.43/cms/view/arquivos/"+item.getFoto()).resize(150, 150).centerInside().into(img_promo);
        String fotoUrl = "http://192.168.1.38/brecho/cms/view/arquivos/"+item.getFoto();
        Picasso.get().load(fotoUrl).resize(150, 150).centerInside().into(img_promo);
        lbl_nome_produto.setText(item.getNomeProduto());
        lbl_valorAntigo_promo.setText(nf.format(item.getPrecoAntigo()));
        lbl_valorNovo_promo.setText(nf.format(item.getPrecoNovo()));
//        setando a ação do botão de conferir
        btn_conferir_promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String endereco = "http://192.168.1.38/brecho/view/visualizar_produto.php?id="+item.getIdProduto()+"&pagina=promoção";
//                String endereco = "http://192.168.1.43/brecho/view/visualizar_produto.php?id="+item.getIdProduto()+"&pagina=promoção";
//                String endereco = "http://www.brechobernadete.com.br/view/visualizar_produto.php?id="+item.getIdProduto()+"&pagina=promoção";
                Uri uri = Uri.parse(endereco);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                getContext().startActivity(intent);
            }
        });
        return v;
    }

}
