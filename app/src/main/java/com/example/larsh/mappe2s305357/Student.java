package com.example.larsh.mappe2s305357;

public class Student
{

    long _ID;
    String firstName;
    String lastName;
    String phonenumber;


    public long get_ID( )
    {

        return _ID;
    }

    public void set_ID( long _ID )
    {

        this._ID = _ID;
    }

    public String getFirstName( )
    {

        return firstName;
    }

    public void setFirstName( String firstName )
    {

        this.firstName = firstName;
    }

    public String getLastName( )
    {

        return lastName;
    }

    public void setLastName( String lastName )
    {

        this.lastName = lastName;
    }

    public String getPhonenumber( )
    {

        return phonenumber;
    }

    public void setPhonenumber( String phonenumber )
    {

        this.phonenumber = phonenumber;
    }

    public Student( String firstName, String lastName, String phonenumber )
    {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phonenumber = phonenumber;
    }

    public Student( )
    {

    }

    public Student( long _ID, String firstName, String lastName, String phonenumber )
    {

        this._ID = _ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phonenumber = phonenumber;
    }
}
