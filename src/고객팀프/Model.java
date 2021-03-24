package 고객팀프;

public class Model {
	int number;	//번호 ID
	int date;	//날짜
	String place;	//장소
	double n2o;	//이산화질소(ppm) 
	double o3;	//오존농도(ppm) 
	double co2;	//이산화탄소(ppm) 
	double so2;	//아황산가스(ppm) 0.03ppm 이하
	int dust;	//미세먼지(㎍/㎥)
	int udust;	//초미세먼지(㎍/㎥)

	
	public String toString() {
		String ret = "";
		ret += "번호 : "+number+"\n";
		ret += "날짜 : "+date+"\n";
		ret += "장소 : "+place+"\n";
		ret += "이산화질소 : "+n2o+" (ppm)\n";
		ret += "오존농도 : "+o3+" (ppm)\n";
		ret += "이산화탄소 : "+co2+" (ppm)\n";
		ret += "아황산가스 : "+so2+" (ppm)\n";
		ret += "미세먼지 : "+dust+" (㎍/㎥)\n";
		ret += "초미세먼지 : "+udust+" (㎍/㎥)\n";
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