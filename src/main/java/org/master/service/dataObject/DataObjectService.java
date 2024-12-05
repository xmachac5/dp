package org.master.service.dataObject;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.dto.dataObject.DataObjectListDTO;
import org.master.model.dataObject.DataObjectsReadModel;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DataObjectService {
    @Inject
    EntityManager em;

    public List<DataObjectListDTO> getAllDataObjects(){
        return em.createQuery(
                        "SELECT new org.master.dto.dataObject.DataObjectListDTO(d.id)" +
                                " FROM DataObjectsReadModel d", DataObjectListDTO.class)
                .getResultList();
    }

    public DataObjectsReadModel findByUuid(UUID uuid) {
        return em.find(DataObjectsReadModel.class, uuid);
    }
}
