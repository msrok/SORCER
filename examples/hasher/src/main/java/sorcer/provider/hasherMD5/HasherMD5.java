package sorcer.provider.hasherMD5;

import sorcer.service.Context;
import sorcer.service.ContextException;

import java.rmi.RemoteException;

@SuppressWarnings("rawtypes")
public interface HasherMD5 {

	public Context add(Context context) throws RemoteException, ContextException;
}
