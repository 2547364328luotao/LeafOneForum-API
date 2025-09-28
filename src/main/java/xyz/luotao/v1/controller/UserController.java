package xyz.luotao.v1.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.luotao.v1.common.ResponseMessage;
import xyz.luotao.v1.entity.User;
import xyz.luotao.v1.mapper.IUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.luotao.v1.mapper.PostLikeMapper;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private PostLikeMapper postLikeMapper;


    //查询所有用户
    @GetMapping
    public ResponseEntity<ResponseMessage> query() {
        List<User> list = userMapper.FindAll();
        log.info("所有用户查询成功：", list);
        return ResponseEntity.ok(ResponseMessage.success(list));
    }


    //获取单个用户信息-只能获取自己账号信息
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage> getUserById(@PathVariable Long id , HttpSession session) {
        //从session中获取当前登录的用户
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || currentUser.getId() == null || !currentUser.getId().equals(id)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ResponseMessage(403, "禁止访问他人信息", null));
        }
        User user = userMapper.FindById(id);
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(404, "用户未找到", null));
        }
        List<Long> postIdsByUserId = postLikeMapper.FindLikedPostIdsByUserId(user.getId());
        user.setLikedPostIds(postIdsByUserId);
        log.info("成功获取用户信息：{}", user);
        return ResponseEntity.ok(ResponseMessage.success(user));
    }


    //增加用户
    @PostMapping
    public ResponseEntity<ResponseMessage> add(@Validated User user) {
        try{
            boolean success = userMapper.AddUser(user);
            //判断是否增加成功
            if (!success) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseMessage(500, "增加用户失败", null));
            }
            log.info("成功添加用户：{}", user);
            return ResponseEntity.ok(ResponseMessage.success(user));
        }catch (DuplicateKeyException e){
            log.error("添加用户失败，邮箱已存在：{}", user.getEmail());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(400, "添加用户失败，邮箱已存在", null));
        }


    }

    //添加用户宿舍信息
    @PostMapping("/dormitory")
    public ResponseEntity<ResponseMessage> addDormitoryInfo(@RequestBody User user) {
        boolean success = userMapper.addDormitoryInfo(user);
        if (!success) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "添加用户宿舍信息失败", null));
        }
        log.info("成功添加/更改用户宿舍信息：{}", user);
        return ResponseEntity.ok(ResponseMessage.success(user));
    }


    //删除用户
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable Integer id) {
        boolean b = userMapper.DeleteUser(id);
        //判断是否删除成功
        if (!b) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "删除用户失败", null));
        }
        log.info("成功删除用户，ID：{}", id);
        return ResponseEntity.ok(ResponseMessage.success());
    }


    //更改用户信息
    @PutMapping
    public ResponseEntity<ResponseMessage> update(@RequestBody User user) {
        boolean b = userMapper.UpdateUser(user);
        //判断是否更改成功
        if (!b) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseMessage(500, "更改用户信息失败", null));
        }
        log.info("成功更改用户信息：{}", user);
        return ResponseEntity.ok(ResponseMessage.success(user));
    }
}
