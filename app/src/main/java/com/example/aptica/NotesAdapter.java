package com.example.aptica;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aptica.NotesData;
import com.example.aptica.NotesListener;
import com.example.aptica.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{

    List<NotesData> notesData;
    NotesListener notesListener;

    public NotesAdapter(List<NotesData> notesData, NotesListener notesListener) {
        this.notesData = notesData;
        this.notesListener = notesListener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.note_container, parent, false);
        NotesViewHolder notesViewHolder = new NotesViewHolder(contactView);
        return notesViewHolder;
    }

    public void removeItem(int position) {
        notesData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.setData(notesData.get(position));
    }

    @Override
    public int getItemCount() {
        return notesData.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView title, note, time;
        ConstraintLayout layout;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textView1);
            note = (TextView) itemView.findViewById(R.id.textView2);
            time = (TextView) itemView.findViewById(R.id.textView4);
            layout = (ConstraintLayout) itemView.findViewById(R.id.layout);
        }

        @SuppressLint("SetTextI18n")
        public void setData(NotesData notesData) {
            title.setText(notesData.title);
            note.setText(notesData.note);
            time.setText("Updated: " + notesData.date);
            layout.setOnClickListener(view-> {notesListener.onNotesClicked(notesData);});
            layout.setOnLongClickListener(view-> {notesListener.onNotesLongClicked(notesData, getAbsoluteAdapterPosition());
                return true;
            });
        }

    }
}