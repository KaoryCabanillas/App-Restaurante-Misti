package com.example.restaurante_misti;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

public class PedidosActivity extends AppCompatActivity {

    ListView lista_pedidos;
    AppDatabase db;
    ExecutorService executor;
    private PedidoDao pedidoDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pedidos);

        lista_pedidos = findViewById(R.id.lista_pedidos);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "Restaurante-Misti"
        ).fallbackToDestructiveMigration().build();

        pedidoDao = db.pedidoDao();
        executor = Executors.newSingleThreadExecutor();

        listarPedidos();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void listarPedidos() {
        executor.execute(
                () -> {
                    try {
                        List<PedidoListadoEntity> pedidos = pedidoDao.ListadoPedidos();

                        runOnUiThread(
                                () -> {
                                    ArrayAdapter<PedidoListadoEntity> adapter = new ArrayAdapter<>(
                                            PedidosActivity.this,
                                            android.R.layout.simple_list_item_2,
                                            android.R.id.text1,
                                            pedidos
                                    );
                                    lista_pedidos.setAdapter(adapter);
                                    Log.d("", "InsertarPlatillo: LISTADO DE PLATILLOS");

                                }
                        );
                    } catch (Exception e)
                    {
                        runOnUiThread(() -> {
                            Log.e("PedidosActivity", "Error al listar pedidos", e);
                            Toast.makeText(PedidosActivity.this, "Error al listar pedidos", Toast.LENGTH_SHORT).show();
                        });
                    }
                }
        );
    }
}