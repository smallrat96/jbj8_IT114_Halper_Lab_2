package com.example.houseapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.File;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


    }

    public void displayHouses(View view) {
        EditText et1 = (EditText) findViewById(R.id.edit_file);
        TextView textView;
        textView = findViewById(R.id.text_main);
        String url = String.valueOf(et1.getText());

        //The try statement to catch an imporperly formatted url or an empty file
        try {
            //URL Object
            URL urlO = new URL(url);
            //Buffered reader attachted to a inputstreamreader. The input stream reader reads the line to the buffered reader and goes line by line in the file
            //to pull out information. I chose this setup for the convience of going line by line because each attribute of the house object is equal
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlO.openStream()));

            House[] houseList = new House[3];
            String line;
            int lineCount = 0;
            int passCount = 0;
            for (int i = 0; i < 3; i++) {
                houseList[i] = new House(); // instantiate all house objects
            }
            while ((line = reader.readLine()) != null) {  //this statement checks to see that the line that is grabbed is not recieved
                //this while statement is essentially a nested for loop :D
                lineCount++;
                if (lineCount == 1) {
                    houseList[passCount].setAddress(line);
                    continue;
                } else if (lineCount == 2) {
                    houseList[passCount].setCity(line);
                    continue;
                } else if (lineCount == 3) {
                    houseList[passCount].setLotnum(line);
                    continue;
                } else if (lineCount == 4) {
                    houseList[passCount].setPrice(Float.parseFloat(line));
                    continue;
                } else if (lineCount == 5) {
                    houseList[passCount].setPtax(Float.parseFloat(line));
                    continue;
                } else if (lineCount == 6) {

                    houseList[passCount].setSqfoot(Integer.parseInt(line));
                    lineCount = 0;
                    passCount++;
                    if (passCount > 2) {
                        break;
                    }
                    continue;
                    /*
                    textView.setText(houseList[passCount].getAddress().concat("\n"));
                    textView.append(houseList[passCount].getCity().concat("\n"));
                    textView.append(houseList[passCount].getLotnum().concat("\n"));
                    textView.append(String.format(Locale.US, "%f3",houseList[passCount].getPtax()).concat("\n"));
                    textView.append(String.format(Locale.US, "%f3", houseList[passCount].getPrice()).concat("\n"));
                    textView.append(String.valueOf(houseList[passCount].getSqfoot()).concat("\n")); */
                }
                /*
                for (int i = 0; i < 3; i++) {

                 */
            }
            textView.setText("");//Reset text view
            double totalSqFt = 0;
            //The url has to have an https:// at the front
            //My sample url docs are at https://web.njit.edu/~jbj8/Sample2.txt and Sample.txt respectively.
            for (int i = 0; i < 3; i++) {
                textView.append(houseList[i].getAddress().concat("\n"));
                textView.append(houseList[i].getCity().concat("\n"));
                textView.append(houseList[i].getLotnum().concat("\n"));
                textView.append(String.format(Locale.US, "%.2f",houseList[i].getPtax()).concat("\n"));
                textView.append(String.format(Locale.US, "%.2f", houseList[i].getPrice()).concat("\n"));
                textView.append(String.valueOf(houseList[i].getSqfoot()).concat("\n"));
                totalSqFt += houseList[i].getSqfoot();
            }
            double avgSqFt = totalSqFt/3;
            double weightedPtax = ((houseList[0].getSqfoot() * houseList[0].getPtax()) + (houseList[1].getSqfoot() * houseList[1].getPtax()) + (houseList[2].getSqfoot() * houseList[2].getPtax()) ) / totalSqFt;
            textView.append("Average Square Feet: " + String.format(Locale.US, "%.3f",avgSqFt) + "\n");
            textView.append("This is the weighted property tax: " + String.format(Locale.US, "%.2f",weightedPtax));





        } catch (MalformedURLException e) { //exception handling for input/output and a malformed url exception
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}



