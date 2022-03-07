package client;


import entity.RpcRequest;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcClient {
    private static final Logger log= LoggerFactory.getLogger(RpcClient.class);


    @SneakyThrows
    public Object sendRequest(RpcRequest rpcRequest, String host, int port){
        /*
        * socket套接字实现TCP网络传输
        * try()中放对资源的申请，若{}中出现异常，则申请的资源会自动关闭
        * */
        try(Socket socket= new Socket(host,port)){
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();
        }catch (IOException|ClassNotFoundException e){
            log.error("调用时发生错误"+e);
            return null;
        }


    }
}
