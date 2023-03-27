package demo.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

import demo.mybatis.plus.entity.Role;
import demo.mybatis.plus.handler.MapResultHandler;
import demo.mybatis.plus.mapper.RoleMapper;
import demo.mybatis.plus.service.RoleService;
import lombok.RequiredArgsConstructor;

/**
 * RoleServiceImpl
 *
 * @author Wenzhou
 * @since 2023/3/24 16:01
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    /**
     * sqlSession
     */
    private final SqlSessionFactory sqlSessionFactory;

    @Override
    public Map<String, String> getUserForMap() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            // 创建一个结果处理器
            MapResultHandler<String, String> mapResultHandler = new MapResultHandler<>();

            // 进行数据查询和结果封装
            sqlSession.select("demo.mybatis.plus.mapper.RoleMapper.getUserForMap", mapResultHandler);

            return mapResultHandler.getMappedResults();
        }
    }

    @Override
    public Map<String, String> getUserForMap2() {
        MapResultHandler<String, String> mapResultHandler = new MapResultHandler<>();
        this.baseMapper.getUserForMap2("1", mapResultHandler);
        return mapResultHandler.getMappedResults();
    }
}
