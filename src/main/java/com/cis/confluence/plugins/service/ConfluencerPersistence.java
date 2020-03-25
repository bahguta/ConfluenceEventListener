package com.cis.confluence.plugins.service;

import com.atlassian.user.Entity;

import java.util.List;

public interface ConfluencerPersistence extends Entity {

    void saveAll(List<ConfluencerPersistence> list);

    List<ConfluencerPersistence> getAll();

}
