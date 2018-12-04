package br.com.senaijandira.brechobernadete.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.dao.RoupasDAO;

public class ViewPagerAdapter extends PagerAdapter {

//    setando atributos, elementos, variaveis
    private Context context;
    private LayoutInflater layoutInflater;
    private int idRoupa;
    //private Integer[] imagens = {R.drawable.blusas, R.drawable.cabide, R.drawable.logo_empresa};
    private RoupasDAO dao = RoupasDAO.getInstance();
    private ArrayList<String> imagens = new ArrayList<>();

//    construtor
    public ViewPagerAdapter(Context context, int idRoupa) {
        this.context = context;
        this.idRoupa = idRoupa;
    }

//    pega o tamanho da lista
    @Override
    public int getCount() {
        return imagens.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

//    faz a instancia do item do viewpager
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

//        pega todas as imagens da roupa
        imagens = dao.selecionarFotosByIdRoupa(context, idRoupa);
        Log.d("listaImagens", String.valueOf(imagens));

//        cria e seta alguns parametros para o imageView
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER);

//        pega uma imagem por vez e seta no imageView
        String imagem = imagens.get(position);
        Bitmap imgBit = BitmapFactory.decodeFile(imagem);
        imageView.setImageBitmap(imgBit);
//        Picasso.get().load(imagem).resize(70, 70).error(R.drawable.blusasbg).centerInside().into(imageView);

//        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.custom_layout, null);
//        ImageView imageView = view.findViewById(R.id.imageView);
//        Picasso.get().load(imagens.get(position)).resize(700, 500).error(R.drawable.acessoriosbg).centerInside().into(imageView);
//        Picasso.get().load(imagens).resize(700, 1000).error(R.drawable.cloud_error).centerInside().into(imageView);

//        imageView.setImageResource([position]);

//        ViewPager vp = (ViewPager) container;
//        vp.addView(view, 0);
//        return view;
//        Toast.makeText(context, imagem, Toast.LENGTH_SHORT).show();

//        adiciona a imageView no container
        container.addView(imageView,0);
        return imageView;
    }

//    método que destrói o item ao ser passado para outro
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((ImageView) object);
//        ViewPager vp = (ViewPager) container;
//        View view = (View) object;
//        vp.removeView(view);
    }
}
