package com.wems.smscode;

/*
 * @author : William Alexander
 */
import java.util.Date;

public class Sender implements Runnable
{
	private static final long STANDARD = 500;
	private static final long LONG = 2000;
	private static final long VERYLONG = 20000;
	private static final char CONTROL_Z = (char)26;

	private SerialConnection mySerial = null;
	private SerialParameters parameters;
	private long delay = STANDARD;
	private String csca;
	private boolean debug;

	String in, out;
	Thread aThread = null;
	String recipient = null;
	String message = null;

	public int step;
	public int status = -1;
	public long messageNo = -1;

	public Sender(boolean debug, String csca, SerialParameters parameters, String recipient, String message)
	{
		this.debug = debug;
		this.csca = csca;
		this.parameters = parameters;
		this.recipient = recipient;
		this.message = message;
	}

	/**
	 * Connect to the port and start the dialogue thread
	 */
	public int send() throws Exception
	{
		SerialParameters params = parameters;
		mySerial = new SerialConnection(debug, params);
		mySerial.openConnection();
		aThread = new Thread(this);
		aThread.start();
		System.out.println("Running SMS Client Service\n==========================");
		System.out.println("- Recipient: " + recipient + "\n- Message: " + message + "\n");
		return 0;
	}

	/**
	 * Implement the dialogue thread: message / response via steps
	 * handle time out
	 */
	public void run()
	{
		long timeDiff;
		boolean timeOut = false;
		long startTime = (new Date()).getTime();
		while ((step <7) && (!timeOut))
		{
			// Check where we are in specified delay
			timeDiff = (new Date()).getTime() - startTime;
			timeOut = timeDiff > delay;
			log("Running in Specified Delay: " + timeDiff);

			// If atz does not work, type to send CONTROL_Z and retry, in case a message was stuck
			if (timeOut && (step==1))
			{
				step = -1;
				mySerial.send("" + CONTROL_Z);
			}

			// Read incoming string
			String result = mySerial.getIncommingString();
			log("Incoming Result: " + result);
			int expectedResult = -1;

			try
			{
				log("Connection Step: " + step);
				switch (step)
				{
				case 0:
					mySerial.send("atz");
					delay = LONG;
					startTime = (new Date()).getTime();
					break;
				case 1:
					delay = STANDARD;
					mySerial.send("ath0");
					startTime = (new Date()).getTime();
					break;
				case 2:
					expectedResult = result.indexOf("OK");
					log("Connection Received OK: " + expectedResult);
					if (expectedResult > -1)
					{
						mySerial.send("at+cmgf=1");
						startTime = (new Date()).getTime();
					}
					else
					{
						step = step-1;
					}
					break;
				case 3:
					expectedResult = result.indexOf("OK");
					log("Connection Received OK: " + expectedResult);
					if (expectedResult >- 1)
					{
						mySerial.send("at+csca=\""+csca+"\"");
						startTime = (new Date()).getTime();
					}
					else
					{
						step = step-1;
					}
					break;
				case 4:
					expectedResult = result.indexOf("OK");
					log("Connection Received OK: " + expectedResult);
					if (expectedResult >- 1)
					{
						mySerial.send("at+cmgs=\""+recipient+"\"");
						startTime=(new Date()).getTime();
					}
					else
					{
						step = step-1;
					}
					break;
				case 5:
					expectedResult = result.indexOf(">");
					log("Connection Received OK: " + expectedResult);
					if (expectedResult >- 1)
					{
						mySerial.send(message + CONTROL_Z);
						startTime = (new Date()).getTime();
					}
					else
					{
						step = step-1;
					}
					// Waiting for message ack
					delay = VERYLONG;
					break;
				case 6:
					expectedResult = result.indexOf("OK");
					// Read message number
					if (expectedResult >- 1)
					{
						int n = result.indexOf("CMGS:");
						result = result.substring(n+5);
						n = result.indexOf("\n");
						status = 0;
						messageNo = Long.parseLong(result.substring(0,n).trim());
						log("Sent message no: " + messageNo);
					}
					else
					{
						step = step-1;
					}
					break;
				}
				step = step + 1;
				Thread.sleep(100);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		mySerial.closeConnection();

		// If timed out set status
		if (timeOut)
		{
			status = -2;
			log("Timeout at Step #" + step);
		}
	}
	
	/**
	 * logging function, includes date and class name
	 */
	private void log(String s)
	{
		if (debug)
			System.out.println("* " + new java.util.Date() + ": " + this.getClass().getName() + ": " + s);
	}
}