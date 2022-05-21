package com.example.demoapp.Models;

public class MessageModel {
    String uId, message, messageId,imagemess,checkSeen;
    Long timestamp;

    public MessageModel(String uId, String message, Long timestamp ,  String imagemess) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
        this.imagemess = imagemess;
    }

    public MessageModel(String uId, String message ,String imagemess) {
        this.uId = uId;
        this.message = message;
        this.imagemess = imagemess;
    }
    public MessageModel(String uId, String message ,String imagemess, String checkseen) {
        this.uId = uId;
        this.message = message;
        this.imagemess = imagemess;
        this.checkSeen = checkseen;
    }





    public MessageModel() {

    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getImageMess() {
        return imagemess;
    }

    public void setImageMess(String imagemess) {
        this.imagemess = imagemess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }



    public String getCheckSeen() {
        return checkSeen;
    }

    public void setCheckSeen(String checkSeen) {
        this.checkSeen = checkSeen;
    }
}
