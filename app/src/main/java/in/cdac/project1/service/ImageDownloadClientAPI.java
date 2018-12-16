package in.cdac.project1.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by q on 16/12/2018.
 */

public interface ImageDownloadClientAPI {

    @GET("yellow-rose-3865041_1280.jpg")
    Call<ResponseBody> downloadImage();
}
