package pro.taskana.mappings;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import pro.taskana.impl.ObjectReference;
/**
 * This class is the mybatis mapping of ObjectReference.
 */
public interface ObjectReferenceMapper {

    @Select("<script>SELECT ID, COMPANY, SYSTEM, SYSTEM_INSTANCE, TYPE, VALUE "
            + "FROM TASKANA.OBJECT_REFERENCE "
            + "ORDER BY ID "
        + "<if test=\"_databaseId == 'db2'\">with UR </if> "
        + "</script>")
    @Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "company", column = "COMPANY"),
        @Result(property = "system", column = "SYSTEM"),
        @Result(property = "systemInstance", column = "SYSTEM_INSTANCE"),
        @Result(property = "type", column = "TYPE"),
        @Result(property = "value", column = "VALUE") })
    List<ObjectReference> findAll();

    @Select("<script>SELECT ID, COMPANY, SYSTEM, SYSTEM_INSTANCE, TYPE, VALUE "
            + "FROM TASKANA.OBJECT_REFERENCE "
            + "WHERE ID = #{id}"
        + "<if test=\"_databaseId == 'db2'\">with UR </if> "
        + "</script>")
    @Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "company", column = "COMPANY"),
        @Result(property = "system", column = "SYSTEM"),
        @Result(property = "systemInstance", column = "SYSTEM_INSTANCE"),
        @Result(property = "type", column = "TYPE"),
        @Result(property = "value", column = "VALUE") })
    ObjectReference findById(@Param("id") String id);

    @Select("<script>SELECT ID, COMPANY, SYSTEM, SYSTEM_INSTANCE, TYPE, VALUE "
            + "FROM TASKANA.OBJECT_REFERENCE "
            + "WHERE COMPANY = #{objectReference.company} "
            + "AND SYSTEM = #{objectReference.system} "
            + "AND SYSTEM_INSTANCE = #{objectReference.systemInstance} "
            + "AND TYPE = #{objectReference.type} "
            + "AND VALUE = #{objectReference.value} "
        + "<if test=\"_databaseId == 'db2'\">with UR </if> "
        + "</script>")
    @Results({
        @Result(property = "id", column = "ID"),
        @Result(property = "company", column = "COMPANY"),
        @Result(property = "system", column = "SYSTEM"),
        @Result(property = "systemInstance", column = "SYSTEM_INSTANCE"),
        @Result(property = "type", column = "TYPE"),
        @Result(property = "value", column = "VALUE") })
    ObjectReference findByObjectReference(@Param("objectReference") ObjectReference objectReference);

    @Insert("INSERT INTO TASKANA.OBJECT_REFERENCE (ID,  COMPANY, SYSTEM, SYSTEM_INSTANCE, TYPE, VALUE) VALUES (#{ref.id}, #{ref.company}, #{ref.system}, #{ref.systemInstance}, #{ref.type}, #{ref.value})")
    void insert(@Param("ref") ObjectReference ref);

    @Update(value = "UPDATE TASKANA.OBJECT_REFERENCE SET COMPANY = #{ref.company}, SYSTEM = #{ref.system}, SYSTEM_INSTANCE = #{ref.systemInstance}, TYPE = #{ref.type}, VALUE = #{ref.value} WHERE ID = #{ref.id}")
    void update(@Param("ref") ObjectReference ref);

    @Delete("DELETE FROM TASKANA.OBJECT_REFERENCE WHERE ID = #{id}")
    void delete(String id);
}
