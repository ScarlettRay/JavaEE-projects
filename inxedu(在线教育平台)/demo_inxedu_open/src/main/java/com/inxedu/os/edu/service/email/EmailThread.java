package com.inxedu.os.edu.service.email;


import com.inxedu.os.common.service.email.EmailService;
import com.inxedu.os.common.util.DateUtils;
import com.inxedu.os.common.util.ObjectUtils;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


/**
 * @author http://www.inxedu.com
 */
public class EmailThread implements Runnable{
    //发送邮件service
    private EmailService emailService;
    //发送内容
    private String content;
    //标题
    private String title;
    @Getter
    private static int sumNum=0;
    @Getter
    private static List<String> list = new ArrayList();
    public EmailThread(List<String> list,String content,String title,EmailService emailService) {
        this.content = content;
        this.title = title;
        this.list.addAll(list);
        sumNum+=list.size();
        this.emailService = emailService;
    }
    public EmailThread() {
    }

    public void run() {
        try {
            //每100个邮箱批量发一次，发完休息1秒，直到发完为止
            if(ObjectUtils.isNotNull(list)){
                while(true){
                    if(list.size()>0){
                        List l = queryList(100);
                        String[] arr = (String[])l.toArray(new String[l.size()]);
                        emailService.sendBatchMail(arr, content, title);
                        Thread.sleep(1000);
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
                System.out.println("发送完成时间"+ DateUtils.getNowTime());
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
