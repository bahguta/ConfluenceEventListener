package com.cis.confluence.plugins.utils;

import com.cis.confluence.plugins.dto.EventUser;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConfluencerManager {

    private static final Logger logger = Logger.getLogger(ConfluencerManager.class);

    private static Map<String, EventUser> list = new LinkedHashMap<>();

    public static boolean containsUser(String correo){
        return ConfluencerManager.list.containsKey(correo);
    }

    public static void addUser(String correo, String fullName){
        ConfluencerManager.list.put(correo, new EventUser(correo, fullName));
    }

    public static void addSpace(String correo){
        ConfluencerManager.list.get(correo).addSpace();
    }

    public static void addPage(String correo){
        ConfluencerManager.list.get(correo).addPage();
    }

    public static void addBlog(String correo){
        ConfluencerManager.list.get(correo).addBlog();
    }

    public static void addComment(String correo){
        ConfluencerManager.list.get(correo).addComment();
    }

    public static void addLike(String correo){
        ConfluencerManager.list.get(correo).addLike();
    }

    public static void printResults(){
        list.entrySet().forEach(e -> {
            System.out.println(" ----- ====== " + e.getValue().toString() + " ====== ------");
            logger.debug(e.getValue().toString());
        });
    }
}
