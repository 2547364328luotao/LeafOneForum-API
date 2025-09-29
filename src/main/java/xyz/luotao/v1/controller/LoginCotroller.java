package xyz.luotao.v1.controller;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.luotao.v1.util.JwtUtil;
import xyz.luotao.v1.common.ResponseMessage;
import xyz.luotao.v1.entity.Token;
import xyz.luotao.v1.entity.User;
import xyz.luotao.v1.entity.dto.UserDto;
import xyz.luotao.v1.mapper.IUserMapper;
import xyz.luotao.v1.service.LoginService;

@RestController
@RequestMapping
public class LoginCotroller {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private LoginService loginService;

    // 登录时用JWT
    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody UserDto userDto, HttpSession session) {
        User user = loginService.validateLogin(userDto);
        if (user == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ResponseMessage(401, "登录失败，邮箱或密码错误", null));
        }
        session.setAttribute("user", user);
        //生成JWT返回给前端
        String token = JwtUtil.generateToken(String.valueOf(userDto.getEmail()));
        //构建返回给前端的token对象
        Token tokenObj = new Token();
        tokenObj.setToken(token);
        tokenObj.setSessionId(session.getId());
        log.info(user.getNickname()+" 登录成功,sessionId={}", session.getId());
        return ResponseEntity.ok(ResponseMessage.success(200,"登录成功", tokenObj));
    }

    //账号注销
    @PostMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            log.info(user.getNickname() + " 注销成功，sessionId={}", session.getId());
            session.invalidate();
            return ResponseEntity.ok(ResponseMessage.success(200, "注销成功", null));
        } else {
            return ResponseEntity.status(401).body(new ResponseMessage(401, "注销失败，未登录", null));
        }
    }

}
