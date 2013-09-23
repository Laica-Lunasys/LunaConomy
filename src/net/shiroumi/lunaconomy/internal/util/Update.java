package net.shiroumi.lunaconomy.internal.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import net.shiroumi.lunaconomy.LunaConomy;

/**
 * Handles update checks and update downloading.
 * 
 * @author Laica-Lunasys (Origin by MjolnirCommando)
 */
public class Update
{
	/**
	 * Holds the new update version.
	 */
	public static String	updateversion;

	/**
	 * Returns true if update is available.
	 * 
	 * @return boolean
	 */
	public static boolean check()
	{
		try
		{
			URL url = new URL("http://v.mjcraft.com/LunaConomy");
		    
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			updateversion = in.readLine();
			in.close();

			return Version.compare(new Version(updateversion), new Version(LunaConomy.getVersion())) == Version.NEWER;
		}
		catch (IOException e)
		{
		    IOH.error("IOException", e);
		    return false;
		}
	}
}
