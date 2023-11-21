package com.jackhack2002.mychat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

//import com.github.barteksc.pdfviewer.PDFView;

public class PdfViewerActivity extends AppCompatActivity {

   // private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        // Get the PDFView from the layout
       // pdfView = findViewById(R.id.pdfView);

        // Load the PDF file
        loadPdfFile("path");
    }
    private void loadPdfFile(String fileName) {
        // Load the PDF file from assets folder
       /* pdfView.fromfile(fileName)
                .enableSwipe(true) // Enable swipe gestures
                .swipeHorizontal(false) // Set horizontal scrolling
                .enableDoubletap(true) // Enable double tap to zoom
                .defaultPage(0) // Set default page index
                .load();*/
    }
}
