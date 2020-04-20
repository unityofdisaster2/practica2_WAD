/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipn.mx.utilerias;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author unityofdisaster
 */
public class Utilerias {
    public void enviarEmail(String correoDestinatario, String asunto, String texto){
        try{
            Properties p = new Properties();
            p.setProperty("mail.smtp.host", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.starttls.required", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", "serviciocorreosdeprueba@gmail.com");
            p.setProperty("mail.smtp.auth", "true");
            
            Session s = Session.getDefaultInstance(p);
            MimeMessage mensaje = new MimeMessage(s);
            // correo de quien lo envia no de donde se esta enviando. 
            mensaje.setFrom(new InternetAddress("serviciocorreosdeprueba@gmail.com"));
            
            mensaje.addRecipients(
                        Message.RecipientType.TO,
                        correoDestinatario);
            mensaje.setSubject(asunto);
            mensaje.setText(texto);
            
            Transport t = s.getTransport("smtp");
            t.connect("serviciocorreosdeprueba@gmail.com","T3st1ng08");
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            t.close();
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
