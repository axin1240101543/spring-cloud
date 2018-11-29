package com.micro.darren.common.utils;

import com.thoughtworks.xstream.XStream;
import org.json.JSONObject;
import org.json.XML;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * xml处理类
 */
public class XmlUtils {

    /**
     * xml转换成json
     * @param xml
     * @return
     * @throws Exception
     */
    public static JSONObject getJsonToXml(String xml) throws Exception{
        return XML.toJSONObject(xml, true);
    }

    /**
     * String转换成xml
     * @param str
     * @param rootNode 根节点
     * @return
     */
    public static String getXmlToString(String str, String rootNode){
        return XML.toString(str, rootNode);
    }

    /**
     * bean转换成xml
     * @param object
     * @param rootNode 根节点
     * @param c  所要转换的类
     * @return
     */
    public static String getXmlToBean2XStream(Object object, String rootNode, Class<?> c){
        XStream xStream = new XStream();
        xStream.alias(rootNode, c);
        return xStream.toXML(object);
    }

    /**
     * xml转换成bean
     * @param xml
     * @return
     */
    public static Object getBeanToXml2XStream(String xml){
        XStream xStream = new XStream();
        return xStream.fromXML(xml);
    }

    /**
     * 通过Jsoup来解析xml获取第一个参数
     * @param xml
     * @param firstParam
     * @return
     */
    public static String getStringFirstToXml2Jsoup(String xml, String firstParam){
        Document document = Jsoup.parse(xml);
        Element element = document.getElementsByTag(firstParam).first();
        return element.html();
    }

    /**
     * 通过Jsoup来解析xml
     * @param xml
     * @param node
     * @return
     */
    public static String getStringToXml2Jsoup(String xml, String node){
        Document document = Jsoup.parse(xml);
        Elements element = document.getElementsByTag(node);
        return element.html();
    }

}
