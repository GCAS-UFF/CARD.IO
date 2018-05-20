package cardio.com.cardio.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import cardio.com.cardio.R;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mLlPesagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLlPesagem = (LinearLayout) findViewById(R.id.ll_controle_peso);

        mLlPesagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), PesagemActivity.class));
            }
        });
    }
}
