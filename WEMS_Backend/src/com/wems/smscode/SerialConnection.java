package com.wems.smscode;

import gnu.io.*;
import java.io.*;
import java.util.TooManyListenersException;

/**
  * A class that handles the details of a serial connection. Reads from one
  * Holds the state of the connection.
  */
public class SerialConnection implements SerialPortEventListener, CommPortOwnershipListener
{
	private SerialParameters parameters;
	private OutputStream os;
	private InputStream is;
	private CommPortIdentifier portId;
	private SerialPort sPort;
	private boolean open;
	private String receptionString = "";
	private int timeOut = 30000;
	private boolean debug;
	
	public String getIncommingString()
	{
		byte[] bVal = receptionString.getBytes();
		receptionString = "";
		return new String(bVal);
	}

	/**
	 * Creates a SerialConnection object and initialises variables passed in as params
     * @param parent A SerialDemo object.
     * @param parameters A SerialParameters object.
     * @param messageAreaOut The TextArea that messages that are to be sent out of the serial port are entered into.
     * @param messageAreaIn The TextArea that messages coming into the serial port are displayed on.
	 */
	public SerialConnection(boolean debug, SerialParameters parameters)
	{
		this.debug = debug;
		this.parameters = parameters;
		open = false;
	}

	/**
	 * Attempts to open a serial connection and streams using the parameters
	 * in the SerialParameters object. If it is unsuccessful at any step it
	 * returns the port to a closed state, throws a
	 * <code>SerialConnectionException</code>, and returns.
	 * 
	 * Gives a timeout of 30 seconds on the portOpen to allow other applications
	 * to relinquish the port if have it open and no longer need it.
	 * @throws Exception 
	 */
	public void openConnection() throws Exception
	{
		// Obtain a CommPortIdentifier object for the port you want to open.
		try
		{
			portId = CommPortIdentifier.getPortIdentifier(parameters.getPortName());
			log(parameters.getPortName());
		}
		catch (NoSuchPortException e)
		{
			throw new SerialConnectionException(e.getMessage());
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
		log("OK: CommPortIdentifier");
		
		// Open the port represented by the CommPortIdentifier object. Give
		// the open call a relatively long timeout of 30 seconds to allow
		// a different application to relinquish the port if the user wants to.
		try
		{
			sPort = (SerialPort)portId.open("SMSConnector", timeOut);
		}
		catch (PortInUseException e)
		{
			throw new SerialConnectionException(e.getMessage());
		}
		sPort.sendBreak(1000);
		log("OK: portId.open");
		
		// Set the parameters of the connection. If they won't set, close the
		// port before throwing an exception.
		try
		{
			setConnectionParameters();
		}
		catch (SerialConnectionException e)
		{
			sPort.close();
			throw new SerialConnectionException(e.getMessage());
		}
		log("OK: setConnectionParameters");
		
		// Open the input and output streams for the connection. If they won't
		// open, close the port before throwing an exception.
		try
		{
			os = sPort.getOutputStream();
			is = sPort.getInputStream();
		}
		catch (IOException e)
		{
			sPort.close();
			throw new SerialConnectionException("Error Opening IO Streams");
		}
		log("OK: getOutputStream/getInputStream");
		
		// Add this object as an event listener for the serial port.
		try
		{
			sPort.addEventListener(this);
		}
		catch (TooManyListenersException e)
		{
			sPort.close();
			throw new SerialConnectionException("Too Many Listeners Added");
		}
		log("OK: addEventListener");
		
		// Set notifyOnDataAvailable to true to allow event driven input.
		sPort.notifyOnDataAvailable(true);

		// Set notifyOnBreakInterrup to allow event driven break handling.
		sPort.notifyOnBreakInterrupt(true);

		// Set receive timeout to allow breaking out of polling loop during
		// input handling.
		try
		{
			sPort.enableReceiveTimeout(30);
		}
		catch (UnsupportedCommOperationException e)
		{
			throw new SerialConnectionException("Error in EnableReceiveTimeout");
		}
		log("OK: UnsupportedCommOperationException");
		
		// Add ownership listener to allow ownership event handling.
		portId.addPortOwnershipListener(this);
		open = true;
	}

	/**
      * Sets the connection parameters to the setting in the parameters object.
      * If set fails return the parameters object to original settings and
      * throw exception.
	 */
	public void setConnectionParameters() throws SerialConnectionException
	{
		// Save state of parameters before trying a set.
		int oldBaudRate = sPort.getBaudRate();
		int oldDatabits = sPort.getDataBits();
		int oldStopbits = sPort.getStopBits();
		int oldParity   = sPort.getParity();

		// Set connection parameters, if set fails return parameters object
		// to original state.
		try
		{
			sPort.setSerialPortParams(parameters.getBaudRate(),
									  parameters.getDatabits(),
									  parameters.getStopbits(),
									  parameters.getParity());
		}
		catch (UnsupportedCommOperationException e)
		{
			parameters.setBaudRate(oldBaudRate);
			parameters.setDatabits(oldDatabits);
			parameters.setStopbits(oldStopbits);
			parameters.setParity(oldParity);
			throw new SerialConnectionException("Unsupported parameter");
		}

		// Set flow control
		try
		{
			sPort.setFlowControlMode(parameters.getFlowControlIn() | parameters.getFlowControlOut());
		}
		catch (UnsupportedCommOperationException e)
		{
			throw new SerialConnectionException("Unsupported Flow Control");
		}
	}

	/**
      * Close the port and clean up associated elements.
	  */
	public void closeConnection()
	{
		// If port is already closed just return.
		if (!open)
		{
			return;
		}
		// Check to make sure sPort has reference to avoid a NPE.
		if (sPort != null)
		{
			try
			{
				// Close the IO streams
				os.close();
				is.close();
			}
			catch (IOException e)
			{
				System.err.println(e);
			}
			// Close the port
			sPort.close();
			// Remove the ownership listener
			portId.removePortOwnershipListener(this);
		}
		open = false;
	}

	/**
      * Send a one second break signal.
	  */
	public void sendBreak()
	{
		sPort.sendBreak(1000);
	}

	/**
      * Reports the open status of the port.
      * @return true if port is open, false if port is closed.
	  */
	public boolean isOpen()
	{
		return open;
	}

	/**
      * Handles SerialPortEvents. The two types of SerialPortEvents that this
      * program is registered to listen for are DATA_AVAILABLE and BI. During
      * DATA_AVAILABLE the port buffer is read until it is drained, when no more
      * data is available and 30ms has passed the method returns. When a BI
      * event occurs the words BREAK RECEIVED are written to the messageAreaIn.
	  */
	public void serialEvent(SerialPortEvent e)
	{
		// Create a StringBuffer and int to receive input data.
		StringBuffer inputBuffer = new StringBuffer();
		int newData = 0;

		// Determine type of event.
		switch (e.getEventType()) {

		// Read data until -1 is returned. If \r is received substitute
		// \n for correct newline handling.
		case SerialPortEvent.DATA_AVAILABLE:
			while (newData != -1)
			{
				try
				{
					newData = is.read();
					if (newData == -1)
					{
						break;
					}
					if ('\r' == (char)newData)
					{
						inputBuffer.append('\n');
					}
					else
					{
						inputBuffer.append((char)newData);
					}
				}
				catch (IOException ex)
				{
					System.err.println(ex);
					return;
				}
			}
			// Append received data to messageAreaIn.
			receptionString = receptionString + (new String(inputBuffer));
			log("Append received data: " + receptionString);
			break;

		// If break event append BREAK RECEIVED message.
		case SerialPortEvent.BI:
			receptionString=receptionString+("\n--- BREAK RECEIVED ---\n");
		}
	}

	/**
      * Handles ownership events. If a PORT_OWNERSHIP_REQUESTED event is
      * received a dialog box is created asking the user if they are
      * willing to give up the port. No action is taken on other types
      * of ownership events.
	  */
	public void ownershipChange(int type) {
		//if (type == CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED)
		//	PortRequestedDialog prd = new PortRequestedDialog(parent);
	}
	
	public void send(String message)
	{
		byte[] theBytes = (message + "\n").getBytes();
		for (int i=0; i<theBytes.length; i++)
		{
			char newCharacter = (char)theBytes[i];
			if ((int)newCharacter==10) newCharacter = '\r';
			try
			{
				os.write((int)newCharacter);
			}
			catch (IOException e)
			{
				log("OutputStream write error: " + e);
			}
		}
		log(message + ": Data Sent");
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