package demo.swagger.beauty.controller;

import com.battcn.boot.swagger.model.DataType;
import com.battcn.boot.swagger.model.ParamType;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import demo.swagger.beauty.common.ApiResponse;
import demo.swagger.beauty.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * UserController
 *
 * @author Wenzhou
 * @since 2023/3/21 11:20
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理", value = "用户管理")
public class UserController {
    /*
     * swagger2 中 ApiImplicitParam不加example会报 exception For input string: ""
     * */

    /**
     * getByUserName
     *
     * @param username String
     * @return ApiResponse<User>
     */
    @GetMapping
    @ApiOperation(value = "条件查询（DONE）", notes = "备注")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名",
            dataType = DataType.STRING, paramType = ParamType.QUERY, dataTypeClass = String.class, defaultValue = "xxx", example = "xxx")})
    public ApiResponse<User> getByUserName(String username) {
        log.info("多个参数用  @ApiImplicitParams");
        return ApiResponse.<User>builder().code(200)
                .message("操作成功")
                .data(new User(1, username, "JAVA"))
                .build();
    }

    /**
     * getUserById
     *
     * @param id Integer
     * @return ApiResponse<User>
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "主键查询（DONE）", notes = "备注")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户编号",
            dataType = DataType.INT, paramType = ParamType.PATH, dataTypeClass = Integer.class, example = "1")})
    public ApiResponse<User> getUserById(@PathVariable Integer id) {
        log.info("单个参数用  @ApiImplicitParam");
        return ApiResponse.<User>builder().code(200)
                .message("操作成功")
                .data(new User(id, "u1", "p1"))
                .build();
    }

    /**
     * deleteUserById
     *
     * @param id Integer
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户（DONE）", notes = "备注")
    @ApiImplicitParam(name = "id", value = "用户编号", dataType = DataType.INT, dataTypeClass = Integer.class, paramType = ParamType.PATH, example = "1")
    public void deleteUserById(@PathVariable Integer id) {
        log.info("单个参数用 ApiImplicitParam");
    }

    /**
     * postUser
     *
     * @param user User
     * @return User
     */
    @PostMapping
    @ApiOperation(value = "添加用户（DONE）")
    public User postUser(@RequestBody User user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");
        return user;
    }

    /**
     * multiUser
     *
     * @param user List<User>
     * @return List<User>
     */
    @PostMapping("/multi")
    @ApiOperation(value = "添加用户（DONE）")
    public List<User> multiUser(@RequestBody List<User> user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");
        return user;
    }

    /**
     * arrayUser
     *
     * @param user User[]
     * @return User[]
     */
    @PostMapping("/array")
    @ApiOperation(value = "添加用户（DONE）")
    public User[] arrayUser(@RequestBody User[] user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");
        return user;
    }

    /**
     * putUser
     *
     * @param id   Long
     * @param user User
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户（DONE）")
    public void putUser(@PathVariable Long id, @RequestBody User user) {
        log.info("如果你不想写 @ApiImplicitParam 那么 swagger 也会使用默认的参数名作为描述信息 ");
    }

    /**
     * file
     *
     * @param id   Long
     * @param file MultipartFile
     * @return String
     */
    @PostMapping("/{id}/file")
    @ApiOperation(value = "文件上传（DONE）")
    public String file(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        log.info(file.getContentType());
        log.info(file.getName());
        log.info(file.getOriginalFilename());
        return file.getOriginalFilename();
    }
}
