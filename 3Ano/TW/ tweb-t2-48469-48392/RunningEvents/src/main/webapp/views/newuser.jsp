<%@ page language="java" session="true" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>RUNNING EVENTS</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style.css"/>" />
    </head>
    <body>
        
        <div id="intro">
            <hgroup>
                <h1><img src="/static/images/logo.png" alt="logo"></h1>
                <h2>DESCOBRE OS EVENTOS QUE TEMOS PARA TI</h2>
            </hgroup>

            <div id="nav">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/index">INICIO</a></li>
                    <li><a href="${pageContext.request.contextPath}/staff">STAFF</a></li>
                    <li><a href="${pageContext.request.contextPath}/atleta">ATLETA</a></li>
                    <li><a href="${pageContext.request.contextPath}/procurar">Procurar</a></li>
                    <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                </ul>
            </div>
        </div>
                    
                    
        <div align="center">
            <h1>${title}</h1>
            <h2>${message}, new user</h2>

            <form id="form1" method="POST" action="/register">
                username: <input type="text" name="user_name"><br>
                password:<input type="password" name="user_pass"><br>
                name: <input type="text" name="nome"><br>
                gender: 
                <div class="radio-group">
                    <div class="radio">
                        <input type="radio" checked name="genero" value="m">
                        <p class="radio-label">masculine</p>
                    </div>
                    <div class="radio">
                        <input type="radio" name="genero" value="f">
                        <p class="radio-label">feminine</p>
                    </div>
                </div>
                tier:
                <div class="radio-group">
                    <div class="radio-subgroup">
                        <div class="radio">
                            <input type="radio" checked name="escalao" value="junior">
                            <p class="radio-label">junior</p>
                        </div>
                        <div class="radio">
                            <input type="radio" name="escalao" value="senior">
                            <p class="radio-label">senior</p>
                        </div>
                        <div class="radio">
                            <input type="radio" name="escalao" value="vet35">
                            <p class="radio-label">vet35</p>
                        </div>
                        <div class="radio">
                            <input type="radio" name="escalao" value="vet40">
                            <p class="radio-label">vet50</p>
                        </div>
                        <div class="radio">
                            <input type="radio" name="escalao" value="vet65">
                            <p class="radio-label">vet65</p>
                        </div>
                    </div>
                </div>
                <input type="submit" value="send"><br>

            </form>
        </div>
            
        
        <div id="rod">
            <div id="authors">
                <p>Barbara Loureiro l48469 & Gustavo Gomes l48392</p>
            </div>

            <div id="sponsors">
                <p>Patrocinadores:</p>
                <a target="_blank" href="https://www.uevora.pt/">
                    <img id="ue" alt="universidade_evora" src="/static/images/ue.png">
                </a>
                <a target="_blank" href="https://www.mercedes-benz.pt/">
                    <img id="mercedes" alt="mercedes" src="/static/images/mercedes.png">
                </a>
                <a target="_blank" href="https://www.creditoagricola.pt/">
                    <img id="ca" alt="credito_agricola" src="/static/images/ca.png">
                </a>
                <a target="_blank" href="https://www.redbull.com/pt-pt/">
                    <img id="redbull" alt="redbull" src="/static/images/redbull.png">
                </a>
            </div>

            <div id="social_networks">
                <p>Redes Sociais:</p>
                <a target="_blank" href="https://www.facebook.com/">
                    <img id="facebook" alt="facebook" src="/static/images/facebook.png">
                </a>
                <a target="_blank" href="https://twitter.com/?lang=pt">
                    <img id="twitter" alt="twitter" src="/static/images/twitter.png">
                </a>
                <a target="_blank" href="https://pt.linkedin.com/">
                    <img id="linkedIn" alt="linkedIn" src="/static/images/linkedIn.png">
                </a>
                <a target="_blank" href="https://www.instagram.com/">
                    <img id="instagram" alt="instagram" src="/static/images/instagram.png">
                </a>
            </div>
        </div>
    </body>
</html>