package com.example.project.admin.user;

import com.example.project.base.exception.InvalidPasswordException;
import com.example.project.base.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }

    @Override
    public List<String> getAllRole() {
        return roleRepository.findAllRoleId();
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO, User user) {
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmNewPassword())) {
            throw new InvalidPasswordException("New password and confirm new password must be the same");
        }
        if (!encoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }
        user = userRepository.findById(user.getId()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        user.setPassword(encoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
