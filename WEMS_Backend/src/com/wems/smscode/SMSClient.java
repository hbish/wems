package com.wems.smscode;

/*
 * @author : William Alexander
 */

public class SMSClient implements Runnable
{
	public final static int SYNCHRONOUS=0;
	public final static int ASYNCHRONOUS=1;
	public int status = -1;
	public long messageNo = -1;

	private Thread myThread = null;
	private int mode = -1;
	private String recipient = null;
	private String message = null;
	private SerialParameters parameters;
	private String csca;
	private boolean debug = true;
	
	public SMSClient(int mode)
	{
		this.mode = mode;
	}

	public static void main(String[] args)
	{
		String csca = "+61418706700"; // Message centre
		SerialParameters parameters = new SerialParameters("COM12",7200000,0,0,8,1,0);
		SMSClient sms = new SMSClient(-1);
		sms.sendMessage(csca, parameters, "0403681998", "This is a test, thankyou.");
	}

	public int sendMessage(String csca, SerialParameters parameters, String recipient, String message)
	{
		this.csca = csca;
		this.parameters = parameters;
		this.recipient = recipient;
		this.message = message;
		myThread = new Thread(this);
		myThread.start();
		return status;
	}

	public void run()
	{
		Sender aSender = new Sender(debug, csca, parameters, recipient, message);
		try
		{
			// Send message
			aSender.send();
			// In SYNCHRONOUS mode wait for return: 0 for OK, -2 for timeout, -1 for other errors
			if (mode==SYNCHRONOUS)
			{
				while (aSender.status == -1)
				{
					Thread.sleep(1000);
				}
			}
			if (aSender.status == 0) messageNo = aSender.messageNo;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.status = aSender.status;
		aSender = null;
	}
}