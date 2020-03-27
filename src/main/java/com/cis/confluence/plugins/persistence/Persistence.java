package com.cis.confluence.plugins.persistence;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.plugin.spring.scanner.annotation.component.ConfluenceComponent;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.cis.confluence.plugins.dto.EventUser;
import java.util.List;

public interface Persistence  {

    void saveAll(List<EventUser> list);

    List<EventUser> getAll();

}
