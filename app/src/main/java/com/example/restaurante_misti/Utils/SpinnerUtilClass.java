package com.example.restaurante_misti.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.function.Function;

import java.util.List;

public class SpinnerUtilClass {

    public static <T> void listarElementos(Context context, Executor executor, List<T> elementos, Spinner spinner, Function<T, String> getNombreFunc) {
        listarElementos(context, executor, elementos, spinner, getNombreFunc,  -1);  // Llama al método principal con null por defecto
    }
    public static <T> void listarElementos(Context context, Executor executor ,List<T> elementos, Spinner spinner, Function<T, String> getNombreFunc, int posicion) {
        executor.execute(() -> {
            try {
                // Crear una lista de nombres
                List<String> nombres = new ArrayList<>();
                for (T elemento : elementos) {
                    nombres.add(getNombreFunc.apply(elemento));  // Usamos la función para obtener el nombre
                }

                // Actualizar el Spinner en el hilo principal
                ((Activity) context).runOnUiThread(() -> {
                    // Crear un ArrayAdapter con los nombres
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nombres);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                });

                if (posicion >= 0 && posicion < spinner.getCount()) {
                    spinner.setSelection(posicion);  // Preseleccionar el ítem en la posición encontrada
                }

            } catch (Exception e) {
                Log.e("SpinnerUtils", "Error al listar los elementos", e);
            }
        });
    }

}
