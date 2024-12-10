package org.master.query.process.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.dto.process.ProcessDetailDTO;
import org.master.dto.process.ProcessListDTO;
import org.master.service.process.ProcessService;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProcessQueryHandler {
    @Inject
    ProcessService processService;

    public List<ProcessListDTO> getProcessList() {
        return processService.getAllProcesses();
    }
    public ProcessDetailDTO getProcessById(UUID id) {
        return processService.findByUuid(id);
    }
}
