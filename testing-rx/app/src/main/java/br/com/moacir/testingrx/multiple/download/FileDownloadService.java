package br.com.moacir.testingrx.multiple.download;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by moacir on 22/10/16.
 */

public interface FileDownloadService {

    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

}
