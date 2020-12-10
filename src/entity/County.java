package entity;

import java.util.TreeSet;

/**
 * This entity represents a county,
 * and its cities.
 */
public class County {

    // Fields for County class
    private String countyName;
    private String countyAbbreviation;
    private TreeSet<String> cities;         // TreeSet is useful, because it inserts element in order

    // Constructor with parameters
    public County(String countyName, String countyAbbreviation) {
        this.countyName = countyName;
        this.countyAbbreviation = countyAbbreviation;
        cities = new TreeSet<>();
    }

    // Getters and Setters

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
