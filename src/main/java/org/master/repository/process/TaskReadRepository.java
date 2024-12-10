package org.master.repository.process;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.events.process.task.TaskCreatedEvent;
import org.master.events.process.task.TaskDeletedEvent;
import org.master.events.process.task.TaskUpdatedEvent;
import org.master.model.process.task.TaskReadModel;

@ApplicationScoped
public class TaskReadRepository {
    @Inject
    EntityManager em;

    @Inject
    ProcessReadRepository processReadRepository;


    public void create(TaskCreatedEvent taskCreatedEvent) {
        TaskReadModel taskReadModel = new TaskReadModel();

        taskReadModel.setId(taskCreatedEvent.getId());
        taskReadModel.setProcessReadModel(processReadRepository.findByUuid(taskCreatedEvent.getProcessWriteModel()));
        taskReadModel.setType(taskCreatedEvent.getType());
        taskReadModel.setColumnsMapping(taskCreatedEvent.getColumnsMapping());
        taskReadModel.setVariableMapping(taskCreatedEvent.getVariableMapping());
        taskReadModel.setTargetEntityUuid(taskCreatedEvent.getTargetEntityUuid());

        em.persist(taskReadModel);
    }

    public void update(TaskUpdatedEvent taskUpdatedEvent ) {
        TaskReadModel taskReadModel = em.find(TaskReadModel.class, taskUpdatedEvent.getId());

        taskReadModel.setColumnsMapping(taskUpdatedEvent.getColumnsMapping());
        taskReadModel.setVariableMapping(taskUpdatedEvent.getVariableMapping());
        taskReadModel.setTargetEntityUuid(taskUpdatedEvent.getTargetEntityUuid());

        em.merge(taskReadModel);
    }

    public void delete(TaskDeletedEvent taskDeletedEvent) {
        em.remove(em.find(TaskReadModel.class, taskDeletedEvent.getId()));
    }
}
