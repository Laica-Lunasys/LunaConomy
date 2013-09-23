package net.shiroumi.lunaconomy.internal.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.shiroumi.lunaconomy.LunaConomy;
import net.shiroumi.lunaconomy.internal.util.IOH;

/**
 * Handles refresh button action.
 * 
 * @author Laica-Lunasys (Origin by MjolnirCommando)
 */
public class Refresh2Listener implements ActionListener
{

	public void actionPerformed(ActionEvent e)
	{
	    IOH.log("Reloading LunaConomy...", IOH.IMPORTANT);
	    LunaConomy.reload();
	    IOH.log("Successfully Reloaded LunaConomy!", IOH.IMPORTANT);
	}

}
