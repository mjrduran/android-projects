package br.com.moacir.testingrx.multiple.download;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class DownloadIntentService extends IntentService {


    private static final String ACTION_DOWNLOAD = "br.com.moacir.testingrx.multiple.download.action.DOWNLOAD";

    private static final String MEDIA_COLLECTION_PARAM = "br.com.moacir.testingrx.multiple.download.extra.MEDIA_COLLECTION_PARAM";



    public static final String ACTION_NOTIFY_COMPLETED = "br.com.moacir.testingrx.multiple.download.action.ACTION_NOTIFY_COMPLETED";

    public static final String ACTION_NOTIFY_ERROR = "br.com.moacir.testingrx.multiple.download.action.ACTION_NOTIFY_ERROR";

    public static final String ACTION_RESULT = "br.com.moacir.testingrx.multiple.download.action.ACTION_RESULT";

    public static final String DATA_RESULT_PARAM = "br.com.moacir.testingrx.multiple.download.action.DATA_RESULT";



    public static final int MAX_CONCURRENT_DOWNLOADS = 5;


    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    public DownloadIntentService() {
        super("DownloadIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionDownload(Context context, MediaCollection mediaCollection) {
        Intent intent = new Intent(context, DownloadIntentService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(MEDIA_COLLECTION_PARAM, mediaCollection);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                final MediaCollection mediaCollection = (MediaCollection) intent.getSerializableExtra(MEDIA_COLLECTION_PARAM);
                handleActionDownload(mediaCollection);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDownload(MediaCollection mediaCollection) {

        download(mediaCollection.files).subscribe(new Subscriber<MediaCollection>() {
            @Override
            public void onCompleted() {
                Intent intent = new Intent();
                intent.setAction(ACTION_NOTIFY_COMPLETED);
                LocalBroadcastManager.getInstance(DownloadIntentService.this).sendBroadcast(intent);
            }

            @Override
            public void onError(Throwable e) {
                Intent intent = new Intent();
                intent.setAction(ACTION_NOTIFY_ERROR);
                LocalBroadcastManager.getInstance(DownloadIntentService.this).sendBroadcast(intent);
            }

            @Override
            public void onNext(MediaCollection mediaCollection) {
                Intent intent = new Intent();
                intent.setAction(ACTION_RESULT);
                intent.putExtra(DATA_RESULT_PARAM, mediaCollection);
                LocalBroadcastManager.getInstance(DownloadIntentService.this).sendBroadcast(intent);
            }
        });
    }

    public Observable<MediaCollection> download(List<MediaInfo> mediaList) {
        Observable<MediaCollection> result = Observable.from(mediaList).flatMap(new Func1<MediaInfo, Observable<MediaInfo>>() {
            @Override
            public Observable<MediaInfo> call(final MediaInfo mediaInfo) {

                final File file = new File(getExternalCacheDir() + File.separator + mediaInfo.name);

//                if (file.exists()) {
//                    return Observable.just(mediaInfo);
//                }

                final Observable<MediaInfo> fileObservable = Observable.create(new Observable.OnSubscribe<MediaInfo>() {
                    @Override
                    public void call(Subscriber<? super MediaInfo> subscriber) {
                        if (subscriber.isUnsubscribed()) {
                            return;
                        }

                        Request request = new Request.Builder().url(mediaInfo.url).build();
                        Response response;
                        try {
                            response = client.newCall(request).execute();
                        } catch (IOException io) {
                            throw OnErrorThrowable.from(OnErrorThrowable.addValueAsLastCause(io, mediaInfo));
                        }

                        if (!subscriber.isUnsubscribed()) {
                            try {
                                BufferedSink sink = Okio.buffer(Okio.sink(file));
                                sink.writeAll(response.body().source());
                                sink.close();
                            } catch (IOException io) {
                                throw OnErrorThrowable.from(OnErrorThrowable.addValueAsLastCause(io, mediaInfo));
                            }

                            mediaInfo.filePath = file.getAbsolutePath();
                            subscriber.onNext(mediaInfo);
                            subscriber.onCompleted();
                        }
                    }
                });
                return fileObservable.subscribeOn(Schedulers.io());
            }
        }, MAX_CONCURRENT_DOWNLOADS).toList().map(new Func1<List<MediaInfo>, MediaCollection>() {
            @Override
            public MediaCollection call(List<MediaInfo> files) {
                return new MediaCollection(files);
            }
        });
        return result;
    }


}
