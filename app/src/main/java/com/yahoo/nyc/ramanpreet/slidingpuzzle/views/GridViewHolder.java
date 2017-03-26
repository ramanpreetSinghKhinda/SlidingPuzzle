package com.yahoo.nyc.ramanpreet.slidingpuzzle.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.yahoo.nyc.ramanpreet.slidingpuzzle.R;

/**
 * Created by raman on 2/7/17.
 */
public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public Button btnTile;

    public GridViewHolder(View itemView) {
        super(itemView);

        btnTile = (Button) itemView.findViewById(R.id.btn_tile);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}

