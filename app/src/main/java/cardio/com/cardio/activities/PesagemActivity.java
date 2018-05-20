package cardio.com.cardio.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cardio.com.cardio.model.Item;
import cardio.com.cardio.adapters.ItemRecycleViewAdapter;
import cardio.com.cardio.R;
import cardio.com.cardio.model.CaixaDeTexto;

public class PesagemActivity extends AppCompatActivity {

    private RecyclerView mRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesagem);

        mRecView = (RecyclerView) findViewById(R.id.rec_view);

        List<Item> items = new ArrayList<>();

        CaixaDeTexto caixaDeTextoPeso = new CaixaDeTexto("Meu peso", "kg", CaixaDeTexto.INPUT_DECIMAL);
        caixaDeTextoPeso.setHint("00.0");
        items.add(caixaDeTextoPeso);

        CaixaDeTexto caixaDeTextoBatimentos = new CaixaDeTexto("Meus batimentos", "bpm", CaixaDeTexto.INPUT_NUMBER);
        caixaDeTextoBatimentos.setHint("000");
        items.add(caixaDeTextoBatimentos);

        CaixaDeTexto caixaDeTextoPressao = new CaixaDeTexto("Minha pressão arterial", "mmHg", CaixaDeTexto.INPUT_TEXT);
        caixaDeTextoPressao.setHint("00x00");
        items.add(caixaDeTextoPressao);

        ItemRecycleViewAdapter itemRecycleViewAdapter = new ItemRecycleViewAdapter(items);

        mRecView.setAdapter(itemRecycleViewAdapter);
        mRecView.setLayoutManager(new LinearLayoutManager(this));
    }
}
