package 고객팀프;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

class DrawPanel extends JPanel {
	
	
	
	
	private double Gn2o;
	private double Go3;
	private double Gco2;
	private double Gso2;
	private int Gdust;
	private int Gudust;
	private double dGdust;
	private double dGudust;
	int value1, value2, value3, value4;
	
	
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawLine(50, 250, 350, 250);
		
		for (int cnt = 1; cnt < 11; cnt++) {
			double count = cnt;
			g.drawString((count / 100) + "", 20, 255 - 20 * cnt);
			
		}
		// n2o, o3, so2의 세로축 좌표 
		
		for (int cnt = 1; cnt < 11; cnt++) {
			double count = cnt;
			g.drawString((count / 10) + "", 180, 255 - 20 * cnt);
			
		}
		// co2 세로축 좌표
		
		for (int cnt = 1; cnt < 11; cnt++) {
			
			g.drawString(cnt * 10 + "", 240, 255 - 20 * cnt); //세로축 값표시
			g.drawLine(50, 250 - 20 * cnt, 350, 250 - 20 * cnt);
			
		}
		// 초미세먼지, 미세먼지 세로축 좌표  + 가로줄선
		
		g.drawLine(50, 20, 50, 250);
		//n2o, o3, so2 세로축 선
		g.drawLine(230, 20, 230, 250);
		//초미세먼지, 미세먼지 세로축 선
		g.drawLine(170, 20, 170, 250);
		//co2 세로축 선
		g.drawString("N2O", 60, 270);
		g.drawString("O3", 105, 270);
		g.drawString("SO2", 140, 270);
		g.drawString("CO2", 200, 270);
		g.drawString("미세먼지", 250, 270);
		g.drawString("초미세먼지", 310, 270);
		g.setColor(Color.RED);
		
		
		if (Gn2o > 0) {
			value1 = (int) (Gn2o*1000);
			g.fillRect(70, 250 - value1 * 2, 10, value1 * 2);
		}
		if (Go3 > 0) {
			value2 = (int) (Go3*1000);
			g.fillRect(110, 250 - value2 * 2, 10, value2 * 2);
		}

		if (Gso2 > 0) {
			value4 = (int) (Gso2*1000);
			g.fillRect(145, 250 - value4 * 2, 10, value4 * 2);
		}
		g.setColor(Color.BLUE);
		if (Gco2 > 0) {
			value3 = (int) (Gco2*100);
			g.fillRect(210, 250 - value3 * 2, 10, value3 * 2);
		}
		
		
		g.setColor(Color.GREEN);
		if (Gdust > 0) {
			
			g.fillRect(270, 250 - Gdust * 2, 10, Gdust * 2);
		}
		if (Gudust > 0) {
			
			g.fillRect(320, 250 - Gudust * 2, 10, Gudust * 2);
		}
	}
	
	public void setScore(double Gn2o, double Go3, double Gco2, double Gso2, int Gdust, int Gudust) {
		this.Gn2o=Gn2o;
		this.Go3=Go3;
		this.Gco2=Gco2;
		this.Gso2=Gso2;
		this.Gdust=Gdust;
		this.Gudust=Gudust;
	}


}