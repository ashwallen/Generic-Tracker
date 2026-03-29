package com.ash.GenericTracker.dto;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RowInput {

    private List<ValueInput> values;
}