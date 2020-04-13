package com.cis.confluence.plugins.persistence;

import net.java.ao.Entity;
import net.java.ao.Preload;

/**
 * Interfaz usada para manejar la persistencia
 */
@Preload
public interface EventUserServ extends Entity {

    boolean isParticipate();
    void setParticipate(boolean participate);

    String getName();

}
