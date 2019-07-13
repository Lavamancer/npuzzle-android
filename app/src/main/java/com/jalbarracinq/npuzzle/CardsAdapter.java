package com.jalbarracinq.npuzzle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class CardsAdapter extends BaseAdapter {

    List<Card> list = new ArrayList<>();
    Activity activity;


    public static class Holder {
        // todo este es el contenedor de datos de cada item
    }


    public CardsAdapter(Activity activity) {
        this.activity = activity;
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;

        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.item_card, viewGroup, false);
            holder = new Holder();
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        // todo aqui vamos a crear el codigo del holder

        return view;
    }


    public List<Card> getList() {
        return list;
    }

}
