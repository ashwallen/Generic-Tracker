package com.ash.GenericTracker.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class ValueInput {

    private UUID parameterId;

    private Object value;
}
