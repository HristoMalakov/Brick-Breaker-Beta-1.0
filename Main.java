import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame JFobj = new JFrame();
        Gameplay GMobj = new Gameplay();
        JFobj.setBounds(0,0,700,600);
        JFobj.setResizable(false);
        JFobj.setVisible(true);
        JFobj.setTitle("Brick Breaker JFX90");
        JFobj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFobj.add(GMobj);
    }
}
