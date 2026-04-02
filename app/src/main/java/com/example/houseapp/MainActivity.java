/*
Jack Johnson
This is my Project 1
Notes: Copied from house app not list processor
Key files:
Asset File Name: SampleData1.txt
AFS File Name: https://web.njit.edu/~jbj8/SampleData3.txt
 */
package com.example.houseapp;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.ArrayList;
@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    //hilt

    @Inject
    ItemList itemList;
    private EditText et1;
    private TextView textView;
    private Button button;
    private int option;
    private final String newConstruct = "new construction";


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
        et1 = findViewById(R.id.edit_file);
        textView = findViewById(R.id.text_main);
        button = findViewById(R.id.button);
        et1.setVisibility(View.GONE);
        textView.setText("Welcome to Jack Johnson's Project 1");
        button.setVisibility(View.GONE);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public void buttonPressed(View view){
        if (option == 2){
            loadWebFile();
        }
        else if (option == 3){
            loadAssetFile();
        }
        else if(option == 4){
            showHouseDetails();
        }
        else if(option == 5){
            removeHouse();
        }
    }

    public void onOption1(MenuItem item) {
        //Display List View of Houses
        et1.setVisibility(View.GONE); //https://stackoverflow.com/questions/60808899/setvisibility-function-in-android-studio-studio-java-parameter
        button.setVisibility(View.GONE);
        displayList();

        //View.GONE takes away layout space and the et1 elemetn making it essentailly disappear
    }
    public void onOption2(MenuItem item) {
        //Load from web file
        et1.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        et1.setHint("What filename?");
        option = 2;
    }
    public void onOption3(MenuItem item) {
        //Add House from asset file
        et1.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        et1.setHint("What Asset File Name");
        option = 3;
    }

    public void onOption4(MenuItem item) {
        //Show House Details
        et1.setVisibility(View.VISIBLE);
        et1.setText("");//clear the input field
        button.setVisibility(View.VISIBLE);
        et1.setHint("What lot #");
        option = 4;

    }
    public void onOption5(MenuItem item) {
        et1.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        et1.setHint("What lot # to be removed");
        option = 5;
        //Remove House

    }
    public void onOption6(MenuItem item) {
        //Weighted Avg Property Tax
        et1.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        showWeightedAvg();
    }
    public void onOption7(MenuItem item) {
        //Number of Mansions
        et1.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        countMansions();
    }


    public void countMansions(){
        ArrayList<House> list = itemList.getHouseList();
        int count = 0;
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).getSqfoot() >= 7000){
                count++; //increment count if house has greater than 7000 square feet
            }
        }
        textView.setText("Mansions: " + count);

    }
    public void showWeightedAvg(){
        ArrayList<House> list = itemList.getHouseList();
        // top/bottom == sqfoot * ptax/ sqfoot (a summation of the sqfoot * ptax of every house and the sqfoot.

        double top = 0;
        double bottom = 0;

        for (int i =0; i < list.size(); i++){
            top += list.get(i).getSqfoot() * list.get(i).getPtax();
            bottom += list.get(i).getSqfoot();
        }
        if(bottom == 0) { //nothing loaded in the list
            textView.setText("No data loaded");
            return; //i explained this implementation
        }

        double result = top/bottom;

        textView.setText("Weighted Avg Tax:: " + result);

    }
    public void removeHouse(){
        String inputLotNum = et1.getText().toString().trim(); //used trim to cut whitespace
        boolean response = itemList.removeByLotNum(inputLotNum);
        if (response) {
            textView.setText("House Removed!");
        }
        else {
            textView.setText("No House by that lot num!");
        }
    }
    public void displayList(){
        ArrayList<House> houseList = itemList.getHouseList();
        if(houseList != null){
            textView.setText("");
            for (int i = 0; i < houseList.size(); i++ ) {
                textView.append(houseList.get(i).getAddress().concat(", "));
                textView.append(houseList.get(i).getCity().concat(", "));
                textView.append(houseList.get(i).getLotnum().concat(", "));
                textView.append(String.valueOf(houseList.get(i).getSqfoot()).concat(", "));
                textView.append(String.valueOf(houseList.get(i).getYearBuilt()).concat("\n"));
            }
        }
        else{
            textView.setText("No houses loaded");
        }

    }
    public void showHouseDetails(){
        String lot = et1.getText().toString();

        ArrayList<House> list = itemList.getHouseList();
        House temp = null;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLotnum().trim().equalsIgnoreCase(lot.trim())) {
                temp = list.get(i);
                break;
            }
        }

        if (temp == null) {
            textView.setText("lot # invalid");
            return;
        }
        textView.setText("");
        textView.append("Address: " + temp.getAddress() + "\n");
        textView.append("City: " + temp.getCity() + "\n");
        textView.append("Price: " + temp.getPrice() + "\n");
        textView.append("Property Tax: " + temp.getPtax() + "\n");
        textView.append("Sqft: " + temp.getSqfoot() + "\n");

        double exlcuded = temp.getTaxExclusion(temp.getPtax());
        if (exlcuded > 0){
            textView.append("Tax Exclusion: " + exlcuded + "\n");
        }
        //no else needed just skip if 0
        int age = temp.getAge();
        if (age == 0){
            textView.append(newConstruct+ "\n");
        }
        else{
            textView.append("Age: " + temp.getAge() + "\n");
        }


    }
    public void loadAssetFile() { // *Removed* The IO exception is for the assetManager.open(infilename) because if the file name is not found an exception will be thrown
        String infilename;
        try {
            AssetManager am = getAssets();
            infilename = et1.getText().toString().trim();
            BufferedReader reader = new BufferedReader(new InputStreamReader(am.open(infilename)));
            String line;
            int lineCount = 0;
            House temp = new House();
            while ((line = reader.readLine()) != null) {  //this statement checks to see that the line that is grabbed is not recieved

                lineCount++;
                if (lineCount == 1) {
                    temp.setAddress(line);
                    continue;
                } else if (lineCount == 2) {
                    temp.setCity(line);
                    continue;
                } else if (lineCount == 3) {
                    temp.setLotnum(line);
                    continue;
                } else if (lineCount == 4) {
                    temp.setPrice(Float.parseFloat(line.trim()));
                    continue;
                } else if (lineCount == 5) {
                    temp.setPtax(Float.parseFloat(line.trim()));
                    continue;
                } else if (lineCount == 6) {

                    temp.setSqfoot(Integer.parseInt(line.trim()));


                    continue;
                } else if (lineCount == 7) {
                    temp.setYearBuilt(Integer.parseInt(line.trim()));
                    lineCount = 0;
                    itemList.addHouse(temp);
                    temp = new House(); //reset the house object
                    continue;

                }

            }
            reader.close();
            textView.setText("Succesfully Loaded");

        } catch (Exception e) {
            textView.setText("No asset file by that name :(");
        }
    }


    public void loadWebFile(){

        String url = et1.getText().toString().trim();
        itemList.clearList();
        //make sure list is cleared befoer a new file is loaded
        //The try statement to catch an imporperly formatted url or an empty file
        try {
            //URL Object
            URL urlO = new URL(url);
            //Buffered reader attachted to a inputstreamreader. The input stream reader reads the line to the buffered reader and goes line by line in the file
            //to pull out information. I chose this setup for the convience of going line by line because each attribute of the house object is equal
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlO.openStream()));


            String line;
            int lineCount = 0;
           // int passCount = 0;
            House temp = new House();
            while ((line = reader.readLine()) != null) {  //this statement checks to see that the line that is grabbed is not recieved
                //this while statement is essentially a nested for loop :D
                lineCount++;
                if (lineCount == 1) {
                    temp.setAddress(line);
                    continue;
                } else if (lineCount == 2) {
                    temp.setCity(line);
                    continue;
                } else if (lineCount == 3) {
                    temp.setLotnum(line);
                    continue;
                } else if (lineCount == 4) {
                    temp.setPrice(Float.parseFloat(line.trim()));
                    continue;
                } else if (lineCount == 5) {
                    temp.setPtax(Float.parseFloat(line.trim()));
                    continue;
                } else if (lineCount == 6) {

                    temp.setSqfoot(Integer.parseInt(line.trim()));


                    continue;
                } else if (lineCount == 7) {
                    temp.setYearBuilt(Integer.parseInt(line.trim()));
                    lineCount = 0;
                    itemList.addHouse(temp);
                    temp = new House();
                    continue;

                }

            }
            reader.close();
            textView.setText("Succesfully Loaded");







        } catch (MalformedURLException e) { //exception handling for input/output and a malformed url exception
            textView.setText("Error loading web file");
        } catch (IOException e) {
            textView.setText("Error loading web file");
        }
    }
}



