package ru.webcrafter.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class WebCrafterServerMain {
    public static void main(String[] args) {
        System.out.println("WebCrafter server started");
        ApplicationContext appContext = new FileSystemXmlApplicationContext(new String[]{"classpath:webcrafter.rmiContext.xml"});
        System.out.println("Application context loaded");
    }
}
