package com.droidvisuals.notesappfirebase;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText titleEditText,contentEditText;
    ImageButton saveNoteBtn;
    TextView pageTitleView;

    String title,content ,docId;
    boolean isEditMode = false;

    TextView deleteNoteTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titleEditText = findViewById(R.id.notes_title_text);
        contentEditText = findViewById(R.id.notes_content_text);
        saveNoteBtn = findViewById(R.id.save_note_btn);
        pageTitleView = findViewById(R.id.page_title);
        deleteNoteTv = findViewById(R.id.delete_note_text_view_btn);


        // To receive data from the intent !
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        // editmode is true only if the user is coming from already created note !
        if(docId!= null && !docId.isEmpty()){
            isEditMode = true;
        }

        if(isEditMode){
            pageTitleView.setText("Edit your note");
            deleteNoteTv.setVisibility(View.VISIBLE);

        }

        titleEditText.setText(title);
        contentEditText.setText(content);

        deleteNoteTv.setOnClickListener((v)->deleteNoteFromFirebase());



        saveNoteBtn.setOnClickListener((v)->saveNote());
    }

    private void deleteNoteFromFirebase() {
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // note is added
                    Utility.showToast(NoteDetailsActivity.this,"Note deleted successfully");
                    finish();
                }
                else{
                    // note failed to add
                    Utility.showToast(NoteDetailsActivity.this,"Failed while deleting note");
                }
            }

        });

    }

    private void saveNote() {
        String noteTitle = titleEditText.getText().toString();
        String noteContent = contentEditText.getText().toString();

        if(noteTitle==null || noteTitle.isEmpty()){
            titleEditText.setError("Title is required");
            return;
        }


        Note note = new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());

        saveNoteToFirebase(note);

    }

    private void saveNoteToFirebase(Note note) {

        DocumentReference documentReference;

        if(isEditMode){
            // to update the docId note
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        }else{
            // no edit > create new note
            documentReference = Utility.getCollectionReferenceForNotes().document();
        }


        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    // note is added
                    Utility.showToast(NoteDetailsActivity.this,"Note added successfully");
                    finish();
                }
                else{
                    // note failed to add
                    Utility.showToast(NoteDetailsActivity.this,"Failed while adding note");
                }
            }

        });
    }
}