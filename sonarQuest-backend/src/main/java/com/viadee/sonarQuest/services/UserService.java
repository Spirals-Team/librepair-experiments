package com.viadee.sonarQuest.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.Permission;
import com.viadee.sonarQuest.entities.RoleName;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorldService worldService;

    @Autowired
    private LevelService levelService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = findByUsername(username);
        final Set<Permission> permissions = permissionService.getAccessPermissions(user);

        final List<SimpleGrantedAuthority> authoritys = permissions
                .stream()
                .map(berechtigung -> new SimpleGrantedAuthority(berechtigung.getPermission()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), true, true, true, true, authoritys);
    }

    public User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    private User findById(final Long id) {
        return userRepository.findOne(id);
    }

    public World updateUsersCurrentWorld(final User user, final Long worldId) {
        final World world = worldService.findById(worldId);
        user.setCurrentWorld(world);
        userRepository.saveAndFlush(user);
        return user.getCurrentWorld();
    }

    public User save(final User user) {
        User toBeSaved = null;
        if (user.getId() == null) {
            toBeSaved = usernameFree(user.getUsername()) ? user : null;
            // Only the password hash needs to be saved
            toBeSaved.setPassword(encoder.encode(user.getPassword()));
            toBeSaved.setRole(roleService.findByName(user.getRole().getName()));
            toBeSaved.setCurrentWorld(user.getCurrentWorld());
            toBeSaved.setGold(0l);
            toBeSaved.setXp(0l);
            toBeSaved.setLevel(levelService.getLevelByUserXp(0l));
        } else {
            toBeSaved = findById(user.getId());
            if (!user.getUsername().equals(toBeSaved.getUsername()) && usernameFree(user.getUsername())) {
                toBeSaved.setUsername(user.getUsername());
            }
            toBeSaved.setAboutMe(user.getAboutMe());
            toBeSaved.setPicture(user.getPicture());
            toBeSaved.setCurrentWorld(user.getCurrentWorld());
            toBeSaved.setWorlds(user.getWorlds());
        }

        return toBeSaved != null ? userRepository.saveAndFlush(toBeSaved) : null;
    }

    private boolean usernameFree(final String username) {
        return userRepository.findByUsername(username) == null;
    }

    public void delete(final Long userId) {
        final User user = findById(userId);
        userRepository.delete(user);
    }

    public User findById(final long userId) {
        return userRepository.findOne(userId);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findByRole(final RoleName roleName) {
        return findAll().stream()
                .filter(user -> user.getRole().getName() == roleName)
                .collect(Collectors.toList());
    }

    public long getLevel(final long xp) {
        return calculateLevel(xp, 1);
    }

    private long calculateLevel(final long xp, final long level) {
        final long step = 10;
        long xpForNextLevel = 0;

        for (long i = 1; i <= level; i++) {
            xpForNextLevel = xpForNextLevel + step;
        }

        // Termination condition: Level 200 or when XP is smaller than the required XP to the higher level
        if (level == 200 || (xp < xpForNextLevel)) {
            return level;
        } else {
            return calculateLevel(xp, level + 1);
        }
    }

}
