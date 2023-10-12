package SpeechEngine;

import java.util.Vector;

//import Speecher.FreeTTSHelloWorld;

public class Speaker extends Thread {
	
	static Vector<String> phrases = new Vector<String>();
	static boolean init = false;
	
	static boolean Running = true;
	static Thread _ref = null;
	
	
	public static void main(String args[])
	{
		Speaker.say("hello.");
		Speaker.say("how are you.");
		Speaker.say("god damn shit you.");
		System.err.println("J'ai fini");
	}
	
	public static void say(String phrase)
	{
		if (init==false) new Speaker().initK();
		System.err.println("Speech engine - Notify");
		phrases.add(phrase);
		synchronized(_ref)
		{
			_ref.notify();
		}
	}
	
	void initK()
	{
	//	FreeTTSHelloWorld.init();
		setName("Thread - Speech engine");
		_ref=this;
		init=true;
		start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(isRunning()==true)
		{
			evolue();
		}
	}
	public void evolue()
	{
		System.err.println("Speech engine - Wait");
		synchronized(this)
		{
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(phrases.size()!=0)
		{
			String phr = phrases.get(0);
			phrases.remove(0);
			System.err.println("Speech engine - Say something : "+phr);
		//	FreeTTSHelloWorld.say(phr);
			
		}
	}

	public static synchronized boolean isRunning() {
		return Running;
	}

	public static synchronized void setRunning(boolean running) {
		Running = running;
	}
	
	
}
