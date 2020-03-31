package com.cis.confluence.plugins.utils;

import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.persistence.ConfluencerPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.inject.Named;
import java.util.*;

@Named("confluencerManager")
public class ConfluencerManager {

    private final Logger logger = LoggerFactory.getLogger(ConfluencerManager.class);

    private Map<String, EventUser> list = new LinkedHashMap<>();

    @Autowired
    private ConfluencerPersistence persistence;

    public ConfluencerPersistence getPersistence() {
        return persistence;
    }

    public void setPersistence(ConfluencerPersistence persistence) {
        this.persistence = persistence;
    }

    public void removeAll(){
        list.forEach((key, value) -> persistence.remove(value));
    }

    public void findUsers(){
        if (persistence.getAll().size() > 0){
            list.clear();
            for (EventUser user : persistence.getAll() ) {
                list.put(user.getUser().getEmail(), user);
                searchEvents(user);
            }
        }
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
        lista.forEach( u -> list.put(u.getUser().getEmail(), u));
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

    public boolean cancelarParticipacion(String name){
        getSortedList().stream().forEach( user ->{
            if (user.getName().equals(name)){
                user.setParticipate(false);
                persistence.remove(user);
            }
        });
        findUsers();
        return true;
    }

    public  boolean containsUser(String correo){
        return list.containsKey(correo);
    }

    public void addUser(String correo){
        EventUser eventUser =  new EventUser(AuthenticatedUserThreadLocal.get());

        if (null == list){
            list = new LinkedHashMap<>();
        }

        list.put(correo, eventUser);

        setParticipa(eventUser.getName());

        searchEvents(eventUser);
    }

    private void searchEvents(EventUser eventUser){
        EventSeekerManager eventSeekerManager = new EventSeekerManager();

        for (int i = 0; i < eventSeekerManager.addNumSpacesForUser(eventUser); i++) {
            addSpace(eventUser.getUser().getEmail());
        }

        for (int i = 0; i < eventSeekerManager.addNumPagesForUser(eventUser); i++) {
            addPage(eventUser.getUser().getEmail());
        }

        for (int i = 0; i < eventSeekerManager.addNumBlogsForUser(eventUser); i++) {
            addBlog(eventUser.getUser().getEmail());
        }

        for (int i = 0; i < eventSeekerManager.addNumCommentsForUser(eventUser); i++) {
            addComment(eventUser.getUser().getEmail());
        }

        for (int i = 0; i < eventSeekerManager.addNumLikesForUser(eventUser); i++) {
            addLike(eventUser.getUser().getEmail());
        }

        persistence.save(eventUser);
    }

    public void addSpace(String correo){ list.get(correo).addSpace(); }

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
        logger.debug("====================================================================================");
        list.forEach((key, value) -> {
            logger.debug(" ----- ====== " + value.toString() + " ====== ------");
        });
        logger.debug("====================================================================================");
    }

}
