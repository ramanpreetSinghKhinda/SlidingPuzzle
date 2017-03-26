package com.yahoo.nyc.ramanpreet.slidingpuzzle.adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yahoo.nyc.ramanpreet.slidingpuzzle.R;
import com.yahoo.nyc.ramanpreet.slidingpuzzle.views.GridViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raman on 2/7/17.
 */
public class GridAdapter extends RecyclerView.Adapter<GridViewHolder> {
    private static final String TAG = "Raman";

    private Context mContext;
    private List<Integer> gridValueList;
    private int gridSize;

    public GridAdapter(@NonNull Context mContext, @IntRange(from = 2, to = 10) int gridSize) {
        this.mContext = mContext;
        this.gridSize = gridSize;
        this.gridValueList = new ArrayList<>(gridSize * gridSize);

        initGrid();
    }

    private void initGrid() {
        this.gridValueList.clear();

        for (int i = 1; i <= gridSize * gridSize; i++) {
            gridValueList.add(i);
        }

        // Represent empty space with 0
        gridValueList.set(gridSize * gridSize - 1, 0);

        notifyDataSetChanged();
    }

    /**
     * check if the particular tile can be moved
     * a tile can be moved if its adjacent to an empty tile
     * if position of the tile in consideration is (a, b) then
     * check if there is Empty Tile in {LEFT (a, b-1), UP (a-1,b), RIGHT(a, b+1) or DOWN(a+1, b)}
     */
    private boolean moveTile(int row, int col) {
        int origPos = getLinearPosition(row, col);

        int posLeft = getLinearPosition(row, col - 1);
        if (posLeft >= 0 && gridValueList.get(posLeft) == 0) {
            swapTiles(origPos, posLeft);
            notifyDataSetChanged();
            return true;
        }

        int posUp = getLinearPosition(row - 1, col);
        if (posUp >= 0 && gridValueList.get(posUp) == 0) {
            swapTiles(origPos, posUp);
            notifyDataSetChanged();
            return true;
        }

        int posRight = getLinearPosition(row, col + 1);
        if (posRight >= 0 && gridValueList.get(posRight) == 0) {
            swapTiles(origPos, posRight);
            notifyDataSetChanged();
            return true;
        }

        int posDown = getLinearPosition(row + 1, col);
        if (posDown >= 0 && gridValueList.get(posDown) == 0) {
            swapTiles(origPos, posDown);
            notifyDataSetChanged();
            return true;
        }

        return false;
    }

    private void swapTiles(int origPos, int finalPos) {
        int numOrigPos = gridValueList.get(origPos);
        int numFinalPos = gridValueList.get(finalPos);

        gridValueList.set(origPos, numFinalPos);
        gridValueList.set(finalPos, numOrigPos);
    }

    private boolean isPuzzleSolved() {
        for (int i = 0; i < gridSize * gridSize - 1; i++) {
            if (gridValueList.get(i) != i + 1) {
                return false;
            }
        }

        return true;
    }

    /**
     * Actual position of tile located at position (a, b) in the ROWSxCOLS Grid is given as
     * position = COLS x a + b
     */
    private int getLinearPosition(int row, int col) {
        if (row < 0 || col < 0 || row >= gridSize || col >= gridSize) {
            return -1;
        }

        return gridSize * row + col;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_grid, parent, false);

        return new GridViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        final int tileValue = gridValueList.get(position);
        final int tilePosition = position;

        holder.btnTile.setText("" + tileValue);

        holder.btnTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] actualPos = getActualPosition(tilePosition);

                Log.v(TAG, "Clicked position is : ( " + "" + actualPos[0] + " , " + actualPos[1] + ")");

                if (tileValue == 0) {
                    Toast.makeText(mContext, "In-Valid Move. You cannot move an empty tile", Toast.LENGTH_SHORT).show();
                } else if (moveTile(actualPos[0], actualPos[1])) {
                    Toast.makeText(mContext, "Valid Move. Tile Swapped", Toast.LENGTH_SHORT).show();

                    if (isPuzzleSolved()) {
                        showWinnerDialog();
                    }

                } else {
                    Toast.makeText(mContext, "In-Valid Move. You can move tile only with an empty space", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showWinnerDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("You Won!!")
                .setMessage("Congrats you have solved the puzzle :)")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_menu_agenda)
                .show();
    }

    /**
     * mapping one dimensional array position to 2-dimensional array position (row, col)
     */
    private int[] getActualPosition(int tilePosition) {
        int actualPos[] = {-1, -1};

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (gridSize * i + j == tilePosition) {
                    actualPos[0] = i;
                    actualPos[1] = j;
                    break;
                }
            }
        }

        return actualPos;
    }

    @Override
    public int getItemCount() {
        return gridValueList.size();
    }

}


