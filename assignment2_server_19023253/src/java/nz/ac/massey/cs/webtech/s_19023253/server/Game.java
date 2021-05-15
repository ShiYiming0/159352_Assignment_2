/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_19023253.server;

import java.util.ArrayList;
import java.util.Random;
import com.alibaba.fastjson.JSONObject;
import java.io.*;

/**
 *
 * @author Yiming Shi
 */
public class Game {
    public ArrayList<Pocker> deck = new ArrayList<Pocker>();
    public ArrayList<Pocker>dealer_deck = new ArrayList<Pocker>();
    public ArrayList<Pocker>player_deck = new ArrayList<Pocker>();
    public String[] faces = {"spades", "hearts", "clubs", "diamonds"};
    public String[] numbers = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    public int number_of_games = 0;
    public int player_win_games = 0;
    public int player_score = 0;
    public int dealer_score = 0;
    public boolean player_have_ace = false;
    public boolean dealer_have_ace = false;
    public boolean hand = true;    // true means player's hand, else dealer's hand.
    
    
    // shuffle
    public <Pocker> ArrayList<Pocker> shuffle(ArrayList<Pocker> sourceList){
        ArrayList<Pocker> randomList = new ArrayList<Pocker>(sourceList.size());
            do {
                int randomIndex = Math.abs( new Random( ).nextInt( sourceList.size() ) );
                randomList.add( sourceList.remove( randomIndex ) );
                } while (sourceList.size() > 0);
        return randomList;
    }
    
    // take a card
    public void take_a_card(ArrayList<Pocker> deck, boolean which_side_up, boolean hand){
        Pocker pocker = deck.get(0);
        pocker.setShow(which_side_up);
        deck.remove(0);
        if (hand) {
            if (pocker.getNumber() == 11) {player_have_ace = true;}
            if (player_have_ace) {
                if (player_score + pocker.getNumber() > 21) {   // this is because ace can be both 1 or 11.
                    player_score -= 10;
                    player_have_ace = false;
                }
            }
            player_score  += pocker.getNumber();
            player_deck.add(pocker);
        }
        else {
            if (pocker.getNumber() == 11) {dealer_have_ace = true;}
            if (dealer_have_ace) {
                if (dealer_score + pocker.getNumber() > 21) {
                    dealer_score -=10;
                    dealer_have_ace = false;
                }
            }
            dealer_score  += pocker.getNumber();
            dealer_deck.add(pocker);
        }
    }
    
    //write json
    private void save_json() {
        JSONObject json  = new JSONObject();
        json.put("gamenumber", number_of_games);
        json.put("playerwin", player_win_games);
        String fileName="stats.json";
        try {
            FileWriter writer=new FileWriter(fileName);
            writer.write(json.toJSONString());
            writer.close();
        } catch (Exception e) {}
    }
    
    // load json
    private void load_json() {
        String fileName="stats.json";
        String line="";
        try {
            BufferedReader rd=new BufferedReader(new FileReader(fileName));
            line=rd.readLine();
//            while (line!=null) {
//                System.out.println(line);
//                line=rd.readLine();
//            }
            JSONObject json = JSONObject.parseObject(line);
            number_of_games = (int)json.get("gamenumber");
            player_win_games = (int)json.get("playerwin");
            rd.close();
        } catch (IOException e) { }
    }
             
    // new game
    public void new_game () {
        load_json();
        dealer_deck = new ArrayList<Pocker>();
        player_deck = new ArrayList<Pocker>();
        player_score = 0;
        dealer_score = 0;
        deck = new ArrayList<Pocker>();
        for (int i = 0; i<faces.length; i++) {
            for (int j = 0; j<numbers.length; j++) {
                this.deck.add(new Pocker(faces[i], numbers[j], false));
            }
        }
        deck = shuffle(deck);
        
        take_a_card(deck, true, false);
        take_a_card(deck, false, false);
        
        take_a_card(deck, true, true);
        take_a_card(deck, true, true);
    }
    
    // hit
    public String hit() {
        if (isHand()) {
            if (player_score > 21) {return "400";}
            else {
                take_a_card(deck, true, true);
                return "1";
            }
        }
        else {
            while (dealer_score <= 17) {
                take_a_card(deck, false, false);
            }
            return "404";
        }
    }
    
    //stand
    public String stand() {
        if (!isHand()) {return "404";}
        setHand(false);
        hit();
        for (Pocker p : dealer_deck) {
            p.setShow(true);
        }
        return "1";
    }
    
    //won
    public String won() {
        if (isHand()) {return "None";}  // the game is still in play
        else {
            number_of_games ++;
            save_json();
            if (player_score > 21 && dealer_score <= 21) {return "Dealer";}   //bust
            if (dealer_score >21 && player_score <= 21) {
                player_win_games ++;
                save_json();
                return "Player";
            }
            if (dealer_score >21 && player_score > 21) {return "Draw";}
            if (player_score > dealer_score) {
                player_win_games ++;
                save_json();
                return "Player";     // player win
            }
            else if (player_score < dealer_score) {return "Dealer";}    // dealer win
            else {return "Draw";}   // no winner.
        }
    }
    
    // possible moves
    public String possiblemoves() {
        if (!isHand()) {return "none";}
        else {
            if (player_score < 21) {return "hit, stand";}
            else {return "stand";}
        }
    }
    
    //stats
    public String stats() {
        return "Number of games played: " + number_of_games + "</br>Player win percentage: " + 100*(player_win_games/number_of_games) + "%";
    }
    
    //js querry for display poker
    public String printPocker(String canvas, Pocker pocker, int pos) {
        String js = canvas + ".beginPath();\n" +
                          canvas + ".fillStyle=\"white\";\n" +
                          canvas + ".fillRect(5+"+pos*155+", 5, 150, 200);\n" +
                          canvas + ".stroke();\n" +
                
                          canvas + ".beginPath();\n" +
                          canvas + ".lineWidth=\"3\";\n" +
                          canvas + ".strokeStyle=\"black\";\n" +
                          canvas + ".rect(5+"+pos*155+",5,150,200);\n" +
                          canvas + ".stroke();";
        if(pocker.isShow()) {
            if (pocker.getSuit().equals("spades")) {
                js += "var suit = \"\\u2660\";\n" +
                          canvas + ".fillStyle=\"black\";\n";
            }
            else if (pocker.getSuit().equals("hearts")) {
                js += "var suit = \"\\u2665\";\n" +
                          canvas + ".fillStyle=\"red\";\n";
            }
            else if (pocker.getSuit().equals("clubs")) {
                js += "var suit = \"\\u2663\";\n" +
                          canvas + ".fillStyle=\"black\";\n";
            }
            else if (pocker.getSuit().equals("diamonds")) {
                js += "var suit = \"\\u2666\";\n" +
                          canvas + ".fillStyle=\"red\";\n";
            }
            js += canvas + ".font=\"70px Georgia\";\n" +
                      canvas + ".fillText(eval('\"' + suit + \'"+pocker.number+"\"'),20+"+pos*155+",80);";
            return js;
        }
        else {
            js += canvas + ".beginPath();\n" +
                      canvas + ".fillStyle=\"rgb(126,206,244)\";\n" +
                      canvas + ".fillRect(5+"+pos*155+", 5, 150, 200);\n" +
                      canvas + ".stroke();\n" +
                      canvas + ".font=\"30px Brush Script MT\";\n" +
                      canvas + ".fillStyle=\"black\";\n" +
                      canvas + ".fillText(\"Black Jack\",15+"+pos*165+",90)\n;" +
                      canvas + ".font=\"50px Brush Script MT\";\n" +
                      "var gradient=" + canvas + ".createLinearGradient(0+"+pos*150+",0,50+"+pos*200+",0);\n" +
                      "gradient.addColorStop(\"0\",\"magenta\");\n" +
                      "gradient.addColorStop(\"0.5\",\"blue\");\n" +
                      "gradient.addColorStop(\"1.0\",\"red\");\n" +
                      canvas + ".fillStyle=gradient;\n" +
                      canvas + ".fillText(\"\\u272F\",32+"+pos*182+",130);";
            return js;
        }
    }

    public ArrayList<Pocker> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Pocker> deck) {
        this.deck = deck;
    }

    public ArrayList<Pocker> getDealer_deck() {
        return dealer_deck;
    }

    public void setDealer_deck(ArrayList<Pocker> dealer_deck) {
        this.dealer_deck = dealer_deck;
    }

    public ArrayList<Pocker> getPlayer_deck() {
        return player_deck;
    }

    public void setPlayer_deck(ArrayList<Pocker> player_deck) {
        this.player_deck = player_deck;
    }

    public int getNumber_of_games() {
        return number_of_games;
    }

    public void setNumber_of_games(int number_of_games) {
        this.number_of_games = number_of_games;
    }

    public int getPlayer_win_games() {
        return player_win_games;
    }

    public void setPlayer_win_games(int player_win_games) {
        this.player_win_games = player_win_games;
    }

    public int getPlayer_score() {
        return player_score;
    }

    public void setPlayer_score(int player_score) {
        this.player_score = player_score;
    }

    public int getDealer_score() {
        return dealer_score;
    }

    public void setDealer_score(int dealer_score) {
        this.dealer_score = dealer_score;
    }

    public boolean isHand() {
        return hand;
    }

    public void setHand(boolean hand) {
        this.hand = hand;
    }
    
    
}
