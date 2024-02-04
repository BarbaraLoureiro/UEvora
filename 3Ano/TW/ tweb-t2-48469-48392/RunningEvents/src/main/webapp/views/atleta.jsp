<%@ page language="java" session="true"
    contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="">
  <head>
    
    <title>RUNNING EVENTS</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style.css"/>" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    
    

  </head>
  <body>
    <div id="intro_listar">
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

    <div id="results">
        <h2>Eventos Ativos</h2>
        <table>
            <thead>
                <tr>
                    <th>Nome do Evento</th>
                    <th>Descricao</th>
                    <th>Data</th>
                    <th>Preco</th>
                    <th>Inscricao</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="evento" items="${eventos}">
                    <tr>
                        <td>${evento.eventoName}</td>
                        <td>${evento.descricao}</td>
                        <td>${evento.data}</td>
                        <td>${evento.preco} euros</td>

                        <td><form action="/inscrever" method="post">
                            <input type="hidden" name="eventoName" value="${evento.eventoName}" />
                            <input type="submit" value="Inscrever" />
                            <p>Referencia MB para pagamento: ${mbRef}</p>
                        </form></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div id="results2">
    <h2>As Minhas Inscricoes</h2>
<table>
    <thead>
        <tr>
            <th>Nome do Evento</th>
            <th>Status</th>
            <th>Referencia MB</th>
            <th>Acao</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="inscricao" items="${inscricoes}">
            <tr>
                <td>${inscricao.eventoName}</td>
                <td>${inscricao.paga ? 'Paga' : 'Nao paga'}</td>
                <td>${inscricao.mbRef}</td>
                <td>
                    <c:if test="${!inscricao.paga}">
                        <form action="${pageContext.request.contextPath}/pagar" method="post">
                            <input type="hidden" name="inscricaoDorsal" value="${inscricao.inscricaoDorsal}">
                            <input type="submit" value="Pagar">
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
    </div>

    <div id="rod_listar">
      <div id="authors">
          <p>Barbara Loureiro l48729 & Gustavo Gomes l48681</p>
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
