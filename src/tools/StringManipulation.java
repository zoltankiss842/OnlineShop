package tools;

public class StringManipulation {

    public String sanitizeForNumbers(String input){
        input = input.replaceAll("\\s+","");

        for(int i = 0; i < input.length(); ++i){
            if(!Character.isDigit(input.charAt(i))){
                return null;
            }
        }

        return input;
    }
}
