package br.com.senaijandira.brechobernadete.fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.senaijandira.brechobernadete.R;
import br.com.senaijandira.brechobernadete.activity.VisualizarActivity;
import br.com.senaijandira.brechobernadete.adapter.ComprasAdapter;
import br.com.senaijandira.brechobernadete.dao.RoupasDAO;
import br.com.senaijandira.brechobernadete.model.HttpConnection;
import br.com.senaijandira.brechobernadete.model.Roupas;
import br.com.senaijandira.brechobernadete.model.SharedPreferencesConfig;


/**
 * A simple {@link Fragment} subclass.
 */
public class ComprasFragment extends Fragment {

    ListView listView_compras;
    ComprasAdapter adapter;
    String API_URL;
    private SharedPreferencesConfig preferencesConfig;
    int idCliente;
    String tipoCliente;
    RoupasDAO dao;
    ArrayList<Roupas> comprasInserir;


    public ComprasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View comprasView = inflater.inflate(R.layout.fragment_compras, container, false);

        preferencesConfig = new SharedPreferencesConfig(getActivity().getApplicationContext());

        API_URL = getString(R.string.API_URL);
        idCliente = preferencesConfig.readUsuarioId();
        tipoCliente = preferencesConfig.readUsuarioTipo();
        dao = RoupasDAO.getInstance();

        comprasInserir = new ArrayList<>();

        listView_compras = comprasView.findViewById(R.id.list_view_compras);

        adapter = new ComprasAdapter(getActivity());
        listView_compras.setAdapter(adapter);
        listView_compras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Roupas roupa =  adapter.getItem(position);
                Intent intencao = new Intent(getContext(), VisualizarActivity.class);
                intencao.putExtra("id", roupa.getId());
                startActivity(intencao);
            }
        });
        return comprasView;
    }


    @Override
    public void onResume() {
        super.onResume();

        adapter.clear();

        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... voids) {
                String json = "";
                final String url = API_URL + "minhasCompras.php?id="+idCliente+"&tipo="+tipoCliente;
                json = HttpConnection.get(url);
                return json;
            }

            @Override
            protected void onPostExecute(String json) {
                super.onPostExecute(json);

                if (json == null){
                    json = "Sem Dados";
                }

                ArrayList<Roupas> roupas = new ArrayList<>();
                if (json != null){
                    try {
                        JSONArray listaRoupas = new JSONArray(json);
                        for(int i = 0; i < listaRoupas.length(); i++){
                            JSONObject compraJson = listaRoupas.getJSONObject(i);
                            Boolean existe = dao.VerificarMinhaCompra(getContext(), compraJson.getInt("idProduto"));
                            Roupas roupa = new Roupas();
                            if (!existe){
                                String cat = compraJson.getString("categoria");
                                switch (cat){
                                    case "Camisetas":
                                        roupa.setIdCategoria(1);
                                        break;
                                    case "Blusas":
                                        roupa.setIdCategoria(2);
                                        break;
                                    case "Calças":
                                        roupa.setIdCategoria(3);
                                        break;
                                    case "Bermudas":
                                        roupa.setIdCategoria(4);
                                        break;
                                    case "Calçados":
                                        roupa.setIdCategoria(5);
                                        break;
                                    case "Social":
                                        roupa.setIdCategoria(6);
                                        break;
                                    case "Vestidos":
                                        roupa.setIdCategoria(7);
                                        break;
                                    case "Acessórios":
                                        roupa.setIdCategoria(8);
                                        break;
                                    case "Roupas íntimas":
                                        roupa.setIdCategoria(9);
                                        break;
                                    case "Outros":
                                        roupa.setIdCategoria(10);
                                        break;
                                    default:
                                        roupa.setIdCategoria(10);
                                        break;

                                }
                                roupa.setNome(compraJson.getString("nomeProduto"));
                                roupa.setDescricao(compraJson.getString("descricao"));
                                roupa.setCor(compraJson.getInt("cor"));
                                roupa.setTamanho(compraJson.getString("tamanho"));
                                roupa.setMarca(compraJson.getString("marca"));
                                roupa.setClassificacao(compraJson.getString("classificacao"));
                                roupa.setFavorito(0);
                                roupa.setIdStatus(0);
                                roupa.setIdSite(compraJson.getInt("idProduto"));
                                dao.cadastrarRoupa(getContext(), roupa, idCliente, tipoCliente);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter.addAll(roupas);
                }
            }
        }.execute();
    }
}
