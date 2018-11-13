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
<<<<<<< HEAD
    private int idRoupa;
    //private Integer[] imagens = {R.drawable.blusas, R.drawable.cabide, R.drawable.logo_empresa};
    private RoupasDAO dao = RoupasDAO.getInstance();
    private ArrayList<String> imagens = new ArrayList<>();
=======
    private ArrayList<String> imagens;
    private RoupasDAO dao = RoupasDAO.getInstance();
    private int idRoupa;
>>>>>>> be6afcfff620c933fa3eda36cde034fc7867dc0f

    public ViewPagerAdapter(Context context, int idRoupa) {
        this.context = context;
        this.idRoupa = idRoupa;
<<<<<<< HEAD

=======
>>>>>>> be6afcfff620c933fa3eda36cde034fc7867dc0f
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

<<<<<<< HEAD

=======
>>>>>>> be6afcfff620c933fa3eda36cde034fc7867dc0f
        imagens = dao.selecionarFotosByIdRoupa(context, idRoupa);

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = view.findViewById(R.id.imageView);
<<<<<<< HEAD
        Picasso.get().load(imagens.get(position)).resize(70, 70).centerCrop().into(imageView);
=======
        Picasso.get().load(imagens.get(position)).into(imageView);
>>>>>>> be6afcfff620c933fa3eda36cde034fc7867dc0f
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
