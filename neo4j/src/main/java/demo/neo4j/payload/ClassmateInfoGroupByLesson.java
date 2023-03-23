package demo.neo4j.payload;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.List;

import demo.neo4j.model.Student;
import lombok.Data;

/**
 * ClassmateInfoGroupByLesson
 * <p>
 * 按照课程分组的同学关系
 *
 * @author Wenzhou
 * @since 2023/3/22 17:51
 */
@Data
@QueryResult
public class ClassmateInfoGroupByLesson {
    /**
     * 课程名称
     */
    private String lessonName;

    /**
     * 学生信息
     */
    private List<Student> students;
}
