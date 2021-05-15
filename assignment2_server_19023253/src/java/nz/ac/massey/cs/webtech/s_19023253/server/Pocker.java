/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_19023253.server;

/**
 *
 * @author Yiming Shi
 */
public class Pocker {
    String suit;
    String number;
    boolean show;
    
    Pocker(String face, String number, boolean show){
        this.suit = face;
        this.number = number;
        this.show= show;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getNumber() {
        int score = 0;
        if (number.equals("A")) {score = 11;}
        else if (number.equals("2")) {score = 2;}
        else if (number.equals("3")) {score = 3;}
        else if (number.equals("4")) {score = 4;}
        else if (number.equals("5")) {score = 5;}
        else if (number.equals("6")) {score = 6;}
        else if (number.equals("7")) {score = 7;}
        else if (number.equals("8")) {score = 8;}
        else if (number.equals("9")) {score = 9;}
        else if (number.equals("10")) {score = 10;}
        else if (number.equals("J")) {score = 10;}
        else if (number.equals("Q")) {score = 10;}
        else if (number.equals("K")) {score = 10;}
        else{score = 0;}
        return score;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
