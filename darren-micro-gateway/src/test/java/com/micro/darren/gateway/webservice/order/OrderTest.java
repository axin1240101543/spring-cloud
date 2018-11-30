package com.micro.darren.gateway.webservice.order;

import com.micro.darren.common.constants.Constants;
import com.micro.darren.common.constants.Context;
import com.micro.darren.common.utils.XmlUtils;
import com.micro.darren.common.http.HttpUtils;
import com.micro.darren.common.http.HttpUtilsImpl;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

    private static final String REQUEST_URL = "http://localhost:8080/services/IOrderWsdl?wsdl";

    @Autowired
    private ApplicationContext applicationContext;

    /*@Test
    public void getOrderById() throws Exception{
        Context context = new Context();
        HttpUtils httpUtils = new HttpUtilsImpl();

        String orderId = "TD18112320005";

        StringBuilder sb = new StringBuilder();
        sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ord=\"http://order.webservice.gateway.darren.micro.com/\">");
        sb.append("<soapenv:Header/>");
        sb.append("<soapenv:Body>");
        sb.append("<ord:getOderById>");
        sb.append("<request><![CDATA[");

        sb.append("<orderId>");
        sb.append(orderId);
        sb.append("</orderId>");

        sb.append("]]></request>");
        sb.append("</ord:getOderById>");
        sb.append("</soapenv:Body>");
        sb.append("</soapenv:Envelope>");
        String result = httpUtils.post(context, REQUEST_URL, sb.toString(), Constants.DEFAULT_CHAR_SET, Constants.DEFAULT_CHAR_SET, Constants.DEFAULT_CONTENT_TYPE);
        result = StringEscapeUtils.unescapeXml(result);
        result = XmlUtils.getStringToXml2Jsoup(result, "return");
        System.out.println(result);
    }*/

    /*@Test
    public void getApiInfoList() throws Exception{
        ApiController apiController = applicationContext.getBean(ApiController.class);
        String result = apiController.getApiInfoList();
        System.out.println(result);
    }*/

}
