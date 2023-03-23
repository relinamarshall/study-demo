package demo.neo4j.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import demo.neo4j.model.Lesson;

/**
 * LessonRepository
 * <p>
 * 课程节点Repository
 *
 * @author Wenzhou
 * @since 2023/3/22 17:53
 */
public interface LessonRepository extends Neo4jRepository<Lesson, String> {
}
