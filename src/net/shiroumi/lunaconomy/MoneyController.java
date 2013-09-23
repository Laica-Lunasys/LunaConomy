package net.shiroumi.lunaconomy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.shiroumi.lunaconomy.Util.Util;

import org.bukkit.entity.Player;

public final class MoneyController {
	private static Map<Player, Integer> moneyMap = new HashMap<Player, Integer>();
	
	public static void load(File par1File) {
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(par1File));
			BufferedReader br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			while(br.ready()){
				sb.append(br.readLine());
			}
			moneyMap = deserializeMoneyMap(sb.toString());
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void save(File par1File){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(par1File));
			bw.write(serializeMoneyMap());
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String serializeMoneyMap(){
		StringBuilder sb = new StringBuilder();
		Set<Player> keySet = moneyMap.keySet();
		for(Player t : keySet){
			sb.append(t.getName());
			sb.append(":");
			sb.append(moneyMap.get(t));
			sb.append("\n");
		}
		return sb.toString();
	}
	private static Map<Player, Integer> deserializeMoneyMap(String par1Buf){
		Map<Player, Integer> map = new HashMap<Player, Integer>();
		for(String s : par1Buf.split("\n")){
			String[] data = s.split(":");
			if(data.length < 2) {
				throw new IllegalArgumentException("Invalid Mapdata");
			}
			map.put(Util.findPlayer(data[0], LunaConomyCore.getInstance()), Integer.parseInt(data[1]));
		}
		return map;
	}
}
