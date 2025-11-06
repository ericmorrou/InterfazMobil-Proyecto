package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidoViewHolder> {

    private List<Pedido> listaDePedidos;
    private Context context;

    public PedidosAdapter(List<Pedido> listaDePedidos, Context context) {
        this.listaDePedidos = listaDePedidos;
        this.context = context;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = listaDePedidos.get(position);
        holder.textoEstado.setText(pedido.getEstado());
        holder.textoFecha.setText(pedido.getFecha());
        holder.textoTotal.setText(pedido.getPrecioTotal());
        Glide.with(context).load(pedido.getImagenResId()).into(holder.imagenPedido);
    }

    @Override
    public int getItemCount() {
        return listaDePedidos != null ? listaDePedidos.size() : 0;
    }

    public void actualizarPedidos(List<Pedido> nuevosPedidos) {
        this.listaDePedidos.clear();
        if (nuevosPedidos != null) {
            this.listaDePedidos.addAll(nuevosPedidos);
        }
        notifyDataSetChanged();
    }


    public static class PedidoViewHolder extends RecyclerView.ViewHolder {
        TextView textoEstado, textoFecha, textoTotal;
        ImageView imagenPedido;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            textoEstado = itemView.findViewById(R.id.texto_estado_pedido);
            textoFecha = itemView.findViewById(R.id.texto_fecha_pedido);
            textoTotal = itemView.findViewById(R.id.texto_total_pedido);
            imagenPedido = itemView.findViewById(R.id.imagen_pedido);
        }
    }
}
