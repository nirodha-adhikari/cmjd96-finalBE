package com.youtube.cmjdFinal.service;

import com.youtube.cmjdFinal.dao.RoleDao;
import com.youtube.cmjdFinal.dao.UserDao;
import com.youtube.cmjdFinal.entity.Role;
import com.youtube.cmjdFinal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);

        User adminUser = new User();
        adminUser.setUserName("niro123");
        adminUser.setUserPassword(getEncodedPassword("admin@niro"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

    }

    /*public User registerNewUser(User user) {
        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userDao.save(user);
    }*/

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public User registerNewUser(User user) {

        Role role = roleDao.findById("User").get();

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRole(roleSet);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return  userDao.save(user);
    }
}
