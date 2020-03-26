package com.cis.confluence.plugins.persistence;


import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.cis.confluence.plugins.dto.EventUser;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.cis.confluence.plugins.services.EventUserServ;
import com.cis.confluence.plugins.utils.ConfluencerManager;
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

        System.out.println("-------------------- GET ALL ::: ");
        ConfluencerManager.getList().forEach(user ->{
            EventUser eventUser = ao.create(EventUser.class, new DBParam("user", user));
            eventUser.save();
        });
    }

    @Override
    public List<EventUser> getAll() {

//        Map <String, List<EventUser>> map = new HashMap<>();
//        map.put("users", list);
//        ao.create(EventUser.class, (List<Map<String, Object>>) map);

        EventUser [] arr = ao.find(EventUser.class, Query.select());

        return Arrays.asList(arr);
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
