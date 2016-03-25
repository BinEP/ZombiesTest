package guns;

public class Automatic extends Gun {
	
	public Automatic() {
		super("AK-47", 160, 40, true, 1500, 200, 100);
	}
	
	@Override
	public int getGunDelay() {
		
		this.firingTimer.setRepeats(true);
		// TODO Auto-generated method stub
		return super.getGunDelay();
	}
}
