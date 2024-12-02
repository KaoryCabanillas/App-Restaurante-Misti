package com.example.restaurante_misti;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlatilloActivity extends AppCompatActivity {

    EditText txt_nombre, txt_precio;
    ListView lista_platillos;
    AppDatabase db;
    ExecutorService executor;
    private PlatilloDao platilloDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_platillo);

        txt_nombre = findViewById(R.id.txt_nombre_platillo);
        txt_precio = findViewById(R.id.txt_precio);
        lista_platillos = findViewById(R.id.ListPlatillo);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "Restaurante-Misti"
        ).build();

        platilloDao = db.platilloDao();

        executor = Executors.newSingleThreadExecutor();
        ListarPlatillo();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void InsertarPlatillo(View view)
    {
        String nombre = txt_nombre.getText().toString();
        String precio_string = txt_precio.getText().toString();

        if( !nombre.isEmpty() && !precio_string.isEmpty() )
        {
            Float precio = Float.parseFloat(precio_string);
            PlatilloEntity platillo = new PlatilloEntity(nombre, precio);

            executor.execute(
                    () -> {
                        platilloDao.nuevoPlatillo(platillo);
                        runOnUiThread(
                                () -> {
                                    Toast.makeText(PlatilloActivity.this , "Platillo guardado", Toast.LENGTH_SHORT).show();
                                    ListarPlatillo();
                                }
                        );
                    }
            );
        }

    }

    public void ListarPlatillo()
    {
        executor.execute(
                () -> {
                    List<PlatilloEntity> platillos = platilloDao.listadoPlatillo();
                    runOnUiThread(
                            () -> {
                                ArrayAdapter<PlatilloEntity> adapter = new ArrayAdapter<>(
                                        PlatilloActivity.this,
                                        android.R.layout.simple_list_item_2,
                                        android.R.id.text1,
                                        platillos
                                );
                                lista_platillos.setAdapter(adapter);
                            }
                    );
                }
        );
    }


}