package com.example.gradle.Mapper;


import com.example.gradle.DTO.AddressDto;
import com.example.gradle.DTO.UserDto;
import com.example.gradle.Entity.Address;
import com.example.gradle.Entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    // Convert Entity -> DTO
    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());

        List<AddressDto> addressDtos = user.getAddress().stream().map(a -> {
            AddressDto addressDto = new AddressDto();
            addressDto.setId(a.getId());
            addressDto.setCity(a.getCity());
            addressDto.setState(a.getState());
            return addressDto;
        }).collect(Collectors.toList());

        userDto.setAddresses(addressDtos);
        return userDto;
    }

    // Convert DTO -> Entity
    public static User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());

        List<Address> addresses = dto.getAddresses().stream().map(a -> {
            Address address = new Address();
            address.setId(a.getId());
            address.setCity(a.getCity());
            address.setState(a.getState());
            address.setUser(user); // maintain back-reference
            return address;
        }).collect(Collectors.toList());

        user.setAddress(addresses);
        return user;
    }
}