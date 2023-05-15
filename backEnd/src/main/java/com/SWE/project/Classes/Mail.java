package com.SWE.project.Classes;

/**
 * 1) Copy the following Courier SDK dependency snippet and add to Maven pom.xml file
 * <dependency>
 *      <groupId>com.courier</groupId>
 *      <artifactId>courier-java</artifactId>
 *      <version>X.X.X</version>
 *      <scope>compile</scope>
 * </dependency>
 * 2) Ask Maven to download the dependencies by running: mvn compile
 */
import services.Courier;
import services.SendService;
import models.SendEnhancedRequestBody;
import models.SendEnhancedResponseBody;
import models.SendRequestMessage;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;

public class Mail {
  public static void sendMail(String studentEmail,Tournament t) {
    Courier.init("pk_prod_54B8G219FE4XQ5G7685VZXWJ77QN");

    SendEnhancedRequestBody request = new SendEnhancedRequestBody();
    SendRequestMessage message = new SendRequestMessage();

    HashMap<String, String> to = new HashMap<String, String>();
    to.put("email", studentEmail);
    message.setTo(to);
    message.setTemplate("GC78WFH3AKMG74P0CWXJ9AQ7GT51");

    HashMap<String, Object> data = new HashMap<String, Object>();
    data.put("tournamentName", t.getName());
    message.setData(data);

    request.setMessage(message);
    try {
        SendEnhancedResponseBody response = new SendService().sendEnhancedMessage(request);
        System.out.println(response);
    } catch (IOException e) {
        System.out.println("e");
        e.printStackTrace();
    }
  }
}