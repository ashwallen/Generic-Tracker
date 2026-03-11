package com.ash.GenericTracker.service;

import com.ash.GenericTracker.dto.ApiResponse;
import com.ash.GenericTracker.entity.Entry;
import com.ash.GenericTracker.entity.EntryValue;
import com.ash.GenericTracker.entity.Parameter;
import com.ash.GenericTracker.exception.CustomExceptionHandler;
import com.ash.GenericTracker.repository.EntryRepository;
import com.ash.GenericTracker.repository.EntryValueRepository;
import com.ash.GenericTracker.repository.ParameterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EntryService {
    private EntryRepository entryRepository;
    private EntryValueRepository entryValueRepository;
    private ParameterRepository parameterRepository;
    public ApiResponse createEntry(Entry entry) {
//        if(!isValidDate()){
//            throw new CustomExceptionHandler("Enter Valid Date");
//        }
        try {
            entryRepository.save(entry);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ApiResponse(true,"Inserted Entry Successfully",1);
    }

    private boolean isValidDate() {
        return false;
    }

    public ApiResponse createEntryValue(EntryValue entryValue) {
        try {
//           entryExist(entryValue.getEntry());
//           paramExist(entryValue.getParameter());
           if(isValidType(entryValue)){
               entryValueRepository.save(entryValue);
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ApiResponse(true,"Inserted EntryValue Successfully",1);
    }

//    private void entryExist(Entry entry) {
//        Optional<Entry>res = entryRepository.findById(entry.getId());
//        if(res.isEmpty()){
//            log.error("Entry does not exist for entryValue : %s",entry.getId());
//            throw new CustomExceptionHandler("Invalid Entry");
//        }
//    }
//
//    private void paramExist(Parameter parameter) {
//        Optional<Parameter>res = parameterRepository.findById(parameter.getId());
//        if(res.isEmpty()){
//            log.error("Parameter does not exist for entryValue : %s",parameter.getId());
//            throw new CustomExceptionHandler("Invalid Entry");
//        }
//    }

    private boolean isValidType(EntryValue entryValue) {
        String dataType = entryValue.getParameter().getDataType();
        Object valueType ;
        if(entryValue.getValueText()!=null) valueType = entryValue.getValueText();
        else if (entryValue.getValueTime()!=null) valueType = entryValue.getValueTime();
        else valueType = entryValue.getValueNumber();

        if (dataType.equalsIgnoreCase("TEXT") && (valueType instanceof String)  ||
        dataType.equalsIgnoreCase("number") && (valueType instanceof Double)){
            return true;
        }
        return false;
    }
}
