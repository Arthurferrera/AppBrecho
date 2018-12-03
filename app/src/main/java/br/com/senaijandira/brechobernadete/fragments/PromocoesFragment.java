package br.com.senaijandira.brechobernadete.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.adapter.PromocaoAdapter;
import br.com.senaijandira.brechobernadete.model.HttpConnection;
import br.com.senaijandira.brechobernadete.model.Promocao;


/**
 * A simple {@link Fragment} subclass.
 */
public class PromocoesFragment extends Fragment {

//    declarando as variaveis, elementos...
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

//        String do caminho padrão da api
        API_URL = getString(R.string.API_URL);

//        instância do SharedPreferencesConfig
        listView_promocao = promoView.findViewById(R.id.list_view_promocoes);

//        criando o adapter, setando na lista e setando click do item da lista
        adapter = new PromocaoAdapter(getActivity());
        listView_promocao.setAdapter(adapter);
//        listView_promocao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                ItemClickPromo();
//            }
//        });
        return promoView;
    }

    @Override
    public void onResume() {
        super.onResume();

//        LIMPANDO O ADAPTER PARA NÃO DUPLICAR OS REGISTROS
        adapter.clear();

        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids) {
//                criando a string que vai pegar o retorno da api
                String json = "";
//                setando a url da api
                final String url = API_URL + "promocoes.php";
//                chamando a api
                json = HttpConnection.get(url);
//                retornando o retorno da api
                return json;
            }

            @Override
            protected void onPostExecute(String json) {
                super.onPostExecute(json);
//                verificando se o json está vazio
                if (json == null) json = "Sem Dados";
                Log.d("onPostExecute", json);

//                criando a lista que vai armazenar todos os registros
                ArrayList<Promocao> promocoes = new ArrayList<>();
                if (json != null){
                    try {
//                        pegando o array que retornou da api
                        JSONArray listaPromocoes = new JSONArray(json);
//                        percorrendo o array, pegando informação de cada registro
                        for (int i = 0; i < listaPromocoes.length(); i++){
                            JSONObject promocaoJson = listaPromocoes.getJSONObject(i);

                            Promocao promo = new Promocao();
                            promo.setId(promocaoJson.getInt("idPromocao"));
                            promo.setIdProduto(promocaoJson.getInt("idProduto"));
                            promo.setNomeProduto(promocaoJson.getString("nomeProduto"));
                            promo.setPrecoAntigo(promocaoJson.getDouble("preco"));
                            promo.setPrecoNovo(promocaoJson.getDouble("valorNovo"));
                            promo.setFoto(promocaoJson.getString("caminhoImagem"));
                            promocoes.add(promo);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    adicionando no adapter
                    adapter.addAll(promocoes);
                }
            }
        }.execute();
    }


}
