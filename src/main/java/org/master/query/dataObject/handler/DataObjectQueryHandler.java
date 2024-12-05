package org.master.query.dataObject.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.dto.dataObject.DataObjectListDTO;
import org.master.model.dataObject.DataObjectsReadModel;
import org.master.service.dataObject.DataObjectService;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class DataObjectQueryHandler {

    @Inject
    DataObjectService dataObjectService;

    public List<DataObjectListDTO> getDataObjectList() {
        return dataObjectService.getAllDataObjects();
    }
    public DataObjectsReadModel getDataObjectById(UUID id) {
        return dataObjectService.findByUuid(id);
    }
}
