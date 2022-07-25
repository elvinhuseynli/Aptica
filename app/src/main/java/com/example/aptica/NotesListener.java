package com.example.aptica;

public interface NotesListener {
    void onNotesClicked(NotesData notesData);
    void onNotesLongClicked(NotesData notesData, int position);
}