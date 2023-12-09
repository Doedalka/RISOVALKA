package com.example.myapplication22;

import androidx.cardview.widget.CardView;

import com.example.myapplication22.Models.Notes;

public interface NotesClickListener {

    void onClick (Notes notes);
    void onLongClick(Notes notes, CardView cardView);
}
