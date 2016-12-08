package com.example.hc_pc.vocalesgestos;

import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GestureOverlayView.OnGesturePerformedListener {

    private GestureOverlayView gestureView;
    private EditText nombreGesto;
    private GestureLibrary gestLib;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureView = (GestureOverlayView) findViewById(R.id.gestures);


        nombreGesto = (EditText) findViewById(R.id.nombreGesto);

        gestLib = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!gestLib.load()) {
            Toast.makeText(this, "Error al cargar gestos predefinidos", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        gestureView.addOnGesturePerformedListener(this);
    }

    @Override
    protected void onStop() {
        gestureView.removeOnGesturePerformedListener(this);
        super.onStop();
    }


    @Override
    public void onGesturePerformed(GestureOverlayView gOverlayView, Gesture gesture) {
        ArrayList<Prediction> predicciones = gestLib.recognize(gesture);

        // We want at least one prediction
        if (predicciones.size() > 0) {
            Prediction prediccion = predicciones.get(0);
            // We want at least some confidence in the result
            if (prediccion.score > 1.0) {
                String texto = nombreGesto.getText().toString() + prediccion.name;
                nombreGesto.setText(texto);
            }
        }
    }
}
