package demo.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

import demo.neo4j.model.Class;

/**
 * ClassRepository
 * <p>
 * 班级节点Repository
 *
 * @author Wenzhou
 * @since 2023/3/22 17:52
 */
public interface ClassRepository extends Neo4jRepository<Class, String> {
    /**
     * 根据班级名称查询班级信息
     *
     * @param name 班级名称
     * @return 班级信息
     */
    Optional<Class> findByName(String name);
}
