package demo.neo4j.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import demo.neo4j.config.CustomIdStrategy;
import demo.neo4j.constants.NeoConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Lesson
 * <p>
 * 课程节点
 *
 * @author Wenzhou
 * @since 2023/3/22 17:48
 */
@Data
@Builder
@NodeEntity
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor
public class Lesson {
    /**
     * 主键，自定义主键策略，使用UUID生成
     */
    @Id
    @GeneratedValue(strategy = CustomIdStrategy.class)
    private String id;

    /**
     * 课程名称
     */
    @NonNull
    private String name;

    /**
     * 任教老师
     */
    @Relationship(NeoConstant.R_TEACHER_OF_LESSON)
    @NonNull
    private Teacher teacher;
}
