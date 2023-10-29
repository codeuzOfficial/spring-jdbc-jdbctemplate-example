package dasturlash.uz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class StudentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(StudentDTO dto) {
        String sql = "INSERT INTO student (name,surname,created_date) values('%s','%s','%s')";
        sql = String.format(sql, dto.getName(), dto.getSurname(), dto.getCreatedDate());
        jdbcTemplate.update(sql);
    }

    public void delete(Integer studentId) {
        String sql = "delete from student whete id = " + studentId;
        jdbcTemplate.update(sql);
    }

    public void saveWithArguments(StudentDTO dto) {
        String sql = "INSERT INTO student (name,surname,created_date) values(?,?,?)";
        jdbcTemplate.update(sql, dto.getName(), dto.getSurname(), dto.getCreatedDate());
    }

    public void saveWithArgumentsAndTypes(StudentDTO dto) {
        String sql = "INSERT INTO student (name,surname,created_date) values(?,?,?)";

        Object[] args = new Object[3];
        args[0] = dto.getName();
        args[1] = dto.getSurname();
        args[2] = dto.getCreatedDate();

        int[] argTypes = new int[3];
        argTypes[0] = Types.VARCHAR;
        argTypes[1] = Types.VARCHAR;
        argTypes[2] = Types.TIMESTAMP;

        jdbcTemplate.update(sql, argTypes, argTypes);
    }

    public void savePreparedStatementSetter(StudentDTO dto) {
        String sql = "INSERT INTO student (name,surname,created_date) values(?,?,?)";

        PreparedStatementSetter setter = ps -> {
            ps.setString(1, dto.getName());
            ps.setString(2, dto.getSurname());
            ps.setTimestamp(3, Timestamp.valueOf(dto.getCreatedDate()));
        };

        jdbcTemplate.update(sql, setter);
    }

    /**
     * Update
     */
    // BeanPropertyRowMapper
    public List<StudentDTO> getStudentListWithMapper() {
        String sql = "select * from Student";
        List<StudentDTO> studentList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(StudentDTO.class));
        return studentList;
    }


    // custom row mapper
    public List<StudentDTO> getStudentListWithCustomRowMapper() {
        String sql = "select * from Student";

        RowMapper<StudentDTO> customMapper = new RowMapper<StudentDTO>() {
            @Override
            public StudentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                StudentDTO dto = new StudentDTO();
                dto.setId(rs.getInt("id"));
                dto.setName(rs.getString("name"));
                dto.setSurname(rs.getString("surname"));
                dto.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                return dto;
            }
        };

        List<StudentDTO> studentList = jdbcTemplate.query(sql, customMapper);
        return studentList;
    }

    public List<StudentDTO> getStudentListWithCustomRowMapperUsingPrepareStatement(String name, String surname) {
        String sql = "select * from Student where name like ? and surname like ?";

        RowMapper<StudentDTO> customMapper = new RowMapper<StudentDTO>() {
            @Override
            public StudentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                StudentDTO dto = new StudentDTO();
                dto.setId(rs.getInt("id"));
                dto.setName(rs.getString("name"));
                dto.setSurname(rs.getString("surname"));
                dto.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                return dto;
            }
        };

        Object[] args = new Object[2];
        args[0] = "%" + name + "%";
        args[1] = "%" + surname + "%";

        List<StudentDTO> studentList = jdbcTemplate.query(sql, args, customMapper);
        return studentList;
    }

    public StudentDTO getStudentByIdWithQueryAndResultSetExtractorAndArgs(Integer id) {
        String sql = "select * from Student where id=?";

        ResultSetExtractor<StudentDTO> extractor = new ResultSetExtractor<StudentDTO>() {
            @Override
            public StudentDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    StudentDTO dto = new StudentDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setName(rs.getString("name"));
                    dto.setSurname(rs.getString("surname"));
                    dto.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
                    return dto;
                }
                return null;
            }
        };
        Object[] args = new Object[1];
        args[0] = id;
        StudentDTO student = jdbcTemplate.query(sql, args, extractor);
        return student;
    }


    public List<StudentDTO> getStudentListWithPreparedStatementSetterAndRowMapper(String name) {
        String sql = "select * from Student where name like ?";
        String paramName = "%" + name + "%";
        PreparedStatementSetter preparedStatementSetter = ps -> ps.setString(1, paramName);

        RowMapper<StudentDTO> customMapper = (rs, rowNum) -> {
            StudentDTO dto = new StudentDTO();
            dto.setId(rs.getInt("id"));
            dto.setName(rs.getString("name"));
            dto.setSurname(rs.getString("surname"));
            dto.setCreatedDate(rs.getTimestamp("created_date").toLocalDateTime());
            return dto;
        };

        List<StudentDTO> studentList = jdbcTemplate.query(sql, preparedStatementSetter, customMapper);
        return studentList;
    }

    public List<Map<String, Object>> getStudentListWithQueryForeList() {
        String sql = "select * from Student";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        return result;
    }

    public List<String> getStudentListWithQueryForeListAndType() {
        String sql = "select name from Student";

        List<String> result = jdbcTemplate.queryForList(sql, String.class);
        return result;
    }

    public List<String> getStudentListWithQueryForeListAndTypeAndArgs(String name) {
        String sql = "select name from Student where name like ?";

        Object[] args = new Object[1];
        args[0] = "%" + name + "%";

        List<String> result = jdbcTemplate.queryForList(sql, String.class, args);
        return result;
    }


    public Map<String, Object> getStudentListWithQueryForMap() {
        String sql = "select * from Student limit 1";

        Map<String, Object> result = jdbcTemplate.queryForMap(sql);
        return result;
    }

    public Map<String, Object> getStudentListWithQueryForMapAndArgs(String name) {
        String sql = "select * from Student where name like ? limit 1";

        Object[] args = new Object[1];
        args[0] = "%" + name + "%";

        Map<String, Object> result = jdbcTemplate.queryForMap(sql, args);
        return result;
    }


    public Long getStudentCountWithQueryForObject() {
        String sql = "select count(*) from Student";

        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count;
    }


    public Long getStudentCountWithQueryForObjectAndArgs(String name) {
        String sql = "select count(*) from Student where name like ?";

        Object[] args = new Object[1];
        args[0] = "%" + name + "%";

        Long count = jdbcTemplate.queryForObject(sql, Long.class, args);
        return count;
    }

    public Long getStudentCountWithQueryForObjectAndArgsAndRowMapper(String name) {
        String sql = "select count(*) from Student where name like ?";

        Object[] args = new Object[1];
        args[0] = "%" + name + "%";

        RowMapper<Long> customMapper = (rs, rowNum) -> {
            Long count = rs.getLong("count");
            return count;
        };

        Long count = jdbcTemplate.queryForObject(sql, customMapper, args);
        return count;
    }

    public void createTableWithExecute() {
        String sql = "Create table mazgijon(id serial primary key," +
                "name varchar not null," +
                "surname varchar not null," +
                "age int not null);";
        jdbcTemplate.execute(sql);
    }

    public void createTableWithExecuteAndCallBack() {
        String sql = "create table if not exists mazgijon(id serial primary key," +
                "name varchar not null," +
                "surname varchar not null," +
                "age int not null);";

        PreparedStatementCallback callback = ps -> {
            System.out.println("Do some action");
            return null;
        };
        jdbcTemplate.execute(sql, callback);
    }

}
