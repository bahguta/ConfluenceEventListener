package com.cis.confluence.plugins.persistence;


import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.plugin.spring.scanner.annotation.component.ConfluenceComponent;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.cis.confluence.plugins.dto.EventUser;
import com.atlassian.activeobjects.external.ActiveObjects;
import net.java.ao.DBParam;
import net.java.ao.Query;
import org.springframework.beans.factory.DisposableBean;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named("PersistenceImpl")
@Scanned
@Transactional
public class PersistenceImpl implements Persistence, DisposableBean {

    private ActiveObjects ao;

    @Inject
    public PersistenceImpl(@ComponentImport ActiveObjects ao) {
        this.ao = ao;
        System.out.println("-------------------------- Constructor ");
    }

//    public void save(List<EventUser> list){
//        Map <String, EventUser> map = new LinkedHashMap<>();
//        list.forEach( eventUser -> {
//            map.put(eventUser.getEmail(), eventUser);
//        });
//        ao.create(EventUser.class, (List<Map<String, Object>>) map);
//    }

//    public EventUser[] get(){
//        return ao.find(EventUser.class, Query.select());
//    }

    @Override
    public void saveAll(List<EventUser> list) {

        System.out.println("-------------------- SAVE ALL ::: ");
        list.forEach(user ->{
            EventUserServ eventUser = ao.create(EventUserServ.class, new DBParam("user", user));
            eventUser.addEventUser(user);
            eventUser.save();
        });
    }

    @Override
    public List<EventUser> getAll() {

        System.out.println("-------------------- GET ALL ::: ");
        EventUserServ [] arr = ao.find(EventUserServ.class);
        System.out.println("--------------------- AFTER AO.FIND");
        List<EventUser> list = new LinkedList<>();
        for (EventUserServ user: arr) {
            System.out.println("-------------------user :: " + user.toString());
            list.add(user.getEventUser());
        }

        return list;
    }

//    @Override
//    public int getID() {
//        return 0;
//    }
//
//    @Override
//    public void init() {
//        ConfluencerManager.setList(getAll());
//        ConfluencerManager.getList().forEach( u -> System.out.println("---------- u :: " + u.toString()));
//    }
//
//    @Override
//    public void save() {
//        System.out.println("-------------- SAVE ::: ");
//        save(ConfluencerManager.getList());
//    }
//
//    @Override
//    public EntityManager getEntityManager() {
//        System.out.println("----------------------- :: getEntityManager");
//        return null;
//    }
//
//    @Override
//    public <X extends RawEntity<Integer>> Class<X> getEntityType() {
//        System.out.println("----------------------- :: getEntityType");
//        return null;
//    }
//
//    @Override
//    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
//        System.out.println("------------------ add :; addPropertyChangeListener" );
//    }
//
//    @Override
//    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
//        System.out.println("----------------------- :: removePropertyChangeListener");
//    }

    @Override
    public void destroy() throws Exception {


    }
}
