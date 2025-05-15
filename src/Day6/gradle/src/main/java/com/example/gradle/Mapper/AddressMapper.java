package com.example.gradle.Mapper;

import com.example.gradle.DTO.AddressDto;
import com.example.gradle.Entity.Address;

public class AddressMapper {
    public static AddressDto entityToDto(Address address) {
    AddressDto dto = new AddressDto();
    dto.setId(address.getId());
    dto.setCity(address.getCity());
    dto.setState(address.getState());
    return dto;
}

    public static Address dtoToEntity(AddressDto dto) {
        Address address = new Address();
        address.setId(dto.getId());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        return address;
    }
}

