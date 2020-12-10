package entity;

import java.util.ArrayList;

/**
 * This class represents a list of counties
 * @see County
 */
public class CountyList {

    // Fields for CountyList class
    private ArrayList<County> countyList;

    // Default Constructor
    public CountyList() {
        countyList = new ArrayList<>();
    }

    // Constructor with parameters
    public CountyList(ArrayList<County> countyList) {
        this.countyList = countyList;
    }

    // Getters and Setters

    public ArrayList<County> getCountyList() {
        return countyList;
    }

    public void setCountyList(ArrayList<County> countyList) {
        this.countyList = countyList;
    }


    /**
     * Returns an array of the county names.
     *
     * @return  array of String containing the county names
     */
    public String[] getEveryCounty(){
        String[] temp = new String[this.countyList.size()];
        int flag = 0;

        for(County c : this.countyList){
            temp[flag++] = c.getCountyName();
        }

        return temp;

    }

    /**
     * Returns an array of the cities of a specified county.
     * If the parameter is not found, it returns an array, which contains
     * one item, saying, "No cities for county countyName"
     *
     * @param countyName
     * @return
     */
    public String[] getEveryCityForCountry(String countyName){
        String[] temp;
        int flag = 0;
        for(County county : this.countyList){
            if(countyName.equals(county.getCountyName())){
                temp = new String[county.getCities().size()];
                for(String s : county.getCities()){
                    temp[flag++] = s;
                }

                return temp;
            }
        }

        return new String[]{"Nincs hozzátartozó város " + countyName + " megyéhez"};
    }
}
