package ca.concordia.drms.orb;


/**
* ca/concordia/drms/orb/_LibraryServerStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from drms.idl
* Saturday, November 15, 2014 2:44:03 PM EST
*/

public class _LibraryServerStub extends org.omg.CORBA.portable.ObjectImpl implements ca.concordia.drms.orb.LibraryServer
{


  /**
  						 * Function that creates an account if the account does not exists 
  						 * @param first
  						 * @param last
  						 * @param email
  						 * @param telephone
  						 * @param username
  						 * @param password
  						 * @param institution
  						 */
  public void createAccount (String first, String last, String email, String telephone, String username, String password, String institution) throws ca.concordia.drms.orb.RemoteException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("createAccount", true);
                $out.write_string (first);
                $out.write_string (last);
                $out.write_string (email);
                $out.write_string (telephone);
                $out.write_string (username);
                $out.write_string (password);
                $out.write_string (institution);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:ca/concordia/drms/orb/RemoteException:1.0"))
                    throw ca.concordia.drms.orb.RemoteExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                createAccount (first, last, email, telephone, username, password, institution        );
            } finally {
                _releaseReply ($in);
            }
  } // createAccount


  /**
  						 * This function checks current library, to reserve a book
  						 * @param username
  						 * @param password
  						 * @param title
  						 * @param author
  						 */
  public void reserveBook (String username, String password, String title, String author) throws ca.concordia.drms.orb.RemoteException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("reserveBook", true);
                $out.write_string (username);
                $out.write_string (password);
                $out.write_string (title);
                $out.write_string (author);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:ca/concordia/drms/orb/RemoteException:1.0"))
                    throw ca.concordia.drms.orb.RemoteExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                reserveBook (username, password, title, author        );
            } finally {
                _releaseReply ($in);
            }
  } // reserveBook


  /**
  						 * This function checks current library, to reserve a book
  						 * @param username
  						 * @param password
  						 * @param title
  						 * @param author
  						 */
  public void reserveInterLibrary (String username, String password, String title, String author) throws ca.concordia.drms.orb.RemoteException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("reserveInterLibrary", true);
                $out.write_string (username);
                $out.write_string (password);
                $out.write_string (title);
                $out.write_string (author);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:ca/concordia/drms/orb/RemoteException:1.0"))
                    throw ca.concordia.drms.orb.RemoteExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                reserveInterLibrary (username, password, title, author        );
            } finally {
                _releaseReply ($in);
            }
  } // reserveInterLibrary


  /**
  						 * uses messages to send back and forth stream of info 
  						 * @param <V>
  						 * @param <K>
  						 * @param username
  						 * @param password
  						 * @param institution
  						 * @param days
  						 * @return
  						 */
  public ca.concordia.drms.orb.Reservation[] getNonReturners (String username, String password, String institution, int days) throws ca.concordia.drms.orb.RemoteException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getNonReturners", true);
                $out.write_string (username);
                $out.write_string (password);
                $out.write_string (institution);
                $out.write_long (days);
                $in = _invoke ($out);
                ca.concordia.drms.orb.Reservation $result[] = ca.concordia.drms.orb.ReservationListHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:ca/concordia/drms/orb/RemoteException:1.0"))
                    throw ca.concordia.drms.orb.RemoteExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getNonReturners (username, password, institution, days        );
            } finally {
                _releaseReply ($in);
            }
  } // getNonReturners


  /**
  						 * 
  						 * @param username
  						 * @param title
  						 * @param days
  						 * @throws RemoteException
  						 */
  public void setDuration (String username, String title, int days) throws ca.concordia.drms.orb.RemoteException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("setDuration", true);
                $out.write_string (username);
                $out.write_string (title);
                $out.write_long (days);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:ca/concordia/drms/orb/RemoteException:1.0"))
                    throw ca.concordia.drms.orb.RemoteExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                setDuration (username, title, days        );
            } finally {
                _releaseReply ($in);
            }
  } // setDuration

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ca/concordia/drms/orb/LibraryServer:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _LibraryServerStub
