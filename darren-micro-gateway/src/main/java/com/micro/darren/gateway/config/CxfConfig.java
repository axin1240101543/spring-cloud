package com.micro.darren.gateway.config;

import com.micro.darren.gateway.webservice.order.IOrderService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.xml.ws.Endpoint;

/**
 * cxf配置
 */
@Configuration
public class CxfConfig {

    /**
     * spring boot默认路径配置为/services
     * @return
     */
    /*@Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new CXFServlet(), "/services/*");
    }*/

    @Autowired
    private Bus bus;

    @Autowired
    private IOrderService iOrderService;

    @Bean
    public Endpoint iOrderServiceEndpoint(){
        EndpointImpl endpoint = new EndpointImpl(bus, iOrderService);
        endpoint.publish();
        return endpoint;
    }

}
