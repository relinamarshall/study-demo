package demo.neo4j.payload;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

import demo.neo4j.model.Student;
import lombok.Data;

/**
 * TeacherStudent
 * <p>
 * 师生关系
 *
 * @author Wenzhou
 * @since 2023/3/22 17:51
 */
@Data
@QueryResult
public class TeacherStudent {
    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 学生信息
     */
    private List<Student> students;
}
