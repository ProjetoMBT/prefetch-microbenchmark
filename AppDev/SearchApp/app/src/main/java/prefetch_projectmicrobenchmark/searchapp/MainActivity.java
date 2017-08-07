package prefetch_projectmicrobenchmark.searchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import prefetch_projectmicrobenchmark.searchapp.TestCaseActivities.*;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //<editor-fold desc="Defining the attributes">
    Class chose;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    //</editor-fold>


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //<editor-fold desc="Setting up the attributes">

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.testcases_array, android.R.layout.simple_spinner_item);


        //</editor-fold>
        spinner.setOnItemSelectedListener(this);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        //String item = parent.getItemAtPosition(position).toString();

        if (position > 0) {
            switch (position) {

                case 1:
                    chose = TestCase1.class;
                    break;
                case 2:
                    chose = TestCase2.class;
                    break;
                case 3:
                    chose = TestCase3.class;
                    break;
                case 4:
                    chose = TestCase4.class;
                    break;
                case 5:
                    chose = TestCase5.class;
                    break;
                case 6:
                    chose = TestCase6.class;
                    break;
                case 7:
                    chose = TestCase7.class;
                    break;
                case 8:
                    chose = TestCase8.class;
                    break;
                case 9:
                    chose = TestCase9.class;
                    break;
                case 10:
                    chose = TestCase10.class;
                    break;
                case 11:
                    chose = TestCase11.class;
                    break;
                case 12:
                    chose = TestCase12.class;
                    break;
                case 13:
                    chose = TestCase13.class;
                    break;
                case 14:
                    chose = TestCase14.class;
                    break;
                case 15:
                    chose = TestCase15.class;
                    break;

                default:

            }

            startActivity(new Intent(MainActivity.this, chose));
            finish();

        }

    }
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}
