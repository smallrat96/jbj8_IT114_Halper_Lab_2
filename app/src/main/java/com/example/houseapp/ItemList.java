package com.example.houseapp;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ItemList {
    //arraylist declaration
    private final ArrayList<House> houseList; //suggestion by vscode to make final

    @Inject
    public ItemList() {
        //array list default constructor + intialization of the houseList array list
        houseList = new ArrayList<>();
    }
    public ArrayList<House> getHouseList() {
        return houseList;
    }
    public void addHouse (House h) {
        houseList.add(h);
    }
    public void clearList(){
        houseList.clear();
    }
    public int size(){
        return houseList.size();
    }
    public boolean removeByLotNum(String lotNum){
        for(int i = 0; i < houseList.size(); i++){
            if (houseList.get(i).getLotnum().equalsIgnoreCase(lotNum.trim())){
                houseList.remove(i);
                return true;
            }
        }
        return false;
    }


}
