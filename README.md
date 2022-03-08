# My-RPC_1.0 
# 概述：
RPC通过远程调用，一个节点通过网络向另一个节点请求服务，让调用远程方法就像本地方法一样简单。
客户端对服务器端的服务做调用，服务器端有多个服务，客户端就告诉一个动态生成的代理类需要调用服务器端的哪个类的哪个方法，并把参数传递给该代理类。  
# 实现思路：
## 通用接口：
	接口名为HelloService，接口中的方法传递一个HelloObject对象（传递id和消息内容），由于该对象需要从客户端传输到服务端，所以需要实现Serializable实现序列化。然后对通用接口写一个实现类。
## 传输协议：
	客户端的请求：由于需要唯一确定服务端需要调用的接口的方法，那就必须传输接口的名字、方法的名字、方法中的参数、参数的类型。于是把这四个信息都写进一对象中，传输时传输这个对象即可，即RpcRequest。
	服务器的回应：服务器调用这个方法后，需要给客服端返回信息。定义如果调用成功，返回调用成功的信息；调用失败则返回失败的信息。即RpcRespon。
	由于这两个对象都需要传输，也要实现序列化接口Serializable。
## 客户端（使用动态代理的方式）：
	客户端没有接口的实现类，所以只能通过动态代理的方法生成实例。使用jdk动态代理的方法生成实例。
	需要传递host和port来指明服务端的位置。并且使用getProxy()方法来生成代理对象。代理类需实现InvocationHandler接口，需要实现invoke()方法，来指明代理对象的方法被调用时的动作。调用invoke()方法时产生RpcRequest对象发送出去，然后从服务端接收结果。
	创建一个RpcClient对象发送RpcRequest，通过Socket传输，创建一个Socket，获取ObjectOutputStream对象，把需要发送的对象传进去即可。接收时获区ObjectInputStream对象，readObject()方法就可以获得一个返回的对象。
## 服务端(使用反射的方式)：
	创建DefaultServiceRegister类实现客户端传入服务的注册，注册后可以通过getService(服务名)获取服务对象。
	创建RequestHandler类实现服务端对方法调用的处理，其中invokeTargetMethod()获取客户端调用的方法，并调用该方法。handle()方法通过invokeTargetMethod()实现服务端对方法的调用处理。并通过RequestHandlerThread类创建线程调用RequestHandler中的方法。
	创建RpcServer类，使用一个ServerSocket一直监听某个端口，循环接收连接请求。如果收到请求就创建一个线程，在新线程中处理调用，使用线程池创建RequestHandlerThread线程，从而在服务端完成方法的执行。
## 测试：
	服务端：创建HelloService的实现类(HelloServiceImpl类)的对象并注册，然后创建RpcServer类并开启。
	客户端：动态代理创建代理对象，即HelloService的对象，通过代理对象调用方法即可向服务端发送请求。

