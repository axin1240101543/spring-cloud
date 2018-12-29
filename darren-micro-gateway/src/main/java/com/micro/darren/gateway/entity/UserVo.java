package com.micro.darren.gateway.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JacksonXmlRootElement(localName = "User")//用在类上，用来自定义根节点名称；
public class UserVo implements Serializable {

    @JacksonXmlProperty(localName = "id")//用在属性上，用来自定义子节点名称；
    private Long id;

    @JacksonXmlProperty(localName = "name")
    private String name;

    @JacksonXmlProperty(localName = "age")
    private Integer age;

    @JacksonXmlProperty(localName = "mobile")
    private String mobile;

    @JacksonXmlProperty(localName = "address")
    private String address;

    @JacksonXmlProperty(localName = "Son")
    @JacksonXmlElementWrapper(useWrapping = false)//用在属性上，可以用来嵌套包装一层父节点，或者禁用此属性参与 XML 转换。默认是true
    private List<Son> list;

}
