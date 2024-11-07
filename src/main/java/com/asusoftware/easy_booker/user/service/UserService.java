package com.asusoftware.easy_booker.user.service;

import com.asusoftware.easy_booker.user.model.User;
import com.asusoftware.easy_booker.user.model.dto.UserRequestDto;
import com.asusoftware.easy_booker.user.model.dto.UserResponseDto;
import com.asusoftware.easy_booker.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Obține toți utilizatorii
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Obține un utilizator după ID
    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToResponseDTO(user);
    }

    // Actualizează un utilizator
    @Transactional
    public UserResponseDto updateUser(UUID id, UserRequestDto UserRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(UserRequestDto.getUsername());
        user.setEmail(UserRequestDto.getEmail());
        user.setRole(UserRequestDto.getRole());

        // Opțional: dacă o nouă parolă este trimisă, actualizeaz-o
        if (UserRequestDto.getPassword() != null && !UserRequestDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(UserRequestDto.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return convertToResponseDTO(updatedUser);
    }

    // Șterge un utilizator
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    // Metodă de conversie a entității User la UserResponseDto
    private UserResponseDto convertToResponseDTO(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());
        return dto;
    }
}