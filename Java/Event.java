package extraCredit;

public class Event {
	double time;            // Time at which Event takes place
	int type;               // Type of Event
	Event next;             // Points to next event in list
	
	Event(){}	
	Event(double t, int i)
	{
		    this.time = t;
		    this.type = i;
		    this.next = null;
	}
}
