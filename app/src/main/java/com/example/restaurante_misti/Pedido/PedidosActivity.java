package com.example.restaurante_misti.Pedido;

import android.content.Intent;
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

import com.example.restaurante_misti.AppDatabase;
import com.example.restaurante_misti.Orden.OrdenActivity;
import com.example.restaurante_misti.R;

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
                        List<PedidoListadoEntity> pedidos = pedidoDao.ListadoPedidosHechos();

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

                                    lista_pedidos.setOnItemClickListener((parent, view, position, id) -> {

                                        PedidoListadoEntity pedidoSeleccionado = (PedidoListadoEntity) parent.getItemAtPosition(position);
                                        int pedidoID = pedidoSeleccionado.getId_pedido();

                                        //Redirigir pantalla con el id segun el pedido clickeado
                                        Intent intentDetallePedido = new Intent(this, OrdenActivity.class);
                                        intentDetallePedido.putExtra("pedido_id", pedidoID);
                                        startActivity(intentDetallePedido);


                                    });
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