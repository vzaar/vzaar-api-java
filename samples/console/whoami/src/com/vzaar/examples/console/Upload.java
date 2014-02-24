package com.vzaar.examples.console;

import com.vzaar.ProgressListener;
import com.vzaar.Vzaar;
import com.vzaar.VzaarException;
import jline.ConsoleReader;
import jline.Terminal;

import javax.smartcardio.TerminalFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class Upload implements ProgressListener{
	private Terminal terminal;
    private ConsoleReader reader;
    private static long contentLength;
	public static void main(String args[]) {
		Vzaar vzaarApi;
		if (args.length == 6) {
			vzaarApi = new Vzaar(args[0], args[1]);
			String guid;
			try {
                contentLength = new File(args[2]).length();
				guid = vzaarApi.uploadVideo(args[2], new Upload());
				System.out.println("GUID - " + guid);
                System.out.println("Initiated Video Processing");
				String videoId = vzaarApi.processVideo(guid, args[3], args[4], args[5]);
				System.out.println("Video ID - " + videoId);
				System.out.println(vzaarApi.getVideoDetails(new BigInteger(videoId), false).toString());
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			printPrompt();
		}
	}

    public Upload(){
        try {
            terminal = Terminal.setupTerminal();
            reader = new ConsoleReader();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        terminal.beforeReadLine(reader, "  ", (char)0);
    }

    public void update(long completed) {
        if (terminal == null)
            return;
        int w = reader.getTermwidth();
        int progress = (int)((completed * 10) / contentLength);
        String totalStr = String.valueOf(contentLength);
        String percent = String.format("%0"+totalStr.length()+"d/%s [", completed, totalStr);
        String result = percent + repetition("=", progress)+ repetition(" ", 10 - progress) + "]";
        try {
            reader.getCursorBuffer().clearBuffer();
            reader.getCursorBuffer().write(result);
            reader.setCursorPosition(w);
            reader.redrawLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
	}

    public String repetition(String repeat, int times) {
        String retVal = new String();
        for(int i = 0; i < times; i++)
            retVal = retVal + repeat;
        return retVal;
    }
	
	public static void printPrompt() {
		System.out.print("Usage:\njava -jar upload.jar token secret video-path title description labels\n");
		System.out.print("token       -  Username of the vzaar account\n");
		System.out.print("secret      -  API application token available at http://vzaar.com/settings/api\n");
		System.out.print("video-path  -  Path of the video file to be uploaded\n");
		System.out.print("title       -  Title of the Video File\n");
		System.out.print("description -  Description of the Video File\n");
		System.out.print("labels      -  Labels (comma separated) of the Video File\n");
	}
}
