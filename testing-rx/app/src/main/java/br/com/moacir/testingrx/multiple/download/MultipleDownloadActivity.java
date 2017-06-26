package br.com.moacir.testingrx.multiple.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.moacir.testingrx.R;
import okhttp3.OkHttpClient;

public class MultipleDownloadActivity extends AppCompatActivity {

    public static final String TAG = MultipleDownloadActivity.class.getSimpleName();

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    public List<MediaInfo> contents = Arrays.asList(
            new MediaInfo("pessoa1.jpg", "http://lorempixel.com/400/200/people/"),
            new MediaInfo("pessoa2.jpg", "http://lorempixel.com/400/200/people/")

    );

    static TextView downloading;
    static TextView completed;
    static TextView error;
    static MediaAdapter mediaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_download);


        DownloadStateReceiver downloadStateReceiver = new DownloadStateReceiver();

        IntentFilter mStatusIntentFilter = new IntentFilter();
        mStatusIntentFilter.addAction(DownloadIntentService.ACTION_NOTIFY_COMPLETED);
        mStatusIntentFilter.addAction(DownloadIntentService.ACTION_NOTIFY_ERROR);
        mStatusIntentFilter.addAction(DownloadIntentService.ACTION_RESULT);

        LocalBroadcastManager.getInstance(this).registerReceiver(downloadStateReceiver, mStatusIntentFilter);

        final ListView listView = (ListView) findViewById(R.id.list_view_media);
        mediaAdapter = new MediaAdapter();
        listView.setAdapter(mediaAdapter);

        Stetho.initializeWithDefaults(this);

        mediaAdapter.setMediaList(contents);

        downloading = (TextView) findViewById(R.id.text_downloading);
        completed = (TextView) findViewById(R.id.text_completed);
        error = (TextView) findViewById(R.id.text_error);

        Button button = (Button) findViewById(R.id.download_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloading.setVisibility(View.VISIBLE);
                completed.setVisibility(View.GONE);
                error.setVisibility(View.GONE);

                DownloadIntentService.startActionDownload(MultipleDownloadActivity.this, new MediaCollection(contents));
            }
        });
    }


    private static final class DownloadStateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DownloadIntentService.ACTION_NOTIFY_COMPLETED)){
                downloading.setVisibility(View.GONE);
                completed.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);
            } else if (intent.getAction().equals(DownloadIntentService.ACTION_NOTIFY_ERROR)){
                downloading.setVisibility(View.GONE);
                completed.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
            } else if (intent.getAction().equals(DownloadIntentService.ACTION_RESULT)){
                downloading.setVisibility(View.GONE);
                completed.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);

                MediaCollection mediaCollection = (MediaCollection) intent.getSerializableExtra(DownloadIntentService.DATA_RESULT_PARAM);

                mediaAdapter.setMediaList(mediaCollection.files);
            }
        }
    }

}
