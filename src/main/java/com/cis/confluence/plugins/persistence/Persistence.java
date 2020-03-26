package com.cis.confluence.plugins.persistence;

import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.plugin.spring.scanner.annotation.component.ConfluenceComponent;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.cis.confluence.plugins.dto.EventUser;
import org.osgi.service.component.annotations.Component;

import javax.annotation.ManagedBean;
import javax.inject.Named;
import java.util.List;



public interface Persistence  {

    void saveAll(List<EventUser> list);

    List<EventUser> getAll();

}
