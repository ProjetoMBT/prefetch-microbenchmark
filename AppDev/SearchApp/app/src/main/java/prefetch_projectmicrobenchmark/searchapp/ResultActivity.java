package prefetch_projectmicrobenchmark.searchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


public class ResultActivity extends AppCompatActivity {

    //<editor-fold desc="Defining the attributes">
    TextView result1; //restaurant_id
    TextView result2; //name
    TextView result3; //cuisine
    TextView result4; //street
    TextView result5; //zipcode
    TextView result6; //borough
    Button button_back;
    String json;
    JSONObject jsonObject;
    //</editor-fold>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //<editor-fold desc="Setting up the attributes">
        result1 = (TextView) findViewById(R.id.id_textView_results1);
        result2 = (TextView) findViewById(R.id.id_textView_results2);
        result3 = (TextView) findViewById(R.id.id_textView_results3);
        result4 = (TextView) findViewById(R.id.id_textView_results4);
        result5 = (TextView) findViewById(R.id.id_textView_results5);
        result6 = (TextView) findViewById(R.id.id_textView_results6);
        button_back = (Button) findViewById(R.id.id_button_back);

        json = getIntent().getStringExtra("json");
        //</editor-fold>

        //<editor-fold desc="Retrieving the data from JSON">
        if(json != null){

            try {
                //recebe o JSON vindo da outra INTENT.
                jsonObject = new JSONObject(json);


                //tem que mudar as strings, para restaurant_id, name, cuisine, adress.street, adress.zipcode e borough.
                result1.setText(jsonObject.getString("id"));
                result2.setText(jsonObject.getString("name"));
                result3.setText(jsonObject.getString("base"));
                result4.setText(jsonObject.getJSONObject("main").getString("temp"));
                result5.setText(jsonObject.getJSONArray("weather").getJSONObject(0).getString("main"));
                result6.setText(jsonObject.getJSONObject("main").getString("humidity"));


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        //</editor-fold>

        //<editor-fold desc="Button to return to MainActivity">
        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                finish();

            }
        });
        //</editor-fold>

    }
}
