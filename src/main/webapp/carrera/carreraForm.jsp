<%-- 
    Document   : carreraForm
    Created on : Apr 19, 2020, 12:53:54 PM
    Author     : unityofdisaster
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Formulario Carrera</title>
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.3.1/dist/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.4/dist/semantic.min.css">
        <script src="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.4/dist/semantic.min.js"></script>     
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
        <script>
            $(document).ready(function () {
                $('#formulario')
                        .form({
                            fields: {
                                txtNombreCarrera: {
                                    identifier: 'txtNombreCarrera',
                                    rules: [
                                        {
                                            type: 'empty',
                                            prompt: 'Por favor ingrese el nombre de carrera'
                                        }
                                    ]
                                },
                                txtDescripcion: {
                                    identifier: 'txtDescripcion',
                                    rules: [
                                        {
                                            type: 'empty',
                                            prompt: 'Por favor ingrese una descripcion'
                                        }
                                    ]
                                },
                                txtDuracion: {
                                    identifier: 'txtDuracion',
                                    rules: [
                                        {
                                            type: 'empty',
                                            prompt: 'Por favor ingrese una duracion'
                                        },
                                        {
                                            type: 'integer[1..20]',
                                            prompt: 'Por favor ingrese valores numericos en la duracion'
                                        }
                                    ]
                                }                                
                            }
                        });

            });
        </script>
    </head>
    <body>


        <c:choose>

            <c:when test="${nombreUsuario != null}" >
                <div class="ui container" style="padding-top: 10px">
                    <div class="ui center aligned">
                        <h2 class="ui center aligned icon header">
                            <i class="edit icon"></i>
                            Ingrese datos de la carrera
                        </h2>
                        <div class="ui center aligned two column grid">
                            <div class="column">
                                <div class="ui green segment">
                                    <form class="ui form" id="formulario" method="POST" action="CarreraServlet?accion=guardar">
                                        <div class="field" hidden>
                                            <label>Id Carrera</label>
                                            <input type="text" name="id" id="id" placeholder="" value="${usuario.entidad.idCarrera}">                                        
                                        </div>
                                        <div class="field">
                                            <label>Nombre de carrera</label>
                                            <input type="text" name="txtNombreCarrera" id="txtNombreCarrera" placeholder="" value="${usuario.entidad.nombreCarrera}">
                                        </div>
                                        <div class="field">
                                            <label>Descripcion de carrera</label>
                                            <input type="text" name="txtDescripcion" id="txtDescripcion" placeholder="" value="${usuario.entidad.descripcion}">
                                        </div>
                                        <div class="field">
                                            <label>Duraci&oacute;n de carrera</label>
                                            <input type="text" name="txtDuracion" id="txtDuracion" placeholder="" value="${usuario.entidad.duracion}">

                                        </div>
                                        <button class="ui primary button" type="submit">Enviar</button>
                                        <div class="ui error message"></div>
                                    </form>
                                </div>
                            </div>

                        </div>                        
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
