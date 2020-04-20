<%-- 
    Document   : menuPrincipal
    Created on : Apr 13, 2020, 8:52:57 PM
    Author     : unityofdisaster
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu Principal</title>
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.3.1/dist/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.4/dist/semantic.min.css">
        <script src="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.4/dist/semantic.min.js"></script> 

        <style>
            #content {               
                padding-top: 90px;
                min-height: 100px;
            }
            .ui.grid{
                padding: 0 !important;
            }
            .pushable.segment{
                margin: 0 !important;
            }            
        </style>
        <script>
            $(document).ready(function () {
                $('.ui.sidebar').sidebar({
                    context: $('.ui.pushable.segment'),
                    transition: 'overlay'
                }).sidebar('attach events', '#mobile_item');
            });
            function cargar() {
                window.location.hash = "no-back-button";
                window.location.hash = "Again-No-back-button";
                window.onhashchange = function () {
                    window.location.hash = "no-back-button";
                };

            }
        </script>

    </head>
    <body onload="cargar()">
        <c:choose>
            <c:when test="${nombreUsuario != null}">
                <div class="ui grid">

                    <div class="computer only row">
                        <div class="column">
                            <div class="ui inverted fixed icon labeled menu">
                                <div class="header item">Escuela Web</div>
                                <a class="item" href="menuPrincipal.jsp">
                                    <i class="home icon"></i>
                                    Home
                                </a>
                                <a class="item" href="UsuarioServlet?accion=listaDeUsuarios">
                                    <i class="user icon"></i>
                                    Lista de usuarios
                                </a>

                                <a class="item" href="CarreraServlet?accion=listaDeCarreras">
                                    <i class="list alternate outline icon"></i>
                                    Lista de carreras
                                </a>

                                <a class="item" href="AlumnoServlet?accion=listaDeAlumnos">
                                    <i class="list alternate icon"></i>
                                    Lista de alumnos
                                </a>
                                <div class="header item">
                                    <i class="angellist icon"></i>
                                    Bienvenido ${nombreUsuario}
                                </div>

                                <a class="item" href="LoginServlet?accion=cerrarSesion">
                                    <i class="power off icon"></i>
                                    Cerrar Sesi&oacute;n
                                </a>
                            </div>
                        </div>
                    </div>

                    <div class="tablet mobile only row">
                        <div class="column">
                            <div class="ui inverted icon labeled fixed menu">
                                <a id="mobile_item" class="item"><i class="bars icon"></i>Menu</a>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="ui pushable segment fixed">
                    <div class="ui sidebar inverted vertical fixed menu"  style="padding-top: 80px">
                        <div class="header item">Escuela Web</div>
                        <a class="item">
                            <i class="home icon"></i>
                            Home
                        </a>
                        <a class="item active" href="UsuarioServlet?accion=listaDeUsuarios">
                            <i class="user icon"></i>
                            Lista de usuarios
                        </a>

                        <a class="item" href="CarreraServlet?accion=listaDeCarreras">
                            <i class="list alternate outline icon"></i>
                            Lista de carreras
                        </a>

                        <a class="item" href="AlumnoServlet?accion=listaDeAlumnos">
                            <i class="list alternate icon"></i>
                            Lista de alumnos
                        </a>
                        <div class="header item">
                            <i class="angellist icon"></i>
                            Bienvenido
                        </div>

                        <a class="item" href="LoginServlet?accion=cerrarSesion">
                            <i class="power off icon"></i>
                            Cerrar Sesi&oacute;n
                        </a>
                    </div>



                    <!--Contenido de la pagina  -->
                    <div class="pusher">

                        <div id="content" class="ui container">
                            <div class="ui center aligned two column grid">


                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <% response.sendRedirect(request.getServletContext().getContextPath()+ "/index.jsp");%>
                </c:otherwise>
            </c:choose>
    </body>
</html>
