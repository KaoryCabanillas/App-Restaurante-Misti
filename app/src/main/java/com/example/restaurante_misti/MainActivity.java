package com.example.restaurante_misti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.restaurante_misti.Pedido.NuevoPedidoActivity;
import com.example.restaurante_misti.Pedido.PedidosActivity;
import com.example.restaurante_misti.Platillo.PlatilloActivity;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void ViewPlatillo(View view)
    {
        Intent intent = new Intent(this, PlatilloActivity.class);
        startActivity(intent);
    }

    public void ViewNuevoPedido(View view)
    {
        Intent intent = new Intent(this, NuevoPedidoActivity.class);
        startActivity(intent);
    }

    public void ViewPedidos(View view)
    {
        Intent intent = new Intent(this, PedidosActivity.class);
        startActivity(intent);
    }
}