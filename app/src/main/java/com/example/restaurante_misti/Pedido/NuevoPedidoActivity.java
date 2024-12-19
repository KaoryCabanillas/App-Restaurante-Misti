package com.example.restaurante_misti.Pedido;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.restaurante_misti.Mesa.MesaDao;
import com.example.restaurante_misti.Mesa.MesaEntity;
import com.example.restaurante_misti.Mesero.MeseroDao;
import com.example.restaurante_misti.Mesero.MeseroEntity;
import com.example.restaurante_misti.Platillo.PlatilloDao;
import com.example.restaurante_misti.Platillo.PlatilloEntity;
import com.example.restaurante_misti.R;
import com.example.restaurante_misti.Utils.SpinnerUtilClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NuevoPedidoActivity extends AppCompatActivity {

    private AppDatabase db;
    private MeseroDao meseroDao;
    private PlatilloDao platilloDao;
    private MesaDao mesaDao;
    private PedidoDao pedidoDao;
    private ExecutorService executor;
    private TextView txtPrecio;
    private EditText txtCantidad;
    private Spinner sp_meseros, sp_platillo, sp_mesas;
    private List<PlatilloEntity> platillos;
    private List<MeseroEntity> meseros;
    private List<MesaEntity> mesas;
    private int platillo_id, mesa_id, mesero_id;
    private float platillo_precio;
    private List<PedidoDetalleEntity> detalles_pedidos;

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
        pedidoDao = db.pedidoDao();

        executor = Executors.newSingleThreadExecutor();

        sp_meseros = findViewById(R.id.sp_mesero);
        sp_platillo = findViewById(R.id.sp_platillo);
        sp_mesas = findViewById(R.id.sp_mesas);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtCantidad = findViewById(R.id.txtCantidad);

        detalles_pedidos = new ArrayList<>();

        validarMeseros(); // valida, si no hay inserta y lista, y si no, solo lista
        validarMesas(); // valida, si no hay inserta y lista, y si no, solo lista
        listarPlatillos();
        capturarSeleccionPlatillo();
        capturarSeleccionMesa();
        capturarSeleccionMeseros();


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
                            listarMeseros();
                        }
                        else {
                            listarMeseros();
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
                            listarMesas();
                        }
                        else {
                            listarMesas();
                        }
                    } catch (Exception e) {
                        Log.d("", "InsertarMesas: ERROR AL INSERTAR LAS MESAS", e);
                    }
                });
    }

    private void listarMeseros() {
        executor.execute(() -> {
            try {
                this.meseros = meseroDao.listadoMeseros();
                // Usar la función genérica para listar meseros
                SpinnerUtilClass.listarElementos(this, executor, meseros, sp_meseros, MeseroEntity::getNombre);
            } catch (Exception e) {
                Log.e("NuevoPedidoActivity", "Error al listar los meseros", e);
            }
        });
    }

    private void listarPlatillos() {
        executor.execute(() -> {
            try {
                this.platillos = platilloDao.listadoPlatillo();

                // Usar la función genérica para listar platillos
                SpinnerUtilClass.listarElementos(this, executor, platillos, sp_platillo, PlatilloEntity::getNombre);
            } catch (Exception e) {
                Log.e("NuevoPedidoActivity", "Error al listar los platillos", e);
            }
        });
    }

    private void listarMesas() {
        executor.execute(() -> {
            try {
                this.mesas = mesaDao.listadoMesas();
                // Usar la función genérica para listar mesas
                SpinnerUtilClass.listarElementos(this, executor, mesas, sp_mesas, MesaEntity::getN_mesa);
            } catch (Exception e) {
                Log.e("NuevoPedidoActivity", "Error al listar las mesas", e);
            }
        });
    }

    private void capturarSeleccionPlatillo()
    {
        sp_platillo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Obtener el PlatilloEnti0ty completo usando la posición
                PlatilloEntity platilloSeleccionado = platillos.get(position);

                // Asignar el id del platillo seleccionado
                platillo_id = platilloSeleccionado.getId();
                platillo_precio = platilloSeleccionado.getPrecio();

                Log.d("", "onItemSelected: "+ platillo_id);

                // Obtener y mostrar el precio del platillo seleccionado
                String precio = "S/"+platilloSeleccionado.getPrecio();
                txtPrecio.setText(precio);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                txtPrecio.setText("----");
            }
        });
    }

    private void capturarSeleccionMesa()
    {
        sp_mesas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MesaEntity mesaSeleccionada = mesas.get(position);
                mesa_id = mesaSeleccionada.getId();
                Log.d("", "onItemSelected: "+mesa_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void capturarSeleccionMeseros()
    {
        sp_meseros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MeseroEntity meseroSeleccionado = meseros.get(position);
                mesero_id = meseroSeleccionado.getId();
                Log.d("", "onItemSelected: "+mesero_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void agregarDetallePedido(View view)
    {
        try {
            String cantidad_string = this.txtCantidad.getText().toString();

            if(!cantidad_string.isEmpty())
            {
                sp_meseros.setEnabled(false);
                sp_mesas.setEnabled(false);

                int cantidad = Integer.parseInt(cantidad_string);

                if(cantidad > 0) {

                    float subtotal = cantidad * platillo_precio;

                    PedidoDetalleEntity nuevo_detalle = new PedidoDetalleEntity(cantidad, subtotal, platillo_id);
                    Log.d("", "agregarDetallePedido: " + nuevo_detalle);
                    detalles_pedidos.add(nuevo_detalle);
                    txtCantidad.setText("");
                    Toast.makeText(NuevoPedidoActivity.this, "PLATILLO AGREGAGADO AL PEDIDO!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(NuevoPedidoActivity.this , "INGRESE UNA CANTIDAD MAYOR A 0!", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(NuevoPedidoActivity.this , "CAMPOS INCOMPLETOS!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("NuevoPedidoActivity", "agregarDetallePedido", e);
        }
    }

    public void guardarPedido(View view)
    {
        if(!detalles_pedidos.isEmpty())
        {
            executor.execute(
                    () -> {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date fecha = new Date();
                            String fecha_formateada = sdf.format(fecha);

                            PedidoEntity nuevoPedido = new PedidoEntity(this.mesa_id, this.mesero_id, fecha_formateada);
                            pedidoDao.insertarPedidoConDetalles(nuevoPedido, detalles_pedidos);

                            detalles_pedidos.clear();

                            runOnUiThread(() -> {
                                sp_meseros.setEnabled(true);
                                sp_mesas.setEnabled(true);
                                Toast.makeText(NuevoPedidoActivity.this, "PEDIDO REGISTRADO.", Toast.LENGTH_SHORT).show();
                            });

                        } catch (Exception e) {
                            Log.d("", "InsertarPedido: ERROR AL INSERTAR EN HILOS", e);
                        }
                    }
            );
        }
        else
        {
            Toast.makeText(NuevoPedidoActivity.this, "AGREGUE ALGUN PLATILLO.", Toast.LENGTH_SHORT).show();
        }
    }
}