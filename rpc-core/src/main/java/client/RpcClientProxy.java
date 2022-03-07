package client;


import entity.RpcRequest;
import entity.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//客户端实现动态代理
public class RpcClientProxy implements InvocationHandler {
    private static final Logger log= LoggerFactory.getLogger(RpcClientProxy.class);
    private String host;
    private int port;

    //传递host和port知名服务端位置
    public RpcClientProxy(String host,int port){
        this.host=host;
        this.port=port;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz){
        //创建并返回代理对象
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class<?>[]{clazz},this);
    }

    //invoke()方法实现代理对象方法被调用时的动作
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("调用方法{}：{}",method.getDeclaringClass().getName(),method.getName());
        //builder模式生成客户端向服务端传输的对象
        RpcRequest rpcRequest=RpcRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameters(args)
                .parametersTypes(method.getParameterTypes())
                .build();
        //进行远程调用的客户端
        RpcClient rpcClient=new RpcClient();
        return ((RpcResponse)rpcClient.sendRequest(rpcRequest,host,port)).getData();
    }
}
