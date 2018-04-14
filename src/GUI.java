import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.SystemColor;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField s;
	private JTextField minp;
	private JTextField maxp;
	private JTextField mina;
	private JTextField q;
	private JTextField maxa;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BOGDAN POPA - QUEUES SIMULATION");
		setBounds(100, 100, 537, 687);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.RED);
		contentPane.setForeground(Color.RED);
		setContentPane(contentPane);
		
		s = new JTextField();
		s.setBounds(384, 32, 128, 22);
		s.setColumns(10);
		
		minp = new JTextField();
		minp.setBounds(168, 78, 85, 22);
		minp.setColumns(10);
		
		maxp = new JTextField();
		maxp.setBounds(418, 78, 94, 22);
		maxp.setColumns(10);
		
		mina = new JTextField();
		mina.setBounds(143, 124, 110, 22);
		mina.setColumns(10);
		
		maxa = new JTextField();
		maxa.setBounds(396, 124, 116, 22);
		maxa.setColumns(10);
		
		q = new JTextField();
		q.setBounds(63, 32, 190, 22);
		q.setColumns(10);
		
		TextArea output = new TextArea();
		output.setForeground(SystemColor.textText);
		output.setBackground(SystemColor.text);
		output.setEditable(false);
		output.setBounds(36, 170, 448, 204);
		contentPane.add(output);
		
		TextArea output2 = new TextArea();
		output2.setForeground(Color.WHITE);
		output2.setBackground(Color.BLACK);
		output2.setBounds(185, 392, 156, 180);
		contentPane.add(output2);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBackground(Color.BLACK);
		btnStart.setForeground(Color.WHITE);
		btnStart.setFont(new Font("Monospaced", Font.PLAIN, 17));
		btnStart.setBounds(208, 602, 116, 25);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				output.setText("");
				int queues=Integer.parseInt(q.getText());
				int simulationTime=Integer.parseInt(s.getText());
				int minProcessingTime=Integer.parseInt(minp.getText());
				int maxProcessingTime=Integer.parseInt(maxp.getText());
				int minArrivalTime=Integer.parseInt(mina.getText());
				int maxArrivalTime=Integer.parseInt(maxa.getText());
				Server arrayServer[] = new Server[queues];
				for (int i =0 ;i<queues;i++){
					arrayServer[i]=new Server(i);
					arrayServer[i].start();
				}
				SimulationManager simulation = new SimulationManager(queues,arrayServer,simulationTime,minProcessingTime,maxProcessingTime,minArrivalTime,maxArrivalTime,output,output2);
				simulation.start();
				
			}
		});
		
		JTextArea txtrQueue = new JTextArea();
		txtrQueue.setEditable(false);
		txtrQueue.setForeground(Color.WHITE);
		txtrQueue.setBackground(Color.BLACK);
		txtrQueue.setBounds(12, 32, 52, 22);
		txtrQueue.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txtrQueue.setText("Queues");
		
		JTextArea txtrSimulationTime = new JTextArea();
		txtrSimulationTime.setEditable(false);
		txtrSimulationTime.setBackground(Color.BLACK);
		txtrSimulationTime.setForeground(Color.WHITE);
		txtrSimulationTime.setBounds(265, 32, 124, 22);
		txtrSimulationTime.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txtrSimulationTime.setText("Simulation Time");
		
		JTextArea txtrMinimProcessingTime = new JTextArea();
		txtrMinimProcessingTime.setEditable(false);
		txtrMinimProcessingTime.setForeground(Color.WHITE);
		txtrMinimProcessingTime.setBackground(Color.BLACK);
		txtrMinimProcessingTime.setBounds(12, 78, 156, 22);
		txtrMinimProcessingTime.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txtrMinimProcessingTime.setText("Min Processing Time");
		
		JTextArea txtrMaximProcessingTime = new JTextArea();
		txtrMaximProcessingTime.setEditable(false);
		txtrMaximProcessingTime.setForeground(Color.WHITE);
		txtrMaximProcessingTime.setBackground(Color.BLACK);
		txtrMaximProcessingTime.setBounds(265, 78, 156, 22);
		txtrMaximProcessingTime.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txtrMaximProcessingTime.setText("Max Processing Time");
		
		JTextArea txtrMinimArrivalTime = new JTextArea();
		txtrMinimArrivalTime.setEditable(false);
		txtrMinimArrivalTime.setForeground(Color.WHITE);
		txtrMinimArrivalTime.setBackground(Color.BLACK);
		txtrMinimArrivalTime.setBounds(12, 124, 132, 22);
		txtrMinimArrivalTime.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txtrMinimArrivalTime.setText("Min Arrival Time");
		
		JTextArea txtrMaximArrivalTime = new JTextArea();
		txtrMaximArrivalTime.setEditable(false);
		txtrMaximArrivalTime.setForeground(Color.WHITE);
		txtrMaximArrivalTime.setBackground(Color.BLACK);
		txtrMaximArrivalTime.setBounds(265, 124, 132, 22);
		txtrMaximArrivalTime.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txtrMaximArrivalTime.setText("Max Arrival Time");
		
		contentPane.setLayout(null);
		contentPane.add(btnStart);
		contentPane.add(txtrQueue);
		contentPane.add(txtrMinimArrivalTime);
		contentPane.add(txtrSimulationTime);
		contentPane.add(txtrMinimProcessingTime);
		contentPane.add(txtrMaximProcessingTime);
		contentPane.add(txtrMaximArrivalTime);
		contentPane.add(s);
		contentPane.add(minp);
		contentPane.add(maxp);
		contentPane.add(mina);
		contentPane.add(q);
		contentPane.add(maxa);
	}
}