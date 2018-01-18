package level2;

public class Experience {
	
	
	public int action;
	public int result;
	public int valence;
	public Persistency persistency = Persistency.UNTESTED ; // untested or persistent or sporadic   get the current belief state of the experience? i'm not sure about this.
	
	public Experience(int action,int result,int valence) {
		this.action = action;
		this.result = result;
		this.valence = valence;
	}
	
	@override
	public String toString() {
		return "("+this.action+", "+this.result+", "+this.valence+")";
	}
	
}
