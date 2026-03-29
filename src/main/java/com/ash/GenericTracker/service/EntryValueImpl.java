package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.*;
import com.ash.GenericTracker.entity.Entry;
import com.ash.GenericTracker.entity.EntryRow;
import com.ash.GenericTracker.entity.EntryValue;
import com.ash.GenericTracker.entity.Parameter;
import com.ash.GenericTracker.repository.EntryRepository;
import com.ash.GenericTracker.repository.EntryRowRepository;
import com.ash.GenericTracker.repository.EntryValueRepository;
import com.ash.GenericTracker.repository.ParameterRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class EntryValueImpl implements EntryValueService{
    private EntryRepository entryRepository;
    private ParameterRepository parameterRepository;
    private EntryRowRepository entryRowRepository;
    private EntryValueRepository entryValueRepository;
    @Override
    public EntryDetailResponse getEntryDetails(UUID entryId, UUID userId) {

        // 1️⃣ Validate entry
        Entry entry = entryRepository.findByIdAndUserId_Id(entryId, userId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        UUID bucketId = entry.getBucketId().getId();

        // 2️⃣ Fetch parameters
        List<Parameter> parameters =
                parameterRepository.findByBucketId_IdAndIsActiveTrueOrderByParameterOrder(bucketId);
                 //   parameterRepository.findByBucketId_IdAndIsActiveTrueOrderByParameterOrder(entry.getBucketId());
        // 3️⃣ Fetch rows
        List<EntryRow> rows = entryRowRepository.findByEntryIdOrderByRowIndex(entryId);

        // 4️⃣ Fetch values
        List<EntryValue> values = entryValueRepository.findByEntryId(entryId);

        // Map: rowId -> values
        Map<UUID, List<EntryValue>> rowValueMap = values.stream()
                .collect(Collectors.groupingBy(ev -> ev.getEntryRow().getId()));

        // 5️⃣ Map rows
        List<RowResponse> rowResponses = rows.stream()
                .map(row -> {

                    List<ValueResponse> valueResponses =
                            rowValueMap.getOrDefault(row.getId(), List.of())
                                    .stream()
                                    .map(ev -> {

                                        Object value = null;

                                        if (ev.getValueText() != null) value = ev.getValueText();
                                        else if (ev.getValueNumber() != null) value = ev.getValueNumber();
                                        else if (ev.getValueTime() != null) value = ev.getValueTime();

                                        return ValueResponse.builder()
                                                .parameterId(ev.getParameter().getId())
                                                .value(value)
                                                .build();
                                    })
                                    .toList();

                    return RowResponse.builder()
                            .rowId(row.getId())
                            .rowIndex(row.getRowIndex())
                            .values(valueResponses)
                            .build();
                })
                .toList();

        // 6️⃣ Map parameters
        List<ParameterResponse> parameterResponses = parameters.stream()
                .map(p -> ParameterResponse.builder()
                        .parameterId(p.getId())
                        .name(p.getParameterName())
                        .dataType(p.getDataType())
                        .parameterOrder(p.getParameterOrder())
                        .build())
                .toList();

        return EntryDetailResponse.builder()
                .entryId(entryId)
                .parameters(parameterResponses)
                .rows(rowResponses)
                .build();
    }

    @Override
    @Transactional
    public void saveEntryRows(EntryValueRequest request, UUID userId) {

        // 1️⃣ Validate entry
        Entry entry = entryRepository.findByIdAndUserId_Id(request.getEntryId(), userId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        UUID bucketId = entry.getBucketId().getId();

        // 2️⃣ Fetch parameters
        List<Parameter> parameters =
                parameterRepository.findByBucketId_IdAndIsActiveTrueOrderByParameterOrder(bucketId);

        Map<UUID, Parameter> paramMap = parameters.stream()
                .collect(Collectors.toMap(Parameter::getId, p -> p));

        int rowIndex = 1;

        // 3️⃣ Loop rows
        for (RowInput rowInput : request.getRows()) {

            // Create row
            EntryRow row = EntryRow.builder()
                    .entry(entry)
                    .rowIndex(rowIndex++)
                    .build();

            entryRowRepository.save(row);

            // 4️⃣ Loop values inside row
            for (ValueInput val : rowInput.getValues()) {

                Parameter param = paramMap.get(val.getParameterId());

                if (param == null) {
                    throw new RuntimeException("Invalid parameter");
                }

                EntryValue ev = new EntryValue();
                ev.setEntry(entry);
                ev.setEntryRow(row);
                ev.setParameter(param);

                // reset
                ev.setValueText(null);
                ev.setValueNumber(null);
                ev.setValueTime(null);

                // datatype handling
                switch (param.getDataType()) {

                    case "TEXT" -> ev.setValueText(val.getValue().toString());

                    case "NUMBER" -> {
                        try {
                            ev.setValueNumber(Double.valueOf(val.getValue().toString()));
                        } catch (Exception e) {
                            throw new RuntimeException("Invalid number for parameter: " + param.getParameterName());
                        }
                    }

                    case "TIME" -> ev.setValueTime(val.getValue().toString());

                    default -> throw new RuntimeException("Unsupported datatype");
                }

                entryValueRepository.save(ev);
            }
        }
    }
}
