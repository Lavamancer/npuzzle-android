package com.jalbarracinq.npuzzle;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    CardsAdapter cardsAdapter;
    RelativeLayout winRelativeLayout;
    RelativeLayout progressBarRelativeLayout;
    Button buttonRefresh;
    EditText sideNumber;
    boolean winGame = false;

    private static final int SIDE = 4;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        winRelativeLayout = findViewById(R.id.winRelativeLayout);
        progressBarRelativeLayout = findViewById(R.id.progressBarRelativeLayout);
        buttonRefresh = findViewById(R.id.buttonRefresh);
        sideNumber = findViewById(R.id.sideNumber);

        cardsAdapter = new CardsAdapter(this, gridView);
        gridView.setNumColumns(SIDE);
        gridView.setAdapter(cardsAdapter);

        for (int i = 1; i <= SIDE * SIDE; i++) {
            cardsAdapter.getList().add(new Card(i == SIDE * SIDE ? null : i));
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                System.out.println("Before: " + new Date().toString());
                for (int i = 0; i < 1000000; i++) {
                    int position = getRandomCard();
                    cardsAdapter.checkEmptyCardAround(cardsAdapter.getList().get(position), position, false);
                }
                System.out.println("After: " + new Date().toString());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressBarRelativeLayout.setVisibility(View.GONE);
                cardsAdapter.notifyDataSetChanged();
                gridView.invalidateViews();
            }
        }.execute();

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        progressBarRelativeLayout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        System.out.println("Before: " + new Date().toString());
                        for (int i = 0; i < 1000000; i++) {
                            int position = getRandomCard();
                            cardsAdapter.checkEmptyCardAround(cardsAdapter.getList().get(position), position, false);
                        }
                        System.out.println("After: " + new Date().toString());

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        progressBarRelativeLayout.setVisibility(View.GONE);
                        cardsAdapter.notifyDataSetChanged();
                        gridView.invalidateViews();
                    }
                }.execute();
                winRelativeLayout.setVisibility(View.GONE);
                winGame = false;
            }
        });
    }


    public void winGame() {
        winRelativeLayout.setVisibility(View.VISIBLE);
        winGame = true;
    }

    private int getRandomCard() {
        Card card;
        int position;
        do {
            position = (int) (Math.random() * (cardsAdapter.getList().size() - 1));
            card = cardsAdapter.getList().get(position);
        } while (card.getPosition() == null);
        return position;
    }


}
