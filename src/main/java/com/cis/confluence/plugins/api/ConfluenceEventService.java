package com.cis.confluence.plugins.api;

import com.cis.confluence.plugins.dto.EventUser;

import java.util.List;

public interface ConfluenceEventService {
    List<EventUser> getList();
}