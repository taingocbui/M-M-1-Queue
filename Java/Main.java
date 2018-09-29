package extraCredit;


public class Main {

	public static void main(String[] args)
	{			
		int k = 4;
		int m = 2;
		int mu = 3;
		double lambda2 = 4;
		int l = 3;
		QueueSystem[] Q = new QueueSystem[10];
		for (int i = 0; i < Q.length; i++)
		{
			Q[i] = new QueueSystem();
		}
		double rho = 0.1;
		for (int j = 0; j < Q.length; j++)
		{
			Q[j].runQ(k, m, mu, rho, l, lambda2);
			rho = rho + 0.1;
		}
	}
}
