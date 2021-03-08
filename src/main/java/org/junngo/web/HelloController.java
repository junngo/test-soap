package org.junngo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;

@RestController
public class HelloController {

    @RequestMapping("/callback")
    public String hello(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("Hello World");

        MessageFactory messageFactory = MessageFactory.newInstance();
        InputStream inStream = request.getInputStream();
        SOAPMessage soapMessage = messageFactory.createMessage(new MimeHeaders(), inStream);
        PrintWriter writer = response.getWriter();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapMessage.writeTo(out);
        String strMsg = new String(out.toByteArray());
        System.out.println(strMsg);
        writer.println(strMsg);

        return "hello";
    }
}
