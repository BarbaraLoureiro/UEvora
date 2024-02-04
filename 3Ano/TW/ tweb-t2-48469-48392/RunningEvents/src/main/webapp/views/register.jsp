<%@ page language="java" session="true"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>RUNNING EVENTS</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<c:url value="https://www.w3schools.com/w3css/4/w3.css"/>"/>
        <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style.css"/>" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        
        <div id="intro_register">
      <hgroup>
          <h1><img src="/static/images/logo.png" alt="logo"></h1>
          <h2>DESCOBRE OS EVENTOS QUE TEMOS PARA TI</h2>
      </hgroup>

      <div id="nav_register">
          <ul>
            <li><a href="${pageContext.request.contextPath}/index">INICIO</a></li>
            <li><a href="${pageContext.request.contextPath}/staff">STAFF</a></li>
            <li><a href="${pageContext.request.contextPath}/atleta">ATLETA</a></li>
            <li><a href="${pageContext.request.contextPath}/procurar">Procurar</a></li>
          </ul>
      </div>
    </div>
        
        <div>
            <h1>${title} at registration</h1>
            <h2>${message}, at registration</h2>                
            <p><a href="<c:url value='login'/>">Login</a></p>
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