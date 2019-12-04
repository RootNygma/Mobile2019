package comasynktask.android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTaskImageDownloader extends AsyncTask <String,Integer, Bitmap> {
    private Activity activity;

    public AsyncTaskImageDownloader(Activity myActivity) {
        activity = myActivity;
    }
    @Override
    protected Bitmap doInBackground(String... strings) {
        Log.i("ASYNCTASK", "hello1");
        publishProgress(1);
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed to connect");
            }
            InputStream is = con.getInputStream();
            publishProgress(0);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        } catch (Exception e) {
            Log.e("Image", "Failed to load image", e);
            Log.e("error", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.i("ASYNCTASKIMAGEDOWNLOAD", "in on post execute 1 ...");
        ImageView iv = (ImageView) activity.findViewById(R.id.remote_image);
        Log.i("ASYNCTASKIMAGEDOWNLOAD", "after findVewById ...");
        if (iv != null && bitmap != null) {
            Log.i("ASYNCTASKIMAGEDOWNLOAD", "bitmap found");
            iv.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        TextView tv = (TextView) activity.findViewById(R.id.tv_loading);
        if (values[0] == 1) {
            tv.setText("Loading...");
        } else {
            tv.setText("");
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
