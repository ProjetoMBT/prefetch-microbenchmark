package prefetch_projectmicrobenchmark.searchapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestCase5 extends AppCompatActivity {

    //<editor-fold desc="Defining the attributes">
    EditText field3;
    EditText field4;
    Button buttonLockIn1;
    Button buttonSearchId4;

    String id;
    String nameURL;
    String idURL;
    String apiKey;
    String urlJson;
    Intent intent;
    Search search;
    URL url;
    //</editor-fold>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //<editor-fold desc="Setting up the attributes">
        intent = new Intent(this, ResultActivity.class);

        nameURL = "http://api.openweathermap.org/data/2.5/weather?units=Imperial&q="; //provisório
        idURL = "http://api.openweathermap.org/data/2.5/weather?units=Imperial&id=";  //provisório
        apiKey = "&APPID=f46f62442611cdc087b629f6e87c7374";                           //provisório

        field3 = (EditText) findViewById(R.id.id_field3);
        field4 = (EditText) findViewById(R.id.id_field4);

        buttonLockIn1 = (Button) findViewById(R.id.id_button_lockIn_1);
        buttonSearchId4 = (Button) findViewById(R.id.id_button_searchID_4);
        //</editor-fold>

        buttonLockIn1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {
            id = field3.getText().toString();

            urlJson = idURL + id + apiKey;
          }
        });

        buttonSearchId4.setOnClickListener(new View.OnClickListener() {
          public void onClick(View view) {
            if(!field4.getText().toString().equals(""))
            {
              id = field4.getText().toString();

              urlJson = idURL + id + apiKey;
            }

            try {
                url = new URL(urlJson);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            search = new Search();
            search.execute(url);
          }
        });
    }

    //<editor-fold desc="Method to send the request">
    private class Search extends AsyncTask<URL, Void, Map<String, String>> {
        @Override
        protected Map<String, String> doInBackground(URL... urlPar){
            Map<String, String> result = new HashMap<String, String>();
                URL url = urlPar[0];
                if(url != null){
                    Log.e("url", url.toString());
                    try {
                        URLConnection urlConnection = url.openConnection();
                        InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder outString = new StringBuilder();
                        String line;
                        while((line = bufferedReader.readLine()) != null){
                            outString.append(line);
                        }
                        inputStream.close();

                        result.put("json", outString.toString().trim());
                        Log.e("json", result.get("json"));



                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            return result;
        }

        @Override
        protected void onPostExecute(Map<String, String> result){
            try {
                if(result.containsKey("json")){
                    //insere o JSON na INTENT
                    intent.putExtra("json", result.get("json"));
                }

                //inicia uma nova intent e fecha a atual
                startActivity(intent);
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    //</editor-fold>
}