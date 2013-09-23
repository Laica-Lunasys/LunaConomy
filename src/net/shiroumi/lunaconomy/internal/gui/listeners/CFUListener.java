package net.shiroumi.lunaconomy.internal.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import net.shiroumi.lunaconomy.internal.gui.GUI;
import net.shiroumi.lunaconomy.internal.util.Update;

/**
 * Handles Check For Updates button action.
 * 
 * @author Laica-Lunasys (Origin by MjolnirCommando)
 */
public class CFUListener implements ActionListener
{
    public void actionPerformed(ActionEvent e)
	{
	    if (Update.check())
		{
			JOptionPane.showMessageDialog(GUI.window,
					"<html><center>An update to LunaConomy Version "
							+ Update.updateversion.replace("-", ".")
							+ " is available. Go to<br>"
							+ "<a href=\"http://dev.bukkit.org/server-mods/LunaConomy\">http://dev.bukkit.org/server-mods/LunaConomy</a>"
							+ "<br>to download the update!</center></html>",
					"Update Check", JOptionPane.PLAIN_MESSAGE);
		}
		else
		{
			JOptionPane
					.showMessageDialog(
							GUI.window,
							"<html>No updates can be found. <br>You have the latest version of LunaConomy!</html>",
							"Update Check", JOptionPane.PLAIN_MESSAGE);
		}
	}

}
