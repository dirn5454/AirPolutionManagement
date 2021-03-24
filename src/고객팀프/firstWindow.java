package ������;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import ������.DrawPanel;
import ������.MySQL;

//���� ù��° ȭ�� Ŭ����
public class firstWindow extends JFrame {

	ArrayList<String> listmodel = new ArrayList<String>();
	//String number;
	String day;
	String local;
	String N2O;
	String O3;
	String CO2;
	String SO2;
	String DUST;
	String UDUST;
	// JTextField�� text ���� ��� ����

	Model model = new Model();
	JTable table = new JTable();
	JTextField dateT, placeT, n2oT, o3T, co2T, so2T, dustT, udustT;
	JTextField dataText;
	JTextField chartText;
	JTextField gradeText;
	Calendar c;
	SimpleDateFormat d;
	Date daydate;
	JPanel nJpanel = new JPanel();
	DrawPanel graphPanel;

	String date;
	String n2o;
	String co2;
	String o3;
	String so2;
	String dust;
	String udust;
	// ������ �׷����� ��Ÿ���� �����͸� ��� ����

	double Gn2o;
	double Go3;
	double Gco2;
	double Gso2;
	int Gdust;
	int Gudust;

	double grade1;
	double grade2;
	double grade3;
	double grade4;
	int grade5;
	int grade6;

	MySQL mysql = new MySQL();
	Connection conn;

	firstWindow() throws SQLException {
		conn = mysql.makeConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("��մ�����");
		this.setLayout(new BorderLayout());

		JPanel Npanel = new JPanel();
		JLabel option = new JLabel("����ϰ� ���� ��� ����");
		Npanel.add(option);

		add(Npanel, BorderLayout.NORTH);
		// ��
		JPanel date = new JPanel();
		JPanel place = new JPanel();
		JPanel month = new JPanel();
		JPanel panel = new JPanel(new GridLayout(3, 0));
		JLabel dateL = new JLabel("������ ����");
		JLabel placeL = new JLabel("�Ϻ� ������");
		JLabel monthL = new JLabel("���� ������");

		JButton changeButton = new JButton("Edit");
		JButton showButton = new JButton("Day");
		JButton monthButton = new JButton("Month");

		JMenuBar mb = new JMenuBar();
		JMenuItem menuItem = new JMenuItem();
		JMenuItem load = new JMenuItem("�ҷ�����");
		JMenuItem save = new JMenuItem("���� ����");
		JMenuItem exit = new JMenuItem("����");

		saveCSVAction listener1 = new saveCSVAction();
		exitAction listener2 = new exitAction();
		loadCSVAction listener3 = new loadCSVAction();
		menuItem.addActionListener(listener1);
		menuItem.addActionListener(listener2);
		menuItem.addActionListener(listener3);

		mb.add(load);
		mb.add(save);
		mb.add(exit);
		setJMenuBar(mb);

		save.addActionListener(new saveCSVAction());
		exit.addActionListener(new exitAction());
		load.addActionListener(new loadCSVAction());

		date.add(dateL);
		date.add(changeButton);
		place.add(placeL);
		place.add(showButton);
		month.add(monthL);
		month.add(monthButton);
		panel.add(date);
		panel.add(place);
		panel.add(month);
		add(panel, BorderLayout.CENTER);
		// ��

		setSize(400, 300);
		setVisible(true);

		changeButton.addActionListener(new changeListener());
		showButton.addActionListener(new showListener());
		monthButton.addActionListener(new monthListener());
	}

	class exitAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(0);
		}
	}
	class loadCSVAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Statement stmt = null;
			
					try {
						String sql;
						stmt = conn.createStatement();
						sql = "CREATE DATABASE DB10";
						stmt.executeUpdate(sql);
						sql = "USE DB10";
						stmt.executeUpdate(sql);
						sql ="create table polu"+ "(date varchar(10), place varchar(20),"
								+ " n2o varchar(10), o3 varchar(10), co2 varchar(10),"
								+ " so2 varchar(10), dust varchar(10), udust varchar(10));";
						stmt.executeUpdate(sql);
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
				
					JFileChooser chooser = new JFileChooser(); // ��ü ����
					chooser.setCurrentDirectory(new File("C:\\"));
					chooser.setFileFilter(new FileNameExtensionFilter("*.CSV", "CSV"));
			
			
					int ret = chooser.showOpenDialog(null); // ����â ����
					if (ret != JFileChooser.APPROVE_OPTION) {
						JOptionPane.showMessageDialog(null, "������ ���������ʾҽ��ϴ�.", "���", JOptionPane.WARNING_MESSAGE);
						return;
					}
					String filePath = chooser.getSelectedFile().getPath();
					filePath = filePath.replace("\\", "/");
					System.out.println(filePath);
					String loadsql = "LOAD DATA INFILE '" + filePath + "' INTO TABLE polu CHARACTER SET UTF8 FIELDS TERMINATED BY ','";
					try {
						stmt.executeUpdate(loadsql);
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					
		}
	}
	
	class saveCSVAction implements ActionListener  {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				mysql.writeCSV();
			} catch(Exception e1) {
			    e1.printStackTrace();
			}
		}
	}


	class changeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				new editWindow();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}
	}
	// ����, ���� , ������Ʈâ�� ���� ���� ������

	class showListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				new dayWindow();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}
	}

	// ���� ���� ��¥�� â�� ���� ���� ������

	class monthListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				new monthWindow();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
		}
	}
	// ���� ��� â�� �������� ������

	// ���� ���� �Է� â
	class monthWindow extends JDialog {
		public monthWindow() throws SQLException {
			setTitle("���� ��� ������");
			this.setLayout(new BorderLayout());

			JPanel Npanel = new JPanel();
			JLabel option = new JLabel("�������� ��¥ ����");
			Npanel.add(option);
			add(Npanel, BorderLayout.NORTH);
			// ��
			JPanel date = new JPanel();
			JPanel place = new JPanel();
			JPanel panel = new JPanel(new GridLayout(2, 0));
			JLabel dateL = new JLabel("month");
			JLabel placeL = new JLabel("������");
			dateT = new JTextField(10);
			placeT = new JTextField(10);
			date.add(dateL);
			date.add(dateT);
			place.add(placeL);
			place.add(placeT);
			panel.add(date);
			panel.add(place);
			add(panel, BorderLayout.CENTER);
			// ��
			JButton check = new JButton("Ȯ��");
			add(check, BorderLayout.SOUTH);

			setSize(400, 300);
			setVisible(true);

			dateT.setText("");
			placeT.setText("");

			check.addActionListener(new monthshowListener());
		}
	}

	class monthshowListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			day = dateT.getText();
			local = placeT.getText();

			try {
				new monthShowWindow();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	// �Է��� ���� ������ ���� ������ ��� â�� ���� ������

	// ���� ����� �����ִ� â
	class monthShowWindow extends JDialog {
		public monthShowWindow() throws SQLException {
			setTitle("�� ��� ������");
			setSize(850, 400);
			setModal(true);
			setLayout(new BorderLayout());

			JPanel nJpanel = new JPanel();
			JPanel wJpanel = new JPanel(new GridLayout(2, 0));

			graphPanel = new DrawPanel();

			model = mysql.monthDataShow(conn, day, local);

//			System.out.println(day);
			dataText = new JTextField();
			gradeText = new JTextField();
			chartText = new JTextField();

			// �׷��� �ǳ��� �ι��� ȭ���� ���ͷ��̾ƿ��� �ִ´�.

			wJpanel.add(dataText);
			wJpanel.add(gradeText);
			add(nJpanel, "North");
			add(wJpanel, "West");
			add(graphPanel, "Center");

			Gn2o = model.get_n2o();
			Go3 = model.get_o3();
			Gco2 = model.get_co2();
			Gso2 = model.get_so2();
			Gdust = model.get_dust();
			Gudust = model.get_udust();
			graphPanel.setScore(Gn2o, Go3, Gco2, Gso2, Gdust, Gudust);
			// graphPanel.repaint();

			// �׷����� ���� ����

			grade1 = model.get_n2o();
			grade2 = model.get_o3();
			grade3 = model.get_co2();
			grade4 = model.get_so2();
			grade5 = model.get_dust();
			grade6 = model.get_udust();

			n2o = Double.toString(model.get_n2o());
			co2 = Double.toString(model.get_co2());
			o3 = Double.toString(model.get_o3());
			so2 = Double.toString(model.get_so2());
			dust = Integer.toString(model.get_dust());
			udust = Integer.toString(model.get_udust());

			String grade = setGrade(grade1, grade2, grade3, grade4, grade5, grade6);

			dataText.setText(model.get_place() + " " + day + "\n" + n2o + "ppm\n" + o3 + "ppm\n" + co2 + "ppm\n" + so2
					+ "ppm\n" + dust + "��/��\n" + udust + "��/��");
			gradeText.setText(grade);
			// �׷�����

			setVisible(true);
		}

		public String setGrade(double n2o, double o3, double co2, double so2, int dust, int udust) {
			String temp = "";
			if (n2o <= 0.02)
				temp += "n2o : ���� ";
			else if (0.02 < n2o && n2o <= 0.05)
				temp += "n2o : ���� ";
			else if (0.05 < n2o && n2o <= 0.15)
				temp += "n2o : ���� ";
			else
				temp += "n2o : �ſ� ���� ";

			if (o3 <= 0.03)
				temp += "o3 : ���� ";
			else if (0.03 < o3 && o3 <= 0.09)
				temp += "o3 : ���� ";
			else if (0.09 < o3 && o3 <= 0.15)
				temp += "o3 : ���� ";
			else
				temp += "o3 : �ſ� ���� ";

			if (co2 <= 0.3)
				temp += "co2 : ���� ";
			else if (0.3 < co2 && co2 <= 0.7)
				temp += "co2 : ���� ";
			else if (0.7 < co2 && co2 <= 1.0)
				temp += "co2 : ���� ";
			else
				temp += "co2 : �ſ� ���� ";

			if (so2 <= 0.03)
				temp += "so2 : ���� ";
			else if (0.03 < so2 && so2 <= 0.06)
				temp += "so2 : ���� ";
			else if (0.06 < so2 && so2 <= 0.2)
				temp += "so2 : ���� ";
			else
				temp += "so2 : �ſ� ���� ";

			if (dust <= 15)
				temp += "�̼����� : ���� ";
			else if (15 < dust && dust <= 35)
				temp += "�̼����� : ���� ";
			else if (35 < dust && dust <= 75)
				temp += "�̼����� : ���� ";
			else
				temp += "�̼����� : �ſ� ���� ";

			if (udust <= 8)
				temp += "�ʹ̼����� : ���� ";
			else if (8 < udust && udust <= 25)
				temp += "�ʹ̼����� : ���� ";
			else if (25 < udust && udust <= 50)
				temp += "�ʹ̼����� : ���� ";
			else
				temp += "�ʹ̼����� : �ſ� ���� ";

			return temp;
		}
	}

	// �ڷΰ��� ��ư �׼Ǹ�����

	// ���� ���� ������Ʈ�� �ϴ� â
	class editWindow extends JDialog {
		public editWindow() throws SQLException {
			setTitle("������ �����ϱ�");
			this.setLayout(new BorderLayout());
			// ��
			JPanel Npanel = new JPanel();
			JButton insertButton = new JButton("Insert");
			JButton updateButton = new JButton("Update");
			JButton deleteButton = new JButton("Delete");
			Npanel.add(insertButton);
			Npanel.add(updateButton);
			Npanel.add(deleteButton);
			add(Npanel, BorderLayout.NORTH);
			// ��

			JPanel numP = new JPanel();
			JPanel dateP = new JPanel();
			JPanel placeP = new JPanel();
			JPanel n2oP = new JPanel();
			JPanel o3P = new JPanel();
			JPanel co2P = new JPanel();
			JPanel so2P = new JPanel();
			JPanel dustP = new JPanel();
			JPanel udustP = new JPanel();
			JPanel panel = new JPanel(new GridLayout(9, 0));

			//JLabel numL = new JLabel("�ѹ�");
			JLabel dateL = new JLabel("��¥");
			JLabel placeL = new JLabel("����");
			JLabel n2oL = new JLabel("N2O");
			JLabel o3L = new JLabel("O3");
			JLabel co2L = new JLabel("CO2");
			JLabel so2L = new JLabel("SO2");
			JLabel dustL = new JLabel("�̼�����");
			JLabel udustL = new JLabel("�ʹ̼�����");

			//numT = new JTextField(10);
			dateT = new JTextField(10);
			placeT = new JTextField(10);
			n2oT = new JTextField(10);
			o3T = new JTextField(10);
			co2T = new JTextField(10);
			so2T = new JTextField(10);
			dustT = new JTextField(10);
			udustT = new JTextField(10);

			//numP.add(numL);
			//numP.add(numT);
			dateP.add(dateL);
			dateP.add(dateT);
			placeP.add(placeL);
			placeP.add(placeT);
			n2oP.add(n2oL);
			n2oP.add(n2oT);
			o3P.add(o3L);
			o3P.add(o3T);
			co2P.add(co2L);
			co2P.add(co2T);
			so2P.add(so2L);
			so2P.add(so2T);
			dustP.add(dustL);
			dustP.add(dustT);
			udustP.add(udustL);
			udustP.add(udustT);

			panel.add(numP);
			panel.add(dateP);
			panel.add(placeP);
			panel.add(n2oP);
			panel.add(o3P);
			panel.add(co2P);
			panel.add(so2P);
			panel.add(dustP);
			panel.add(udustP);
			add(panel, BorderLayout.CENTER);
			// ��

			setSize(400, 500);
			setVisible(true);

			insertButton.addActionListener(new insertListener());
			updateButton.addActionListener(new updateListener());
			deleteButton.addActionListener(new deleteListener());
		}
	}

	class insertListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//number = numT.getText();
			day = dateT.getText();
			local = placeT.getText();
			N2O = n2oT.getText();
			O3 = o3T.getText();
			CO2 = co2T.getText();
			SO2 = so2T.getText();
			DUST = dustT.getText();
			UDUST = udustT.getText();

			try {
				mysql.Insertdata(conn, day, local, N2O, O3, CO2, SO2, DUST, UDUST);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	// �μ�Ʈ ������

	class updateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//number = numT.getText();
			day = dateT.getText();
			local = placeT.getText();
			N2O = n2oT.getText();
			O3 = o3T.getText();
			CO2 = co2T.getText();
			SO2 = so2T.getText();
			DUST = dustT.getText();
			UDUST = udustT.getText();

			try {
				mysql.Updatedata(conn, day, local, N2O, O3, CO2, SO2, DUST, UDUST);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	// ������Ʈ ������

	class deleteListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//number = numT.getText();
			day = dateT.getText();
			local = placeT.getText();
			N2O = n2oT.getText();
			O3 = o3T.getText();
			CO2 = co2T.getText();
			SO2 = so2T.getText();
			DUST = dustT.getText();
			UDUST = udustT.getText();

			try {
				mysql.Deletedata(conn, day, local);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	// ����Ʈ ������

	// ���ϴ� ������ ��¥�� �Է��ϴ� â
	class dayWindow extends JDialog {
		public dayWindow() throws SQLException {
//			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("���� ���� ��¥����");
			this.setLayout(new BorderLayout());
			// ��
			JPanel Npanel = new JPanel();
			JLabel option = new JLabel("�������� ��¥ ����");
			Npanel.add(option);
			add(Npanel, BorderLayout.NORTH);
			// ��
			JPanel date = new JPanel();
			JPanel place = new JPanel();
			JPanel panel = new JPanel(new GridLayout(2, 0));
			JLabel dateL = new JLabel("��¥");
			JLabel placeL = new JLabel("������");
			dateT = new JTextField(10);
			placeT = new JTextField(10);
			date.add(dateL);
			date.add(dateT);
			place.add(placeL);
			place.add(placeT);
			panel.add(date);
			panel.add(place);
			add(panel, BorderLayout.CENTER);
			// ��
			JButton check = new JButton("Ȯ��");
			add(check, BorderLayout.SOUTH);

			setSize(400, 300);
			setVisible(true);

			dateT.setText("");
			placeT.setText("");

			check.addActionListener(new dayShowListener());

		}

		// �Է��� ��¥�� ������ ���� ������
		class dayShowListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					day = dateT.getText();
					local = placeT.getText();

					// ���� ��¥ ��� �ڵ�
					try {
						d = new SimpleDateFormat("yyyyMMdd");
						daydate = d.parse(day);
						c = Calendar.getInstance();
						c.setTime(daydate);
						day = d.format(c.getTime());

					} catch (ParseException e1) {

						e1.printStackTrace();
					}

					new dayShowWindow();

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

// �Է��� ������ ��¥�� ���� �����͸� �����ִ� ȭ��
	class dayShowWindow extends JDialog {

		public dayShowWindow() throws SQLException {
			setTitle("���� ���� ��¥����");
			setSize(850, 400);
			setModal(true);
			setLayout(new BorderLayout());

			JPanel nJpanel = new JPanel();
			JPanel wJpanel = new JPanel(new GridLayout(2, 0));
			JButton prev = new JButton("���� ��¥");
			JButton next = new JButton("���� ��¥");
			
			JMenuBar mb = new JMenuBar();
			JMenuItem menuItem = new JMenuItem();
			
			JMenuItem exit = new JMenuItem("����");

			
			exitAction listener2 = new exitAction();
			
			menuItem.addActionListener(listener2);


	
			mb.add(exit);
			setJMenuBar(mb);

			
			exit.addActionListener(new exitAction());

			

			graphPanel = new DrawPanel();

			add("West", prev);
			add("East", next);

			model = mysql.currentDataShow(conn, day, local);

//			System.out.println(day);
			dataText = new JTextField();
			gradeText = new JTextField();
			chartText = new JTextField();

			wJpanel.add(dataText);
			wJpanel.add(gradeText);
			nJpanel.add(prev);
			nJpanel.add(next);

			// �׷��� �ǳ��� �ι��� ȭ���� ���ͷ��̾ƿ��� �ִ´�.
			add(nJpanel, "North");
			add(wJpanel, "West");
			add(graphPanel, "Center");

			Gn2o = model.get_n2o();
			Go3 = model.get_o3();
			Gco2 = model.get_co2();
			Gso2 = model.get_so2();
			Gdust = model.get_dust();
			Gudust = model.get_udust();
			graphPanel.setScore(Gn2o, Go3, Gco2, Gso2, Gdust, Gudust);

			grade1 = model.get_n2o();
			grade2 = model.get_o3();
			grade3 = model.get_co2();
			grade4 = model.get_so2();
			grade5 = model.get_dust();
			grade6 = model.get_udust();

			n2o = Double.toString(model.get_n2o());
			co2 = Double.toString(model.get_co2());
			o3 = Double.toString(model.get_o3());
			so2 = Double.toString(model.get_so2());
			dust = Integer.toString(model.get_dust());
			udust = Integer.toString(model.get_udust());

			//listmodel.add(number);
			listmodel.add(model.get_place());
			listmodel.add(day);
			listmodel.add(n2o);
			listmodel.add(o3);
			listmodel.add(co2);
			listmodel.add(so2);
			listmodel.add(dust);
			listmodel.add(udust);
			
			
			String grade = setGrade(grade1, grade2, grade3, grade4, grade5, grade6);

			dataText.setText(model.get_place() + " " + day + "\n" + n2o + "ppm\n" + o3 + "ppm\n" + co2 + "ppm\n" + so2
					+ "ppm\n" + dust + "��/��\n" + udust + "��/��");
			gradeText.setText(grade);
			// �׷�����

			prev.addActionListener(new prevAction());
			next.addActionListener(new nextAction());

			setVisible(true);

		}

		public String setGrade(double n2o, double o3, double co2, double so2, int dust, int udust) {
			String temp = "";
			if (n2o <= 0.02)
				temp += "n2o : ���� ";
			else if (0.02 < n2o && n2o <= 0.05)
				temp += "n2o : ���� ";
			else if (0.05 < n2o && n2o <= 0.15)
				temp += "n2o : ���� ";
			else
				temp += "n2o : �ſ� ���� ";

			if (o3 <= 0.03)
				temp += "o3 : ���� ";
			else if (0.03 < o3 && o3 <= 0.09)
				temp += "o3 : ���� ";
			else if (0.09 < o3 && o3 <= 0.15)
				temp += "o3 : ���� ";
			else
				temp += "o3 : �ſ� ���� ";

			if (co2 <= 0.3)
				temp += "co2 : ���� ";
			else if (0.3 < co2 && co2 <= 0.7)
				temp += "co2 : ���� ";
			else if (0.7 < co2 && co2 <= 1.0)
				temp += "co2 : ���� ";
			else
				temp += "co2 : �ſ� ���� ";

			if (so2 <= 0.03)
				temp += "so2 : ���� ";
			else if (0.03 < so2 && so2 <= 0.06)
				temp += "so2 : ���� ";
			else if (0.06 < so2 && so2 <= 0.2)
				temp += "so2 : ���� ";
			else
				temp += "so2 : �ſ� ���� ";

			if (dust <= 15)
				temp += "�̼����� : ���� ";
			else if (15 < dust && dust <= 35)
				temp += "�̼����� : ���� ";
			else if (35 < dust && dust <= 75)
				temp += "�̼����� : ���� ";
			else
				temp += "�̼����� : �ſ� ���� ";

			if (udust <= 8)
				temp += "�ʹ̼����� : ���� ";
			else if (8 < udust && udust <= 25)
				temp += "�ʹ̼����� : ���� ";
			else if (25 < udust && udust <= 50)
				temp += "�ʹ̼����� : ���� ";
			else
				temp += "�ʹ̼����� : �ſ� ���� ";

			return temp;
		}

		class prevAction implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {

				dataText.setText("");

				d = new SimpleDateFormat("yyyyMMdd");
				try {
					daydate = d.parse(day);
				} catch (ParseException e2) {
					e2.printStackTrace();
				}
				c = Calendar.getInstance();
				c.setTime(daydate);
				c.add(Calendar.DATE, -1);
				day = d.format(c.getTime());
				try {
					model = mysql.currentDataShow(conn, day, local);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				grade1 = model.get_n2o();
				grade2 = model.get_o3();
				grade3 = model.get_co2();
				grade4 = model.get_so2();
				grade5 = model.get_dust();
				grade6 = model.get_udust();

				n2o = Double.toString(model.get_n2o());
				co2 = Double.toString(model.get_co2());
				o3 = Double.toString(model.get_o3());
				so2 = Double.toString(model.get_so2());
				dust = Integer.toString(model.get_dust());
				udust = Integer.toString(model.get_udust());

				//listmodel.add(number);
				listmodel.add(model.get_place());
				listmodel.add(day);
				listmodel.add(n2o);
				listmodel.add(o3);
				listmodel.add(co2);
				listmodel.add(so2);
				listmodel.add(dust);
				listmodel.add(udust);
				
				
				String grade = setGrade(grade1, grade2, grade3, grade4, grade5, grade6);

				dataText.setText(model.get_place() + " " + day + "\n" + n2o + "ppm\n" + o3 + "ppm\n" + co2 + "ppm\n"
						+ so2 + "ppm\n" + dust + "��/��\n" + udust + "��/��");
				gradeText.setText(grade);
				Gn2o = model.get_n2o();
				Go3 = model.get_o3();
				Gco2 = model.get_co2();
				Gso2 = model.get_so2();
				Gdust = model.get_dust();
				Gudust = model.get_udust();
				graphPanel.setScore(Gn2o, Go3, Gco2, Gso2, Gdust, Gudust);
				graphPanel.repaint();

			}
		}
		// ������¥��ư�� ���� ������

		class nextAction implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				dataText.setText("");

				d = new SimpleDateFormat("yyyyMMdd");
				try {
					daydate = d.parse(day);
				} catch (ParseException e2) {
					e2.printStackTrace();
				}
				c = Calendar.getInstance();
				c.setTime(daydate);
				c.add(Calendar.DATE, +1);
				day = d.format(c.getTime());

				try {
					model = mysql.currentDataShow(conn, day, local);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				grade1 = model.get_n2o();
				grade2 = model.get_o3();
				grade3 = model.get_co2();
				grade4 = model.get_so2();
				grade5 = model.get_dust();
				grade6 = model.get_udust();

				n2o = Double.toString(model.get_n2o());
				co2 = Double.toString(model.get_co2());
				o3 = Double.toString(model.get_o3());
				so2 = Double.toString(model.get_so2());
				dust = Integer.toString(model.get_dust());
				udust = Integer.toString(model.get_udust());
				
				

				String grade = setGrade(grade1, grade2, grade3, grade4, grade5, grade6);

				dataText.setText(model.get_place() + " " + day + "\n" + n2o + "ppm\n" + o3 + "ppm\n" + co2 + "ppm\n"
						+ so2 + "ppm\n" + dust + "��/��\n" + udust + "��/��");
				gradeText.setText(grade);

				Gn2o = model.get_n2o();
				Go3 = model.get_o3();
				Gco2 = model.get_co2();
				Gso2 = model.get_so2();
				Gdust = model.get_dust();
				Gudust = model.get_udust();
				graphPanel.setScore(Gn2o, Go3, Gco2, Gso2, Gdust, Gudust);
				graphPanel.repaint();

			}
		}
		// ���� ��¥��ư�� ���� ������
	}
}