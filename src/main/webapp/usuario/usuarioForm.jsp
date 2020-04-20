<%-- 
    Document   : usuarioForm
    Created on : Apr 19, 2020, 12:54:44 PM
    Author     : unityofdisaster
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>formulario Usuario</title>
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.3.1/dist/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.4/dist/semantic.min.css">
        <script src="https://cdn.jsdelivr.net/npm/fomantic-ui@2.8.4/dist/semantic.min.js"></script>     
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
        <script>
            $(document).ready(function () {
                $('#formularioUsuario')
                        .form({
                            fields: {
                                txtNombre: {
                                    identifier: 'txtNombre',
                                    rules: [
                                        {
                                            type: 'empty',
                                            prompt: 'Por favor ingrese el nombre de alumno'
                                        }
                                    ]
                                },
                                txtPaterno: {
                                    identifier: 'txtPaterno',
                                    rules: [
                                        {
                                            type: 'empty',
                                            prompt: 'Por favor ingrese apellido paterno'
                                        }
                                    ]
                                },
                                txtMaterno: {
                                    identifier: 'txtMaterno',
                                    rules: [
                                        {
                                            type: 'empty',
                                            prompt: 'Por favor ingrese un apellido materno'
                                        }
                                    ]
                                },

                                txtEmail: {
                                    identifier: 'txtEmail',
                                    rules: [
                                        {
                                            type: 'email',
                                            prompt: 'Por favor ingrese un email valido'
                                        }
                                    ]
                                },

                                txtNombreUsuario: {
                                    identifier: 'txtNombreUsuario',
                                    rules: [
                                        {
                                            type: 'empty',
                                            prompt: 'Por favor ingrese un nombre de usuario'
                                        }
                                    ]
                                },

                                txtClaveUsuario: {
                                    identifier: 'txtClaveUsuario',
                                    rules: [
                                        {
                                            type: 'empty',
                                            prompt: 'Por favor ingrese una clave de usuario'
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
                            <i class="user plus icon"></i>
                            Ingrese datos de Usuario
                        </h2>
                        <div class="ui center aligned two column grid">
                            <div class="column">
                                <div class="ui green segment">
                                    <form class="ui form" id="formularioUsuario" method="POST" action="UsuarioServlet?accion=guardar" enctype="multipart/form-data">
                                        <div class="field" hidden>
                                            <label>Id Usuario</label>
                                            <input type="text" name="id" id="id" placeholder="" value="${usr.entidad.idUsuario}">
                                        </div>
                                        <div class="field">
                                            <label>Nombre</label>
                                            <input type="text" name="txtNombre" id="txtNombre" placeholder="" value="${usr.entidad.nombre}">
                                        </div>
                                        <div class="field">
                                            <label>Paterno</label>
                                            <input type="text" name="txtPaterno" id="txtPaterno" placeholder="" value="${usr.entidad.paterno}">
                                        </div>
                                        <div class="field">
                                            <label>Materno</label>
                                            <input type="text" name="txtMaterno" id="txtMaterno" placeholder="" value="${usr.entidad.materno}">

                                        </div>
                                        <div class="field">
                                            <label>Email</label>
                                            <input type="text" name="txtEmail" id="txtEmail" placeholder="" value="${usr.entidad.email}">

                                        </div>          
                                        <div class="field">
                                            <label>Foto</label>
                                            <input type="file" name="txtFoto" id="txtFoto">

                                        </div>

                                        <div class="field">
                                            <label>Nombre Usuario</label>
                                            <input type="text" name="txtNombreUsuario" id="txtNombreUsuario" placeholder="" value="${usr.entidad.nombreUsuario}">
                                        </div>          


                                        <div class="field">
                                            <label>Clave Usuario</label>
                                            <input type="text" name="txtClaveUsuario" id="txtClaveUsuario" placeholder="" value="${usr.entidad.claveUsuario}">

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
