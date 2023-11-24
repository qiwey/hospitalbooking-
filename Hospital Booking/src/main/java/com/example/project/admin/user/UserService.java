package com.example.project.admin.user;

import java.util.List;

public interface UserService {
    User getUserById(Long id);

    List<String> getAllRole();

    void changePassword(ChangePasswordDTO changePasswordDTO, User user);

    List<Role> getAllRoles();
}
