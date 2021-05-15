<%-- 
    Document   : index
    Created on : 2021年5月11日, 下午11:05:03
    Author     : Yiming Shi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Black Jack</title>
        <style type="text/css">
            form, input{margin:0px; display:inline}
            input{height:40px; width:150px; font-size: 15px}
        </style>
    </head>
    <body>
        <h1>Welcome To Black Jack Game!</h1>
        <form action="/jack/start" method="post">
            <input type="submit" value="Start New Game" />
        </form>
    </body>
</html>
