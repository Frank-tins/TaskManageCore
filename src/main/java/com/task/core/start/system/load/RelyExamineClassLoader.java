//package com.task.core.start.system.load;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.io.FileInputStream;
//
///**
// * 依赖检查ClassLoad
// * 通过破坏双亲委派机制独立引导Class 文件达到加载流中依赖判别与应用中CLASS对象互相独立
// * 防止出现应用中ClassLoader 重写产生运行级别的影响
// * @author Frank
// */
//public class RelyExamineClassLoader extends ClassLoader {
//
//    private Logger logger = LogManager.getLogger(RelyExamineClassLoader.class);
//
//    private String classPath;
//
//    public RelyExamineClassLoader() {
//        this.classPath = this.getClass().getResource("/").getPath();
//    }
//
//    /**
//     * 破坏双亲委派机制 阻塞类资源共享
//     * @param name
//     * @param resolve
//     * @return
//     * @throws ClassNotFoundException
//     */
//    @Override
//    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
//        return findClass(name);
//    }
//    @Override
//    public Class<?> loadClass(String name) throws ClassNotFoundException {
//        return findClass(name);
//    }
//
//    /**
//     * IO 读写类文件
//     * @param name
//     * @return
//     * @throws Exception
//     */
//    private byte[] loadByte(String name) throws Exception {
//        name = name.replaceAll("\\.", "/");
//        FileInputStream fis = new FileInputStream(classPath + "/" + name
//                + ".class");
//        int len = fis.available();
//        byte[] data = new byte[len];
//        fis.read(data);
//        fis.close();
//        return data;
//
//    }
//
//    /**
//     * Class 对象转换
//     * @param name
//     * @return
//     * @throws ClassNotFoundException
//     */
//    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        try {
//            byte[] data = loadByte(name);
//            return defineClass(name, data, 0, data.length);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ClassNotFoundException(name);
//        }
//    }
//
//
//}
