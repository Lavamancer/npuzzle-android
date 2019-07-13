package com.jalbarracinq.npuzzle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardsAdapter extends BaseAdapter {

    private static final int N = 2;

    List<Card> list = new ArrayList<>();
    Activity activity;
    GridView gridView;


    public static class Holder {
        RelativeLayout relativeLayout;
        TextView textView;
    }


    public CardsAdapter(Activity activity, GridView gridView) {
        this.activity = activity;
        this.gridView = gridView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        Holder holder;

        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.item_card, viewGroup, false);
            holder = new Holder();
            holder.relativeLayout = view.findViewById(R.id.relativeLayout);
            holder.textView = view.findViewById(R.id.textView);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        final Card card = list.get(position);
        if (card.getPosition() == null) {
            holder.relativeLayout.setBackgroundResource(R.color.gray);
            holder.textView.setVisibility(View.GONE);
            holder.relativeLayout.setOnClickListener(null);
        } else {
            holder.relativeLayout.setBackgroundResource(R.color.red);
            holder.textView.setVisibility(View.VISIBLE);
            holder.textView.setText(card.getPosition().toString());
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkEmptyCardAround(card, position);
                }
            });
        }

        return view;
    }


    public List<Card> getList() {
        return list;
    }

    private void checkEmptyCardAround(Card card, int position) {
        int total = list.size();
        int row = (position / N) + 1;

        boolean leftBoard = (float) position / N == row - 1;
        boolean rightBoard = (position + 1) / row == N;

        Card emptyCard = null;
        Card auxCard;

        if (position - N >= 0) { // up
            auxCard = list.get(position - N);
            if (auxCard.getPosition() == null) {
                emptyCard = auxCard;
            }
        }

        if (!rightBoard) { // right
            auxCard = list.get(position + 1);
            if (auxCard.getPosition() == null) {
                emptyCard = auxCard;
            }
        }

        if (position + N < total) { // down
            auxCard = list.get(position + N);
            if (auxCard.getPosition() == null) {
                emptyCard = auxCard;
            }
        }

        if (!leftBoard) { // left
            auxCard = list.get(position - 1);
            if (auxCard.getPosition() == null) {
                emptyCard = auxCard;
            }
        }

        if (emptyCard != null) {
            changeCards(card, emptyCard);
        }

        // todo aqui comprobamos fin de partida
//        tablero.getPartida().comprobarFinJuego();
    }

    private void changeCards(Card card, Card emptyCard) {
        emptyCard.setPosition(card.getPosition());
        card.setPosition(null);
        notifyDataSetChanged();
        gridView.invalidateViews();
    }

}
