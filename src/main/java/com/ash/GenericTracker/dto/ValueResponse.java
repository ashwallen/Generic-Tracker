package com.ash.GenericTracker.dto;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ValueResponse {

    private UUID parameterId;
    private Object value;
}