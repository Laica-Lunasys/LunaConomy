package net.shiroumi.lunaconomy.internal.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import net.shiroumi.lunaconomy.internal.MCCom;
import net.shiroumi.lunaconomy.internal.gui.GUI;

/**
 * Handles delete button action.
 * 
 * @author Laica-Lunasys (Origin by MjolnirCommando)
 */
public class DeleteListener implements ActionListener
{

	public void actionPerformed(ActionEvent e)
	{
	    String account = GUI.selectedAccount;
	
		int result = JOptionPane.showConfirmDialog(GUI.window,
				"Are you sure you want to delete account \"" + account + "\"?",
				"Confirm Deletion", JOptionPane.YES_NO_OPTION);

		if (result == JOptionPane.YES_OPTION)
		{
		    MCCom.delete(account);
			GUI.reloadAccounts(true);
		}
	}

}