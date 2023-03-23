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
 * Class
 * <p>
 * 班级节点
 *
 * @author Wenzhou
 * @since 2023/3/22 17:47
 */
@Data
@Builder
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
public class Class {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = CustomIdStrategy.class)
    private String id;

    /**
     * 班级名称
     */
    @NonNull
    private String name;

    /**
     * 班级的班主任
     */
    @Relationship(NeoConstant.R_BOSS_OF_CLASS)
    @NonNull
    private Teacher boss;
}
