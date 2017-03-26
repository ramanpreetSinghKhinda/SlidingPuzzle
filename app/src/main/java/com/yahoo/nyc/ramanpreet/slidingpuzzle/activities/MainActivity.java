package com.yahoo.nyc.ramanpreet.slidingpuzzle.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.yahoo.nyc.ramanpreet.slidingpuzzle.R;
import com.yahoo.nyc.ramanpreet.slidingpuzzle.adapters.GridAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.input_atv)
    EditText mInputView;

    @Bind(R.id.grid_recycler_view)
    RecyclerView mRecyclerView;

    private GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mInputView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    createGrid(Integer.parseInt(mInputView.getText().toString()));
                    Toast.makeText(MainActivity.this, "Creating Grid of Size " + mInputView.getText() + " X " + mInputView.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void createGrid(int size) {
        if (size < 2) {
            Toast.makeText(MainActivity.this, "Grid Size should be atleast 2x2", Toast.LENGTH_SHORT).show();
            return;
        }

        if (size > 6) {
            Toast.makeText(MainActivity.this, "For demo purpose Grid Size should be less than 7x7", Toast.LENGTH_SHORT).show();
            return;
        }

        gridAdapter = new GridAdapter(this, size);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, size));
        mRecyclerView.setAdapter(gridAdapter);
    }
}
