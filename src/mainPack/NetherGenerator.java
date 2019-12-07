package mainPack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

//import net.minecraft.server.v1_12_R1.BlockPosition;
//import net.minecraft.server.v1_12_R1.IBlockData;

public class NetherGenerator extends BlockPopulator {
	
	Random randor = new Random();
	
	@Override
	public void populate(World wor, Random rand, Chunk chnk) {
		
		if(rand.nextInt(main.structchance+1)==0&&main.nethstruct==true) {
		//-1 + | 1 == random pillar or shape / boulder 2 == random skeleton skull 3 == random room with stuff or random chest 4 == monsters spawner surrounded 5 == random mineshaft / tunnel 6 == spiders nest small 7 == traps
		//int typeC = rand.nextInt(8);
			int typeC = rand.nextInt(1);
		//sendCaveMessage(typeC);
		int cX = chnk.getX() * 16;
		int cZ = chnk.getZ() * 16;
		int cXOff = cX + rand.nextInt(10);
		int cZOff = cZ + rand.nextInt(10);
    	if(randor.nextInt(main.webchance+1)==0) {
    	    createWebs(cXOff, cZOff, wor);
    		createWebs(cXOff+rand.nextInt(10)-rand.nextInt(10), cZOff+rand.nextInt(10)-rand.nextInt(10), wor);
    		createWebs(cXOff+rand.nextInt(10)-rand.nextInt(10), cZOff+rand.nextInt(10)-rand.nextInt(10), wor);
    	}
    	if(randor.nextInt(main.mushchance+1)==0) {
    		createMushroom(cXOff, cZOff, wor);
    	}
    	}
	}
	
	public List<Integer> getClosestAirA(int cXOff, int cZOff, World w) {
		List<Integer> yvals = new ArrayList<Integer>();
		try {
		Location loc = new Location(w, cXOff, 20, cZOff);
		while(loc.getY()<120) {
			loc.add(0, 1, 0);
			if(loc.getBlock().getType()==Material.AIR) {
				Location loc2 = new Location(w, loc.getX(), loc.getY() + 1, loc.getZ());
				if(loc2.getBlock().getType()==Material.NETHERRACK) {
					yvals.add((int) loc.clone().getY());
				}
			}
		}
		return yvals;
		}
		catch(Exception error) {
			return null;
		}
	}
	
	public List<Integer> getClosestAirB(int cXOff, int cZOff, World w) {
		List<Integer> yvals = new ArrayList<Integer>();
		try {
		Location loc = new Location(w, cXOff, 20, cZOff);
		while(loc.getY()<120) {
			loc.add(0, 1, 0);
			if(loc.getBlock().getType()==Material.AIR) {
				Location loc2 = new Location(w, loc.getX(), loc.getY() - 1, loc.getZ());
				if(loc2.getBlock().getType()==Material.NETHERRACK) {
					yvals.add((int) loc.clone().getY());
				}
			}
		}
		return yvals;
		}
		catch(Exception error) {
			return null;
		}
	}
	
	public Material getRandStone(int define) {
		if(define == 1) {
			if(randor.nextBoolean()==true) {
				return Material.STONE;
			}
			else {
				return Material.COBBLESTONE;
			}
		}
		else {
			return Material.AIR;
		}
	}
	
	public void createWebs(int cXOff, int cZOff, World w) {
		try {
			List<Integer> yvals = getClosestAirA(cXOff, cZOff, w);
			if(yvals!=null) {
				for(int y : yvals) {
					if(randor.nextInt(2)==1) {
						int length = randor.nextInt(5)+7;
						Location l = new Location(w, cXOff, y, cZOff);
						for(int count = 0; count < length; count++) {
							if(l.getBlock().getType()!=Material.AIR) {
								break;
							}
							else {
								l.getBlock().setType(Material.COBWEB);
							}
							l.subtract(0, 1, 0);
						}
					}
				}
			}
		}
		catch(Exception error) {
			
		}
	}
	
	public void createMushroom(int cXOff, int cZOff, World w) {
		try {
			List<Integer> yvals = getClosestAirB(cXOff, cZOff, w);
			if(yvals!=null) {
				for(int y : yvals) {
					if(randor.nextInt(2)==1) {
						Location l = new Location(w, cXOff, y-1, cZOff);
						l.getBlock().setType(Material.MYCELIUM);
						if(randor.nextBoolean()==true) {
							l.add(0, 1, 0);
						Boolean b = w.generateTree(l, TreeType.BROWN_MUSHROOM);
						l.subtract(0, 1, 0).getBlock().setType(Material.NETHERRACK);
						}
						else {
							l.add(0, 1, 0);
							Boolean b =	w.generateTree(l, TreeType.RED_MUSHROOM);
							l.subtract(0, 1, 0).getBlock().setType(Material.NETHERRACK);
						}
					}
				}
			}
		}
		catch(Exception error) {
			
		}
	}
	
}
