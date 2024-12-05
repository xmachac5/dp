package org.master.repository.dataObject;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.events.dataObject.DataObjectCreatedEvent;
import org.master.events.dataObject.DataObjectDeletedEvent;
import org.master.events.dataObject.DataObjectUpdatedEvent;
import org.master.model.dataObject.DataObjectsReadModel;

@ApplicationScoped
public class DataObjectReadRepository implements PanacheRepository<DataObjectsReadModel> {
    @Inject
    EntityManager em;

    public void create(DataObjectCreatedEvent dataObjectCreatedEvent) {
        DataObjectsReadModel dataObjectsReadModel = new DataObjectsReadModel();
        dataObjectsReadModel.setId(dataObjectCreatedEvent.getId());
        dataObjectsReadModel.setColumns(dataObjectCreatedEvent.getColumns());
        em.persist(dataObjectsReadModel);
    }

    public void update(DataObjectUpdatedEvent dataObjectUpdatedEvent ) {
        DataObjectsReadModel dataObjectsReadModel = em.find(DataObjectsReadModel.class, dataObjectUpdatedEvent.getId());
        dataObjectsReadModel.setColumns(dataObjectUpdatedEvent.getColumns());

        em.merge(dataObjectsReadModel);
    }

    public void delete(DataObjectDeletedEvent dataObjectDeletedEvent) {
        em.remove(em.find(DataObjectsReadModel.class, dataObjectDeletedEvent.getId()));
    }
}
