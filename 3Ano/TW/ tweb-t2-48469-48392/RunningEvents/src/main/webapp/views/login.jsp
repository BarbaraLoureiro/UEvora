<%@ page language="java" session="true"
         contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html >
    <head>
        <title>RUNNING EVENTS </title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style.css"/>" />
            
        </head>
        <body>
            <div id="intro_login">
                <hgroup>
                    <h1><img src="/static/images/logo.png" alt="logo"></h1>
                    <h2>DESCOBRE OS EVENTOS QUE TEMOS PARA TI</h2>
                </hgroup>

                <div id="nav_login">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/index">INICIO</a></li>
                        <li><a href="${pageContext.request.contextPath}/staff">STAFF</a></li>
                        <li><a href="${pageContext.request.contextPath}/atleta">ATLETA</a></li>
                        <li><a href="${pageContext.request.contextPath}/procurar">Procurar</a></li>
                    </ul>
                </div>
            </div>

            <div id="login-box">
                <h2>Login Here</h2>
                <c:if test="${not empty error}">
                    <div class="error">${error}</div>
                </c:if>
                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>
                    <form name='loginForm' id="form_login"
                      action="<c:url value='j_spring_security_check' />" method='POST'>
                    <table>
                        <tr>
                            <td>User:</td>
                            <td><input type='text' name='user_name' value=''></td>
                        </tr>
                        <tr>
                            <td>Password:</td>
                            <td><input type='password' name='user_pass' /></td>
                        </tr>
                        <tr>
                            <td colspan='2'><input name="submit" type="submit"
                                                   value="Submit" /></td>
                        </tr>
                    </table>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}" />
                </form>
            </div>
                
            <p id="perg_conta">Ainda nao tens uma conta?</p>
            <a id="registar_novo_user" href="${pageContext.request.contextPath}/newuser">Regista-te agora</a>

            <div id="rod_login">
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
