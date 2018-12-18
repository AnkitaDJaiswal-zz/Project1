package in.cdac.project1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;

import in.cdac.project1.service.ImageDownloadClientAPI;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {

    Button button;
    ImageView imageView2;
    String baseUrl = "https://cdn.pixabay.com/photo/2018/12/09/15/06/";
    //For Testing commit changes in git

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button button = (Button) findViewById(R.id.b11);
        imageView2 = (ImageView) findViewById(R.id.imageView2id);
        //ImageView imageView2 = (ImageView) findViewById(R.id.i11);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRetrofitImage();
            }
        });

    }

    void getRetrofitImage(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ImageDownloadClientAPI service = retrofit.create(ImageDownloadClientAPI.class);

        Call<ResponseBody> call = service.downloadImage();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d("OnResponse","Response from Server..."+response);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                        Log.d("Bitmap"," = "+bitmap);
                        setImage(bitmap);
                    } else {
                        Log.d("Repose.body"," = "+response.body());
                    }
                } else {
                    Log.d("Response"," = "+response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("OnFailure",t.toString());
            }
        });

    }

    void setImage(Bitmap bitmap){
        Log.d("in setImage","");
        imageView2.setImageBitmap(bitmap);
    }

}
