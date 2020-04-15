<%-- 
    Document   : index
    Created on : Apr 14, 2020, 12:48:27 PM
    Author     : unityofdisaster
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.3.1/dist/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.4/dist/semantic.min.css">
        <script src="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.4/dist/semantic.min.js"></script>     
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>

    </head>
    <script>
        $(document).ready(function () {
            $('#frmLogin')
                    .form({
                        fields: {
                            txtUsuario: {
                                identifier: 'txtUsuario',
                                rules: [
                                    {
                                        type: 'empty',
                                        prompt: 'Por favor ingrese un nombre'
                                    }
                                ]
                            },
                            txtPass: {
                                identifier: 'txtPass',
                                rules: [
                                    {
                                        type: 'empty',
                                        prompt: 'Por favor ingrese una contraseña'
                                    }
                                ]
                            }
                        }
                    });

        });
        function cargar(){
                window.location.hash = "no-back-button";
                window.location.hash = "Again-No-back-button";
                window.onhashchange = function () {
                    window.location.hash = "no-back-button";
                };
            
        }
    </script>

    <body id="cuerpo" onload="cargar()">
        <c:choose>
            <c:when test="${nombreUsuario == null}">    
                <c:choose>
                    <c:when test="${mensajeError != null && mensajeError == true}">
                        <script>
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: 'Something went wrong!'
                            })
                        </script>
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>
                <div class="ui center aligned two column grid">
                    <div class="row">

                    </div>

                    <div class="row">

                    </div>

                    <div class="row">

                    </div>


                    <div class="row">

                    </div>

                    <div class="row">

                    </div>



                    <div class="row">
                        <div class="column">
                            <div class="ui placeholder segment">
                                <div class="ui very relaxed stackable grid">
                                    <div class="column">
                                        <form class="ui form" id="frmLogin" method="POST" action="LoginServlet?accion=iniciarSesion">

                                            <div class="field">
                                                <label>Nombre de usuario</label>
                                                <div class="ui left icon input" >
                                                    <input type="text" name="txtUsuario" id="txtUsuario" >
                                                    <i class="user icon"></i>
                                                </div>
                                            </div>
                                            <div class="field">
                                                <label>Contrase&ntilde;a</label>
                                                <div class="ui left icon input">
                                                    <input type="password" name="txtPass" id="txtPass">
                                                    <i class="lock icon"></i>
                                                </div>
                                            </div>
                                            <button class="ui fluid blue submit button" id="boton" >Login</button>
                                            <div class="ui error message"></div>
                                        </form>

                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                
                <%
                    response.sendRedirect("menuPrincipal.jsp");
                %>
            </c:otherwise>
        </c:choose>

    </body>
</html>
