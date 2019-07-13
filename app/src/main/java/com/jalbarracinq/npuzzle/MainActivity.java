package com.jalbarracinq.npuzzle;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    CardsAdapter cardsAdapter;
    RelativeLayout winRelativeLayout;
    boolean winGame = false;

    private static final int SIDE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        winRelativeLayout = findViewById(R.id.winRelativeLayout);

        cardsAdapter = new CardsAdapter(this, gridView);
        gridView.setNumColumns(SIDE);
        gridView.setAdapter(cardsAdapter);

        for (int i = 0; i < SIDE * SIDE; i++) {
            cardsAdapter.getList().add(new Card(i == 0 ? null : i));
        }

        Collections.shuffle(cardsAdapter.list);

        cardsAdapter.notifyDataSetChanged();
        gridView.invalidateViews();
    }


    public void winGame() {
        winRelativeLayout.setVisibility(View.VISIBLE);
        winGame = true;
    }

}
