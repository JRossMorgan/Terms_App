package com.termsapp.Pages;

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

import entites.Notes;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NotesViewHolder> {
    public List<Notes> notesClassList;
    private final Context context;
    private final LayoutInflater noteInflator;
    public NoteAdapter(Context context){
        noteInflator = LayoutInflater.from(context);
        this.context = context;
    }
    public class NotesViewHolder extends RecyclerView.ViewHolder{
        private final TextView noteViewItem;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            noteViewItem = itemView.findViewById(R.id.noteItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Notes current = notesClassList.get(position);
                    Intent intent = new Intent(context, NotesPage.class);
                    intent.putExtra("Note ID", current.getNoteId());
                    intent.putExtra("Note Body", current.getBody());
                    intent.putExtra("Course ID", current.getCourseId());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = noteInflator.inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NotesViewHolder holder, int position) {
        if(notesClassList != null){
            Notes current = notesClassList.get(position);
            String note = current.getBody();
            holder.noteViewItem.setText(note);
        }
        else{
            holder.noteViewItem.setText("None Found.");
        }
    }

    @Override
    public int getItemCount() {
        if(notesClassList != null){
            return notesClassList.size();
        }
        else{
            return 0;
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setNotesClassList(List<Notes> notes){
        notesClassList = notes;
        notifyDataSetChanged();
    }
}
