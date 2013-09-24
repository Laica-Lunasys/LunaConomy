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

import org.bukkit.entity.Player;

public final class MoneyController {
	private static Map<String, Integer> moneyMap = new HashMap<String, Integer>();
	
	public static void load(File par1File) {
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(par1File));
			BufferedReader br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			while(br.ready()){
				sb.append(br.readLine());
				sb.append('\n');
			}
			moneyMap = deserializeMoneyMap(sb.toString());
			br.close();
			LunaConomyCore.log.info(String.format("Loaded %d Player(s) Data.", moneyMap.size()));
		} catch (FileNotFoundException e) {
			try {
				par1File.createNewFile();
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void save(File par1File) throws IOException{
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(par1File));
			bw.write(serializeMoneyMap());
			bw.close();
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		}
	}
	
	public static int getMoney(String par1Player){
		if(!moneyMap.containsKey(par1Player)){
			moneyMap.put(par1Player, 0);
		}
		System.out.print("[DEBUG] mapdata : ");
		System.out.println(moneyMap);
		return moneyMap.get(par1Player);
	}
	
	public static int getMoney(Player par1Player){
		return getMoney(par1Player.getName());
	}
	
	public static int setMoney(Player par1Player, int par2Amount){
		moneyMap.put(par1Player.getName(), par2Amount);
		return par2Amount;
	}
	
	public static boolean containPlayer(Player par1Player){
		return moneyMap.containsKey(par1Player);
	}
	
	private static String serializeMoneyMap(){
		StringBuilder sb = new StringBuilder();
		Set<String> keySet = moneyMap.keySet();
		for(String t : keySet){
			sb.append(t);
			sb.append(":");
			sb.append(moneyMap.get(t));
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private static Map<String, Integer> deserializeMoneyMap(String par1Buf){
		Map<String, Integer> map = new HashMap<String, Integer>();
		if(par1Buf.length() < 1) return map = new HashMap<String, Integer>();
		for(String s : par1Buf.split("\n")){
			String[] data = s.split(":");
			if(data.length < 2) {
				throw new IllegalArgumentException("Invalid Mapdata");
			}
			map.put(data[0], Integer.parseInt(data[1]));
		}
		return map;
	}
}
