package org.master.events.process;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.master.events.BaseEvent;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProcessDeletedEvent extends BaseEvent {

    @Builder
    @JsonCreator
    public ProcessDeletedEvent(
            @JsonProperty(value = "id", required = true) UUID id
    ) {
        super(id);
    }
}