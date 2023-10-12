package Main;

public class Thread_Affichage extends Thread {

	BufferDraw parent = null;
	private boolean Pause=false;
	
	public synchronized boolean isPause() {
		return Pause;
	}


	public synchronized void setPause(boolean pause) {
		Pause = pause;
	}


	Thread_Affichage(BufferDraw par)
	{
		this.parent = par;
	}
	
	
	  public void run() {
        	while(true)
        	{
        		
        	try
			{
				Thread.sleep(33); // 1000/ 33 -> 30 fps
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (this.isPause()==false)
				parent.redraw_buffer_screen();
        	}
        }
      
}
