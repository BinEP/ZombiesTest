package guns;

public class DropItem {

	private boolean ammo = false;
	private boolean isNewGun = true;
	private Gun gun;
	private String name;
	
	public DropItem(String name, Gun gun) {
		this.gun = gun;
		this.name = name;
	}
	
	public DropItem(boolean ammo) {
		this.ammo = ammo;
		this.name = "Ammo";
	}
	
	public boolean isNewGun() {
		return isNewGun;
	}
	
	public boolean isMoreAmmo() {
		return ammo;
	}
	
	public Gun getGun() {
		return gun;
	}
	
	public String getName() {
		return name;
	}
	
}
