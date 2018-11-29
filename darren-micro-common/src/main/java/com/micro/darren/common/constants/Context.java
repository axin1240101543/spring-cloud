package com.micro.darren.common.constants;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Context implements Serializable {

    private static final long serialVersionUID = 1;

    private Map<String, Object> attrs;
    private String msgSeq;
    private String clientIP;// 用户IP

    public <T> void setAttr(String key, T value) {
        if (attrs == null) {
            attrs = new HashMap<String, Object>();
        }
        this.attrs.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttr(String key) {
        if (attrs != null) {
            return (T) this.attrs.get(key);
        }
        return null;
    }

    public Context() {
    }

    public Context(String msgSeq) {
        this.msgSeq = msgSeq;
    }

    @Override
    public String toString() {
        return "[" + msgSeq + "]";
    }

}
