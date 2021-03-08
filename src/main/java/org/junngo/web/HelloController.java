package org.junngo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPMessage;
import java.io.*;
import java.util.Enumeration;

@RestController
public class HelloController {

    @RequestMapping("/callback")
    public String hello(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("Hello World");

        // Traverse the HTTP headers and show them on the screen
//        for(Enumeration enum = request.getHeaderNames(); enum.hasMoreElements(); ) {
//            String header = (String)enum.nextElement();
//            String value  = request.getHeader(header);
//            System.out.println("  " + header + " = " + value);
//        }
        // If there is anything in the body of the message, dump it to the screen as well
        if(request.getContentLength() > 0) {
            try {
                ReadTwiceHttpServletRequestWrapper readTwiceHttpServletRequestWrapper = new ReadTwiceHttpServletRequestWrapper(
                        (HttpServletRequest) request);

                String newBody = readTwiceHttpServletRequestWrapper.getBody();
                System.out.println(newBody);
                java.io.BufferedReader reader = readTwiceHttpServletRequestWrapper.getReader();
                String line = null;
                while((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

//                java.io.BufferedReader reader = request.getReader();
//                String line = null;
//                while((line = reader.readLine()) != null) {
//                    System.out.println(line);
//                }
            } catch(Exception e) {
                System.out.println(e);
            }
        }
        response.setContentType("text/xml"); // Need this to prevent Apache SOAP from gacking

        System.out.println("good!!!");
        return "hello";
    }
}
