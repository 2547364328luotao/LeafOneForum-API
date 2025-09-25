package xyz.luotao.v1.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ResponseMessage<T> {
    private Integer code;                   // 200表示成功，其他表示失败 500表示服务器错误 404表示找不到资源
    private String message;                 // 提示信息
    private T data;                         // 返回的数据

    public  static<T> ResponseMessage<T> success(T data){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(HttpStatus.OK.value());
        responseMessage.setMessage("success");
        responseMessage.setData(data);
        return responseMessage;
    }

    public  static<T> ResponseMessage<T> success(){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(HttpStatus.OK.value());
        responseMessage.setMessage("success");
        responseMessage.setData(null);
        return responseMessage;
    }

    public ResponseMessage(Integer code,String message,T data) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public static <T> ResponseMessage<T> success(int code, String message, T data) {
        return new ResponseMessage<>(code, message, data);
    }


    public ResponseMessage(String message) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public ResponseMessage() {
    }
}
