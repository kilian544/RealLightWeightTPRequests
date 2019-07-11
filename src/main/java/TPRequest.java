import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class TPRequest {

    private Player requestee;
    private Player requested;
    private long expiration;

    public TPRequest(Player requestee, Player requested){
        this.requestee = requestee;
        requestee.sendMessage(ChatColor.GREEN + "You requested to teleport to: " + requested.getDisplayName() + ".");
        this.requested = requested;
        requested.sendMessage(ChatColor.GREEN + WordUtils.capitalize(requestee.getDisplayName()) + " requested to teleport to you.");
        requested.playSound(requested.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1, .1f);
        this.expiration = System.currentTimeMillis() + 1000 * 30;
    }

    public boolean accept(Player player){

        if (!isRequested(player)){
            return false;
        }

        if (!this.requestee.isOnline()){
            requested.sendMessage(ChatColor.RED + WordUtils.capitalize(requestee.getDisplayName()) + " is no longer online.");
            return true;
        }

        if (this.expired()){
            requested.sendMessage(ChatColor.RED + "The TPRequest has expired.");
            return true;
        }

        requestee.teleport(requested);
        requested.sendMessage(ChatColor.GREEN + WordUtils.capitalize(requestee.getDisplayName()) + " has been teleported to you!");
        requestee.sendMessage(ChatColor.GREEN + "Teleporting to " + WordUtils.capitalize(requested.getDisplayName()) + "...");
        requested.playSound(requested.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1, 1);
        requestee.playSound(requestee.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1, 1);
        return true;
    }

    public boolean deny(Player player){

        if (!isRequested(player)){
            return false;
        }

        if (!this.requestee.isOnline()){
            requested.sendMessage(ChatColor.RED + WordUtils.capitalize(requestee.getDisplayName()) + " is no longer online.");
            return true;
        }

        if (this.expired()){
            requested.sendMessage(ChatColor.RED + "The TPRequest has expired.");
            return true;
        }

        requestee.sendMessage(ChatColor.RED + WordUtils.capitalize(requested.getDisplayName()) + " has denied your TPRequest.");
        requested.sendMessage(ChatColor.GREEN + "Denied " + WordUtils.capitalize(requestee.getDisplayName()) + "'s TPRequest.");
        return true;
    }

    public boolean expired(){
        return this.expiration < System.currentTimeMillis();
    }

    private boolean isRequested(Player player){
        return this.requested.getUniqueId().equals(player.getUniqueId());
    }

}
