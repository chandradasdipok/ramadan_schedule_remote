package com.example.user.ramadan_schedule.utils;

/**
 * Created by chandradasdipok on 6/15/2016.
 */
public class Utils {

    public static String convertDigitToNumberString(int digit){
        digit = Math.abs(digit);
        switch (digit){
            case 1:
                return "One";
            case 2:
                return "Two";
            case 3:
                return "Three";
            case 4:
                return "Four";
            case 5:
                return "Five";
            case 6:
                return "Six";
            case 7:
                return "Seven";
            case 8:
                return "Eight";
            case 9:
                return "Nine";
            case 10:
                return "Ten";
            case 11:
                return "Eleven";
            case 12:
                return "Twelve";
            case 13:
                return "Thirteen";
            case 14:
                return "Fourteen";
            case 15:
                return "Fifteen";
            case 16:
                return "Sixteen";
            case 17:
                return "Seventeen";
            case 18:
                return "Eighteen";
            case 19:
                return "Nineteen";
            case 20:
                return "Twenty";
            case 21:
                return "Twenty One";
            case 22:
                return "Twenty Two";
            case 23:
                return "Twenty Three";
            case 24:
                return "Twenty Four";
            case 25:
                return "Twenty Five";
            case 26:
                return "Twenty Six";
            case 27:
                return "Twenty Seven";
            case 28:
                return "Twenty Eight";
            case 29:
                return "Twenty Nine";
            case 30:
                return "Thirty";
            default:
                return "NAN";
        }
    }
}
