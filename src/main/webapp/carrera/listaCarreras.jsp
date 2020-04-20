<%-- 
    Document   : listaCarreras
    Created on : Apr 19, 2020, 12:53:44 PM
    Author     : unityofdisaster
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de carreras</title>

        <script src="https://cdn.jsdelivr.net/npm/jquery@3.3.1/dist/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.4/dist/semantic.min.css">
        <script src="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.4/dist/semantic.min.js"></script>     
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
        <script>
            $(document).ready(function(){
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
                                    text: 'Se ha creado carrera'
                                })
                            </script>
                        </c:if>
                        <c:if test="${mensaje == 'actualizado'}">
                            <script>
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Operacion exitosa',
                                    text: 'Se ha actualizado carrera'
                                })
                            </script>

                        </c:if>
                        <c:if test="${mensaje == 'eliminado'}">
                            <script>
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Operacion exitosa',
                                    text: 'Se ha eliminado carrera'
                                })
                            </script>

                        </c:if>                        
                        
                    </c:when>
                    <c:otherwise></c:otherwise>
                </c:choose>
                            
                <div class="ui grid">

                    <div class="computer only row">
                        <div class="column">
                            <div class="ui inverted fixed icon labeled menu">
                                <div class="header item">Gesti&oacute;n Carreras</div>
                                <a class="item" href="menuPrincipal.jsp">
                                    <i class="home icon"></i>
                                    Home
                                </a>
                                <a class="item" href="CarreraServlet?accion=nuevo">
                                    <i class="plus square outline icon"></i>
                                    Nueva Carrera
                                </a>

                                <a class="item" href="CarreraServlet?accion=reporteGeneral" target="_blank">
                                    <i class="list alternate outline icon"></i>
                                    Reporte general
                                </a>

                                <a class="item" href="CarreraServlet?accion=verGrafica" target="_blank">
                                    <i class="chart pie icon"></i>
                                    Gr&aacute;fica
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
                        <div class="header item">Gesti&oacute;n Carreras</div>
                        <a class="item" href="menuPrincipal.jsp">
                            <i class="home icon"></i>
                            Home
                        </a>
                        <a class="item" href="CarreraServlet?accion=nuevo">
                            <i class="user icon"></i>
                            Nueva Carrera
                        </a>

                        <a class="item" href="CarreraServlet?accion=reporteGeneral" target="_blank">
                            <i class="list alternate outline icon"></i>
                            Reporte General
                        </a>

                        <a class="item" href="CarreraServlet?accion=verGrafica" target="_blank">
                            <i class="list alternate icon"></i>
                            Gr&aacute;fica
                        </a>
                    </div>



                    <!--Contenido de la pagina  -->
                    <div class="pusher" style="padding-top: 80px">
                        <div id="content" class="ui container">
                            <table class="ui blue table">
                                <thead>
                                    <tr><th>idCarrera</th>
                                        <th>nombreCarrera</th>
                                        <th>descripcion</th>
                                        <th>duracion</th>
                                        <th>Eliminar</th>
                                        <th>Actualizar</th>
                                        <th>Reporte</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaCarreras}" var="carrera">
                                        <tr>
                                            <td><c:out value="${carrera.entidad.idCarrera}"></c:out></td>
                                            <td><c:out value="${carrera.entidad.nombreCarrera}"></c:out></td>
                                            <td><c:out value="${carrera.entidad.descripcion}"></c:out></td>
                                            <td><c:out value="${carrera.entidad.duracion}"></c:out></td>
                                                <td><a class="ui inverted red button"
                                                       href="CarreraServlet?accion=eliminar&id=<c:out value='${carrera.entidad.idCarrera}'/> ">
                                                    Eliminar
                                                </a>
                                            </td>
                                            <td><a class="ui inverted primary button"
                                                   href="CarreraServlet?accion=actualizar&id=<c:out value='${carrera.entidad.idCarrera}' />">
                                                    Actualizar
                                                </a></td>
                                            <td><a class="ui inverted green button" 
                                                   href="CarreraServlet?accion=generarReporte&id=<c:out value='${carrera.entidad.idCarrera}'/>" target="_blank">
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
