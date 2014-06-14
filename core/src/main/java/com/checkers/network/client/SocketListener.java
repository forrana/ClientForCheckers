package com.checkers.network.client;


import com.checkers.server.beans.Step;

/**
 * 
 * @author forrana
 *	This class - listen socket and return enemy step
 */
public class SocketListener implements Runnable {

	NetworkClient network;
	protected boolean isCancel = false;
	Step step;
	/**
	 * 
	 * @param net - current NetworkClient
	 */
	public SocketListener(NetworkClient net){
		network = net;
	}
	/**
	 * 
	 * @return Cancel status flag.
	 * Is work not Canceled - return false.
	 */
	public boolean isCanceled(){
		return isCancel;
	}
	
	public void setIsCanceled(){
		isCancel = !isCancel;
	}
	/**
	 * Check isCancel flag and return step. 
	 * @return if thread work cancelled and false, if not. 
	 */
	public Step getStep(){
		if(isCancel)
			return step;
		else return null;
	}

	@Override
	public void run() {
		do{
			step = network.getLastStep();
		}while(step == null);
//		System.out.println("IsCanceled");
		isCancel =  true;
		// TODO Auto-generated method stub
	}
	

}
