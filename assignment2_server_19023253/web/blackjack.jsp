<%-- 
    Document   : index
    Created on : 2021年5月8日, 下午5:21:34
    Author     : Yiming Shi
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.PrintWriter, nz.ac.massey.cs.webtech.s_19023253.server.*, com.alibaba.fastjson.JSONObject, java.util.*"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Black Jack</title>
        <style type="text/css">
            form, input{margin:0px; display:inline}
            input{height:40px; width:150px; background-color: brown; color: white; font-size: 15px}
            body{background-color: green}
            h1,h2,h3,p{color: white}
        </style>
    </head>
    <body>
        <h1>Black Jack</h1>
        
        <%
            Game game = (Game)session.getAttribute("game");
            String won = (String)session.getAttribute("won");
            String number_of_games = (String)session.getAttribute("number_of_games");
            String rate = (String)session.getAttribute("rate");
            String possiblemoves = (String)session.getAttribute("possiblemoves");
            String state = (String)session.getAttribute("state");
            Map map =  JSONObject.parseObject(state); 
        %>
        
        <div>
            <p>Dealer's cards</p>
            <p>Score: <% if (game.isHand()){ %>
                              <%="?"%>
                              <% } else {  %>
                              <% try { %>
                              <%= map.get("dealer") %>
                              <% } catch(Exception e) {} %>
                              <% } %></p>
            <canvas id="dealercanvas" width="1510" height="210"></canvas>"
        </div>
        <div>
            <p>Player's cards</p>
            <p>Score: 
                <% try{ %>
                <%= map.get("player") %>
                <% } catch (Exception e) {} %>
            </p>
            <canvas id="playercanvas" width="1510" height="210"></canvas>
        </div>
        <% if (!game.isHand()) { %>
        <form action="/jack/start" method="post">
            <input type="submit" value="Start New Game" />
        </form>
        <% } if (game.possiblemoves().contains("hit")) { %>
        <form action="/jack/move/hit" method="post">
            <input type="submit" value="Hit" />
        </form>
        <% } if (game.possiblemoves().contains("stand")) { %>
        <input type="button" value="Stand" onclick="stand()" />
        <% } %>
        </br></br>
        <form action="/jack/state" method="get">
            <input type="submit" value="State" />
        </form>
        <form action="/jack/stats" method="get">
            <input type="submit" value="Stats" />
        </form>
        <form action="/jack/possiblemoves" method="get">
            <input type="submit" value="Possible moves" />
        </form>
            
        <h2>
                <% try { %>
                <% if (won.equals("Player")) { %>
                <%= "Player win!" %>
                <% } else if (won.equals("Dealer")) { %>
                <%= "Dealer win!" %>
                <% } else if (won.equals("Draw")) { %>
                <%= "Draw! No winner." %>
                <% } else {} %>
                <%} catch (Exception e) {}%>
        </h2>
        <h3>
            <% try { %>
            Number of game played: <%= number_of_games %></br>
            Player win rate: <%= rate %>
            <% } catch (Exception e) { } %>
        </h3>
        <% try { %>
        <h3>Possible moves: <%= possiblemoves %></h3>
        <% } catch (Exception e) { } %>
        
    </body>
    <script>
        var canvas1 = document.getElementById('dealercanvas').getContext('2d');
        var canvas2 = document.getElementById('playercanvas').getContext('2d');
 
        <% for (int i = 0; i < game.dealer_deck.size(); i++) { %>
            <%= game.printPocker("canvas1", game.dealer_deck.get(i), i) %>
        <% } %>
        <% for (int i = 0; i < game.player_deck.size(); i++) { %>
            <%= game.printPocker("canvas2", game.player_deck.get(i), i) %>
        <% } %>
    </script>
    <script type="text/javascript">
            var xmlhttp

            function stand() {
                xmlhttp = new XMLHttpRequest();
                xmlhttp.open("POST", "/jack/move/stand", true);
                xmlhttp.send();
                xmlhttp.open("GET", "/jack/won", true);
                xmlhttp.send();
                location.reload();
                location.reload();
            }
        </script>
    
</html>


