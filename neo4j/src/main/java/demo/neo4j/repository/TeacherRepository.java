package demo.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import demo.neo4j.model.Teacher;

/**
 * TeacherRepository
 * <p>
 * 教师节点Repository
 *
 * @author Wenzhou
 * @since 2023/3/22 17:53
 */
public interface TeacherRepository extends Neo4jRepository<Teacher, String> {

}
