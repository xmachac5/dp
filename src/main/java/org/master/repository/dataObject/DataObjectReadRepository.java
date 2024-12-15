package org.master.repository.dataObject;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.events.dataObject.DataObjectCreatedEvent;
import org.master.events.dataObject.DataObjectDeletedEvent;
import org.master.events.dataObject.DataObjectPublishedEvent;
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

    public void update(DataObjectPublishedEvent dataObjectPublishedEvent ) {
        DataObjectsReadModel dataObjectsReadModel = em.find(DataObjectsReadModel.class, dataObjectPublishedEvent.getId());
        dataObjectsReadModel.setColumns(dataObjectPublishedEvent.getColumns());

        em.merge(dataObjectsReadModel);
    }

    public void delete(DataObjectDeletedEvent dataObjectDeletedEvent) {
        em.remove(em.find(DataObjectsReadModel.class, dataObjectDeletedEvent.getId()));
    }
}
