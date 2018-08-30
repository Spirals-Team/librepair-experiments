package ru.job4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dao.*;
import ru.job4j.dto.CatalogDto;
import ru.job4j.dto.UserDto;
import ru.job4j.model.Address;
import ru.job4j.model.MusicType;
import ru.job4j.model.Role;
import ru.job4j.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UserRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);

    private static final ResourceBundle RB_USER_SQL = ResourceBundle.getBundle("users");
    private DbConnector conn = DbConnector.getInstance();

    private UserDao userDao = new UserDaoImpl();
    private AddressDaoImpl addressDao = new AddressDaoImpl();
    private CatalogDaoImpl catalogDao = new CatalogDaoImpl();
    private MusicTypeDaoImpl musicTypeDao = new MusicTypeDaoImpl();
    private RoleDaoImpl roleDao = new RoleDaoImpl();

    public List<User> findAll() {
        return userDao.findAll().stream()
            .map(this::extractUser).collect(Collectors.toList());
    }

    public Long create(final User user) {
        Long result = addressDao.create(user.getAddress());
        Role role = roleDao.findOne(user.getRole().getType());
        List<MusicType> realTypes = findMusicTypes(user.getGenres());
        if (role.isEmpty() || realTypes.isEmpty()) {
            result = -1L;
        }
        UserDto userDto = new UserDto()
            .setEmail(user.getEmail())
            .setPassw(user.getPassw())
            .setRoleId(role.getId())
            .setAddressId(result);
        Long userId = addUser(result, userDto);
        result = createMusicCatalog(userId, realTypes);
        return isSuccess(result) ? userId : -1L;
    }

    public boolean update(final User user) {
        UserDto oldUserDto = userDao.findOne(user.getId());
        user.getAddress().setId(oldUserDto.getAddressId());
        boolean result = addressDao.update(user.getAddress());
        Role role = roleDao.findOne(user.getRole().getType());
        List<MusicType> realTypes = findMusicTypes(user.getGenres());
        if (role.isEmpty() || realTypes.isEmpty()) {
            result = false;
        }
        UserDto userDto = new UserDto()
            .setId(user.getId())
            .setEmail(user.getEmail())
            .setPassw(user.getPassw())
            .setRoleId(role.getId())
            .setAddressId(user.getAddress().getId())
            .setCreateDate(Date.valueOf(LocalDate.now()));
        if (result) {
            result = userDao.update(userDto);
        }
        if (result) {
            updateMusicCatalog(userDto.getId(), realTypes);
        }
        return result;
    }

    private List<MusicType> findMusicTypes(final List<MusicType> genres) {
        List<MusicType> musicTypes = musicTypeDao.findAll();
        List<String> userGenres = genres.stream()
            .map(MusicType::getGenre).collect(Collectors.toList());
        return musicTypes.stream()
            .filter(g -> userGenres.contains(g.getGenre()))
            .collect(Collectors.toList());
    }

    public List<User> findByAddress(final Address address) {
        return findByConditionId("select.by.address", address.getId());
    }

    public List<User> findByAddressPart(final String address) {
        String searchCondition = "%" + address + "%";
        List<UserDto> users = new ArrayList<>();
        try (PreparedStatement pstmt = conn.connect().prepareStatement(
            RB_USER_SQL.getString("select.by.address.string"))) {
            pstmt.setString(1, searchCondition);
            try (ResultSet rs = pstmt.executeQuery()) {
                users = extractUserDtos(rs);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return users.stream().map(this::extractUser).collect(Collectors.toList());
    }

    public List<User> findByRole(final Role role) {
        return findByConditionId("select.by.role", role.getId());
    }

    public List<User> findByMusicType(final MusicType genre) {
        return findByConditionId("select.by.genre", genre.getId());
    }

    public User findByEmailPassw(final String email, final String passw) {
        UserDto userDto = new UserDto();
        try (PreparedStatement pstmt = conn.connect().prepareStatement(
            RB_USER_SQL.getString("select.user.by.email.passw"))) {
            pstmt.setString(1, email);
            pstmt.setString(2, passw);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    userDto = extractUserDto(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return this.extractUser(userDto);
    }

    /**
     * Check matching of credentials
     *
     * @param email email
     * @param passw passw
     * @return result
     */
    public boolean isCredential(final String email, final String passw) {
        return !findByEmailPassw(email, passw).isEmpty();
    }

    private List<User> findByConditionId(final String propName, final Long id) {
        List<UserDto> users = new ArrayList<>();
        try (PreparedStatement pstmt = conn.connect().prepareStatement(
            RB_USER_SQL.getString(propName))) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                users = extractUserDtos(rs);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return users.stream().map(this::extractUser).collect(Collectors.toList());
    }

    private User extractUser(final UserDto userDto) {
        User user = new User();
        if (!userDto.isEmpty()) {
            Role role = roleDao.findOne(userDto.getRoleId());
            Address addr = addressDao.findOne(userDto.getAddressId());
            List<CatalogDto> genreIds = catalogDao.findAll().stream()
                .filter(id -> id.getUserId().equals(userDto.getId()))
                .collect(Collectors.toList());
            List<MusicType> catalog = musicTypeDao.findAll().stream()
                .filter(g -> isGenreInCatalog(genreIds, g))
                .collect(Collectors.toList());
            user = buildUser(userDto, role, addr, catalog);
        }
        return user;
    }

    private List<UserDto> extractUserDtos(final ResultSet rs) throws SQLException {
        List<UserDto> users = new ArrayList<>();
        while (rs.next()) {
            users.add(extractUserDto(rs));
        }
        return users;
    }

    private UserDto extractUserDto(final ResultSet rs) throws SQLException {
        return new UserDto().setId(rs.getLong("id"))
            .setEmail(rs.getString("email"))
            .setPassw(rs.getString("passw"))
            .setRoleId(rs.getLong("user_role"))
            .setAddressId(rs.getLong("user_address"))
            .setCreateDate(rs.getDate("create_date"));
    }

    private Long createMusicCatalog(final Long userId,
                                    final List<MusicType> realTypes) {
        Long result = userId;
        for (MusicType genre : realTypes) {
            if (!isSuccess(result)) {
                break;
            }
            result = catalogDao.create(new CatalogDto(result, genre.getId()));
        }
        return result;
    }

    private Long updateMusicCatalog(final Long userId,
                                    final List<MusicType> realTypes) {
        catalogDao.deleteAllByUserId(userId);
        Long result = userId;
        for (MusicType genre : realTypes) {
            if (!isSuccess(result)) {
                break;
            }
            result = catalogDao.create(new CatalogDto(userId, genre.getId()));
        }
        return result;
    }

    private Long addUser(final Long userId, final UserDto userDto) {
        return isSuccess(userId) ? userDao.create(userDto) : userId;
    }

    private boolean isSuccess(final Long result) {
        return result != -1;
    }

    private User buildUser(final UserDto u, final Role role,
                           final Address addr, final List<MusicType> catalog) {
        return new User().setId(u.getId())
            .setEmail(u.getEmail())
            .setPassw(u.getPassw())
            .setRole(role)
            .setAddress(addr)
            .setGenres(catalog)
            .setCreateDate(u.getCreateDate());
    }

    private boolean isGenreInCatalog(final List<CatalogDto> genreIds,
                                     final MusicType genre) {
        return genreIds.stream()
            .map(CatalogDto::getGenreId).collect(Collectors.toList())
            .contains(genre.getId());
    }
}
