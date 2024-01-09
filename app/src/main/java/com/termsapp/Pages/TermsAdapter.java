package com.termsapp.Pages;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.termsapp.R;

import java.util.List;

import entites.TermClass;

public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.TermsViewHolder> {
    public List<TermClass> termClassList;
    private final Context context;

    private final LayoutInflater termInflater;
    public TermsAdapter(Context context){
        termInflater = LayoutInflater.from(context);
        this.context = context;
    }
    public class TermsViewHolder extends RecyclerView.ViewHolder{
        private final TextView termViewItem;
        public TermsViewHolder(@NonNull View itemView) {
            super(itemView);
            termViewItem = itemView.findViewById(R.id.termItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final TermClass current = termClassList.get(position);
                    Intent intent = new Intent(context, AddTerms.class);
                    intent.putExtra("Term ID", current.getTermId());
                    intent.putExtra("Term Title", current.getTermTitle());
                    intent.putExtra("Start Date", current.getStart());
                    intent.putExtra("End Date", current.getEnd());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public TermsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = termInflater.inflate(R.layout.terms_item, parent, false);
        return new TermsViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TermsViewHolder holder, int position) {
        if (termClassList != null){
            TermClass current = termClassList.get(position);
            String name = current.getTermTitle();
            holder.termViewItem.setText(name);
        }
        else {
            holder.termViewItem.setText("None Found");
        }
    }

    @Override
    public int getItemCount() {
        if(termClassList != null){
            return termClassList.size();
        }
        else{
            return 0;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTermClassList(List<TermClass> terms){
        termClassList = terms;
        notifyDataSetChanged();
    }


}
