package com.cis.confluence.plugins.utils;

import com.atlassian.confluence.api.model.web.Icon;
import com.atlassian.sal.api.user.UserKey;
import com.cis.confluence.plugins.dto.EventUser;
import org.apache.log4j.Logger;

import java.util.*;

public class ConfluencerManager {

    private static final Logger logger = Logger.getLogger(ConfluencerManager.class);

    private static Map<String, EventUser> list = new LinkedHashMap<>();

    public static List<EventUser> getList(){
        List<EventUser> lista = new LinkedList<>();
        ConfluencerManager.list.forEach((key, value) -> lista.add(value));
        return lista;
    }

    public static List<EventUser> getSortedList(){
        List<EventUser> sortedList = getList();
        Collections.sort(sortedList);
        return sortedList;
    }

    public static EventUser getFirst(){
        List<EventUser> sortedList = getSortedList();
        if (sortedList.size() > 0) {
            return sortedList.get(0);
        } else {
            return null;
        }
    }

    public static List<EventUser> sortedListWithoutFirst(){
        List<EventUser> lista = new LinkedList<>();
        List<EventUser> sortedList = getSortedList();
        for (int i = 1; i < sortedList.size(); i++) {
            lista.add(sortedList.get(i));
        }
        return lista;
    }

    public static boolean participa(String name){
        return getSortedList().stream().filter( user -> user.getName().equals(name)).findFirst().get().isParticipate();
    }

    public static boolean setParticipa(String name, boolean participa){
        System.out.println("------------> participaaaa :: " + name + " :: " + participa );
        getSortedList().stream().filter( user -> user.getName().equals(name)).findFirst().get().setParticipate(participa);
        return participa;
    }

//    public static String getUserCorreo(String name){
//        return getSortedList().stream().filter( user -> user.getName().equals(name)).findFirst().get().getEmail();
//    }

    public static boolean containsUser(String correo){
        return ConfluencerManager.list.containsKey(correo);
    }

    public static void addUser(String correo, String name, String fullName, String key, Icon icon){
        EventUser eventUser = new EventUser(correo, name, fullName, key, icon);
        ConfluencerManager.list.put(correo, eventUser);
        EventSeekerManager.userParticipate(eventUser);
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
        System.out.println("====================================================================================");
        list.forEach((key, value) -> {
            System.out.println(" ----- ====== " + value.toString() + " ====== ------");
            logger.debug(" ----- ====== " + value.toString() + " ====== ------");
        });
        System.out.println("====================================================================================");
    }
}
