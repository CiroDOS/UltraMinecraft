package net.ultraminecraft.crash;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import org.sibermatica.util.StringUtils;

import net.ultraminecraft.config.Workspace;

public class CrashReport {

	public final Throwable cause;

	public CrashReport(final Throwable cause) {
		this.cause = cause;
	}

	public String dumpStackTrace() {
		String[] commentaries = new String[] { "Oops...", "Oh, that is", "F my bro", "Guaranted", "I did what?",
				"Mojang Quality System", "Help on stackoverflow.com" };

		StringBuilder stackTrace = new StringBuilder();

		stackTrace.append("--- ULTRAMINECRAFT CRASH REPORT BEGIN ---");
		stackTrace.append("\n");
		stackTrace.append("// " + commentaries[(int) (Math.random() * 10) % commentaries.length]);
		stackTrace.append("\n");
		stackTrace.append(this.cause.getStackTrace()[0].toString());
		stackTrace.append("\n");
		stackTrace.append("--- ULTRAMINECRAFT CRASH REPORT END ---");

		return stackTrace.toString();
	}

	public void dumpInFile() {
		if (!Workspace.isInitializated())
			throw new IllegalStateException("workspace should be initializated");

		String filepath = "crash-reports\\crash-" + ((Integer) (Calendar.getInstance().get(Calendar.YEAR))).toString()
				+ "-" + Calendar.getInstance().get(Calendar.MONTH) + "-"
				+ Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "_" + Calendar.getInstance().get(Calendar.HOUR)
				+ "-" + Calendar.getInstance().get(Calendar.MINUTE) + "-" + Calendar.getInstance().get(Calendar.SECOND);
		try {

			new File(filepath).createNewFile();
			FileOutputStream fileStream = new FileOutputStream(filepath);

			fileStream.write(this.dumpStackTrace().getBytes(StandardCharsets.UTF_8));

			fileStream.close(); // Closes the filestream

		} catch (Exception ignored) {
			System.out.println("cannot write or create crash-file");
			System.out.println("Halting Game!");
			System.exit(-1);
		}
	}

	public static void launchCorruptionMessage() {
		StringBuilder message = new StringBuilder("\n\n\n\n\n\n\n");

		message.append("----------->>>>>>>>>>>>>>--SIBERMATICA---PROTECTION---PROGRAM--<<<<<<<<<<<<<<-----------");
		message.append("  ¡IF YOU RECEIVE THIS MESSAGE YOUR MINECRAFT AND JAVA ARE GETTING SEVERALLY CORRUPTED!\n");
		message.append("  THE PROGRAM WILL EXIT INMEDIATLY.                 ¡SAVE YOURSELF!\n");
		message.append('\n');
		message.append("                               ¡DON'T LET HIM ESCAPE!\n");
		message.append('\n');
		message.append("  DANGER CODE: 0x" + StringUtils.padStart(Integer.toHexString(666), 8, "0") + '\n');
		message.append("----------->>>>>>>>>>>>>>--SIBERMATICA---PROTECTION---PROGRAM--<<<<<<<<<<<<<<-----------");

		message.append('\n');
		System.err.println(message.toString());

		new IllegalStateException().printStackTrace();
		System.exit(-2);
	}

}
