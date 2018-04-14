import java.awt.TextArea;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager extends Thread{
	public int timeLimit;
	public int maxProcessingTime;
	public int minProcessingTime;
	public int maxArrivalTime;
	public int minArrivalTime;
	public int numberOfServers;
	public int numberOfTask=0;
	public Server serverArray[];
	public int averageWaitingTime=0;
	public TextArea output;
	public TextArea output2;
	
	private AtomicInteger timp = new AtomicInteger();
	int taskMax=0;
	int peakHour;
	int countServingTime=0;
	int countWaitingTime=0;
	static int id=0;
	static int x=0;
	static int tnext=0;
	String s =" ";
	
	public SimulationManager(int s, Server server[],int sim, int minp, int maxp, int mina, int maxa,TextArea o,TextArea o2){
		timeLimit= sim;
		maxProcessingTime = maxp;
		minProcessingTime=minp;
		maxArrivalTime=maxa;
		minArrivalTime=mina;
		numberOfServers=s;
		serverArray=new Server[s];
		for (int i=0;i<s;i++){
			serverArray[i]=server[i];
		}
		output=o;
		output2=o2;
	}
	
	public SimulationManager(){}
	
	public int randomTaskArrival(int mina, int maxa){
		return mina + (int) (Math.random()*(maxa-mina));
	}
	
	public int randomTaskProcessing(int minp, int maxp){
		return minp + (int) (Math.random()*(maxp-minp));
	}
	
	public int SHORTEST_TIME(){ //selection policy. se compara timpul de procesare al serverelor, se alege minimul dintre ele, deci acolo punem noul client.
		int i=0;
		try
		{
			int minim=serverArray[0].totalProcessing();
			int lungime;
			for (int j=1;j<numberOfServers;j++){
				lungime = serverArray[j].totalProcessing();
				if (lungime<minim){
					minim=lungime;
					i=j;
				}
			}
		}
		catch(InterruptedException e){
			System.err.println("eroare");
			output.setText("error");
		}
		return i;
	}
	
	public int SHORTEST_QUEUE(){ 
		int i=0;
		int minim=serverArray[0].task.size();
		int lungime;
		for (int j=1;j<numberOfServers;j++){
			lungime = serverArray[j].task.size();
			if (lungime<minim){
				minim=lungime;
				i=j;
			}
		}
		return i;
	}
	
	public synchronized void run(){
		try{
			while (timp.get()<timeLimit){
				
				int curent=timp.get();
				int t;
				int timeP;
				s="";
				int nextTask=0;
				if (x==0){ // daca x e 0 se calculeaza cand trebuie sa vina urmatorul client
					nextTask=randomTaskArrival(minArrivalTime, maxArrivalTime);
					x=1;
					t = timp.get();
					tnext = t + nextTask;
				}
				if (numberOfTask>0){ // daca am clienti atunci calculam daca de la vreo coada trebuie sa plece vreun client
					for (int i=0;i<numberOfServers;i++){
						if (serverArray[i].task.size()>0){
							if(!serverArray[i].isInterrupted()){
								int processingFirst = serverArray[i].task.get(0).processingTime;
								int arriveFirst = serverArray[i].task.get(0).queueFirst;
								int arrive = serverArray[i].task.get(0).arrivalTime;
								if (processingFirst + arriveFirst == curent){
									averageWaitingTime = averageWaitingTime + arriveFirst - arrive; // calculez timpu mediu de asteptare
									countWaitingTime++;
									s=s+"La momentul " + Integer.toString(curent);
									s=serverArray[i].removeTask(s, serverArray[i], true);// pleaca clientul de pe pozitia i
									System.out.println(s);
									output.append(s+"\n");
									if (serverArray[i].task.size()>0){ 
										serverArray[i].task.get(0).queueFirst=curent; //initializez instanta queueFirst a clinetului de pe pozitia 0
									}
									numberOfTask--;
								}
							}
						}
					}
				}
				
				boolean servingTime=true; // calculez serving time
				for (int i=0;i<numberOfServers;i++){
					if(serverArray[i].task.size()>0 && servingTime){
						servingTime=false;
						countServingTime++;
					}
				}
				
				int countTasks=0; //calculez peak hour
				for (int i=0;i<numberOfServers;i++){
					countTasks+=serverArray[i].task.size();
				}
				if (countTasks>taskMax){
					taskMax=countTasks;
					peakHour=timp.get(); 
				}
				
				if (tnext==curent){ 
					timeP =randomTaskProcessing(minProcessingTime, maxProcessingTime);
					Task t1= new Task(curent,timeP,++id);
					numberOfTask++;
					int nextServer = SHORTEST_TIME();
					System.out.println("La momentul " + Integer.toString(curent) + " clientul " + Integer.toString(id)+ " este adaugat la coada " + Integer.toString(nextServer) + " cu timpul de procesare " + Integer.toString(timeP) + ". ");
					output.append("La momentul " + Integer.toString(curent) + " clientul " + Integer.toString(id)+ " este adaugat la coada " + Integer.toString(nextServer) + " cu timpul de procesare " + Integer.toString(timeP) + ". "  + "\n");
					serverArray[nextServer].addTask(t1);
					if (serverArray[nextServer].task.size()==1){
						t1.queueFirst=curent;
					}
					x=0;
				}
				timp.incrementAndGet();
				wait(1000); //afisarea se face tot la o secunda
				System.out.println("Timpul este " + curent + ".");
				output.append("Timpul este" + curent + ".\n");
				
				output2.append("Timpul este " + Integer.toString(curent) + "\n");
				for (int i=0;i<numberOfServers;i++){
					output2.append("Coada "+ i +" are "+ Integer.toString(serverArray[i].task.size()) + " clienti."+ "\n");
				}
				output2.append("\n");
				
				
				if (timp.get()==timeLimit){
					System.out.println("Timpul este " + timeLimit + ".");
					output.append("Timpul este" + timeLimit + ".\n");
					System.out.println("SIMULATION IS OVER!");
					output.append("SIMULATION IS OVER!" + "\n");
					System.out.println();
					output.append("\n");
					if (countWaitingTime!=0){
						System.out.println("Average waiting time=" +averageWaitingTime/countWaitingTime);
						output.append("Average waiting time=" +averageWaitingTime/countWaitingTime + "\n");
					}
					else{
						System.out.println("Average waiting time=0");
						output.append("Average waiting time=0\n");
					}
					System.out.println("Serving time=" +  countServingTime);
					output.append("Serving time=" +  countServingTime + "\n");
					System.out.println("Peak hour="+peakHour);
					output.append("Peak hour="+peakHour + "\n");
					
					interrupt();
				}
			}
		}
		catch(InterruptedException e2){}
	}		
}