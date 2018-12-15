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

                String url = "https://scontent.fpnq8-1.fna.fbcdn.net/v/t1.0-9/28278948_1599915910097582_8523401707870952260_n.jpg?_nc_cat=103&_nc_ht=scontent.fpnq8-1.fna&oh=fe22db225b3c017a2959e921fc943080&oe=5CA4CC38";
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
