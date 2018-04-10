package com.work.java_demo.zookeeper;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

/**
 * 
 * @author：ancel.wang
 * @creattime：2018年4月10日 上午10:27:42 
 * 
 */  
public class ZkClientWatcher1 {

    /** zookeeper地址 */
	static final String CONNECT_ADDR = "172.18.19.82:2181,172.18.19.123:2181";
    /** session超时时间 */
    static final int SESSION_OUTTIME = 10000;//ms


    public static void main(String[] args) throws Exception {
        ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), SESSION_OUTTIME);

        //对父节点添加监听子节点变化。
        zkc.subscribeChildChanges("/super", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("parentPath: " + parentPath);
                System.out.println("currentChilds: " + currentChilds);
            }
        });

        Thread.sleep(3000);

        zkc.createPersistent("/super");
        Thread.sleep(1000);

        zkc.createPersistent("/super" + "/" + "c1", "c1内容");
        Thread.sleep(1000);

        zkc.createPersistent("/super" + "/" + "c2", "c2内容");
        Thread.sleep(1000);     

        zkc.delete("/super/c2");
        Thread.sleep(1000); 

        zkc.deleteRecursive("/super");
//        Thread.sleep(Integer.MAX_VALUE);

    }
}