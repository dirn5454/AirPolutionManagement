package ������;

public class Model {
	int number;	//��ȣ ID
	int date;	//��¥
	String place;	//���
	double n2o;	//�̻�ȭ����(ppm) 
	double o3;	//������(ppm) 
	double co2;	//�̻�ȭź��(ppm) 
	double so2;	//��Ȳ�갡��(ppm) 0.03ppm ����
	int dust;	//�̼�����(��/��)
	int udust;	//�ʹ̼�����(��/��)

	
	public String toString() {
		String ret = "";
		ret += "��ȣ : "+number+"\n";
		ret += "��¥ : "+date+"\n";
		ret += "��� : "+place+"\n";
		ret += "�̻�ȭ���� : "+n2o+" (ppm)\n";
		ret += "������ : "+o3+" (ppm)\n";
		ret += "�̻�ȭź�� : "+co2+" (ppm)\n";
		ret += "��Ȳ�갡�� : "+so2+" (ppm)\n";
		ret += "�̼����� : "+dust+" (��/��)\n";
		ret += "�ʹ̼����� : "+udust+" (��/��)\n";
		ret+="\n";
		return ret;
	}
	public void set_number(int number) {
		this.number = number;
	}
	public int get_number() {
		return this.number;
	}
	
	public void set_date(int date) {
		this.date = date;
	}
	public int get_date() {
		return this.date;
	}
	
	public void set_place(String place) {
		this.place = place;
	}
	public String get_place() {
		return this.place;
	}
	
	public void set_n2o(double n2o) {
		this.n2o = n2o;
	}
	public double get_n2o() {
		return this.n2o;
	}
	
	public void set_o3(double o3) {
		this.o3 = o3;
	}
	public double get_o3() {
		return this.o3;
	}
	
	public void set_co2(double co2) {
		this.co2 = co2;
	}
	public double get_co2() {
		return this.co2;
	}
	public void set_so2(double so2) {
		this.so2 = so2;
	}
	public double get_so2() {
		return this.so2;
	}
	

	public void set_dust(int dust) {
		this.dust = dust;
	}
	public int get_dust() {
		return this.dust;
	}
	

	public void set_udust(int udust) {
		this.udust = udust;
	}
	public int get_udust() {
		return this.udust;
	}

	
}