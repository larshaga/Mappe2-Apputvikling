package com.example.larsh.mappe2s305357;

public class Student
{

    long _ID;
    String fornavn;
    String etternavn;
    String phonenumber;

    public long get_ID( )
    {

        return _ID;
    }

    public void set_ID( long _ID )
    {

        this._ID = _ID;
    }

    public String getFirstname( )
    {

        return fornavn;
    }

    public void setFirstname( String navn )
    {

        fornavn = navn;
    }

    public String getLastName( )
    {

        return etternavn;
    }

    public void setLastName( String navn )
    {

        etternavn = navn;
    }

    public String getPhonenumber( )
    {

        return phonenumber;
    }

    public void setPhonenumber( String telefon )
    {

        phonenumber = telefon;
    }

    public Student( long _ID, String navn, String navn1, String telefon )
    {

        this._ID = _ID;
        fornavn = navn;
        etternavn = navn1;
        phonenumber = telefon;
    }

    public Student( String navn, String navn1, String telefon )
    {


        fornavn = navn;
        etternavn = navn1;
        phonenumber = telefon;


    }

    public Student()
    {

    }
}
