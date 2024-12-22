package org.example.model.mapper;

import org.example.model.database.Debts;
import org.example.model.debts.CreateDebt;
import org.example.model.debts.UpdateDebt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DebtMapper {
    @Mappings({
            @Mapping(source = "createDebt.id_debtor", target = "idDebtor")
    })
    Debts toDebtor(CreateDebt createDebt);

    @Mappings({
//            @Mapping(target = "docNumber", ignore = true)
    })
    Debts toDebtor(UpdateDebt updateDebt);
}
