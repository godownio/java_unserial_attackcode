package com.example.jdbctest.bean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.HashMap;

public class UserBean implements Serializable {
    private String name;

    private String age;

    private Object obj;

    public UserBean(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public UserBean() {}

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Object getObj() {
        return this.obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField gf = ois.readFields();
        HashMap<String, byte[]> a = (HashMap<String, byte[]>)gf.get("obj", (Object)null);
        String name = (String)gf.get("name", (Object)null);
        String age = (String)gf.get("age", (Object)null);
        if (a == null) {
            this.obj = null;
        } else {
            try {
                a.put(name, Base64.getDecoder().decode(age));
            } catch (Exception var7) {
                var7.printStackTrace();
            }
        }
    }
}

