package entity;

import java.util.ArrayList;

public class CountyList {

    private ArrayList<County> countyList;

    public ArrayList<County> getCountyList() {
        return countyList;
    }

    public void setCountyList(ArrayList<County> countyList) {
        this.countyList = countyList;
    }

    public String[] getEveryCounty(){
        String[] temp = new String[this.countyList.size()];
        int flag = 0;
        for(County c : this.countyList){
            temp[flag++] = c.getCountyName();
        }

        return temp;
    }

    public String[] getEveryCityForCountry(County c){
        String[] temp;
        int flag = 0;
        for(County county : this.countyList){
            if(c.equals(county)){
                temp = new String[county.getCities().size()];
                for(String s : county.getCities()){
                    temp[flag++] = s;
                }

                return temp;
            }
        }

        return null;
    }

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

        return null;
    }
}
