package sorcer.provider.hasherMD5.impl;

import sorcer.core.context.PositionalContext;
import sorcer.core.context.ServiceContext;
import sorcer.core.provider.Provider;
import sorcer.provider.hasherMD5.HasherMD5;
import sorcer.service.Context;
import sorcer.service.ContextException;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Logger;

import java.security.*;

@SuppressWarnings("rawtypes")
public class HasherMD5Impl implements HasherMD5 {
	public static final String RESULT_PATH = "result/value";
	private Provider provider;
	private static Logger logger = Logger.getLogger(HasherMD5Impl.class.getName());
	
	public void init(Provider provider) {
		this.provider = provider;
		try {
			logger = provider.getLogger();
		} catch (RemoteException e) {
			// ignore it, local call
		}
	}
	
	public Context hash(Context context) throws RemoteException, ContextException {
		// get inputs and outputs from the service context
		PositionalContext cxt = (PositionalContext) context;
		List<String> inputs = cxt.getInValues();
		logger.info("inputs: " + inputs);
		List<String> outpaths = cxt.getOutPaths();
		logger.info("outpaths: " + outpaths);

		// calculate the result
		String result = "";
		//for (Double value : inputs)
		//	result += value;
		try{

			/////////////////////


			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputs.get(0).getBytes());

			byte byteData[] = md.digest();

			//convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			result = sb.toString();

			////////////////////



		}catch(Exception ex)
		{
			logger.info(ex.toString());
		}

		logger.info("result: " + result);
		
		// update the service context
		if (provider != null)
			cxt.putValue("calculated/provider", provider.getProviderName());
		else
			cxt.putValue("calculated/provider", getClass().getName());
		if (((ServiceContext)context).getReturnPath() != null) {
			((ServiceContext)context).setReturnValue(result);
		} else if (outpaths.size() == 1) {
			// put the result in the existing output path
			cxt.putValue(outpaths.get(0), result);
		} else {
			cxt.putValue(RESULT_PATH, result);
		}

		// get a custom provider property
		if (provider != null) {
			try {
				int st = new Integer(provider.getProperty("provider.sleep.time"));
				if (st > 0) {
					Thread.sleep(st);
					logger.info("slept for: " + st);
					cxt.putValue("provider/slept/ms", st);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.info(e.toString());
			}
		}
		
		return cxt;
	}

}
