package api;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.io.Serializable;

//自动加上所有属性的get\set等方法
@Data
//添加一个无参构造方法
@NoArgsConstructor
//添加一个含有已声明属性的构造方法
@AllArgsConstructor
public class HelloObject implements Serializable {
    private Integer id;
    private String message;
}
