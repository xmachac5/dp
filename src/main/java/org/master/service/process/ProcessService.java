package org.master.service.process;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.master.dto.process.ProcessDetailDTO;
import org.master.dto.process.ProcessListDTO;
import org.master.dto.process.task.TaskListDTO;
import org.master.model.process.ProcessReadModel;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProcessService {

    @Inject
    EntityManager em;

    public List<ProcessListDTO> getAllProcesses(){
        return em.createQuery(
                        "SELECT new org.master.dto.process.ProcessListDTO(p.id) FROM ProcessReadModel p", ProcessListDTO.class)
                .getResultList();
    }

    public ProcessDetailDTO findByUuid(UUID uuid) {
        ProcessReadModel processReadModel = em.find(ProcessReadModel.class, uuid);
        List<TaskListDTO> taskListDTOs = em.createQuery(
                "SELECT new org.master.dto.process.task.TaskListDTO(t.id, t.type, t.targetEntityUuid," +
                        " t.variableMapping, t.columnsMapping) FROM TaskReadModel t WHERE t.processReadModel.id = :uuid",
                TaskListDTO.class
        ).setParameter("uuid", processReadModel.getId()).getResultList();

        return new ProcessDetailDTO(
                processReadModel.getId(),
                processReadModel.getVariables(),
                taskListDTOs
        );
    }
}
