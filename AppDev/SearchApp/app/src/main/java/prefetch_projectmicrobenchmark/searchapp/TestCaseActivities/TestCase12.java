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
import java.util.Random;

import prefetch_projectmicrobenchmark.searchapp.R;
import prefetch_projectmicrobenchmark.searchapp.ResultActivity;

public class TestCase12 extends AppCompatActivity {


    //<editor-fold desc="Defining the attributes">
    EditText fieldName;
    EditText fieldAdress1;
    EditText fieldAdress2;
    Button buttonLockIn1;
    Button buttonSearchId4;
    String id;
    String name;
    String adress;
    String nameURL;
    String apiKey;
    String urlJson;
    Intent intent;
    Search search;
    URL url;
    //</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_case12);


        //<editor-fold desc="Setting up the attributes">
        intent = new Intent(this, ResultActivity.class);

        nameURL = "https://radiant-gorge-51458.herokuapp.com/restaurants/?name=";
        apiKey = "&address=";

        buttonLockIn1 = (Button) findViewById(R.id.id_button_lockin1);
        buttonSearchId4 = (Button) findViewById(R.id.id_button_search);
        fieldAdress1 = (EditText) findViewById(R.id.id_field_adress);
        fieldAdress2 = (EditText) findViewById(R.id.id_field_adress2);
        fieldName = (EditText) findViewById(R.id.id_field_name);
        buttonSearchId4.setEnabled(false);

        //</editor-fold>


        //<editor-fold desc="Button to search with 2 parameters, name and adress">
        buttonLockIn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                name = fieldName.getText().toString();
                adress = fieldAdress1.getText().toString();





                buttonSearchId4.setEnabled(true);

            }
        });
        //</editor-fold>


        buttonSearchId4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(!fieldAdress2.getText().toString().equals("")){
                    adress = fieldAdress2.getText().toString();

                }

                if(adress.charAt(adress.length()-1)==' '){
                    adress = adress.substring(0,adress.length()-1);
                }

                if(name.charAt(name.length()-1)==' '){
                    name = name.substring(0,name.length()-1);
                }

                urlJson = nameURL+name+apiKey+adress;


                try {
                    url = new URL(urlJson.replace(" ",""));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                search = new Search();
                search.execute(url);

                buttonSearchId4.setEnabled(false);
            }
        });

    }

    //---------------------------------------------------------------------
    //-------------------------------AUXILIARY-----------------------------
    //---------------------------------------------------------------------

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
