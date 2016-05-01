package me.gET.sv.MinigameBase.game;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import me.gET.sv.MinigameBase.main.Arena;

public class GameListener implements Listener {

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e){
		if(Arena.getArena(e.getPlayer()) != null){
			Arena.getArena(e.getPlayer()).removePlayer(e.getPlayer());
		}
	}
	
}
