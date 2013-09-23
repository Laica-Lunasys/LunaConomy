package net.shiroumi.lunaconomy.internal.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.shiroumi.lunaconomy.exceptions.AccountNameConflictException;
import net.shiroumi.lunaconomy.internal.MCCom;
import net.shiroumi.lunaconomy.internal.gui.GUI;

/**
 * Handles create button action.
 * 
 * @author Laica-Lunasys (Origin by MjolnirCommando)
 */
public class CreateListener implements ActionListener
{

	public void actionPerformed(ActionEvent e)
	{
	    try
        {
	        String newaccount = GUI.newaccount.getText();
            MCCom.create(newaccount);
            GUI.reloadAccounts(true);
            GUI.title.setText("<html><center>Control Panel<br><span style=\"color:green;\">Account created successfully.</span><br><br></center></html>");
        }
        catch (AccountNameConflictException e1)
        {
            GUI.title.setText("<html><center>Control Panel<br><span style=\"color:red;\">Account already exists.</span><br><br></center></html>");
        }
	}

}
