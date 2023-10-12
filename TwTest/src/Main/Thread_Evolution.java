package Main;

import DTO.Game;

public class Thread_Evolution extends Thread
{
        private int time=0;
        private Game game = null;
        Thread_Evolution(Game g)
        {
        	this.game=g;
        }
        
		public synchronized final int getTime()
		{
			return time;
		}

		public synchronized final void setTime(int time)
		{
			this.time = time;
		}

		public void run() {
        	while(true)
        	{
        	try
			{
				Thread.sleep(10);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized(game)
			{
				game.evolue();
			
			}
			time++;
        	}
        }

		public synchronized Game getGame() {
			return game;
		}

		public synchronized void setGame(Game game) {
			this.game = game;
		}
      
}
