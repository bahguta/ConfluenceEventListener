package com.cis.confluence.plugins.utils;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.confluence.api.model.web.Icon;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.persistence.ConfluencerPersistence;
import com.cis.confluence.plugins.persistence.ConfluencerPersistenceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

//@Named("ConfluencerManager")
public class ConfluencerManager implements ConfluencerManagerServ {

    private static final Logger logger = LoggerFactory.getLogger(ConfluencerManager.class);

    private static Map<String, EventUser> list = new LinkedHashMap<>();

    private ConfluencerPersistenceImpl persistence;
    private EventSeekerManager eventSeekerManager;

    public void setEventSeekerManager(EventSeekerManager eventSeekerManager) {
        this.eventSeekerManager = eventSeekerManager;
    }

    public void setPersistence(ConfluencerPersistenceImpl persistence) {
        this.persistence = persistence;
    }

    public static List<EventUser> getList(){
        List<EventUser> lista = new LinkedList<>();
        ConfluencerManager.list.forEach((key, value) -> lista.add(value));
        return lista;
    }

    public static void setList(List<EventUser> list){
        list.forEach( u -> ConfluencerManager.list.put(u.getEmail(), u));
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

    public boolean participa(String name){
        return getSortedList().stream().filter( user -> user.getName().equals(name)).findFirst().get().isParticipate();
    }

    public boolean setParticipa(String name){
        getSortedList().stream().filter( user -> user.getName().equals(name)).findFirst().get().setParticipate(true);
        return true;
    }

    public  boolean containsUser(String correo){
        return list.containsKey(correo);
    }

    public void addUser(String correo, String name, String fullName, String key, Icon icon){
        EventUser eventUser = new EventUser(correo, name, fullName, key, icon);
        list.put(correo, eventUser);
        eventSeekerManager.userParticipate(eventUser);
//        List<EventUser> l = new LinkedList<>();
//        l.add(eventUser);
//        persistence.saveAll(l);
    }

    public void addSpace(String correo){
        ConfluencerManager.list.get(correo).addSpace();
    }

    public void addPage(String correo){
        ConfluencerManager.list.get(correo).addPage();
    }

    public void addBlog(String correo){
        ConfluencerManager.list.get(correo).addBlog();
    }

    public void addComment(String correo){
        ConfluencerManager.list.get(correo).addComment();
    }

    public void addLike(String correo){
        ConfluencerManager.list.get(correo).addLike();
    }

    public void restSpace(String correo) { ConfluencerManager.list.get(correo).restSpace(); }

    public void restPage(String correo) { ConfluencerManager.list.get(correo).restPage(); }

    public void restBlog(String correo) { ConfluencerManager.list.get(correo).restBlog(); }

    public void restComment(String correo) { ConfluencerManager.list.get(correo).restComment(); }

    public void restLike(String correo) { ConfluencerManager.list.get(correo).restLike(); }

    public static void printResults(){
        System.out.println("====================================================================================");
        list.forEach((key, value) -> {
            System.out.println(" ----- ====== " + value.toString() + " ====== ------");
            logger.debug(" ----- ====== " + value.toString() + " ====== ------");
        });
        System.out.println("====================================================================================");
    }

}
