package com.cis.confluence.plugins.persistence;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.confluence.user.UserAccessor;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.spring.container.ContainerManager;
import com.cis.confluence.plugins.dto.EventUser;
import com.atlassian.activeobjects.external.ActiveObjects;
import com.google.common.collect.ImmutableMap;
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
    public List<EventUser> getAll() {
        UserAccessor userAccessor = (UserAccessor) ContainerManager.getComponent("userAccessor");
        List<EventUser> list = new LinkedList<>();
        try {
            EventUserServ[] arr = ao.find(EventUserServ.class);
            if (arr.length > 0) {
                for (EventUserServ user : arr) {
                    EventUser eventUser = new EventUser(userAccessor.getUserByName(user.getName()));
                    eventUser.setParticipate(user.isParticipate());
                    list.add(eventUser);
                }
            }
        }catch (Exception e){
            logger.error("ERROR :: {}", e.getMessage());
        }
        return list;
    }

    @Override
    public void save(EventUser user) {
        EventUserServ[] u = ao.find(EventUserServ.class, Query.select().where("NAME = ?", user.getName()));
        if ( u.length == 0){
            EventUserServ eventUser = ao.create(EventUserServ.class, ImmutableMap.<String, Object>of("NAME", user.getName()));
            eventUser.setParticipate(user.isParticipate());
            eventUser.save();
        }
    }

    @Override
    public void remove(EventUser user) {
        ao.deleteWithSQL(EventUserServ.class, "NAME = ?", user.getName());
    }

    @Override
    public void destroy() {
    }
}
