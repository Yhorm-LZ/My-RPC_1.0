package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import registry.ServiceRegistry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class RpcServer {
    private final ExecutorService threadPool;
    private static final Logger log= LoggerFactory.getLogger(RpcServer.class);
    private RequestHandler requestHandler = new RequestHandler();
    private final ServiceRegistry serviceRegistry;

    public RpcServer(ServiceRegistry serviceRegistry){
        this.serviceRegistry=serviceRegistry;
        int corePoolSize=5;
        int maximunPoolSize=50;
        long keepAliveTime=60;
        BlockingQueue<Runnable> workingQueue=new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool=new ThreadPoolExecutor(corePoolSize,maximunPoolSize,keepAliveTime
        ,TimeUnit.SECONDS,workingQueue,threadFactory);
    }


    public  void start(int port){
        try (ServerSocket serverSocket=new ServerSocket(port)){
            log.info("客户端正在启动");
            Socket socket;
            while ((socket=serverSocket.accept())!=null){
                log.info("客户端连接成功！{}:{}"+socket.getInetAddress(),socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket,requestHandler,serviceRegistry));
            }
            threadPool.shutdown();
        }catch (IOException e){
            log.error("连接时发生错误",e);
        }
    }
}
