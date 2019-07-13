package com.jalbarracinq.npuzzle;

import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;
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

        int side = (int) Math.sqrt(list.size());

        int textSize = 200 / side;
        holder.textView.setTextSize(textSize);

        int margin = 30 / side;
        Resources r = activity.getResources();
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, r.getDisplayMetrics());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(px, px, px, px);
        holder.relativeLayout.setLayoutParams(params);

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
                    if (!((MainActivity) activity).winGame) {
                        checkEmptyCardAround(card, position);
                    }
                }
            });
        }

        return view;
    }


    public List<Card> getList() {
        return list;
    }

    private void checkEmptyCardAround(Card card, int position) {
        int n = (int) Math.sqrt(list.size());
        int total = list.size();
        int row = (position / n) + 1;

        boolean leftBoard = (float) position / n == row - 1;
        boolean rightBoard = (position + 1) / row == n;

        Card emptyCard = null;
        Card auxCard;

        if (position - n >= 0) { // up
            auxCard = list.get(position - n);
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

        if (position + n < total) { // down
            auxCard = list.get(position + n);
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

        checkOrderedCards();
    }

    private void changeCards(Card card, Card emptyCard) {
        emptyCard.setPosition(card.getPosition());
        card.setPosition(null);
        notifyDataSetChanged();
        gridView.invalidateViews();
    }

    private void checkOrderedCards() {
        boolean orderedCards = true;
        for (int i = 0; i < list.size() - 1; i++) {
            Card card = list.get(i);
            if (card.getPosition() == null || card.getPosition() != i + 1) {
                orderedCards = false;
                break;
            }
        }

        if (orderedCards) {
            ((MainActivity) activity).winGame();
        }

    }

}
