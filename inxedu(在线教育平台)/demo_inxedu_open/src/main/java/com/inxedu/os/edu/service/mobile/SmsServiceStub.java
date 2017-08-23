package com.inxedu.os.edu.service.mobile;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.net.URLEncoder;

/**
 * @author www.inxedu.com
 */
public class SmsServiceStub {
	private final String USERID = ""; 
    private final String PASSWORD = "";
    private String msgContent;
    private String destNumber;
    public void sendmsg(){
        try {
        	System.out.println(USERID);
            System.out.println(PASSWORD);
            System.out.println(msgContent);
            System.out.println(destNumber);
            HttpClient client = new HttpClient();
            String url="http://138.u59e.com/index.php?action=interface&op=sendmess";
             url=url+"&username="+USERID;
             url=url+"&userpwd="+PASSWORD;
             url=url+"&logid=";
             url=url+"&mobiles="+destNumber;
             url=url+"&content="+URLEncoder.encode(msgContent,"UTF-8");
             //要提交的短信内容，中文内容要使用UTF-8字符集进行URL编码，避免有特殊符号造成提交失败
            //例如c# 用HttpUtility.UrlEncode("发送内容",Encoding.UTF8) ，java用URLEncoder.encode("发送内容", "UTF-8")
             PostMethod method = new PostMethod(url);
             client.executeMethod(method);
             method.getResponseBodyAsStream();
             /*BufferedReader 
             reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(),"UTF-8"));
             String str = null;  
               while ((str = reader.readLine()) != null) {
                 System.out.println(str);
             }*/
            method.abort();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getMsgContent() {
        return msgContent;
    }
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
    public String getDestNumber() {
        return destNumber;
    }
    public void setDestNumber(String destNumber) {
        this.destNumber = destNumber;
    }
}
