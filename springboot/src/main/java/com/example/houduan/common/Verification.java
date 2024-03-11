package com.example.houduan.common;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;

import java.util.Random;

public class Verification {
    private String code;
    private String phone;

    public Verification(String phone){
        this.phone=phone;
    }
    //制作随机的短信验证码（5位）
    public String makecode(){
        //制作6位数的验证码
        int start=0;
        int end=9;
        StringBuffer numStr=new StringBuffer();
        Random number=new Random();
        for(int i=1;i<=5;i++){
            numStr=numStr.append(number.nextInt(end - start+1)+start);
        }
        code=String.valueOf(numStr);
        return code;
    }
    //发送手机验证码
    public void sms(){
        try {
            Credential cred = new Credential("AKIDWRAj5FDNckjYkwvzqVzFG5h4Ci3AFE5k", "TbaNyLWUItJZcUCh2JzZ5UoAMJ1Yv5Bb");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(60);
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            SmsClient client = new SmsClient(cred, "ap-guangzhou",clientProfile);
            SendSmsRequest req = new SendSmsRequest();
            String sdkAppId = "1400799170";
            req.setSmsSdkAppId(sdkAppId);
            String signName = "雲雀的个人测试公众号";
            req.setSignName(signName);
            String templateId = "1716683";
            req.setTemplateId(templateId);
            String[] templateParamSet = {code,"5"};
            req.setTemplateParamSet(templateParamSet);
            String[] phoneNumberSet = {"+86"+phone};
            req.setPhoneNumberSet(phoneNumberSet);
            String sessionContext = "";
            req.setSessionContext(sessionContext);
            String extendCode = "";
            req.setExtendCode(extendCode);
            String senderid = "";
            req.setSenderId(senderid);
            SendSmsResponse res = client.SendSms(req);
            System.out.println(SendSmsResponse.toJsonString(res));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }
}
