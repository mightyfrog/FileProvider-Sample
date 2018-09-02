package org.mightyfrog.android.filesharingsample;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * https://developer.android.com/training/secure-file-sharing/setup-sharing.html
 *
 * @author Shigehiro Soejima
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    // android:authorities="${applicationId}.file_provider"
    private static final String PROVIDER_AUTHORITY = BuildConfig.APPLICATION_ID + ".file_provider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareSampleTextFile();
            }
        });
    }

    /**
     *
     */
    private void shareSampleTextFile() {
        String fileName = "time.txt";
        createTempFile(fileName);

        File file = new File(getFilesDir(), fileName);
        Uri uri = FileProvider.getUriForFile(this, PROVIDER_AUTHORITY, file);
        ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain") // change type accordingly
                .addStream(uri);
        startActivity(builder.createChooserIntent());
    }

    /**
     * @param fileName The file name.
     */
    private void createTempFile(String fileName) {
        try (OutputStream out = openFileOutput(fileName, Context.MODE_PRIVATE)) {
            out.write(("time: " + System.currentTimeMillis()).getBytes());
        } catch (IOException e) {
            android.util.Log.e(TAG, e.getMessage());
        }
    }
}