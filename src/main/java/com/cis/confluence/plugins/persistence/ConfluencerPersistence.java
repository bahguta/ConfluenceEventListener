package com.cis.confluence.plugins.persistence;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.cis.confluence.plugins.dto.EventUser;
import com.atlassian.activeobjects.external.ActiveObjects;
import net.java.ao.RawEntity;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfluencerPersistence {

    private Map<String, EventUser> list = new LinkedHashMap<>();

    @ComponentImport
    private ActiveObjects ao;

    @Inject
    public ConfluencerPersistence(@ComponentImport ActiveObjects ao) {
        this.ao = ao;
    }

    public void save(List<EventUser> list){
        Map <String, EventUser> map = new LinkedHashMap<>();
        list.forEach( eventUser -> {
            map.put(eventUser.getEmail(), eventUser);
        });
        ao.create(EventUser.class, (List<Map<String, Object>>) map);
    }

    public RawEntity[] get(){
        return ao.find(EventUser.class);
    }
}
