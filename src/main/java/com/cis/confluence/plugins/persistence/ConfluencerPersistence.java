package com.cis.confluence.plugins.persistence;

import com.atlassian.activeobjects.tx.Transactional;
import com.cis.confluence.plugins.dto.EventUser;
import java.util.List;

@Transactional
public interface ConfluencerPersistence {

    List<EventUser> getAll();

    void save(EventUser user);

    void remove(EventUser user);

}
