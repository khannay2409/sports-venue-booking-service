package com.company.user_management.booking_domain.mappers;

import com.company.user_management.booking_domain.dto.response.SlotResponse;
import com.company.user_management.booking_domain.entity.Slot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SlotMapper {

    @Mapping(source = "id", target = "slotId")
    @Mapping(source = "status", target = "status")
    SlotResponse toSlotResponse(Slot slot);

    List<SlotResponse> toSlotResponseList(List<Slot> slots);
}
