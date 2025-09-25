package xyz.luotao.v1.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import xyz.luotao.v1.common.ResponseMessage;
import xyz.luotao.v1.entity.User;
import xyz.luotao.v1.entity.dto.UserDto;
import xyz.luotao.v1.mapper.IUserMapper;
import xyz.luotao.v1.mapper.IUserRoleMapper;
import xyz.luotao.v1.service.EmailService;
import xyz.luotao.v1.service.TokenService;

@RestController
@RequestMapping("/sign")
public class SignCotroller {


    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserMapper userMapper;
    @Autowired
    private IUserRoleMapper userRoleMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmailService emailService;

    //实现注册功能
    @PostMapping
    public ResponseEntity<ResponseMessage> sign(@RequestBody UserDto userDto, HttpSession session , HttpServletRequest request) {
        System.out.println(userDto);
        try{
            User user = new User();
            BeanUtils.copyProperties(userDto,user);
            System.out.println(user);
            boolean success = userMapper.AddUser(user);
            if (!success) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseMessage(500, "注册失败", null));
            }
            //添加UUID，用于邮箱激活,构建激活链接
            String uuid = tokenService.addTokenUserRole(user.getEmail());
            String domain = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String activateUrl = domain + "/sign/" + user.getEmail() + "/" + uuid;
            log.info("激活链接：{}", activateUrl);
            session.setAttribute("user", user);
            log.info("成功注册，待激活用户：{}", user);
            //发送激活邮件
            try{
                emailService.sendActivationMail(user.getEmail(), activateUrl);
            } catch(MailException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ResponseMessage(500, "注册成功，但激活邮件发送失败，请联系管理员", null));
            }
            return ResponseEntity.ok(ResponseMessage.success(200,"注册成功，待激活", null));
        }catch (DuplicateKeyException e){
            log.error("添加用户失败，邮箱已存在：{}", userDto.getEmail());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(400, "注册失败，邮箱已存在", null));
        }
    }

    //用户账号激活功能
    @GetMapping("/{email}/{token}")
    public ResponseEntity<ResponseMessage> activateAccount(@PathVariable String email, @PathVariable String token) {
        boolean success = tokenService.verifyActivateUser(email, token);
        System.out.println(""+email+token);
        if (!success) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseMessage(400, "激活失败，链接无效或已过期", null));
        }
        log.info("用户激活成功，邮箱：{}", email);
        //发送激活成功邮件
        try{
            emailService.sendActivationSuccessMail(email);
        } catch(MailException e) {
            log.error("激活成功邮件发送失败，邮箱：{}", email);
        }
        return ResponseEntity.ok(new ResponseMessage(200, "激活成功", null));
    }
}
