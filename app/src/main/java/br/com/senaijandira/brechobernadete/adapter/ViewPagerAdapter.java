package br.com.senaijandira.brechobernadete.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.dao.RoupasDAO;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private int idRoupa;
    //private Integer[] imagens = {R.drawable.blusas, R.drawable.cabide, R.drawable.logo_empresa};
    private RoupasDAO dao = RoupasDAO.getInstance();
    private ArrayList<String> imagens = new ArrayList<>();

    public ViewPagerAdapter(Context context, int idRoupa) {
        this.context = context;
        this.idRoupa = idRoupa;
    }

    @Override
    public int getCount() {
        return imagens.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        imagens = dao.selecionarFotosByIdRoupa(context, idRoupa);

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        Picasso.get().load(imagens.get(position)).into(imageView);

//        imageView.setImageResource([position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
