package com.cis.confluence.plugins.persistence;

import com.atlassian.activeobjects.tx.Transactional;
import com.cis.confluence.plugins.dto.EventUser;
import java.util.List;

/**
 * Clase para manejar la persistencia ,
 * contiene los medotos usados para obtener, guardar y borrar registros de la base de datos
 */
@Transactional
public interface ConfluencerPersistence {

    List<EventUser> getAll();

    void save(EventUser user);

    void remove(EventUser user);

}
