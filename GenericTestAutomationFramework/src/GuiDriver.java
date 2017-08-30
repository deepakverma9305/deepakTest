
public class GuiDriver {

	public static void main(String[] args) {
		for ( int i = 0 ; i < 4 ; i ++){
			Runnable r = new Test1();
			Thread t = new Thread(r);
			t.start();
		}
	}	
}
