package ru.job4j.tracker.start;

import ru.job4j.tracker.connection.ConnectionSQL;
import ru.job4j.tracker.connection.Query;
import ru.job4j.tracker.models.Comment;
import ru.job4j.tracker.models.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Клвас Трэкер.
 * @author Hincu Andrei (andreih1981@gmail.com)on 04.09.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Tracker {
    /**
     * Хранилище заявок.
     */
    private List<Item> items;
    /**
     * Подключение к бд.
     */
    private final Connection connection;

    public Tracker(ConnectionSQL connectionSQL) {
        this.connection = connectionSQL.getConnection();
        init();
    }

    /**
     * Инициализация трекера. При первом запуске создает пустую таблицу в бд.
     */
    private void init() {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(Query.CREATE_TRACKER_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Добавление заявок.
     * @param item - заявка.
     * @return - заявка.
     */
    public Item add(Item item) {
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(Query.INSERT_NEW_ITEM, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDesc());
            preparedStatement.setTimestamp(3, new Timestamp(item.getCreated()));
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    item.setId(String.valueOf(generatedKeys.getInt(1)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
    /**
     * Редактирование  заявок.
     * @param item item.
     */
    public void update(Item item) {
        if (item != null) {
            try (final PreparedStatement preparedStatement = this.connection.prepareStatement(Query.UPDATE_ITEM)) {
                preparedStatement.setString(1, item.getName());
                preparedStatement.setString(2, item.getDesc());
                preparedStatement.setInt(3, Integer.parseInt(item.getId()));
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Удаление заявок.
     * @param item item.
     */
    public void delete(Item item) {
        if (item != null) {
            try (final PreparedStatement preparedStatement = this.connection.prepareStatement(Query.REMOVE_ALL_COMMENTS)) {
                preparedStatement.setInt(1, Integer.parseInt(item.getId()));
                preparedStatement.executeUpdate();
                try (final PreparedStatement preparedStatement1 = connection.prepareStatement(Query.DELETE_ITEM)) {
                    preparedStatement1.setInt(1, Integer.parseInt(item.getId()));
                    preparedStatement1.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Получение списка всех заявок.
     * @return массив заявок.
     */
    public List<Item> getAll() {
        try (final Statement statement = this.connection.createStatement();
             final ResultSet rs = statement.executeQuery(Query.SELECT_ALL_ITEMS)) {
            complete(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    /**
     * Получение списка по имени.
     * @param key key.
     * @return item.
     */
    public List<Item> findByName(String key) {
        try (final PreparedStatement preparedStatement = connection.prepareStatement(Query.SELECT_BY_NAME)) {
            preparedStatement.setString(1, "%" + key + "%");
            try (final ResultSet rs = preparedStatement.executeQuery()) {
                complete(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Метод заполняет список получеными из запроса данными.
     * @param rs результат запроса к бд.
     */
    private void complete(ResultSet rs) {
        if (items == null) {
            items = new ArrayList<>();
        } else {
            items.clear();
        }
        try  {
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getString("id"));
                item.setName(rs.getString("name"));
                item.setDesc(rs.getString("description"));
                item.setCreated(rs.getTimestamp("create_date").getTime());
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Получение заявки по id.
     * @param id id.
     * @return item.
     */
    public Item findById(String id) {
        Item item = null;
        try (final PreparedStatement preparedStatement = this.connection.prepareStatement(Query.SELECT_BY_ID)) {
            preparedStatement.setInt(1, Integer.parseInt(id));
            try (final ResultSet rs = preparedStatement.executeQuery()) {
                complete(rs);
            }
            if (!items.isEmpty()) {
                item = items.get(0);
                item.setComments(getAllComments(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод возвращает все коментарии к заявке.
     * @param id id заявки.
     * @return список коментариев.
     */
    public List<Comment> getAllComments(String id) {
        List<Comment> comments = new ArrayList<>();
        try (final Statement statement = connection.createStatement();
             final PreparedStatement preparedStatement = connection.prepareStatement(Query.SELECT_ALL_COMMENTS)) {
            statement.executeUpdate(Query.CREATE_COMMENTS_TABLE);
            preparedStatement.setInt(1, Integer.parseInt(id));
            try (final ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Comment comment = new Comment();
                    comment.setId(rs.getInt("id"));
                    comment.setText(rs.getString("description"));
                    comment.setCreate(rs.getTimestamp("data_create").getTime());
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments.isEmpty() ? null : comments;
    }

    /**
     * Метод добавляет новый коментарий к заявке.
     * @param id id заявки.
     * @param text содержимое коментария.
     * @return 0 или 1 успешна ли операция.
     */
    public int addNewComment(String id, String text) {
        int result = 0;
        try (final Statement statement = this.connection.createStatement();
             final PreparedStatement preparedStatement = connection.prepareStatement(Query.INSERT_NEW_COMMENT)) {
            statement.executeUpdate(Query.CREATE_COMMENTS_TABLE);
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.setString(2, text);
            preparedStatement.setTimestamp(3, new Timestamp(new Date(System.currentTimeMillis()).getTime()));
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    private static final String ALL = "all";

    /**
     * Метод удаляет коментарии к заявке, если text равен ALL тогда все коментарии, если id коментария  то только этот коментарий.
     * @param id ID Item которому пренадлежит коментарий.
     * @param text либо ALL для удаления всех коментариев либо id для удаления конкретного коментария.
     * @return колличество столбцов над которыми произвелись изменения.
     */
    public int  deleteComment(String id, String text) {
        int result = 0;
        try {
            if (ALL.equalsIgnoreCase(text)) {
                try (final PreparedStatement preparedStatement = this.connection.prepareStatement(Query.REMOVE_ALL_COMMENTS)) {
                    preparedStatement.setInt(1, Integer.parseInt(id));
                    result = preparedStatement.executeUpdate();
                }
            } else {
                try (final PreparedStatement preparedStatement = connection.prepareStatement(Query.REMOVE_COMMENT)) {
                    preparedStatement.setInt(1, Integer.parseInt(text));
                    preparedStatement.setInt(2, Integer.parseInt(id));
                    result = preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
