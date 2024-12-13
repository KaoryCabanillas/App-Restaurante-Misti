package com.example.restaurante_misti;

import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.function.Function;

public class NuevoPedidoActivity extends AppCompatActivity {

    private AppDatabase db;
    private MeseroDao meseroDao;
    private PlatilloDao platilloDao;
    private MesaDao mesaDao;
    private ExecutorService executor;
    private TextView txtPrecio;
    private Spinner sp_meseros, sp_platillo, sp_mesas;
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
        platilloDao = db.platilloDao();
        mesaDao = db.mesaDao();

        executor = Executors.newSingleThreadExecutor();

        sp_meseros = findViewById(R.id.sp_mesero);
        sp_platillo = findViewById(R.id.sp_platillo);
        sp_mesas = findViewById(R.id.sp_mesas);
        txtPrecio = findViewById(R.id.txtPrecio);

        validarMeseros();
        validarMesas();
        listarMeseros();
        listarPlatillos();
        listarMesas();


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

    private boolean listadoMesasVacias() {
        List<MesaEntity> mesas = mesaDao.listadoMesas();
        return mesas.isEmpty();
    }

    private void insertarMeseros() {
        MeseroEntity mesero1 = new MeseroEntity("Juan", "Perez");
        MeseroEntity mesero2 = new MeseroEntity("Ana", "Franchezcoli");
        MeseroEntity mesero3 = new MeseroEntity("Pedro", "Smith");

        meseroDao.nuevoMesero(mesero1);
        meseroDao.nuevoMesero(mesero2);
        meseroDao.nuevoMesero(mesero3);
    }

    private void insertarMesas() {
        MesaEntity nuevaMesa1 = new MesaEntity("Mesa 1");
        MesaEntity nuevaMesa2 = new MesaEntity("Mesa 2");
        MesaEntity nuevaMesa3 = new MesaEntity("Mesa 3");
        MesaEntity nuevaMesa4 = new MesaEntity("Mesa 4");

        mesaDao.nuevaMesa(nuevaMesa1);
        mesaDao.nuevaMesa(nuevaMesa2);
        mesaDao.nuevaMesa(nuevaMesa3);
        mesaDao.nuevaMesa(nuevaMesa4);
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
    private void validarMesas() {
        executor.execute(
                () -> {
                    try {
                        if (listadoMesasVacias()) {
                            insertarMesas();
                        }
                    } catch (Exception e) {
                        Log.d("", "InsertarMesas: ERROR AL INSERTAR LAS MESAS", e);
                    }
                });
    }
    private void listarMeseros() {
        executor.execute(() -> {
            try {
                List<MeseroEntity> meseros = meseroDao.listadoMeseros();
                // Usar la función genérica para listar meseros
                listarElementos(meseros, sp_meseros, MeseroEntity::getNombre);
            } catch (Exception e) {
                Log.e("NuevoPedidoActivity", "Error al listar los meseros", e);
            }
        });
    }

    private void listarPlatillos() {
        executor.execute(() -> {
            try {
                List<PlatilloEntity> platillos = platilloDao.listadoPlatillo();

                // Usar la función genérica para listar platillos
                listarElementos(platillos, sp_platillo, PlatilloEntity::getNombre);
            } catch (Exception e) {
                Log.e("NuevoPedidoActivity", "Error al listar los platillos", e);
            }
        });
    }

    private void listarMesas() {
        executor.execute(() -> {
            try {
                List<MesaEntity> mesas = mesaDao.listadoMesas();
                // Usar la función genérica para listar platillos
                listarElementos(mesas, sp_mesas, MesaEntity::getN_mesa);
            } catch (Exception e) {
                Log.e("NuevoPedidoActivity", "Error al listar las mesas", e);
            }
        });
    }
    private <T> void listarElementos(List<T> elementos, Spinner spinner, Function<T, String> getNombreFunc) {
        executor.execute(() -> {
            try {
                // Crear una lista de nombres
                List<String> nombres = new ArrayList<>();
                for (T elemento : elementos) {
                    nombres.add(getNombreFunc.apply(elemento));  // Usamos la función para obtener el nombre
                }

                // Actualizar el Spinner en el hilo principal
                runOnUiThread(() -> {
                    // Crear un ArrayAdapter con los nombres
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            NuevoPedidoActivity.this,
                            android.R.layout.simple_spinner_item,
                            nombres
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                });
            } catch (Exception e) {
                Log.e("NuevoPedidoActivity", "Error al listar los elementos", e);
            }
        });
    }

}