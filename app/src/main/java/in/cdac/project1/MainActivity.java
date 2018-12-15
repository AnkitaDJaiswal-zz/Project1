package in.cdac.project1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button button1,button2;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button)findViewById(R.id.b1);
        button2 = (Button)findViewById(R.id.b2);
        imageView = (ImageView)findViewById(R.id.i1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://i.ndtvimg.com/i/2017-12/virat-kohli-and-anushka-sharma-instagram_806x605_61513058442.jpg";
                DownloadImageTask downloadImageTask = new DownloadImageTask();
                downloadImageTask.execute(url);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    class DownloadImageTask extends AsyncTask<String,Void,Bitmap>{
        ProgressDialog progressDialog = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Downloading image...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String str_url = strings[0];
            System.out.print("*****************************************"+str_url);
            Bitmap bitmap = null;
            try{
                URL url = new URL(str_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                System.out.print("*****************************************"+httpURLConnection);
                if(httpURLConnection.getResponseCode() == 200){
                    InputStream inputStream = httpURLConnection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
                httpURLConnection.disconnect();
            }
            catch (Exception exception){
                exception.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if(bitmap!=null){
                imageView.setImageBitmap(bitmap);
            }
            progressDialog.dismiss();
        }
    }

}
