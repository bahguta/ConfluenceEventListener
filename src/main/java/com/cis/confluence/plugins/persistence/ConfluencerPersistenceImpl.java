package com.cis.confluence.plugins.persistence;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.confluence.api.model.web.Icon;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.cis.confluence.plugins.dto.EventUser;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.google.common.collect.ImmutableMap;
import net.java.ao.DBParam;
import net.java.ao.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.LinkedList;
import java.util.List;

@Named("ConfluencerPersistenceImpl")
@Transactional
public class ConfluencerPersistenceImpl implements ConfluencerPersistence, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(ConfluencerPersistenceImpl.class);

    @ComponentImport
    private ActiveObjects ao;

    @Inject
    public ConfluencerPersistenceImpl(ActiveObjects ao) {
        this.ao = ao;
    }

    @Override
    public void saveAll(List<EventUser> list) {
        list.forEach(user ->{
            EventUserServ eventUser = ao.create(EventUserServ.class, new DBParam("user", user));
            eventUser.save();
        });
    }

    @Override
    public List<EventUser> getAll() {
        List<EventUser> list = new LinkedList<>();
        try {
            EventUserServ[] arr = ao.find(EventUserServ.class);
            if (arr.length > 0) {
                for (EventUserServ user : arr) {
                    EventUser eventUser = new EventUser(user.getEmail(), user.getName(), user.getFullName(), user.getKeyString(), new Icon(user.getIconPath(), 40, 40, true));
                    eventUser.setParticipate(user.isParticipate());
                    eventUser.setSpace(user.getSpace());
                    eventUser.setPage(user.getPage());
                    eventUser.setBlog(user.getBlog());
                    eventUser.setComment(user.getComment());
                    eventUser.setLike(user.getLike());
                    list.add(eventUser);
                }
            }
        }catch (Exception e){
            logger.error("{}", e.getMessage());
        }
        return list;
    }

    @Override
    public void save(EventUser user) {
        EventUserServ[] u = ao.find(EventUserServ.class, Query.select().where("NAME = ?", user.getName()));
        if ( u.length > 0){
            u[0].setParticipate(user.isParticipate());
            u[0].setIconPath(user.getIcon().getPath());
            u[0].setEmail(user.getEmail());
            u[0].setName(user.getName());
            u[0].setFullName(user.getFullName());
            u[0].setKeyString(user.getKey().getStringValue());
            u[0].setSpace(user.getSpace());
            u[0].setPage(user.getPage());
            u[0].setBlog(user.getBlog());
            u[0].setComment(user.getComment());
            u[0].setLike(user.getLike());
            u[0].save();
        } else {
            EventUserServ eventUser = ao.create(EventUserServ.class, ImmutableMap.<String, Object>of("NAME", user.getName()));
            eventUser.setParticipate(user.isParticipate());
            eventUser.setIconPath(user.getIcon().getPath());
            eventUser.setEmail(user.getEmail());
            eventUser.setName(user.getName());
            eventUser.setFullName(user.getFullName());
            eventUser.setKeyString(user.getKey().getStringValue());
            eventUser.setSpace(user.getSpace());
            eventUser.setPage(user.getPage());
            eventUser.setBlog(user.getBlog());
            eventUser.setComment(user.getComment());
            eventUser.setLike(user.getLike());
            eventUser.save();
        }
    }

    @Override
    public void remove(EventUser user) {
        ao.delete(user);
    }

    @Override
    public void removeAll(List<EventUser> list) {
        list.forEach( eventUser -> ao.delete(eventUser));
    }


    @Override
    public void destroy() throws Exception {
    }
}
