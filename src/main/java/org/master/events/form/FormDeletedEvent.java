package org.master.events.form;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.master.events.BaseEvent;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class FormDeletedEvent extends BaseEvent {
    @Builder
    @JsonCreator
    public FormDeletedEvent(
            @JsonProperty(value = "id", required = true) UUID id
    )
    {
        super(id);
    }
}
