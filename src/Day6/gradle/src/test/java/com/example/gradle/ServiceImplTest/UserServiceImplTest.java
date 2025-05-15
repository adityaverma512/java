package com.example.gradle.ServiceImplTest;

import com.example.gradle.DTO.AddressDto;
import com.example.gradle.DTO.UserDto;
import com.example.gradle.Entity.User;
import com.example.gradle.Exceptions.UserNotFoundException;
import com.example.gradle.Mapper.UserMapper;
import com.example.gradle.Repository.UserRepository;
import com.example.gradle.ServiceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        UserDto userDto = UserDto.builder()
                .name("Test User")
                .addresses(Collections.emptyList())
                .build();

        String result = userService.addUser(userDto);
        assertEquals("User Added Successfully", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetUserById_Success() {
        User user = User.builder().id(1).name("John").address(new ArrayList<>()).build();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserDto result = userService.getUserById(1);
        assertEquals("John", result.getName());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1));
    }

    @Test
    void testGetAllUsers() {
        User user = User.builder().id(1).name("John").address(new ArrayList<>()).build();
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));


        List<UserDto> users = userService.getAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    void testDeleteUser_Success() {
        User user = User.builder().id(1).name("John").address(new ArrayList<>()).build();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        String response = userService.deleteUser(1);
        assertEquals("User Deleted Successfully", response);
        verify(userRepository, times(1)).delete(user);
    }
    @Test
    void testAddUserWithAddress() {
        AddressDto addressDto = AddressDto.builder()
                .id(0)
                .city("Mumbai")
                .state("MH")
                .build();

        UserDto userDto = UserDto.builder()
                .name("Amit")
                .addresses(Arrays.asList(addressDto))
                .build();

        // Act
        String response = userService.addUser(userDto);

        // Assert
        assertEquals("User Added Successfully", response);

        verify(userRepository, times(1)).save(argThat(user ->
                user.getName().equals("Amit") &&
                        user.getAddress().size() == 1 &&
                        user.getAddress().get(0).getCity().equals("Mumbai") &&
                        user.getAddress().get(0).getState().equals("MH")
        ));
    }
    @Test
    void testUpdateUserWithAddress() {
        // Existing user with no address
        User existingUser = User.builder()
                .id(1)
                .name("Old Name")
                .address(new ArrayList<>())
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));

        // New DTO with new name and address
        AddressDto newAddress = AddressDto.builder()
                .city("Pune")
                .state("MH")
                .build();

        UserDto updatedDto = UserDto.builder()
                .name("Updated Name")
                .addresses(Arrays.asList(newAddress))
                .build();

        // Simulate saving updated user
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDto result = userService.updateUser(1, updatedDto);

        assertEquals("Updated Name", result.getName());
        assertEquals(1, result.getAddresses().size());
        assertEquals("Pune", result.getAddresses().get(0).getCity());

        verify(userRepository, times(1)).save(any(User.class));
    }


}
