import api.HelloObject;
import api.HelloService;
import client.RpcClientProxy;


public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy=new RpcClientProxy("127.0.0.1",10086);
        HelloService helloService=proxy.getProxy(HelloService.class);
        HelloObject obj=new HelloObject(12,"Hello,RPC!");
        String res=helloService.hello(obj);
        System.out.println(res);
    }
}
