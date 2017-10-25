package com.example.larsh.mappe2s305357;

public class Message
{

    long message_ID;
    String messagePhonenumber;
    String messageDate;
    String messageStatus;
    String messageMessage;

    public Message( )
    {

    }

    public long getMessage_ID( )
    {

        return message_ID;
    }

    public void setMessage_ID( long message_ID )
    {

        this.message_ID = message_ID;
    }

    public String getMessagePhonenumber( )
    {

        return messagePhonenumber;
    }

    public String getMessageDate( )
    {

        return messageDate;
    }

    public String getMessageStatus( )
    {

        return messageStatus;
    }

    public String getMessageMessage( )
    {

        return messageMessage;
    }

    public void setMessagePhonenumber(String messagePhonenumber)
    {
        this.messagePhonenumber = messagePhonenumber;
    }

    public void setMessageDate (String messageDate)
    {
        this.messageDate = messageDate;
    }

    public void setMessageStatus (String messageStatus)
    {
        this.messageStatus = messageStatus;
    }

    public void setMessageMessage(String messageMessage)
    {
        this.messageMessage = messageMessage;
    }

    public Message( String messagePhonenumber, String messageDate, String messageStatus, String messageMessage )
    {

        this.messagePhonenumber = messagePhonenumber;
        this.messageDate = messageDate;
        this.messageStatus = messageStatus;
        this.messageMessage = messageMessage;
    }

}
