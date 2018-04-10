package com.work.java_demo.zookeeper;

import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

/**
 * zkclient节点中增删改查操作
 * @author：ancel.wang
 * @creattime：2018年4月10日 上午10:27:19 
 * 
 */  
public class ZkClientBase {

    /** zookeeper地址 */
    static final String CONNECT_ADDR = "172.18.19.82:2181,172.18.19.123:2181";
    /** session超时时间 */
    static final int SESSION_OUTTIME = 10000;//ms


    public static void main(String[] args) throws Exception {
        ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), SESSION_OUTTIME);
        //1. create and delete方法

        //添加临时节点
        zkc.createEphemeral("/temp");
        //添加持久节点
        zkc.createPersistent("/super/c1", true);
        Thread.sleep(10000);
        zkc.delete("/temp");
        //迭代删除
        zkc.deleteRecursive("/super");

        //2. 设置path和data 并且读取子节点和每个节点的内容
        zkc.createPersistent("/super", "1234");
        zkc.createPersistent("/super/c1", "c1内容");
        zkc.createPersistent("/super/c2", "c2内容");
        List<String> list = zkc.getChildren("/super");
        for(String p : list){
            System.out.println(p);
            String rp = "/super/" + p;
            String data = zkc.readData(rp);
            System.out.println("节点为：" + rp + "，内容为: " + data);
        }

        //3. 更新和判断节点是否存在
        zkc.writeData("/super/c1", "新内容");
        System.out.println(zkc.readData("/super/c1").toString());
        System.out.println(zkc.exists("/super/c1"));

        //4.递归删除/super内容
        zkc.deleteRecursive("/super");
        
        zkc.createPersistent("/super", "super");
        zkc.createPersistentSequential("/super/seq", 1);
        
        //序列节点
        String seqPath = zkc.createPersistentSequential("/super/seq", 2);
        System.out.println(seqPath);
    }
}