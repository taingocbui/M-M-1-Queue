package extraCredit;

public class QueueSystem {
	QueueSystem(){}
	
	void runQ(int k, int m, double mu, double rho, int l, double lambda2){
	EventList Elist = new EventList();   // Create event list
	double lambda1 = m*mu*rho;            // Arrival rate
	
	double clock = 0.0;             // System clock
	int N = 0;                      // Number of customers in system
	int Narr = 0;					// Number of Arrivals
	int Ndep = 0;                   // Number of departures from system
	int NSblock = 0;				// Number of system programs blocked
	int NUblock = 0;				// Number of user programs blocked
	double EN = 0.0;                // For calculating E[N]
	
	Elist.insert(exp_rv(lambda1),1); // Generate first arrival System event
	Elist.insert(exp_rv(lambda2),2); // Generate first arrival User event
	
	Event CurrentEvent = new Event();
	  while (Ndep <= 200000)
	  {
	    CurrentEvent = Elist.get();               	// Get next Event from the head of the EventList
	    double prev = clock;                      	// Store old clock value
	    clock = CurrentEvent.time;                	// Update system clock 

	    switch (CurrentEvent.type) {
	    case 1:                                   	// If System arrival 
	      EN += N*(clock-prev);                   	// update system statistics
	      if (N < k)							  	// If number of events less than l
	      {
	    	  N++;                                	// update system size
	    	  Narr++;							  	// update number of arrivals
	    	  Elist.insert(clock+exp_rv(lambda1),1);//generate next System arrival
	      }
	      else								// If the system is full
	      {
	    	  Narr++;								//update number of arrivals
	    	  NSblock++;							   	//update the number of blocked system events
	    	  Elist.insert(clock+exp_rv(lambda1),1);	//buffering the blocked System event
	      }	      
	      if (N <= m) {                             					// If number of events are less than or equal number of machines
	    	  Elist.insert(clock+exp_rv(mu),0);     // generate its departure event
	      }
	      break;
	    case 2:
	    	EN += N*(clock-prev);                   	// update system statistics
	    	if (N < l)
	    	{
	    		N++;
	    		Narr++;
	    		Elist.insert(clock+exp_rv(lambda2),2);
	    		if (N <= m) {                           // If number of events are less than or equal number of machines
	  	    	  Elist.insert(clock+exp_rv(mu),0);     // generate its departure event
	  	      }
	    	}
	    	else
	    	{
	    		Narr++;
	    		NUblock++;
	    		Elist.insert(clock+exp_rv(lambda2),2);
	    	}
	    	break;
	    case 0:                                   	// If departure
	      EN += N*(clock-prev);                  	//  update system statistics
	      N--;                                  	//  decrement system size
	      Ndep++;                                	//  increment number of departures
	      if (N >= m) {                           	// If customers remain
	        Elist.insert(clock+exp_rv(mu),0);   	//  generate next departure
	      } 
	      break;
	    }	    
	  }
	  // output simulation results for N, E[N] 
	  System.out.print("Rho's value: ");
	  System.out.printf("%.2f\n", rho);
	  System.out.println("Current number of customers in system: " + N);
	  System.out.println("Total number of customers arrived: "+ Narr);
	  System.out.println("Total number of system programs blocked: "+ NSblock);
	  System.out.println("Total number of user programs blocked: "+NUblock );
	  System.out.println("Expected number of customers (simulation): "+ EN/clock);
	
	  // output derived value for E[N]
	  rho = lambda1/(m*mu); 
	  System.out.println("Expected number of customers (analysis): "+rho/(1-rho));
	 	  
	  System.out.println("Expected time spent by a customer: "+EN/Ndep);
	  
	  System.out.println("blocking probability for system programs: "+NSblock/(double)Narr);
	  System.out.println("blocking probability for user programs: "+NUblock/(double)Narr);
	  
	  System.out.println("-------------------------------------------------------------");
	}

static double Seed = 1111.0;

/*******************************************/
/* returns a uniform (0,1) random variable */
/*******************************************/
static double uni_rv()           
{
    double k1 = 16807.0;
    double m1 = 2.147483647e9;
    double rv;

    Seed= (k1*Seed) %m1;	
    rv=Seed/m1;
    return(rv);
}

/*******************************/
/* given arrival rate lambda   */
/* returns an exponential r.v. */ 
/*******************************/
static double exp_rv(double lambda)
{
    double exp;
    exp = ((-1) / lambda) * Math.log(uni_rv());
    return(exp);
}
}

