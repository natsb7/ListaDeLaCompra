package com.nataliasep.listadelacompra.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nataliasep.listadelacompra.Interfaces.IOnClickListener;
import com.nataliasep.listadelacompra.Models.Item;
import com.nataliasep.listadelacompra.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private final ArrayList<Item> items;

    private final IOnClickListener listener;


    public ItemAdapter(ArrayList<Item> items, IOnClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_item, parent, false);
        return new ItemViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bindItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView ivImage;
        private final TextView tvProductName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivProduct);
            tvProductName = itemView.findViewById(R.id.tvProductName);
        }

        public void bindItem(Item item) {
            //ivImage.setImageResource(item.getImg());
            tvProductName.setText(item.getName());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(getAdapterPosition());
        }
    }
}
