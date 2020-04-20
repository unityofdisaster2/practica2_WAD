<%-- 
    Document   : listaUsuarios
    Created on : Apr 19, 2020, 12:54:34 PM
    Author     : unityofdisaster
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de usuarios</title>
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.3.1/dist/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.4/dist/semantic.min.css">
        <script src="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.4/dist/semantic.min.js"></script>     
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
        <script>
            $(document).ready(function () {
                $('.ui.sidebar').sidebar({
                    context: $('.ui.pushable.segment'),
                    transition: 'overlay'
                }).sidebar('attach events', '#mobile_item');

            });
        </script>

    </head>
    <body>

        <c:choose>

            <c:when test="${nombreUsuario != null}" >
                <c:choose>
                    <c:when test="${mensaje != null}">
                        <c:if test="${mensaje =='creado'}">
                            <script>
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Operacion exitosa',
                                    text: 'Se ha creado Alumno'
                                });
                            </script>
                        </c:if>
                        <c:if test="${mensaje == 'actualizado'}">
                            <script>
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Operacion exitosa',
                                    text: 'Se ha actualizado Alumno'
                                });
                            </script>

                        </c:if>
                        <c:if test="${mensaje == 'eliminado'}">
                            <script>
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Operacion exitosa',
                                    text: 'Se ha eliminado Alumno'
                                });
                            </script>

                        </c:if>                        

                    </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>

                <div class="ui grid">

                    <div class="computer only row">
                        <div class="column">
                            <div class="ui inverted fixed icon labeled menu">
                                <div class="header item">Gesti&oacute;n Usuarios</div>
                                <a class="item" href="menuPrincipal.jsp">
                                    <i class="home icon"></i>
                                    Home
                                </a>
                                <a class="item" href="UsuarioServlet?accion=nuevo">
                                    <i class="user plus icon"></i>
                                    Nuevo Usuario
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
                        <div class="header item">Gesti&oacute;n Usuarios</div>
                        <a class="item" href="menuPrincipal.jsp">
                            <i class="home icon"></i>
                            Home
                        </a>
                        <a class="item" href="AlumnoServlet?accion=nuevo">
                            <i class="user plus icon"></i>
                            Nuevo Usuario
                        </a>
                    </div>



                    <!--Contenido de la pagina  -->
                    <div class="pusher" style="padding-top: 80px">
                        <div id="content" class="ui container">
                            <table class="ui blue table">
                                <thead>
                                    <tr><th>idUsuario</th>
                                        <th>Nombre</th>
                                        <th>Paterno</th>
                                        <th>Materno</th>
                                        <th>email</th>
                                        <th>imagen</th>
                                        <th>Eliminar</th>
                                        <th>Actualizar</th>
                                        <th>Reporte</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                    <c:forEach items="${listaUsuarios}" var="usuario">
                                        <tr>
                                            <td><c:out value="${usuario.entidad.idUsuario}"></c:out></td>
                                            <td><c:out value="${usuario.entidad.nombre}"></c:out></td>
                                            <td><c:out value="${usuario.entidad.paterno}"></c:out></td>
                                            <td><c:out value="${usuario.entidad.materno}"></c:out></td>
                                            <td><c:out value="${usuario.entidad.email}"></c:out></td>
                                            <td>
                                                <img src="ControladorImagen?id=${usuario.entidad.idUsuario}" height="150" width="150"/>
                                            </td>
                                            <td><a class="ui inverted red button"
                                                       href="UsuarioServlet?accion=eliminar&id=<c:out value='${usuario.entidad.idUsuario}'/> ">
                                                    Eliminar
                                                </a>
                                            </td>
                                            <td><a class="ui inverted primary button"
                                                   href="UsuarioServlet?accion=actualizar&id=<c:out value='${usuario.entidad.idUsuario}' />">
                                                    Actualizar
                                                </a></td>
                                            <td><a class="ui inverted green button" 
                                                   href="UsuarioServlet?accion=generarReporte&id=<c:out value='${usuario.entidad.idUsuario}'/>" target="_blank">
                                                    <i class="file pdf icon"></i> 
                                                </a></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>

                    <%
                        response.sendRedirect(request.getServletContext().getContextPath() + "/index.jsp");
                    %>
                </c:otherwise>
            </c:choose>
        
        
        
    </body>
</html>
