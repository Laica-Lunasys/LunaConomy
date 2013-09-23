package net.shiroumi.lunaconomy.internal.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.shiroumi.lunaconomy.internal.MCCom;
import net.shiroumi.lunaconomy.internal.gui.GUI;

/**
 * Handles give button action.
 * 
 * @author Laica-Lunasys (Origin by MjolnirCommando)
 */
public class GiveListener implements ActionListener
{

	public void actionPerformed(ActionEvent e)
	{
		try
		{
			double amount = Double.parseDouble(GUI.amount.getText());
			amount = Math.abs(amount);
			MCCom.setBalance(
					GUI.selectedAccount,
					MCCom.getBalance(GUI.selectedAccount) + amount);
			if (MCCom.getBalance(GUI.selectedAccount) >= 0)
			{
				GUI.balance
						.setText("<html><center>Balance: <span style=\"color:green;\">"
								+ MCCom.getBalance(GUI.selectedAccount)
								+ "</span> "
								+ MCCom.getAccountCurrency(GUI.selectedAccount) + "</center></html>");
			}
			else
			{
				GUI.balance
						.setText("<html><center>Balance: <span style=\"color:red;\">"
								+ MCCom.getBalance(GUI.selectedAccount)
								+ "</span> "
								+ MCCom.getAccountCurrency(GUI.selectedAccount) + "</center></html>");
			}
			GUI.balance.revalidate();
			GUI.reloadAccounts(false);
			GUI.title
					.setText("<html><center>Control Panel<br><br><br></center></html>");
		}
		catch (NumberFormatException e1)
		{
			GUI.title
					.setText("<html><center>Control Panel<br><span style=\"color:red;\">Amount must be a number.</span><br><br></center></html>");
		}
	}

}
