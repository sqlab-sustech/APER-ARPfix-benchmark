package com.deadman.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pedro.library.AutoPermissions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    File qr_string = new File(
            Environment.getExternalStorageDirectory() + File.separator + "FRC" + File.separator + "qr_string.txt");

    private LineChart chart;

    private final int[] colors = new int[]{
            ColorTemplate.MATERIAL_COLORS[0],
            ColorTemplate.LIBERTY_COLORS[3],
            ColorTemplate.MATERIAL_COLORS[2]
    };

    private final int[] colors2 = new int[]{
            ColorTemplate.PASTEL_COLORS[3],
            ColorTemplate.COLORFUL_COLORS[3],
            ColorTemplate.MATERIAL_COLORS[3]
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button qr_button = findViewById(R.id.qr_button);
        qr_button.setOnClickListener(view -> scanner());

//    AutoPermissions.Companion.loadAllPermissions(this, 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Go Full screen

    }

    private void scanner() {
        Intent intent = new Intent(MainActivity.this, Scanner.class);
        startActivity(intent);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        TextView selected_number = findViewById(R.id.selected_number);
        selected_number.setText(String.valueOf(h.getY()));
    }

    @Override
    public void onNothingSelected() {

    }
}
