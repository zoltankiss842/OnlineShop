package tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringManipulation {

    private final String lettersAndDigitsRegex = "[^a-zA-ZöÖüÜóÓőŐúÚűŰáÁéÉ0-9.\\s-]";
    private final String phoneNumberRegex = "[^0-9\\+-/]";
    private final String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    private final String houseNumberRegex = "[^a-zA-ZöÖüÜóÓőŐúÚűŰáÁéÉ0-9.-\\/\\s]";
    private final String cardNumberRegex = "[^0-9]";

    public String sanitizeForNumbers(String input){
        input = input.replaceAll("\\s+","");
        input = input.replaceAll(",","");

        for(int i = 0; i < input.length(); ++i){

            if(!Character.isDigit(input.charAt(i))){
                return null;
            }
        }

        return input;
    }

    public int containsExactAmountDigit(String s, int amount){
        if(s.length()>=amount){
            String firstFour = s.substring(0, amount);
            for(int i =0; i < firstFour.length(); ++i){
                if(Character.isDigit(s.charAt(i))){
                    amount--;
                }

            }
        }

        return amount;

    }

    public boolean isValidString(String s, boolean acceptEmptyString){
        if(!acceptEmptyString && s.length() == 0){
            return false;
        }

        Pattern pattern = Pattern.compile(lettersAndDigitsRegex);
        Matcher matcher = pattern.matcher(s);

        if(matcher.find()){
            return false;
        }

        return true;
    }

    public boolean isValidPhoneNumber(String s, boolean acceptEmptyString){
        if(!acceptEmptyString && s.length() == 0){
            return false;
        }

        Pattern pattern = Pattern.compile(phoneNumberRegex);
        Matcher matcher = pattern.matcher(s);

        if(matcher.find()){
            return false;
        }

        return true;
    }

    public boolean isValidEmail(String s, boolean acceptEmptyString){
        if(!acceptEmptyString && s.length() == 0){
            return false;
        }

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(s);

        if(matcher.find()){
            return true;
        }

        return false;
    }

    public boolean isValidHouseNumber(String s, boolean acceptEmptyString){
        if(!acceptEmptyString && s.length() == 0){
            return false;
        }

        Pattern pattern = Pattern.compile(houseNumberRegex);
        Matcher matcher = pattern.matcher(s);

        if(matcher.find()){
            return false;
        }

        return true;
    }

    public boolean isValidCardNumber(String s, boolean acceptEmptyString){
        if(!acceptEmptyString && s.length() == 0){
            return false;
        }

        if(s.length() < 12 || s.length() > 19){
            return false;
        }

        Pattern pattern = Pattern.compile(cardNumberRegex);
        Matcher matcher = pattern.matcher(s);

        if(matcher.find()){
            return false;
        }

        return true;
    }

    public boolean isValidPostCode(String s, boolean acceptEmptyString){
        if(!acceptEmptyString && s.length() == 0){
            return false;
        }

        if(s.length() != 4){
            return false;
        }

        Pattern pattern = Pattern.compile(cardNumberRegex);
        Matcher matcher = pattern.matcher(s);

        if(matcher.find()){
            return false;
        }

        return true;
    }

    public String sanitizeString(String s){
        StringBuilder result = new StringBuilder();
        boolean wasOneSpace = false;

        for(int i = 0; i < s.length();++i){
            char c = s.charAt(i);
            if(!Character.isSpaceChar(c) && !Character.isLetterOrDigit(c)){
                continue;
            }

            if(Character.isSpaceChar(c) && !wasOneSpace){
                result.append(c);
                wasOneSpace = true;
            }

            if(Character.isSpaceChar(c) && wasOneSpace){
                continue;
            }

            if(Character.isLetterOrDigit(c)){
                result.append(c);
                wasOneSpace = false;
            }
        }


        return result.toString();
    }
}
