import java.util.ArrayList;

public class Server extends Thread{
	public ArrayList<Task> task = new ArrayList<>();
	public int id;
	
	public Server(int i){
		id = i;
	}
	
	public synchronized void run()
	{
		try {
			wait(100*1000);
		} catch (InterruptedException e) {
			System.err.println("ERROR!");
		}
	}
	
	public synchronized void addTask (Task t) throws InterruptedException
	{
		task.add(t);
		notifyAll();
	}
	
	public synchronized String removeTask (String s, Server se, boolean da){
		if (da){
			Task t = se.task.get(0);
			task.remove(t);
			s = s + " Clientul " + t.id + " a plecat de la coada " + se.id + ". " ;
			notifyAll();
		}
		return s;
	}
	
	public synchronized int totalProcessing() throws InterruptedException{
		int total=0;
		for (int i=0;i<task.size();i++){
			total = total + task.get(i).processingTime;
		}
		notifyAll();
		return total;
	}
}