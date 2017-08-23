package com.inxedu.os.edu.service.mobile;


import com.inxedu.os.common.util.DateUtils;
import com.inxedu.os.common.util.ObjectUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 多线程批量发送短信
 * @author www.inxedu.com
 */
public class SmsBatchThread implements Runnable{
    private String content;
    @Getter
    private static int sumNum=0;
    @Getter
    private static List<String> list = new ArrayList();
    public SmsBatchThread(List<String> list,String content) {
        this.content = content;
        this.list.addAll(list);
        sumNum+=list.size();
    }
    public SmsBatchThread() {
    }
    @Override
    public void run() {
        try {
            //每100个手机批量发一次，发完休息1秒，直到发完为止
            if(ObjectUtils.isNotNull(list)){
                while(true){
                    if(list.size()>0){
                        List l = queryList(50);
                        String[] arr = (String[])l.toArray(new String[l.size()]);
                        if(ObjectUtils.isNotNull(arr)){
                            for(String str:arr){
                                SmsServiceStub serviceStub  = new SmsServiceStub();
                                serviceStub.setDestNumber(str.trim());
                                serviceStub.setMsgContent(content);
                                SmsThread smsThread = new SmsThread(serviceStub);
                                smsThread.start();
                                Thread.sleep(100);
                            }
                        }
                    }else{
                        sumNum =0;
                        break;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获得要发送的list加锁
    public synchronized List queryList(int num){
        List newList = new ArrayList();
        if(ObjectUtils.isNotNull(list)){
            if(list.size()<=num){
                System.out.println("发送短信完成时间"+ DateUtils.getNowTime());
                for(int i=0;i<list.size();i++){
                    newList.add(list.get(i));
                }
                list = new ArrayList();
                return newList;
            }else{
                for(int i=0;i<num;i++){
                    newList.add(list.get(i));
                }
                for(int i=0;i<num;i++){
                    list.remove(0);
                }

            }
        }
        return newList;
    }
}
