package dasturlash.uz;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        StudentRepository studentRepository = (StudentRepository) context.getBean("studentRepository");

        StudentDTO student1 = new StudentDTO("Ali", "Aliyev", LocalDateTime.now());
        StudentDTO student2 = new StudentDTO("Vali", "Valiyev", LocalDateTime.now());
        StudentDTO student3 = new StudentDTO("Toshmat", "Toshmatov", LocalDateTime.now());
        StudentDTO student4 = new StudentDTO("Eshmat", "Eshmatov", LocalDateTime.now());
        StudentDTO student5 = new StudentDTO("Alihon2", "Alihanov2", LocalDateTime.now());

//        studentRepository.save(student1);
//        studentRepository.save(student2);
//        studentRepository.save(student3);
//        studentRepository.save(student4);

//        studentRepository.saveWithArguments(student5);
//        studentRepository.savePreparedStatementSetter(student5);

//        List<StudentDTO> studentList = studentRepository.getStudentListWithCustomRowMapper();
//        List<StudentDTO> studentList = studentRepository.getStudentListWithCustomRowMapperUsingPrepareStatement("A", "A");
//
//        StudentDTO student = studentRepository.getStudentListWithCustomRowMapperUsingPrepareStatement(1);
//        StudentDTO student = studentRepository.getStudentByIdWithQueryAndResultSetExtractorAndArgs(1);
//        List<StudentDTO> studentList = studentRepository.getStudentListWithPreparedStatementSetterAndRowMapper("A");
//        System.out.println(student);
//        List<Map<String, Object>> studentList = studentRepository.getStudentListWithQueryForeList();
//        studentList.forEach(dto -> System.out.println(dto));
//        List<String> studentList = studentRepository.getStudentListWithQueryForeListAndType();
//        List<String> studentList = studentRepository.getStudentListWithQueryForeListAndTypeAndArgs("A");
//        studentList.forEach(dto -> System.out.println(dto));
//        Map<String, Object> studentList1 = studentRepository.getStudentListWithQueryForMap();
//        Map<String, Object> studentList2 = studentRepository.getStudentListWithQueryForMapAndArgs("TTTT");
//        System.out.println(studentRepository.getStudentCountWithQueryForObject());
//        System.out.println(studentRepository.getStudentCountWithQueryForObjectAndArgs("A"));
//        System.out.println(studentRepository.getStudentCountWithQueryForObjectAndArgsAndRowMapper("A"));
//        System.out.println();
        studentRepository.createTableWithExecute();
        studentRepository.createTableWithExecuteAndCallBack();
    }

}