package com.work.java_demo.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

/**
 * 添加节点数据监听器
 * @author：ancel.wang
 * @creattime：2018年4月10日 下午1:52:38 
 * 
 */  
public class ZkClientWatcher2 {

    /** zookeeper地址 */
	static final String CONNECT_ADDR = "172.18.19.82:2181,172.18.19.123:2181";
    /** session超时时间 */
    static final int SESSION_OUTTIME = 10000;//ms


    public static void main(String[] args) throws Exception {
        ZkClient zkc = new ZkClient(new ZkConnection(CONNECT_ADDR), SESSION_OUTTIME);

        zkc.createPersistent("/super", "1234");

        //对节点添加监听数据变化。
        zkc.subscribeDataChanges("/super", new IZkDataListener() {
            @Override
            public void handleDataDeleted(String path) throws Exception {
                System.out.println("删除的节点为:" + path);
            }

            @Override
            public void handleDataChange(String path, Object data) throws Exception {
                System.out.println("变更的节点为:" + path + ", 变更内容为:" + data);
            }
        });

        Thread.sleep(3000);
        zkc.writeData("/super", "456", -1);
        Thread.sleep(1000);

        zkc.delete("/super");
        Thread.sleep(Integer.MAX_VALUE);
    }
}