package ca.concordia.drms.orb;

/**
* ca/concordia/drms/orb/ReservationHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from drms.idl
* Saturday, November 15, 2014 2:44:03 PM EST
*/

public final class ReservationHolder implements org.omg.CORBA.portable.Streamable
{
  public ca.concordia.drms.orb.Reservation value = null;

  public ReservationHolder ()
  {
  }

  public ReservationHolder (ca.concordia.drms.orb.Reservation initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ca.concordia.drms.orb.ReservationHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ca.concordia.drms.orb.ReservationHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ca.concordia.drms.orb.ReservationHelper.type ();
  }

}
