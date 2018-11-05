package metier;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class graphic implements ActionListener, MouseListener {

	private JFrame frame;
	private Movement movement;
	private List<File> files;
	private HashMap<JLabel, String> dataSetLabel;
	private HashMap<JLabel, String> filesOperations;
	private HashMap<JButton, String> operations;
	private JButton btnSelect;
	private JButton btnCombine;
	private JButton btnGroupBy;
	private JButton btnTransform;
	private JPanel TransPanel;
	private JPanel operationPanel;

	private JPanel mainPanel;
	
	private int transform;
	private boolean activeTransform;
	private static Transform trsf;
	
	private Point p1, p2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					graphic window = new graphic();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public graphic() {
		files=new ArrayList<>();
		dataSetLabel=new HashMap<>();
		filesOperations=new HashMap<>();
		operations=new HashMap<>();
		int x=10, y=10, w=155, h=25;
		try {
			File folder = new File("src/dataSet");
			for (File file : folder.listFiles()) {
				System.out.println(file.getName());
				files.add(file);
				JLabel lbl=new JLabel(file.getName());
				lbl.setBounds(x, y, w, h);
				lbl.addMouseListener(this);
				dataSetLabel.put(lbl, file.getName());
				y+=30;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 641, 431);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		operationPanel = new JPanel();
		operationPanel.setBounds(460, 0, 165, 392);
		frame.getContentPane().add(operationPanel);
		operationPanel.setLayout(null);
		operationPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		TransPanel = new JPanel();
		TransPanel.setBounds(0, 36, 165, 133);
		operationPanel.add(TransPanel);
		TransPanel.setLayout(null);
		TransPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		btnSelect = new JButton("Select");
		btnSelect.setBounds(40, 11, 89, 23);
		TransPanel.add(btnSelect);
		btnSelect.addActionListener(this);
		
		btnCombine = new JButton("Combine");
		btnCombine.setBounds(40, 40, 89, 23);
		TransPanel.add(btnCombine);
		btnCombine.addActionListener(this);
		
		btnGroupBy = new JButton("Group By");
		btnGroupBy.setBounds(40, 68, 89, 23);
		TransPanel.add(btnGroupBy);
		btnGroupBy.addActionListener(this);
		
		btnTransform = new JButton("transform");
		btnTransform.setBounds(40, 100, 89, 23);
		TransPanel.add(btnTransform);
		btnTransform.addActionListener(this);
		
		mainPanel = new JPanel();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		mainPanel.setBounds(0, 0, 460, 392);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel lblNewLabel = new JLabel("Transformations");
		lblNewLabel.setBounds(10, 11, 119, 14);
		operationPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("DataSet");
		lblNewLabel_1.setBounds(10, 176, 46, 14);
		operationPanel.add(lblNewLabel_1);
		
		JPanel dataSetPanel = new JPanel();
		dataSetPanel.setBounds(0, 201, 165, 191);
		operationPanel.add(dataSetPanel);
		dataSetPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		dataSetPanel.setLayout(null);
		
		for(JLabel key : dataSetLabel.keySet()) {
			dataSetPanel.add(key);
		}
		
		movement=new Movement(mainPanel.getComponents());
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		System.out.println("actionPerformed");
		if((event.getSource() == btnSelect) || (event.getSource() == btnCombine) || (event.getSource() == btnGroupBy)) {
			String btnName="";
			if(event.getSource() == btnSelect){
				btnName="select";
				System.out.println(btnName);
			}else if(event.getSource() == btnCombine){
				btnName="combine";
				System.out.println(btnName);
			}else{
				btnName="Group By";
				System.out.println(btnName);
			}
			JButton button=new JButton(btnName);
			button.setBounds(mainPanel.getWidth()-200, 50, 89, 23);
			button.addActionListener(this);
			mainPanel.add(button);
			operations.put(button, btnName);
			initialize();
		}else if(event.getSource() == btnTransform) {
			transform=0;
			activeTransform=true;
			trsf=new Transform();
		}else {
			if(activeTransform && transform<2) {
				if(trsf.getOperationName()==null) {
					trsf.setOperationName(operations.get(event.getSource()));
					transform++;
					if(transform==2) {
						JLabel lbl=new JLabel("->");
						int x=(int) (p1.getX()+10);
						int y=(int) p1.getY();
						lbl.setBounds(x, y, 20, 20);
						mainPanel.add(lbl);
						System.out.println(trsf.getFileName()+" "+ trsf.getOperationName());
						initialize();
					}
				}
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if(dataSetLabel.get(event.getComponent())!=null) {
			System.out.println("mouseClicked");
			JLabel jLabel=new JLabel(dataSetLabel.get(event.getComponent()));
			System.out.println(dataSetLabel.get(event.getComponent()));
			jLabel.setBounds(mainPanel.getWidth()-200, 150, 155, 25);
			jLabel.addMouseListener(this);
			mainPanel.add(jLabel);
			filesOperations.put(jLabel, dataSetLabel.get(event.getComponent()));
			initialize();
		}else {
			if(activeTransform && transform<2) {
				if(trsf.getFileName()==null) {
					trsf.setFileName(filesOperations.get(event.getComponent()));
					transform++;
					p1=new Point(movement.getX(), movement.getY());
					if(transform==2) {
						JLabel lbl=new JLabel("->");
						int x=(int) (p1.getX()+10);
						int y=(int) p1.getY();
						lbl.setBounds(x, y, 20, 20);
						mainPanel.add(lbl);
						
						System.out.println(trsf.getFileName()+" "+ trsf.getOperationName());
						initialize();
					}
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		event.getComponent().setBounds(event.getComponent().getX(), event.getComponent().getY(), event.getComponent().getWidth(), event.getComponent().getHeight()+2);
	}

	@Override
	public void mouseExited(MouseEvent event) {
		event.getComponent().setBounds(event.getComponent().getX(), event.getComponent().getY(), event.getComponent().getWidth(), event.getComponent().getHeight()-2);
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
