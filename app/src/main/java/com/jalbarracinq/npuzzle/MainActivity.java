package com.jalbarracinq.npuzzle;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    CardsAdapter cardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        cardsAdapter = new CardsAdapter(this);
        gridView.setAdapter(cardsAdapter);

        cardsAdapter.getList().add(new Card());
        cardsAdapter.getList().add(new Card());
        cardsAdapter.getList().add(new Card());
        cardsAdapter.getList().add(new Card());

        cardsAdapter.notifyDataSetChanged();
        gridView.invalidateViews();

    }

}
