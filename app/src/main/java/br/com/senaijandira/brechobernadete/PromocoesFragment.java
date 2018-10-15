package br.com.senaijandira.brechobernadete;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PromocoesFragment extends Fragment {

    ListView listView_promocao;
    PromocaoAdapter adapter;
    String API_URL;


    public PromocoesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View promoView = inflater.inflate(R.layout.fragment_promocoes, container, false);

        API_URL = getString(R.string.API_URL);

        listView_promocao = promoView.findViewById(R.id.list_view_promocoes);

        adapter = new PromocaoAdapter(getActivity());
        listView_promocao.setAdapter(adapter);
        listView_promocao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ItemClickPromo();
            }
        });
        return promoView;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter.clear();

        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids) {
                String json = "";
                final String url = API_URL + "promocoes.php";
                json = HttpConnection.get(url);
                return json;
            }

            @Override
            protected void onPostExecute(String json) {
                super.onPostExecute(json);
                if (json == null) json = "Sem Dados";
                Log.d("onPostExecute", json);
                ArrayList<Promocao> promocoes = new ArrayList<>();
                if (json != null){
                    try {
                        JSONArray listaPromocoes = new JSONArray(json);
                        for (int i = 0; i < listaPromocoes.length(); i++){
                            JSONObject promocaoJson = listaPromocoes.getJSONObject(i);

                            Promocao promo = new Promocao();
                            promo.setId(promocaoJson.getInt("idPromocao"));
                            promo.setNomeProduto(promocaoJson.getString("nomeProduto"));
                            promo.setPrecoAntigo(promocaoJson.getDouble("preco"));
                            promo.setPrecoNovo(promocaoJson.getDouble("valorNovo"));
                            promocoes.add(promo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.addAll(promocoes);
                }
            }
        }.execute();
    }

    public void ItemClickPromo(){
        Toast.makeText(getContext(), "funcionou", Toast.LENGTH_SHORT).show();
    }


}
