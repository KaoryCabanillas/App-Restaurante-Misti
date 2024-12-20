package com.example.restaurante_misti.Orden;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.restaurante_misti.AppDatabase;
import com.example.restaurante_misti.Pedido.NuevoPedidoActivity;
import com.example.restaurante_misti.Pedido.PedidoDao;
import com.example.restaurante_misti.Pedido.PedidoDetalleEntity;
import com.example.restaurante_misti.Pedido.PedidoEntity;
import com.example.restaurante_misti.Platillo.PlatilloDao;
import com.example.restaurante_misti.Platillo.PlatilloEntity;
import com.example.restaurante_misti.R;
import com.example.restaurante_misti.Utils.SpinnerUtilClass;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OrdenActivity extends AppCompatActivity {

    private AppDatabase db;
    private Executor executor;
    private PedidoDao pedidoDao;
    private EditText txt_cantidad;
    private TextView txt_precio_unitario, txt_subtotal, txt_total;
    private Spinner spn_platillo;
    private int pedido_id;
    private List<PlatilloEntity> platillos;
    private PlatilloDao platilloDao;
    private OrdenDetalleEntity ordenDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orden);

        pedido_id = getIntent().getIntExtra("pedido_id",-1);

        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "Restaurante-Misti"
        ).fallbackToDestructiveMigration().build();

        pedidoDao = db.pedidoDao();
        platilloDao = db.platilloDao();
        executor = Executors.newSingleThreadExecutor();

        txt_cantidad = findViewById(R.id.txt_orden_cantidad);
        txt_precio_unitario = findViewById(R.id.txt_orden_preciou);
        txt_subtotal = findViewById(R.id.txt_orden_subtotal);
        txt_total = findViewById(R.id.txt_orden_total);
        spn_platillo = findViewById(R.id.spn_orden_platillo);

        cargarDatosOrden(pedido_id);
        listarPlatillos();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void cargarDatosOrden(int pedido_id)
    {
        executor.execute(
                () -> {
                    try {
                        ordenDetalle = pedidoDao.ordenDetalles(pedido_id);

                        runOnUiThread(() -> {
                            if (ordenDetalle != null) {
                                PedidoEntity pedido = ordenDetalle.pedido;
                                List<PedidoDetalleEntity> detalles = ordenDetalle.detalle_pedido;

                                // Ejemplo de asignación de datos a componentes
                                txt_cantidad.setText(detalles.get(0).getCantidad() + "");
                                txt_subtotal.setText("S/ " + detalles.get(0).getSubtotal());
                                txt_total.setText("S/ " + pedido.getTotal());

                                int pos = getPosicionPlatilloByID(platillos, detalles.get(0).getPlatillo_id());
                                spn_platillo.setSelection(pos);
                            }
                        });

                    } catch (Exception e) {
                        runOnUiThread(() -> {
                            Log.e("OrdenActivity", "Error al cargar datos de la orden", e);
                        });
                    }
                }
        );
    }

    private void listarPlatillos() {
        executor.execute(() -> {
            try {
                this.platillos = platilloDao.listadoPlatillo();
            }
            catch (Exception e) {
                Log.e("NuevoPedidoActivity", "Error al listar los platillos", e);
            }
        });
    }

    private int getPosicionPlatilloByID(List<PlatilloEntity> platillos, int id)
    {
        for (int i = 0; i < platillos.size(); i++)
        {
            if (platillos.get(i).getId() == id)
            {
                return i;  // Retorna la posición del platillo en la lista
            }
        }
        return 0;
    }



}