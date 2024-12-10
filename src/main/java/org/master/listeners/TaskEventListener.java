package org.master.listeners;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.master.events.BaseEvent;
import org.master.events.process.task.TaskCreatedEvent;
import org.master.events.process.task.TaskDeletedEvent;
import org.master.events.process.task.TaskUpdatedEvent;
import org.master.repository.process.TaskReadRepository;

@ApplicationScoped
public class TaskEventListener {
    @Inject
    TaskReadRepository taskReadRepository;

    @ActivateRequestContext
    @Incoming("task-commands")
    @Transactional
    public void consume(BaseEvent event) {
        if (event instanceof TaskCreatedEvent) {
            handle((TaskCreatedEvent) event);
        } else if (event instanceof TaskUpdatedEvent) {
            handle((TaskUpdatedEvent) event);
        } else if (event instanceof TaskDeletedEvent) {
            handle((TaskDeletedEvent) event);
        }
    }

    private void handle(TaskCreatedEvent event) {
        taskReadRepository.create(event);
    }

    private void handle(TaskUpdatedEvent event) {
        taskReadRepository.update(event);
    }

    private void handle(TaskDeletedEvent event) {
        taskReadRepository.delete(event);
    }
}
