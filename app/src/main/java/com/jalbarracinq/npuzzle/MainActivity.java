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
        cardsAdapter = new CardsAdapter(this, gridView);
        gridView.setAdapter(cardsAdapter);

        cardsAdapter.getList().add(new Card(1));
        cardsAdapter.getList().add(new Card(2));
        cardsAdapter.getList().add(new Card(3));
        cardsAdapter.getList().add(new Card(null));

        cardsAdapter.notifyDataSetChanged();
        gridView.invalidateViews();

    }

}
