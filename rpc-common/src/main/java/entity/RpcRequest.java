package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
//使用Builder模式一次给所有变量初始赋值
@Builder
public class RpcRequest implements Serializable {
    //请求号
    //private String requestId;
    //待调用接口名
    private String interfaceName;
    //待调用方法名
    private String methodName;
    //待调用方法的参数
    private Object[]parameters;
    //待调用方法的参数类型
    private Class<?>[]parametersTypes;
}
