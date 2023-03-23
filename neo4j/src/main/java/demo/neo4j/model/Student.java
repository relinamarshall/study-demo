package demo.neo4j.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

import demo.neo4j.config.CustomIdStrategy;
import demo.neo4j.constants.NeoConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Student
 * <p>
 * 学生节点
 *
 * @author Wenzhou
 * @since 2023/3/22 17:49
 */
@Data
@Builder
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class Student {
    /**
     * 主键，自定义主键策略，使用UUID生成
     */
    @Id
    @GeneratedValue(strategy = CustomIdStrategy.class)
    private String id;

    /**
     * 学生姓名
     */
    @NonNull
    private String name;

    /**
     * 学生选的所有课程
     */
    @Relationship(NeoConstant.R_LESSON_OF_STUDENT)
    @NonNull
    private List<Lesson> lessons;

    /**
     * 学生所在班级
     */
    @Relationship(NeoConstant.R_STUDENT_OF_CLASS)
    @NonNull
    private Class clazz;
}
