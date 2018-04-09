package lelental.repository;

import fr.inria.spirals.npefix.resi.CallChecker;
import fr.inria.spirals.npefix.resi.context.ConstructorContext;
import fr.inria.spirals.npefix.resi.context.MethodContext;
import fr.inria.spirals.npefix.resi.context.TryContext;
import fr.inria.spirals.npefix.resi.exception.ForceReturn;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import lelental.domain.Author;

public class AuthorRepositoryImpl implements AuthorRepository {
    private Connection connection;

    private PreparedStatement addAuthorStatement;

    private PreparedStatement getAllAuthorsStatement;

    private PreparedStatement deleteAuthor;

    private PreparedStatement findById;

    public AuthorRepositoryImpl(Connection connection) throws SQLException {
        ConstructorContext _bcornu_methode_context1 = new ConstructorContext(AuthorRepositoryImpl.class, 20, 462, 682);
        try {
            this.connection = connection;
            CallChecker.varAssign(this.connection, "this.connection", 21, 543, 571);
            if (!(isDatabaseReady())) {
                populateDb();
            }
            setConnection(connection);
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    public AuthorRepositoryImpl() {
        ConstructorContext _bcornu_methode_context2 = new ConstructorContext(AuthorRepositoryImpl.class, 28, 689, 725);
        try {
        } finally {
            _bcornu_methode_context2.methodEnd();
        }
    }

    public boolean isDatabaseReady() {
        MethodContext _bcornu_methode_context1 = new MethodContext(boolean.class, 31, 732, 1224);
        try {
            CallChecker.varInit(this, "this", 31, 732, 1224);
            CallChecker.varInit(this.findById, "findById", 31, 732, 1224);
            CallChecker.varInit(this.deleteAuthor, "deleteAuthor", 31, 732, 1224);
            CallChecker.varInit(this.getAllAuthorsStatement, "getAllAuthorsStatement", 31, 732, 1224);
            CallChecker.varInit(this.addAuthorStatement, "addAuthorStatement", 31, 732, 1224);
            CallChecker.varInit(this.connection, "connection", 31, 732, 1224);
            TryContext _bcornu_try_context_1 = new TryContext(1, AuthorRepositoryImpl.class, "java.sql.SQLException");
            try {
                ResultSet rs = CallChecker.init(ResultSet.class);
                if (CallChecker.beforeDeref(connection, Connection.class, 33, 808, 817)) {
                    connection = CallChecker.beforeCalled(connection, Connection.class, 33, 808, 817);
                    if (CallChecker.beforeDeref(CallChecker.isCalled(connection, Connection.class, 33, 808, 817).getMetaData(), DatabaseMetaData.class, 33, 808, 831)) {
                        connection = CallChecker.beforeCalled(connection, Connection.class, 33, 808, 817);
                        rs = CallChecker.isCalled(CallChecker.isCalled(connection, Connection.class, 33, 808, 817).getMetaData(), DatabaseMetaData.class, 33, 808, 831).getTables(null, null, null, null);
                        CallChecker.varAssign(rs, "rs", 33, 808, 817);
                    }
                }
                boolean tableExists = CallChecker.varInit(((boolean) (false)), "tableExists", 34, 880, 907);
                rs = CallChecker.beforeCalled(rs, ResultSet.class, 35, 928, 929);
                while (CallChecker.isCalled(rs, ResultSet.class, 35, 928, 929).next()) {
                    if (CallChecker.beforeDeref(rs, ResultSet.class, 36, 987, 988)) {
                        if (CallChecker.beforeDeref("Author", String.class, 36, 961, 968)) {
                            rs = CallChecker.beforeCalled(rs, ResultSet.class, 36, 987, 988);
                            if (CallChecker.isCalled("Author", String.class, 36, 961, 968).equalsIgnoreCase(CallChecker.isCalled(rs, ResultSet.class, 36, 987, 988).getString("TABLE_NAME"))) {
                                tableExists = true;
                                CallChecker.varAssign(tableExists, "tableExists", 37, 1038, 1056);
                                break;
                            }
                        }
                    }
                } 
                return tableExists;
            } catch (SQLException e) {
                _bcornu_try_context_1.catchStart(1);
                return false;
            } finally {
                _bcornu_try_context_1.finallyStart(1);
            }
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    @Override
    public boolean insert(Author author1) {
        MethodContext _bcornu_methode_context2 = new MethodContext(boolean.class, 48, 1231, 1612);
        try {
            CallChecker.varInit(this, "this", 48, 1231, 1612);
            CallChecker.varInit(author1, "author1", 48, 1231, 1612);
            CallChecker.varInit(this.findById, "findById", 48, 1231, 1612);
            CallChecker.varInit(this.deleteAuthor, "deleteAuthor", 48, 1231, 1612);
            CallChecker.varInit(this.getAllAuthorsStatement, "getAllAuthorsStatement", 48, 1231, 1612);
            CallChecker.varInit(this.addAuthorStatement, "addAuthorStatement", 48, 1231, 1612);
            CallChecker.varInit(this.connection, "connection", 48, 1231, 1612);
            TryContext _bcornu_try_context_2 = new TryContext(2, AuthorRepositoryImpl.class, "java.sql.SQLException");
            try {
                if (CallChecker.beforeDeref(author1, Author.class, 50, 1343, 1349)) {
                    if (CallChecker.beforeDeref(addAuthorStatement, PreparedStatement.class, 50, 1311, 1328)) {
                        author1 = CallChecker.beforeCalled(author1, Author.class, 50, 1343, 1349);
                        addAuthorStatement = CallChecker.beforeCalled(addAuthorStatement, PreparedStatement.class, 50, 1311, 1328);
                        CallChecker.isCalled(addAuthorStatement, PreparedStatement.class, 50, 1311, 1328).setString(1, CallChecker.isCalled(author1, Author.class, 50, 1343, 1349).getName());
                    }
                }
                if (CallChecker.beforeDeref(author1, Author.class, 51, 1423, 1429)) {
                    author1 = CallChecker.beforeCalled(author1, Author.class, 51, 1423, 1429);
                    if (CallChecker.beforeDeref(CallChecker.isCalled(author1, Author.class, 51, 1423, 1429).getDateOfCreation(), Date.class, 51, 1423, 1449)) {
                        if (CallChecker.beforeDeref(addAuthorStatement, PreparedStatement.class, 51, 1375, 1392)) {
                            author1 = CallChecker.beforeCalled(author1, Author.class, 51, 1423, 1429);
                            addAuthorStatement = CallChecker.beforeCalled(addAuthorStatement, PreparedStatement.class, 51, 1375, 1392);
                            CallChecker.isCalled(addAuthorStatement, PreparedStatement.class, 51, 1375, 1392).setDate(2, new java.sql.Date(CallChecker.isCalled(CallChecker.isCalled(author1, Author.class, 51, 1423, 1429).getDateOfCreation(), Date.class, 51, 1423, 1449).getTime()));
                        }
                    }
                }
                if (CallChecker.beforeDeref(addAuthorStatement, PreparedStatement.class, 52, 1476, 1493)) {
                    addAuthorStatement = CallChecker.beforeCalled(addAuthorStatement, PreparedStatement.class, 52, 1476, 1493);
                    CallChecker.isCalled(addAuthorStatement, PreparedStatement.class, 52, 1476, 1493).executeUpdate();
                }
                return true;
            } catch (SQLException e) {
                _bcornu_try_context_2.catchStart(2);
                return false;
            } finally {
                _bcornu_try_context_2.finallyStart(2);
            }
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context2.methodEnd();
        }
    }

    @Override
    public void update(Author author) throws SQLException {
        MethodContext _bcornu_methode_context3 = new MethodContext(void.class, 61, 1620, 2083);
        try {
            CallChecker.varInit(this, "this", 61, 1620, 2083);
            CallChecker.varInit(author, "author", 61, 1620, 2083);
            CallChecker.varInit(this.findById, "findById", 61, 1620, 2083);
            CallChecker.varInit(this.deleteAuthor, "deleteAuthor", 61, 1620, 2083);
            CallChecker.varInit(this.getAllAuthorsStatement, "getAllAuthorsStatement", 61, 1620, 2083);
            CallChecker.varInit(this.addAuthorStatement, "addAuthorStatement", 61, 1620, 2083);
            CallChecker.varInit(this.connection, "connection", 61, 1620, 2083);
            PreparedStatement preparedStatement = CallChecker.init(PreparedStatement.class);
            if (CallChecker.beforeDeref(connection, Connection.class, 62, 1736, 1745)) {
                connection = CallChecker.beforeCalled(connection, Connection.class, 62, 1736, 1745);
                preparedStatement = CallChecker.isCalled(connection, Connection.class, 62, 1736, 1745).prepareStatement("UPDATE Author SET name = ?, date_of_creation = ? WHERE id= ? ");
                CallChecker.varAssign(preparedStatement, "preparedStatement", 62, 1736, 1745);
            }
            if (CallChecker.beforeDeref(author, Author.class, 63, 1869, 1874)) {
                if (CallChecker.beforeDeref(preparedStatement, PreparedStatement.class, 63, 1838, 1854)) {
                    author = CallChecker.beforeCalled(author, Author.class, 63, 1869, 1874);
                    preparedStatement = CallChecker.beforeCalled(preparedStatement, PreparedStatement.class, 63, 1838, 1854);
                    CallChecker.isCalled(preparedStatement, PreparedStatement.class, 63, 1838, 1854).setString(1, CallChecker.isCalled(author, Author.class, 63, 1869, 1874).getName());
                }
            }
            if (CallChecker.beforeDeref(author, Author.class, 64, 1943, 1948)) {
                author = CallChecker.beforeCalled(author, Author.class, 64, 1943, 1948);
                if (CallChecker.beforeDeref(CallChecker.isCalled(author, Author.class, 64, 1943, 1948).getDateOfCreation(), Date.class, 64, 1943, 1968)) {
                    if (CallChecker.beforeDeref(preparedStatement, PreparedStatement.class, 64, 1896, 1912)) {
                        author = CallChecker.beforeCalled(author, Author.class, 64, 1943, 1948);
                        preparedStatement = CallChecker.beforeCalled(preparedStatement, PreparedStatement.class, 64, 1896, 1912);
                        CallChecker.isCalled(preparedStatement, PreparedStatement.class, 64, 1896, 1912).setDate(2, new java.sql.Date(CallChecker.isCalled(CallChecker.isCalled(author, Author.class, 64, 1943, 1948).getDateOfCreation(), Date.class, 64, 1943, 1968).getTime()));
                    }
                }
            }
            if (CallChecker.beforeDeref(author, Author.class, 65, 2020, 2025)) {
                if (CallChecker.beforeDeref(preparedStatement, PreparedStatement.class, 65, 1991, 2007)) {
                    author = CallChecker.beforeCalled(author, Author.class, 65, 2020, 2025);
                    preparedStatement = CallChecker.beforeCalled(preparedStatement, PreparedStatement.class, 65, 1991, 2007);
                    CallChecker.isCalled(preparedStatement, PreparedStatement.class, 65, 1991, 2007).setLong(3, CallChecker.isCalled(author, Author.class, 65, 2020, 2025).getId());
                }
            }
            if (CallChecker.beforeDeref(preparedStatement, PreparedStatement.class, 66, 2045, 2061)) {
                preparedStatement = CallChecker.beforeCalled(preparedStatement, PreparedStatement.class, 66, 2045, 2061);
                CallChecker.isCalled(preparedStatement, PreparedStatement.class, 66, 2045, 2061).executeQuery();
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context3.methodEnd();
        }
    }

    @Override
    public Author findById(long id) throws SQLException {
        MethodContext _bcornu_methode_context4 = new MethodContext(Author.class, 70, 2090, 2478);
        try {
            CallChecker.varInit(this, "this", 70, 2090, 2478);
            CallChecker.varInit(id, "id", 70, 2090, 2478);
            CallChecker.varInit(this.findById, "findById", 70, 2090, 2478);
            CallChecker.varInit(this.deleteAuthor, "deleteAuthor", 70, 2090, 2478);
            CallChecker.varInit(this.getAllAuthorsStatement, "getAllAuthorsStatement", 70, 2090, 2478);
            CallChecker.varInit(this.addAuthorStatement, "addAuthorStatement", 70, 2090, 2478);
            CallChecker.varInit(this.connection, "connection", 70, 2090, 2478);
            if (CallChecker.beforeDeref(findById, PreparedStatement.class, 71, 2166, 2173)) {
                findById = CallChecker.beforeCalled(findById, PreparedStatement.class, 71, 2166, 2173);
                CallChecker.isCalled(findById, PreparedStatement.class, 71, 2166, 2173).setLong(1, id);
            }
            ResultSet resultSet = CallChecker.init(ResultSet.class);
            if (CallChecker.beforeDeref(findById, PreparedStatement.class, 72, 2221, 2228)) {
                findById = CallChecker.beforeCalled(findById, PreparedStatement.class, 72, 2221, 2228);
                resultSet = CallChecker.isCalled(findById, PreparedStatement.class, 72, 2221, 2228).executeQuery();
                CallChecker.varAssign(resultSet, "resultSet", 72, 2221, 2228);
            }
            Long idFromDb = CallChecker.init(Long.class);
            if (CallChecker.beforeDeref(resultSet, ResultSet.class, 73, 2270, 2278)) {
                resultSet = CallChecker.beforeCalled(resultSet, ResultSet.class, 73, 2270, 2278);
                idFromDb = CallChecker.isCalled(resultSet, ResultSet.class, 73, 2270, 2278).getLong("id");
                CallChecker.varAssign(idFromDb, "idFromDb", 73, 2270, 2278);
            }
            String name = CallChecker.init(String.class);
            if (CallChecker.beforeDeref(resultSet, ResultSet.class, 74, 2317, 2325)) {
                resultSet = CallChecker.beforeCalled(resultSet, ResultSet.class, 74, 2317, 2325);
                name = CallChecker.isCalled(resultSet, ResultSet.class, 74, 2317, 2325).getString("name");
                CallChecker.varAssign(name, "name", 74, 2317, 2325);
            }
            java.sql.Date dateOfCreation = CallChecker.init(java.sql.Date.class);
            if (CallChecker.beforeDeref(resultSet, ResultSet.class, 75, 2376, 2384)) {
                resultSet = CallChecker.beforeCalled(resultSet, ResultSet.class, 75, 2376, 2384);
                dateOfCreation = CallChecker.isCalled(resultSet, ResultSet.class, 75, 2376, 2384).getDate("date_of_creation");
                CallChecker.varAssign(dateOfCreation, "dateOfCreation", 75, 2376, 2384);
            }
            return new Author(idFromDb, name, dateOfCreation);
        } catch (ForceReturn _bcornu_return_t) {
            return ((Author) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context4.methodEnd();
        }
    }

    @Override
    public List<Author> findAll() throws SQLException {
        MethodContext _bcornu_methode_context5 = new MethodContext(List.class, 80, 2485, 3080);
        try {
            CallChecker.varInit(this, "this", 80, 2485, 3080);
            CallChecker.varInit(this.findById, "findById", 80, 2485, 3080);
            CallChecker.varInit(this.deleteAuthor, "deleteAuthor", 80, 2485, 3080);
            CallChecker.varInit(this.getAllAuthorsStatement, "getAllAuthorsStatement", 80, 2485, 3080);
            CallChecker.varInit(this.addAuthorStatement, "addAuthorStatement", 80, 2485, 3080);
            CallChecker.varInit(this.connection, "connection", 80, 2485, 3080);
            List<Author> authorList = CallChecker.varInit(new LinkedList<>(), "authorList", 81, 2559, 2603);
            TryContext _bcornu_try_context_3 = new TryContext(3, AuthorRepositoryImpl.class, "java.sql.SQLException");
            try {
                ResultSet resultSet = CallChecker.init(ResultSet.class);
                if (CallChecker.beforeDeref(getAllAuthorsStatement, PreparedStatement.class, 83, 2653, 2674)) {
                    getAllAuthorsStatement = CallChecker.beforeCalled(getAllAuthorsStatement, PreparedStatement.class, 83, 2653, 2674);
                    resultSet = CallChecker.isCalled(getAllAuthorsStatement, PreparedStatement.class, 83, 2653, 2674).executeQuery();
                    CallChecker.varAssign(resultSet, "resultSet", 83, 2653, 2674);
                }
                resultSet = CallChecker.beforeCalled(resultSet, ResultSet.class, 84, 2711, 2719);
                while (CallChecker.isCalled(resultSet, ResultSet.class, 84, 2711, 2719).next()) {
                    Author author = CallChecker.init(Author.class);
                    if (CallChecker.beforeDeref(resultSet, ResultSet.class, 85, 2774, 2782)) {
                        if (CallChecker.beforeDeref(resultSet, ResultSet.class, 85, 2799, 2807)) {
                            if (CallChecker.beforeDeref(resultSet, ResultSet.class, 86, 2852, 2860)) {
                                resultSet = CallChecker.beforeCalled(resultSet, ResultSet.class, 85, 2774, 2782);
                                resultSet = CallChecker.beforeCalled(resultSet, ResultSet.class, 85, 2799, 2807);
                                resultSet = CallChecker.beforeCalled(resultSet, ResultSet.class, 86, 2852, 2860);
                                author = new Author(CallChecker.isCalled(resultSet, ResultSet.class, 85, 2774, 2782).getLong("id"), CallChecker.isCalled(resultSet, ResultSet.class, 85, 2799, 2807).getString("name"), CallChecker.isCalled(resultSet, ResultSet.class, 86, 2852, 2860).getDate("date_of_creation"));
                                CallChecker.varAssign(author, "author", 85, 2774, 2782);
                            }
                        }
                    }
                    if (CallChecker.beforeDeref(authorList, List.class, 87, 2908, 2917)) {
                        authorList = CallChecker.beforeCalled(authorList, List.class, 87, 2908, 2917);
                        CallChecker.isCalled(authorList, List.class, 87, 2908, 2917).add(author);
                    }
                } 
            } catch (SQLException e) {
                _bcornu_try_context_3.catchStart(3);
                e.printStackTrace();
                return null;
            } finally {
                _bcornu_try_context_3.finallyStart(3);
            }
            return authorList;
        } catch (ForceReturn _bcornu_return_t) {
            return ((List<Author>) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context5.methodEnd();
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        MethodContext _bcornu_methode_context6 = new MethodContext(void.class, 97, 3087, 3229);
        try {
            CallChecker.varInit(this, "this", 97, 3087, 3229);
            CallChecker.varInit(id, "id", 97, 3087, 3229);
            CallChecker.varInit(this.findById, "findById", 97, 3087, 3229);
            CallChecker.varInit(this.deleteAuthor, "deleteAuthor", 97, 3087, 3229);
            CallChecker.varInit(this.getAllAuthorsStatement, "getAllAuthorsStatement", 97, 3087, 3229);
            CallChecker.varInit(this.addAuthorStatement, "addAuthorStatement", 97, 3087, 3229);
            CallChecker.varInit(this.connection, "connection", 97, 3087, 3229);
            if (CallChecker.beforeDeref(deleteAuthor, PreparedStatement.class, 98, 3159, 3170)) {
                deleteAuthor = CallChecker.beforeCalled(deleteAuthor, PreparedStatement.class, 98, 3159, 3170);
                CallChecker.isCalled(deleteAuthor, PreparedStatement.class, 98, 3159, 3170).setLong(1, id);
            }
            if (CallChecker.beforeDeref(deleteAuthor, PreparedStatement.class, 99, 3196, 3207)) {
                deleteAuthor = CallChecker.beforeCalled(deleteAuthor, PreparedStatement.class, 99, 3196, 3207);
                CallChecker.isCalled(deleteAuthor, PreparedStatement.class, 99, 3196, 3207).executeQuery();
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context6.methodEnd();
        }
    }

    @Override
    public String sayWhoYouAre() throws SQLException {
        MethodContext _bcornu_methode_context7 = new MethodContext(String.class, 103, 3236, 3683);
        try {
            CallChecker.varInit(this, "this", 103, 3236, 3683);
            CallChecker.varInit(this.findById, "findById", 103, 3236, 3683);
            CallChecker.varInit(this.deleteAuthor, "deleteAuthor", 103, 3236, 3683);
            CallChecker.varInit(this.getAllAuthorsStatement, "getAllAuthorsStatement", 103, 3236, 3683);
            CallChecker.varInit(this.addAuthorStatement, "addAuthorStatement", 103, 3236, 3683);
            CallChecker.varInit(this.connection, "connection", 103, 3236, 3683);
            PreparedStatement preparedStatement = CallChecker.init(PreparedStatement.class);
            if (CallChecker.beforeDeref(connection, Connection.class, 104, 3347, 3356)) {
                connection = CallChecker.beforeCalled(connection, Connection.class, 104, 3347, 3356);
                preparedStatement = CallChecker.isCalled(connection, Connection.class, 104, 3347, 3356).prepareStatement("SELECT (id,name,date_of_creation) FROM Author WHERE id = 1");
                CallChecker.varAssign(preparedStatement, "preparedStatement", 104, 3347, 3356);
            }
            ResultSet resultSet = CallChecker.init(ResultSet.class);
            if (CallChecker.beforeDeref(preparedStatement, PreparedStatement.class, 105, 3468, 3484)) {
                preparedStatement = CallChecker.beforeCalled(preparedStatement, PreparedStatement.class, 105, 3468, 3484);
                resultSet = CallChecker.isCalled(preparedStatement, PreparedStatement.class, 105, 3468, 3484).executeQuery();
                CallChecker.varAssign(resultSet, "resultSet", 105, 3468, 3484);
            }
            Author author = CallChecker.init(Author.class);
            if (CallChecker.beforeDeref(resultSet, ResultSet.class, 106, 3537, 3545)) {
                if (CallChecker.beforeDeref(resultSet, ResultSet.class, 106, 3562, 3570)) {
                    if (CallChecker.beforeDeref(resultSet, ResultSet.class, 106, 3591, 3599)) {
                        resultSet = CallChecker.beforeCalled(resultSet, ResultSet.class, 106, 3537, 3545);
                        resultSet = CallChecker.beforeCalled(resultSet, ResultSet.class, 106, 3562, 3570);
                        resultSet = CallChecker.beforeCalled(resultSet, ResultSet.class, 106, 3591, 3599);
                        author = new Author(CallChecker.isCalled(resultSet, ResultSet.class, 106, 3537, 3545).getLong("id"), CallChecker.isCalled(resultSet, ResultSet.class, 106, 3562, 3570).getString("name"), CallChecker.isCalled(resultSet, ResultSet.class, 106, 3591, 3599).getDate("date_of_creation"));
                        CallChecker.varAssign(author, "author", 106, 3537, 3545);
                    }
                }
            }
            author = CallChecker.beforeCalled(author, Author.class, 107, 3661, 3666);
            return "Hello I'm " + (CallChecker.isCalled(author, Author.class, 107, 3661, 3666).getName());
        } catch (ForceReturn _bcornu_return_t) {
            return ((String) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context7.methodEnd();
        }
    }

    @Override
    public Author findByName(String name) {
        MethodContext _bcornu_methode_context8 = new MethodContext(Author.class, 111, 3690, 3769);
        try {
            CallChecker.varInit(this, "this", 111, 3690, 3769);
            CallChecker.varInit(name, "name", 111, 3690, 3769);
            CallChecker.varInit(this.findById, "findById", 111, 3690, 3769);
            CallChecker.varInit(this.deleteAuthor, "deleteAuthor", 111, 3690, 3769);
            CallChecker.varInit(this.getAllAuthorsStatement, "getAllAuthorsStatement", 111, 3690, 3769);
            CallChecker.varInit(this.addAuthorStatement, "addAuthorStatement", 111, 3690, 3769);
            CallChecker.varInit(this.connection, "connection", 111, 3690, 3769);
            return null;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Author) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context8.methodEnd();
        }
    }

    @Override
    public void populateDb() throws SQLException {
        MethodContext _bcornu_methode_context9 = new MethodContext(void.class, 116, 3776, 4018);
        try {
            CallChecker.varInit(this, "this", 116, 3776, 4018);
            CallChecker.varInit(this.findById, "findById", 116, 3776, 4018);
            CallChecker.varInit(this.deleteAuthor, "deleteAuthor", 116, 3776, 4018);
            CallChecker.varInit(this.getAllAuthorsStatement, "getAllAuthorsStatement", 116, 3776, 4018);
            CallChecker.varInit(this.addAuthorStatement, "addAuthorStatement", 116, 3776, 4018);
            CallChecker.varInit(this.connection, "connection", 116, 3776, 4018);
            if (CallChecker.beforeDeref(connection, Connection.class, 117, 3845, 3854)) {
                connection = CallChecker.beforeCalled(connection, Connection.class, 117, 3845, 3854);
                if (CallChecker.beforeDeref(CallChecker.isCalled(connection, Connection.class, 117, 3845, 3854).createStatement(), Statement.class, 117, 3845, 3872)) {
                    connection = CallChecker.beforeCalled(connection, Connection.class, 117, 3845, 3854);
                    CallChecker.isCalled(CallChecker.isCalled(connection, Connection.class, 117, 3845, 3854).createStatement(), Statement.class, 117, 3845, 3872).executeUpdate("CREATE TABLE Author(id BIGINT GENERATED BY DEFAULT AS IDENTITY,name VARCHAR(20) NOT NULL, date_of_creation DATE NOT NULL)");
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context9.methodEnd();
        }
    }

    @Override
    public void dropAuthorTable() throws SQLException {
        MethodContext _bcornu_methode_context10 = new MethodContext(void.class, 121, 4025, 4168);
        try {
            CallChecker.varInit(this, "this", 121, 4025, 4168);
            CallChecker.varInit(this.findById, "findById", 121, 4025, 4168);
            CallChecker.varInit(this.deleteAuthor, "deleteAuthor", 121, 4025, 4168);
            CallChecker.varInit(this.getAllAuthorsStatement, "getAllAuthorsStatement", 121, 4025, 4168);
            CallChecker.varInit(this.addAuthorStatement, "addAuthorStatement", 121, 4025, 4168);
            CallChecker.varInit(this.connection, "connection", 121, 4025, 4168);
            if (CallChecker.beforeDeref(connection, Connection.class, 122, 4099, 4108)) {
                connection = CallChecker.beforeCalled(connection, Connection.class, 122, 4099, 4108);
                if (CallChecker.beforeDeref(CallChecker.isCalled(connection, Connection.class, 122, 4099, 4108).createStatement(), Statement.class, 122, 4099, 4126)) {
                    connection = CallChecker.beforeCalled(connection, Connection.class, 122, 4099, 4108);
                    CallChecker.isCalled(CallChecker.isCalled(connection, Connection.class, 122, 4099, 4108).createStatement(), Statement.class, 122, 4099, 4126).executeUpdate("DROP TABLE Author");
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context10.methodEnd();
        }
    }

    @Override
    public Connection getConnection() {
        MethodContext _bcornu_methode_context11 = new MethodContext(Connection.class, 126, 4175, 4256);
        try {
            CallChecker.varInit(this, "this", 126, 4175, 4256);
            CallChecker.varInit(this.findById, "findById", 126, 4175, 4256);
            CallChecker.varInit(this.deleteAuthor, "deleteAuthor", 126, 4175, 4256);
            CallChecker.varInit(this.getAllAuthorsStatement, "getAllAuthorsStatement", 126, 4175, 4256);
            CallChecker.varInit(this.addAuthorStatement, "addAuthorStatement", 126, 4175, 4256);
            CallChecker.varInit(this.connection, "connection", 126, 4175, 4256);
            return connection;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Connection) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context11.methodEnd();
        }
    }

    public void setConnection(Connection connection) throws SQLException {
        MethodContext _bcornu_methode_context12 = new MethodContext(void.class, 130, 4263, 4861);
        try {
            CallChecker.varInit(this, "this", 130, 4263, 4861);
            CallChecker.varInit(connection, "connection", 130, 4263, 4861);
            CallChecker.varInit(this.findById, "findById", 130, 4263, 4861);
            CallChecker.varInit(this.deleteAuthor, "deleteAuthor", 130, 4263, 4861);
            CallChecker.varInit(this.getAllAuthorsStatement, "getAllAuthorsStatement", 130, 4263, 4861);
            CallChecker.varInit(this.addAuthorStatement, "addAuthorStatement", 130, 4263, 4861);
            CallChecker.varInit(this.connection, "connection", 130, 4263, 4861);
            this.connection = connection;
            CallChecker.varAssign(this.connection, "this.connection", 131, 4342, 4370);
            if (CallChecker.beforeDeref(connection, Connection.class, 132, 4401, 4410)) {
                connection = CallChecker.beforeCalled(connection, Connection.class, 132, 4401, 4410);
                addAuthorStatement = CallChecker.isCalled(connection, Connection.class, 132, 4401, 4410).prepareStatement("INSERT INTO Author (name, date_of_creation) VALUES (?, ?)");
                CallChecker.varAssign(this.addAuthorStatement, "this.addAuthorStatement", 132, 4380, 4531);
            }
            if (CallChecker.beforeDeref(connection, Connection.class, 135, 4566, 4575)) {
                connection = CallChecker.beforeCalled(connection, Connection.class, 135, 4566, 4575);
                getAllAuthorsStatement = CallChecker.isCalled(connection, Connection.class, 135, 4566, 4575).prepareStatement("SELECT id, name, date_of_creation FROM Author");
                CallChecker.varAssign(this.getAllAuthorsStatement, "this.getAllAuthorsStatement", 135, 4541, 4659);
            }
            if (CallChecker.beforeDeref(connection, Connection.class, 137, 4684, 4693)) {
                connection = CallChecker.beforeCalled(connection, Connection.class, 137, 4684, 4693);
                deleteAuthor = CallChecker.isCalled(connection, Connection.class, 137, 4684, 4693).prepareStatement("DELETE FROM Author WHERE id = ?");
                CallChecker.varAssign(this.deleteAuthor, "this.deleteAuthor", 137, 4669, 4746);
            }
            if (CallChecker.beforeDeref(connection, Connection.class, 138, 4767, 4776)) {
                connection = CallChecker.beforeCalled(connection, Connection.class, 138, 4767, 4776);
                findById = CallChecker.isCalled(connection, Connection.class, 138, 4767, 4776).prepareStatement("SELECT id,name,date_of_creation FROM Author WHERE id = ?");
                CallChecker.varAssign(this.findById, "this.findById", 138, 4756, 4854);
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context12.methodEnd();
        }
    }
}

