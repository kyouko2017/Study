package jvm;

import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelloClassLoader extends ClassLoader{
    public static void main(String[] args) throws ClassNotFoundException {
        HelloClassLoader loader = new HelloClassLoader();
        Class<?> aClass = loader.findClass("Hello");
        try {
            Object obj = aClass.newInstance();
            Method method = aClass.getMethod("hello");
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String myPath = "file:///Users/tangchenbing/IdeaProjects/Study/Unit1/resource/jvm/" + name.replace(".","/") + ".xlass";
        System.out.println(myPath);
        byte[] encodeCLassBytes = null;
        byte[] decodeCLassBytes = null;
        try {
            Path path = Paths.get(new URI(myPath));
            encodeCLassBytes = Files.readAllBytes(path);
            decodeCLassBytes = new byte[encodeCLassBytes.length];
            for(int i = 0; i < encodeCLassBytes.length; i++){
                byte encodeClassByte = encodeCLassBytes[i];
                byte decodeClassByte =(byte) (255-encodeClassByte);
                decodeCLassBytes[i] = decodeClassByte;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Class clazz = defineClass(name, decodeCLassBytes, 0, decodeCLassBytes.length);
        return clazz;
    }
}
