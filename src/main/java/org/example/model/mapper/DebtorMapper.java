package org.example.model.mapper;

import org.example.model.CreateDebtor;
import org.example.model.UpdateDebtor;
import org.example.model.database.Debtor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DebtorMapper {
    @Mappings({
            @Mapping(source = "createDebtor.doc_number", target = "docNumber")
    })
    Debtor toDebtor(CreateDebtor createDebtor);

    @Mappings({
            @Mapping(target = "docNumber", ignore = true)
    })
    Debtor toDebtor(UpdateDebtor updateDebtor);
}
