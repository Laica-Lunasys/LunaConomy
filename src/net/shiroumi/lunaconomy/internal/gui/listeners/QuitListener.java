package net.shiroumi.lunaconomy.internal.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.shiroumi.lunaconomy.internal.gui.GUI;

import org.bukkit.Bukkit;

/**
 * Handles quit button action.
 * 
 * @author Laica-Lunasys (Origin by MjolnirCommando)
 */
public class QuitListener implements ActionListener
{

	public void actionPerformed(ActionEvent e)
	{
		GUI.window.setVisible(false);
		Bukkit.getServer().shutdown();
	}

}