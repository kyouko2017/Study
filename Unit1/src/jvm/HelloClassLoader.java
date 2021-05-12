package jvm;

import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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
        String myPath = "file:///Users/tangchenbing/项目/Study/Unit1/resource/jvm/" + name.replace(".","/") + ".class";
        System.out.println(myPath);
        byte[] encodeCLassBytes = null;
        byte[] decodeClassBytes = null;
        try {
            Path path = Paths.get(new URI(myPath));
            encodeCLassBytes = Files.readAllBytes(path);
            Arrays.asList(encodeCLassBytes).forEach(classByte-> {
                System.out.println(classByte);
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Class clazz = defineClass(name, decodeClassBytes, 0, decodeClassBytes.length);
        return clazz;
    }
}
