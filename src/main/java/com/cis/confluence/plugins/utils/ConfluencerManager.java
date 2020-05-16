package com.cis.confluence.plugins.utils;

import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.cis.confluence.plugins.dto.EventUser;
import com.cis.confluence.plugins.persistence.ConfluencerPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.inject.Named;
import java.util.*;


/**
 * Clase para manejar la persistencia y la lista de los usuarios que estan participando en el evento Confluencer
 */
@Named("confluencerManager")
public class ConfluencerManager {

    private final Logger logger = LoggerFactory.getLogger(ConfluencerManager.class);

    private Map<String, EventUser> list = new LinkedHashMap<>();
    private EventSeekerManager eventSeekerManager;

    public ConfluencerManager() {
        this.eventSeekerManager = new EventSeekerManager();
    }

    public EventSeekerManager getEventSeekerManager() {
        return eventSeekerManager;
    }

    public void setEventSeekerManager(EventSeekerManager eventSeekerManager) {
        this.eventSeekerManager = eventSeekerManager;
    }

    @Autowired
    private ConfluencerPersistence persistence;

    public ConfluencerPersistence getPersistence() {
        return persistence;
    }

    public void setPersistence(ConfluencerPersistence persistence) {
        this.persistence = persistence;
    }

    /**
     * Metodo para borrar todos los usuarios de la base de datos que estan participando en el evento Confluencer
     */
    public void removeAll(){
        list.forEach((key, value) -> persistence.remove(value));
    }

    /**
     * Metodo para recoger todos los usuarios de la base de datos que estan participando en el evento Confluencer
     *
     * Actualiza el mapa con los usuarios
     */
    public void findUsers(){
        if (persistence.getAll().size() > 0){
            list.clear();
            for (EventUser user : persistence.getAll() ) {
                list.put(user.getUser().getEmail(), user);
                searchEvents(user);
            }
        }
    }

    /**
     * Metodo para obtener una lista de usuarios que participan en el evento Confluencer
     * @return una lista con usuarios
     */
    public List<EventUser> getList(){
        //findUsers();
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

    public void setList(Map<String, EventUser> list) {
        this.list = list;
    }

    /**
     * Metodo para sortear los usuarios
     * @return una lista de usuarios
     */
    public List<EventUser> getSortedList(){
        List<EventUser> sortedList = getList();
        Collections.sort(sortedList);
        return sortedList;
    }

    /**
     * Metodo para obtener el primer usuario de la lista
     * @return el primer usuario
     */
    public EventUser getFirst(){
        List<EventUser> sortedList = getSortedList();
        if (sortedList.size() > 0) {
            return sortedList.get(0);
        } else {
            return null;
        }
    }

    /**
     * Metodo para obtener una lista de usuarios que participan en el evento Confluencer
     * @return una lista de usuarios SIN el primer usuario
     */
    public List<EventUser> sortedListWithoutFirst(){
        List<EventUser> lista = new LinkedList<>();
        List<EventUser> sortedList = getSortedList();
        for (int i = 1; i < sortedList.size(); i++) {
            lista.add(sortedList.get(i));
        }
        return lista;
    }

    /**
     * Metodo para comprobar si un usuario participa en el evento Confluencer
     * @param name el nombre del usuario
     * @return true si participa y false en caso contrario
     */
    public boolean participa(String name){
        return getSortedList().stream().filter( user -> user.getName().equals(name)).findFirst().get().isParticipate();
    }

    /**
     * Metodo para que un usuario participe en el evento Confluencer
     * @param name el nombre del usuario que va a participar
     */
    public void setParticipa(String name){
        getSortedList().stream().filter( user -> user.getName().equals(name)).findFirst().get().setParticipate(true);
    }

    /**
     * Metodo para cancelar la participacion de un usuario
     * @param name el nombre del usuario que cancelara la participacion
     */
    public void cancelarParticipacion(String name){
        getSortedList().stream().forEach( user ->{
            if (user.getName().equals(name)){
                user.setParticipate(false);
                persistence.remove(user);
                list.remove(user.getUser().getEmail());
            }
        });
        findUsers();
    }

    public boolean containsUser(String correo){
        return list.containsKey(correo);
    }

    /**
     * Metodo para añadir un usuario en el mapa y posteriormente agregado en la base de datos
     * @param eventUser el usuario
     */
    public void addUser(EventUser eventUser){
        if (null == list){
            list = new LinkedHashMap<>();
        }

        list.put(eventUser.getUser().getEmail(), eventUser);

        setParticipa(eventUser.getName());

        searchEvents(eventUser);
    }

    /**
     * Metodo para buscar eventos que ha creado un usuario
     * @param eventUser el usuario para hacer la busqueda
     */
    private void searchEvents(EventUser eventUser){

        eventSeekerManager.searchEvents(eventUser);

//        eventUser.setSpace(eventUser.getSpace() + eventSeekerManager.addNumSpacesForUser(eventUser));
//        eventUser.setPage(eventUser.getPage() + eventSeekerManager.addNumPagesForUser(eventUser));
//        eventUser.setBlog(eventUser.getBlog() + eventSeekerManager.addNumBlogsForUser(eventUser));
//        eventUser.setComment(eventUser.getComment() + eventSeekerManager.addNumCommentsForUser(eventUser));
//        eventUser.setLike(eventUser.getLike() + eventSeekerManager.addNumLikesForUser(eventUser));

//        for (int i = 0; i < eventSeekerManager.addNumSpacesForUser(eventUser); i++) {
//            addSpace(eventUser.getUser().getEmail());
//        }
//
//        for (int i = 0; i < eventSeekerManager.addNumPagesForUser(eventUser); i++) {
//            addPage(eventUser.getUser().getEmail());
//        }
//
//        for (int i = 0; i < eventSeekerManager.addNumBlogsForUser(eventUser); i++) {
//            addBlog(eventUser.getUser().getEmail());
//        }
//
//        for (int i = 0; i < eventSeekerManager.addNumCommentsForUser(eventUser); i++) {
//            addComment(eventUser.getUser().getEmail());
//        }
//
//        for (int i = 0; i < eventSeekerManager.addNumLikesForUser(eventUser); i++) {
//            addLike(eventUser.getUser().getEmail());
//        }

        persistence.save(eventUser);
    }

    /**
     * Metodos para añadir / restar eventos de un usuario
     */

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
