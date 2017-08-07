package prefetch_projectmicrobenchmark.searchapp.TestCaseActivities;

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

import prefetch_projectmicrobenchmark.searchapp.R;
import prefetch_projectmicrobenchmark.searchapp.ResultActivity;

public class TestCase3 extends AppCompatActivity {

    //<editor-fold desc="Defining the attributes">
    EditText field3;
    Button buttonSearchId1;
    Button buttonSearchId2;
    Button buttonSearchId3;

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
        setContentView(R.layout.activity_test_case3);

        //<editor-fold desc="Setting up the attributes">
        intent = new Intent(this, ResultActivity.class);

        nameURL = "http://api.openweathermap.org/data/2.5/weather?units=Imperial&q="; //provisório
        idURL = "http://api.openweathermap.org/data/2.5/weather?units=Imperial&id=";  //provisório
        apiKey = "&APPID=f46f62442611cdc087b629f6e87c7374";                           //provisório

        field3 = (EditText) findViewById(R.id.id_fieldID);
        buttonSearchId1 = (Button) findViewById(R.id.id_button_search1);
        buttonSearchId2 = (Button) findViewById(R.id.id_button_search2);
        buttonSearchId3 = (Button) findViewById(R.id.id_button_search3);

        //</editor-fold>

        //<editor-fold desc="First search with 1 parameter, ID">
        buttonSearchId1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                id = field3.getText().toString();

                urlJson = idURL+id+apiKey;

                try {
                    url = new URL(urlJson);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                search = new Search();
                search.execute(url);
            }
        });
        //</editor-fold>

        //<editor-fold desc="Second search with 1 parameter, ID">
        buttonSearchId2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                id = field3.getText().toString();

                secondSearch(id);
            }
        });
        //</editor-fold>

        //<editor-fold desc="Third search with 1 parameter, ID">
        buttonSearchId3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                urlJson = idURL+getID()+apiKey;

                try {
                    url = new URL(urlJson);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                search = new Search();
                search.execute(url);
            }
        });
        //</editor-fold>
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

    public String getID(){
        return field3.getText().toString();
    }

    public void secondSearch(String id){
        urlJson = idURL+id+apiKey;

        try {
            url = new URL(urlJson);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        search = new Search();
        search.execute(url);
    }

}
