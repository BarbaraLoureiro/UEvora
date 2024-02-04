<%@ page language="java" session="true"
    contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="">
  <head>
    <title>RUNNING EVENTS</title>
    <link rel="stylesheet" type="text/css" href="<c:url value ="/static/css/style.css"/>"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

  </head>
  <body>
      
    <div id="intro_listar">
      <hgroup>
          <h1><img src="/static/images/logo.png" alt="logo"></h1>
          <H2>DESCOBRE OS EVENTOS QUE TEMOS PARA TI</H2>
      </hgroup>

      <div id="nav">
        <ul>
            <li><a href="${pageContext.request.contextPath}/index">INICIO</a></li>
            <li><a href="${pageContext.request.contextPath}/staff">STAFF</a></li>
            <li><a href="${pageContext.request.contextPath}/atleta">ATLETA</a></li>
            <li><a href="${pageContext.request.contextPath}/procurar">Procurar</a></li>
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
            </c:if>
          </ul>
          <c:if test="${pageContext.request.userPrincipal.name != null}">
              <p id="username_nav">
                Bem-vindo
                , ${pageContext.request.userPrincipal.name} | <a
                        href="<c:url value='logout'/>">Logout</a>
            </p>
          </c:if>
      </div>
    </div>
            
           

    <div id="criar_evento">
        <form id="form" name="form" action="/registarevento" method="post" onsubmit="return validate_evento()">
          <h3>Criar Evento</h3>
          <p>
              <b>Nome do Evento:</b>
              <input type="text" id="evento_name" name="evento_name">
 
          </p>
          <p>
              <b>Descricao:</b>
              <input type="text" id="descricao" name="descricao">
            
          </p>
          <p>
            <b>Data:</b>
            <input type="date" id="data" name="data">
          <p>
          <p>
              <b>Preco:</b>
              <input type="text" id="preco" name="preco">
          </p>
          <input id="submeter_evento" type="Submit" value="Submeter">
        </form>  
    </div>
    
    <div id="criar_tempo">

    <form id="" action="/registarTempo" method="post">
        <h3>Registar Tempo</h3>
        <p>
            <b>Nome do Evento:</b>
            <input type="text" id="evento_name" name="evento_name">
        </p>
        <p>
            <b>Dorsal do Participante:</b>
            <input type="number" id="dorsal" name="dorsal">
        </p>
        <p>
            <b>Ponto da Prova:</b>
            <input type="text" id="sectionID" name="sectionID">
        </p>
        <p>
            <b>Tempo:</b>
            <input type="text" id="elapsed_time" name="elapsed_time">
        </p>
       
        <input id="submeter_tempo" type="submit" value="Submeter">
    </form></div>

    <div id="rod_listar">
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
    <script>
        
        function validate_evento(){
            var evento_name = document.getElementById("evento_name").value;

            if (evento_name.length < 1) {
                alert("Atributo nome em falta!");
                return false;
            }
            
            var descricao = document.getElementById("descricao").value;

            if (descricao.length < 1) {
                alert("Atributo descricao em falta!");
                return false;
            }
            
            var data = document.getElementById("data").value;

            if (data.length < 1) {
                alert("Atributo data em falta!");
                return false;
            }
            
            var preco = document.getElementById("preco").value;

            if (preco.length < 1) {
                alert("Atributo preco em falta!");
                return false;
            }
            
            return true;
        }

    </script>
  </body>
</html>
