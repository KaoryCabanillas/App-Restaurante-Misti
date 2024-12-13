package com.example.restaurante_misti;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NuevoPedidoActivity extends AppCompatActivity {

    private AppDatabase db;
    private MeseroDao meseroDao;

    private ExecutorService executor;
    private Spinner sp_meseros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nuevo_pedido);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "Restaurante-Misti"
        ).fallbackToDestructiveMigration().build();

        meseroDao = db.meseroDao();

        executor = Executors.newSingleThreadExecutor();

        sp_meseros = findViewById(R.id.sp_mesero);

        validarMeseros();

        listarMeseros();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean listadoMeserosVacios() {
        List<MeseroEntity> meseros = meseroDao.listadoMeseros();
        return meseros.isEmpty();
    }

    private void insertarMeseros() {
        MeseroEntity mesero1 = new MeseroEntity("Juan", "Perez");
        MeseroEntity mesero2 = new MeseroEntity("Ana", "Franchezcoli");
        MeseroEntity mesero3 = new MeseroEntity("Pedro", "Smith");

        meseroDao.nuevoMesero(mesero1);
        meseroDao.nuevoMesero(mesero2);
        meseroDao.nuevoMesero(mesero3);
    }

    private void validarMeseros() {
        executor.execute(
                () -> {
                    try {
                        if (listadoMeserosVacios()) {
                            insertarMeseros();
                        }
                    } catch (Exception e) {
                        Log.d("", "InsertarMeseros: ERROR AL INSERTAR LOS MESEROS", e);
                    }
                });
    }

    private void listarMeseros() {
        executor.execute(() -> {
            try {
                // Obtener los meseros completos
                List<MeseroEntity> meseros = meseroDao.listadoMeseros();

                // Crear una lista de nombres
                List<String> nombresMeseros = new ArrayList<>();
                for (MeseroEntity mesero : meseros) {
                    nombresMeseros.add(mesero.getNombre());  // Asumiendo que 'getNombre' es un método en MeseroEntity
                }

                // Actualizar el Spinner en el hilo principal
                runOnUiThread(() -> {
                    // Crear un ArrayAdapter con los nombres de los meseros
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            NuevoPedidoActivity.this,
                            android.R.layout.simple_spinner_item,
                            nombresMeseros
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_meseros.setAdapter(adapter);
                });
            } catch (Exception e) {
                Log.e("NuevoPedidoActivity", "Error al listar los meseros", e);
            }
        });
    }

}