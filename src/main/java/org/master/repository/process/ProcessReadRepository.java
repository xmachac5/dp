package org.master.repository.process;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.events.process.ProcessCreatedEvent;
import org.master.events.process.ProcessDeletedEvent;
import org.master.events.process.ProcessPublishedEvent;
import org.master.model.process.ProcessReadModel;

import java.util.UUID;

@ApplicationScoped
public class ProcessReadRepository {
    @Inject
    EntityManager em;

    public void create(ProcessCreatedEvent processCreatedEvent) {
        ProcessReadModel processReadModel = new ProcessReadModel();
        processReadModel.setId(processCreatedEvent.getId());
        processReadModel.setVariables(processCreatedEvent.getVariables());
        em.persist(processReadModel);
    }

    public void publish(ProcessPublishedEvent processPublishedEvent ) {
        ProcessReadModel processReadModel = em.find(ProcessReadModel.class, processPublishedEvent.getId());

        processReadModel.setVariables(processPublishedEvent.getVariables());

        em.merge(processReadModel);
    }

    public void delete(ProcessDeletedEvent processDeletedEvent) {
        em.remove(em.find(ProcessReadModel.class, processDeletedEvent.getId()));
    }

    public ProcessReadModel findByUuid(UUID uuid) {
        return em.find(ProcessReadModel.class, uuid);
    }
}
