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
import java.util.StringTokenizer;

@RestController
public class HelloController {

    public MimeHeaders getMIMEHeaders(HttpServletRequest request) {
        MimeHeaders mimeHeaders = new MimeHeaders();
        Enumeration enums = request.getHeaderNames();
        while (enums.hasMoreElements()) {
            String headerName = (String)enums.nextElement();
            String headerValue = request.getHeader(headerName);
            StringTokenizer st = new StringTokenizer(headerValue, ",");
            System.out.println("HeaderName: " + headerName);
            System.out.println("headerValue: " + headerValue);
            System.out.println("st: " + st.toString());
            System.out.println("==============================");
            while (st.hasMoreTokens()) {
                mimeHeaders.addHeader(headerName, st.nextToken().trim());
            }
        }
        System.out.println("finish MimeHeaders");
        return mimeHeaders;
    }

    @RequestMapping("/callback")
    public String hello(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("Hello World");
        MimeHeaders header = getMIMEHeaders(request);

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

                String newBody2 = readTwiceHttpServletRequestWrapper.getBody();
                System.out.println(newBody2);

            } catch(Exception e) {
                System.out.println(e);
            }
        }
        response.setContentType("text/xml"); // Need this to prevent Apache SOAP from gacking

        System.out.println("good!!!");
        return "hello";
    }
}
