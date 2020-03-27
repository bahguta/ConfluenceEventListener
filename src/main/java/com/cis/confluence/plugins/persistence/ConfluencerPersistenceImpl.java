package com.cis.confluence.plugins.persistence;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.cis.confluence.plugins.dto.EventUser;
import com.atlassian.activeobjects.external.ActiveObjects;
import net.java.ao.DBParam;
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

    @Override
    public void destroy() throws Exception {


    }
}
