package com.cis.confluence.plugins.persistence;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.cis.confluence.plugins.dto.EventUser;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.google.common.collect.ImmutableMap;
import net.java.ao.*;
import net.java.ao.builder.DataSourceFactory;
import net.java.ao.types.TypeManager;
import org.springframework.beans.factory.DisposableBean;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

@Named("ConfluencerPersistenceImpl")
@Transactional
public class ConfluencerPersistenceImpl implements ConfluencerPersistence, DisposableBean {

    @ComponentImport
    private ActiveObjects ao;

    @Inject
    public ConfluencerPersistenceImpl(ActiveObjects ao) {
        this.ao = ao;
        System.out.println("-------------------------- Constructor ");
    }

    @Override
    public void saveAll(List<EventUser> list) {

        System.out.println("-------------------- SAVE ALL ::: ");
        list.forEach(user ->{
            EventUserServ eventUser = ao.create(EventUserServ.class, new DBParam("user", user));
            //eventUser.addEventUser(user);
            eventUser.save();
        });
    }

    @Override
    public List<EventUser> getAll() {

        System.out.println("-------------------- GET ALL ::: ");
        EventUserServ [] arr = ao.find(EventUserServ.class, Query.select("user"));
        System.out.println("--------------------- AFTER AO.FIND");
        List<EventUser> list = new LinkedList<>();
        for (EventUserServ user: arr) {
            System.out.println("-------------------user :: " + user.toString());
            //list.add(user.getEventUser());
            //list.add(user.getUser());
        }

        return list;
    }

    @Override
    public void save(EventUser user) {
        System.out.println("-------------------- SAVE ::: " + user.toString());
        //EntityManager entityManager = new EntityManager(new DatabaseProvider(net.java.ao.db., TypeManager.h2()), new EntityManagerConfiguration())
        //EventUserServ eventUser = ao.create(EventUser.class, new DBParam("user", user));
        //System.out.println("-------------- au.create :: " + ao.create(EventUserServ.class, new DBParam("NAME", user.getName())).toString());
        EventUserServ[] u = ao.find(EventUserServ.class, Query.select().where("NAME = ?", user.getName()));

        if ( u.length > 0){
            for (int i = 0; i < u.length; i++) {
                System.out.println("----------u[] :: " + u[i].toString());
            }
            System.out.println("------------dentro de if :::");
            u[0].setName(user.getName());
            u[0].setEmail(user.getEmail());
            System.out.println("---------------u[0].toString() :: " + u[0].toString());
        } else {
            System.out.println("------------- DIDNT FINT ANYTHING -> creando usuario");
            EventUserServ eventUser = ao.create(EventUserServ.class, ImmutableMap.<String, Object>of("NAME", user.getName()));
            eventUser.setEmail(user.getEmail());
            eventUser.setName(user.getName());
            eventUser.save();
        }
        //EventUserServ eventUser = ao.create(EventUserServ.class, ImmutableMap.<String, Object>of("user", user));
        //ImmutableMap.<String, Object>of("USER_ID", currentUser())

    }


    @Override
    public void destroy() throws Exception {


    }
}
