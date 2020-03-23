package com.cis.confluence.plugins.persistence;

import com.atlassian.activeobjects.spi.ActiveObjectsPluginConfiguration;
import com.atlassian.confluence.plugin.persistence.PluginData;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.cis.confluence.plugins.dto.EventUser;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomPluginData implements PluginSettings {

    private Map<String, EventUser> list = new LinkedHashMap<>();

    @Override
    public Object get(String key) {
        return list.getOrDefault(key, null);
    }

    @Override
    public Object put(String key, Object value) {
        return list.put(key, (EventUser) value);
    }

    @Override
    public Object remove(String key) {
        return list.containsKey(key) ? list.remove(key) : null;
    }
}
