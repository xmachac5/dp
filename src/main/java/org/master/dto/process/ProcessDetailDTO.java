package org.master.dto.process;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.master.model.process.task.TaskReadModel;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ProcessDetailDTO {

    private UUID id;
    private JsonNode variables;
    private List<TaskReadModel> taskReadModelList;
}
