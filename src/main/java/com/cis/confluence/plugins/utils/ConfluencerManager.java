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
import javax.inject.Scope;
import java.util.*;

@Named("confluencerManager")
public class ConfluencerManager {

    private final Logger logger = LoggerFactory.getLogger(ConfluencerManager.class);

    private Map<String, EventUser> list = new LinkedHashMap<>();

    private ConfluencerPersistenceImpl persistence;
//    private EventSeekerManager eventSeekerManager;
//
//    public void setEventSeekerManager(EventSeekerManager eventSeekerManager) {
//        this.eventSeekerManager = eventSeekerManager;
//    }

    public void setPersistence(ConfluencerPersistenceImpl persistence) {
        this.persistence = persistence;
    }

    public List<EventUser> getList(){
        if (null == list){
            list = new LinkedHashMap<>();
        }
        List<EventUser> lista = new LinkedList<>();
        list.forEach((key, value) -> lista.add(value));
        return lista;
    }

    public void setList(List<EventUser> lista){
        lista.forEach( u -> list.put(u.getEmail(), u));
    }

    public List<EventUser> getSortedList(){
        List<EventUser> sortedList = getList();
        Collections.sort(sortedList);
        return sortedList;
    }

    public EventUser getFirst(){
        List<EventUser> sortedList = getSortedList();
        if (sortedList.size() > 0) {
            return sortedList.get(0);
        } else {
            return null;
        }
    }

    public List<EventUser> sortedListWithoutFirst(){
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
        if (null == list){
            list = new LinkedHashMap<>();
        }
        list.put(correo, eventUser);
        EventSeekerManager eventSeekerManager = new EventSeekerManager();
        eventSeekerManager.userParticipate(eventUser);
//        List<EventUser> l = new LinkedList<>();
//        l.add(eventUser);
//        persistence.saveAll(l);
    }

    public void addSpace(String correo){
        list.get(correo).addSpace();
    }

    public void addPage(String correo){ list.get(correo).addPage(); }

    public void addBlog(String correo){
        list.get(correo).addBlog();
    }

    public void addComment(String correo){
        list.get(correo).addComment();
    }

    public void addLike(String correo){
        list.get(correo).addLike();
    }

    public void restSpace(String correo) { list.get(correo).restSpace(); }

    public void restPage(String correo) { list.get(correo).restPage(); }

    public void restBlog(String correo) { list.get(correo).restBlog(); }

    public void restComment(String correo) { list.get(correo).restComment(); }

    public void restLike(String correo) { list.get(correo).restLike(); }

    public void printResults(){
        System.out.println("====================================================================================");
        list.forEach((key, value) -> {
            System.out.println(" ----- ====== " + value.toString() + " ====== ------");
            logger.debug(" ----- ====== " + value.toString() + " ====== ------");
        });
        System.out.println("====================================================================================");
    }

}
