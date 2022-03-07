
import annotation.Service;

import api.HelloObject;
import api.HelloService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(HelloObject obj) {
        log.info("接收消息:{}",obj.getMessage());
        return "成功调用Hello()方法,id="+obj.getId();
    }
}
