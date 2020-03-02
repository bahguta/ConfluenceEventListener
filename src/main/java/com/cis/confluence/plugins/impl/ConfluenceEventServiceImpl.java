package com.cis.confluence.plugins.impl;

import com.atlassian.confluence.event.events.content.page.PageCreateEvent;
import com.atlassian.confluence.event.events.content.page.PageRemoveEvent;
import com.atlassian.confluence.event.events.content.page.PageUpdateEvent;
import com.cis.confluence.plugins.api.ConfluenceEventService;
import com.atlassian.confluence.event.events.space.SpaceCreateEvent;
import com.atlassian.event.api.EventListener;
import com.atlassian.plugin.spring.scanner.annotation.component.ConfluenceComponent;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.annotation.Annotation;


@ConfluenceComponent
@ExportAsService({ConfluenceEventServiceImpl.class})
@Named("ConfluenceEventServiceImpl")
public class ConfluenceEventServiceImpl implements ConfluenceEventService, EventListener {

    private Logger logger = LoggerFactory.getLogger(ConfluenceEventService.class);

    @ComponentImport
    private final ApplicationProperties applicationProperties;

    @Inject
    public ConfluenceEventServiceImpl(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public String getName() {
            logger.error("--------------->>> ConfluenceEventService  ");
            return "ConfluenceEventService:" ;

    }

    @Override
    public String scope() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @EventListener
    public void onEvent(SpaceCreateEvent event) {
        for (int i = 0; i < 10; i++) {
            logger.error("-------------------------------- > [error] Handled an event: " + event.getSpace().getName());
            logger.debug("-------------------------------- > [DEBUG] Handled an event: " + event.getSpace().getCreator());
        }
    }

    @EventListener
    public void handleEvent(SpaceCreateEvent event){
        for (int i = 0; i < 10; i++) {
            logger.error("--------------------CREATED>> {}", event.getSpace().getCreator());
            logger.debug("--------------------CREATED-debug>> {}", event.getSpace().getName());
        }
    }

    @EventListener
    public void handleEventUpdate(PageUpdateEvent event){
            for (int i = 0; i < 10; i++) {

                logger.error("--------------------UPDATED>> {}", event.getPage().getCreator());
                logger.debug("--------------------UPDATED-debug>> {}", event.getPage().getContentStatus());
            }
    }

    @EventListener
    public void handleEventDelete(PageRemoveEvent event){
        for (int i = 0; i < 10; i++) {

            logger.error("--------------------REMOVED>> {}", event.getPage().getCreator());
            logger.debug("--------------------REMOVED-debug>> {}", event.getPage().getLastModifier().getFullName());
        }
    }

    @EventListener
    public void handleEventPageCreate(PageCreateEvent event){
        for (int i = 0; i < 10; i++) {

            logger.error("--------------------PAGE CREATED>> {}", event.getPage().getCreator());
            logger.debug("--------------------PAGE CREATED-debug>> {}", event.getPage().getLastModifier().getFullName());
        }
    }

}