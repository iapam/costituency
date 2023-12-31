package com.apam.constituencies.Services;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OTPGenerator {
   public String SendOtp(String number) throws IOException {
       System.out.println(number);
       OkHttpClient client=new OkHttpClient().newBuilder().build();
       MediaType mediaType=MediaType.parse("application/json");
       RequestBody requestBody=RequestBody.create(mediaType,"{\r\n \"expiry\":\"1\",\r\n\"length\":\"6\"," +
               "\r\n\"medium\":\"sms\",\r\n\"message\":\"This is your otp,%otp_code%\"," +
               "\r\n\"number\":\""+number+"\",\r\n\"sender_id\":\"ndc-office\"," +
               "\r\n\"type\":\"numeric\"\r\n}");
       Request request=new Request.Builder()
               .url("https://sms.arkesel.com/api/otp/generate")
               .method("POST",requestBody)
               .addHeader("api-key", "eGtnTXZVR3JTZldBbkJNV3paSGw")
               .addHeader("Content-Type", "application/json")
               .build();
       Response response=client.newCall(request).execute();
       System.out.println(response.body().string());
       return "verifyotpPage";
   }
   public String Verify_otp(String otp,String number) throws IOException {
       OkHttpClient client = new OkHttpClient().newBuilder()
               .build();
       MediaType mediaType = MediaType.parse("application/json");
       RequestBody body = RequestBody.create(mediaType, "{\r\n    \"code\":\""+otp+"\",\r\n   " +
               " \"number\":\""+number+"\"\r\n}");
       Request request = new Request.Builder()
               .url("https://sms.arkesel.com/api/otp/verify")
               .method("POST", body)
               .addHeader("api-key", "eGtnTXZVR3JTZldBbkJNV3paSGw")
               .addHeader("Content-Type", "application/json")
               .build();
       Response response = client.newCall(request).execute();
       JSONObject object=new JSONObject(response.body().string());
       return object.getString("message");
   }

}
