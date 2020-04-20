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


/**
 * Clase que maneja la persistencia con Active Objects
 */
@Named("ConfluencerPersistenceImpl")
@Transactional
public class ConfluencerPersistenceImpl implements ConfluencerPersistence, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(ConfluencerPersistenceImpl.class);

    @ComponentImport
    private ActiveObjects ao;

    private UserAccessor userAccessor;

    @Inject
    public ConfluencerPersistenceImpl(ActiveObjects ao) {
        this.ao = ao;
    }

    /**
     * Metodo para obtener todos los registros de la base de datos
     * @return retorna una lista con los usuarios encontrados
     */
    @Override
    public List<EventUser> getAll() {
        List<EventUser> list = new LinkedList<>();
        try {
            EventUserServ[] arr = ao.find(EventUserServ.class);
            userAccessor = (UserAccessor) ContainerManager.getComponent("userAccessor");
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

    /**
     * Metodo para guardar un registro en la base de datos
     * @param user el usuario que se va a guardar
     */
    @Override
    public void save(EventUser user) {
        EventUserServ[] u = ao.find(EventUserServ.class, Query.select().where("NAME = ?", user.getName()));
        if ( u.length == 0){
            EventUserServ eventUser = ao.create(EventUserServ.class, ImmutableMap.<String, Object>of("NAME", user.getName()));
            eventUser.setParticipate(user.isParticipate());
            eventUser.save();
        }
    }

    /**
     * Metodo para borrar un registro de la base de datos
     * @param user el usuario que se va a borrar
     */
    @Override
    public void remove(EventUser user) {
        int result = ao.deleteWithSQL(EventUserServ.class, "NAME = ?", user.getName());
        user.setParticipate(false);
        logger.debug(" filas borradas :: " + result);
    }

    @Override
    public void destroy() throws Exception {
    }

    @Override
    public String toString() {
        return "ConfluencerPersistenceImpl{" +
                "ao=" + ao.toString() +
                '}';
    }

}
