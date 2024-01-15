package com.nataliasep.listadelacompra.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nataliasep.listadelacompra.Interfaces.IOnClickListener;
import com.nataliasep.listadelacompra.Models.ShoppingList;
import com.nataliasep.listadelacompra.R;

import java.util.List;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ListsViewHolder> {

    private final IOnClickListener listener;
    private final List<ShoppingList> lists;

    public ListsAdapter(IOnClickListener listener, List<ShoppingList> lists) {
        this.listener = listener;
        this.lists = lists;
    }


    @NonNull
    @Override
    public ListsAdapter.ListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ListsAdapter.ListsViewHolder holder, int position) {
        ShoppingList list = lists.get(position);
        holder.bindList(list);


    }

    @Override
    public int getItemCount() {
        if (lists != null) {
            return lists.size();
        }
        return 0;
    }

    public void refresh() {
        notifyDataSetChanged();

    }


    public class ListsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView tvListName, tvDate;
        public ListsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListName = itemView.findViewById(R.id.tvListName);
            tvDate = itemView.findViewById(R.id.tvDate);
            itemView.setOnClickListener(this);

        }

        public void bindList(ShoppingList list) {
            tvListName.setText(list.getName());
            tvDate.setText(list.getDate());
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onClick(getAdapterPosition());
            }
        }
    }
}
