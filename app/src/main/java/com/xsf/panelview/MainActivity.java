package com.xsf.panelview;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import PanelView.PanelView;

public class MainActivity extends AppCompatActivity {


    private PanelView panelView;
    private SeekBar skBar;


   /* public static void launch(Context ctx) {
        Intent it = new Intent(ctx, PanOneActivity.class);
        ctx.startActivity(it);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        panelView = (PanelView) findViewById(R.id.panView);
        skBar = (SeekBar) findViewById(R.id.seekBar);
        skBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                panelView.setPercent(progress);
                // Log.d("xsf","progress="+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
