package com.nataliasep.listadelacompra.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nataliasep.listadelacompra.Models.ItemList;
import com.nataliasep.listadelacompra.R;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder>{

    private final List<ItemList> itemLists;

    public ItemListAdapter(List<ItemList> itemLists) {
        this.itemLists = itemLists;
    }

    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ItemListViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ItemListViewHolder holder, int position) {
        ItemList itemList = itemLists.get(position);
        holder.bindItemList(itemList);

    }

    @Override
    public int getItemCount() {
        return itemLists.size();
    }

    public class ItemListViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemName;
        ImageView ivImg;
        CheckBox cbIsPurchased;
        public ItemListViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            ivImg = itemView.findViewById(R.id.ivImg);
            cbIsPurchased = itemView.findViewById(R.id.cbPurchased);
        }

        public void bindItemList(ItemList itemList) {
            tvItemName.setText(itemList.getItem());
            //ivImg.setImageResource(itemList.getImg());
            //cbIsPurchased.setChecked(itemList.getPurchased());
        }
    }


}
