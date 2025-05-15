package com.example.gradle.ServiceImpl;

import com.example.gradle.DTO.UserDto;
import com.example.gradle.Entity.Address;
import com.example.gradle.Entity.User;
import com.example.gradle.Exceptions.UserNotFoundException;
import com.example.gradle.Mapper.AddressMapper;
import com.example.gradle.Mapper.UserMapper;
import com.example.gradle.Repository.UserRepository;
import com.example.gradle.Service.IUserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public String addUser(UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        userRepository.save(user);
        return "User Added Successfully";
    }

    @Override
    public List<UserDto> getAllUsers() {

        return userRepository.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(int id) {
        return userRepository.findById(id).map(UserMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));
    }
    @Override
    public UserDto updateUser(int id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));

        // Update name if it's different
        if (userDto.getName() != null && !userDto.getName().equals(user.getName())) {
            user.setName(userDto.getName());
        }

        // Clear old addresses and add updated ones
        if (userDto.getAddresses() != null && !userDto.getAddresses().isEmpty()) {
            user.getAddress().clear();  // optional: orphans will be removed if cascade is set
            List<Address> updatedAddresses = userDto.getAddresses().stream()
                    .map(addressDto -> {
                        Address address = AddressMapper.dtoToEntity(addressDto);
                        address.setUser(user); // set bi-directional relationship
                        return address;
                    })
                    .collect(Collectors.toList());
            user.setAddress(updatedAddresses);
        }

        // Save user and return updated DTO
        User updatedUser = userRepository.save(user);
        return UserMapper.toDto(updatedUser);
    }


    @Override
    @Transactional
    public String deleteUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found."));
        userRepository.delete(user);
        return "User Deleted Successfully";
    }

}