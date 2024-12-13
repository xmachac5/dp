package org.master.dto.process.task;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.master.model.process.task.TaskType;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class TaskListDTO {
    private UUID id ;
    private TaskType type;
    private UUID targetEntityUuid;
    private JsonNode variableMapping;
    private JsonNode columnsMapping;
}
