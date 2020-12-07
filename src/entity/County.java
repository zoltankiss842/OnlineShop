package entity;

import java.util.HashSet;
import java.util.TreeSet;

public class County {

    private String countyName;
    private String countyAbbreviation;
    private TreeSet<String> cities;

    public County(String countyName, String countyAbbreviation) {
        this.countyName = countyName;
        this.countyAbbreviation = countyAbbreviation;
        cities = new TreeSet<>();
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCountyAbbreviation() {
        return countyAbbreviation;
    }

    public void setCountyAbbreviation(String countyAbbreviation) {
        this.countyAbbreviation = countyAbbreviation;
    }

    public TreeSet<String> getCities() {
        return cities;
    }

    public void setCities(TreeSet<String> cities) {
        this.cities = cities;
    }



    @Override
    public String toString() {
        return "County{" +
                "countyName='" + countyName + '\'' +
                ", countyAbbreviation='" + countyAbbreviation + '\'' +
                ", cities=" + cities +
                '}';
    }
}
