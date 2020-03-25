package com.cis.confluence.plugins.persistence;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.cis.confluence.plugins.dto.EventUser;
//import com.atlassian.activeobjects.external.ActiveObjects;
import com.cis.confluence.plugins.service.ConfluencerPersistence;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfluencerPersistenceImpl implements ConfluencerPersistence {

    //private ActiveObjects activeObjects;


    @Override
    public void saveAll(List<ConfluencerPersistence> list) {

    }

    @Override
    public List<ConfluencerPersistence> getAll() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
