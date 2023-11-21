package com.jackhack2002.mychat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatBackupActivity extends AppCompatActivity {

    private Button backupButton;
    private GoogleApiClient googleApiClient;
    private Object Drive;
    private Api<? extends Api.ApiOptions.NotRequiredOptions> API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_backup);

        backupButton = findViewById(R.id.backup_button);

        backupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform chat backup to Google Drive
                performChatBackup();
            }
        });

        // Initialize Google Drive API client
        googleApiClient = new GoogleApiClient.Builder(this)
               // .addApi(Drive.API)
                //.addScope(Drive.SCOPE_FILE)
                .addConnectionCallbacks(connectionCallbacks)
                .addOnConnectionFailedListener(connectionFailedListener)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    private void performChatBackup() {
        // Check if Google Drive API client is connected
        if (!googleApiClient.isConnected()) {
            Toast.makeText(this, "Google Drive not connected", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new backup file on Google Drive
       // Drive.DriveApi.newDriveContents(googleApiClient)
               // .setResultCallback(driveContentsCallback);
    }

    private final GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {
            // Google Drive API client connected
        }

        @Override
        public void onConnectionSuspended(int i) {
            // Connection to Google Drive API client suspended
        }
    };

    private final GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }
        // @Override
        //public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            // Connection to Google Drive API client failed
      //  }
    };

    private final ResultCallback<DriveApi.DriveContentsResult> driveContentsCallback = new ResultCallback<DriveApi.DriveContentsResult>() {
        @Override
        public void onResult(@NonNull DriveApi.DriveContentsResult driveContentsResult) {
            if (!driveContentsResult.getStatus().isSuccess()) {
                Toast.makeText(ChatBackupActivity.this, "Error creating backup file", Toast.LENGTH_SHORT).show();
                return;
            }

            final DriveContents driveContents = driveContentsResult.getDriveContents();

            // Write chat data to the backup file
            Executor executor = Executors.newSingleThreadExecutor();
            Task<Void> writeTask = Tasks.call(executor, () -> {
                try (OutputStream outputStream = driveContents.getOutputStream();
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                    // Replace with your code to write chat data to the file
                    // Example: writer.write(chatData);
                    writer.write("This is a chat backup file.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            });

            writeTask.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Create metadata for the backup file
                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                            .setTitle("ChatBackup.txt") // Replace with your desired file name
                            .setMimeType("text/plain")
                            .build();

                    // Create the backup file on Google Drive
                  //  Drive.DriveApi.getRootFolder(googleApiClient)
                   //         .createFile(googleApiClient, changeSet, driveContents)
                     //       .setResultCallback(fileCallback);
                }
            });
        }
    };

    private final ResultCallback<DriveFolder.DriveFileResult> fileCallback = new ResultCallback<DriveFolder.DriveFileResult>() {
        @Override
        public void onResult(@NonNull DriveFolder.DriveFileResult driveFileResult) {
            if (!driveFileResult.getStatus().isSuccess()) {
                Toast.makeText(ChatBackupActivity.this, "Error creating backup file", Toast.LENGTH_SHORT).show();
                return;
            }

            DriveFile backupFile = driveFileResult.getDriveFile();
            DriveId backupFileId = backupFile.getDriveId();

            Toast.makeText(ChatBackupActivity.this, "Chat backup created: " + backupFileId, Toast.LENGTH_SHORT).show();
        }
    };
}
