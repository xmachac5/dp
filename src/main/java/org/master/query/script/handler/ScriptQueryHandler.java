package org.master.query.script.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.master.dto.script.ScriptListDTO;
import org.master.model.script.ScriptReadModel;
import org.master.service.script.ScriptService;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ScriptQueryHandler {
    @Inject
    ScriptService scriptService;

    public List<ScriptListDTO> getScriptList() {
        return scriptService.getAllScripts();
    }
    public ScriptReadModel getScriptById(UUID id) {
        return scriptService.findByUuid(id);
    }
}
