package spoon.test.secondaryclasses.testclasses;


public class AnonymousClass {
    public interface I {}

    private javax.swing.JButton foo;

    public void crashingMethod() {
        foo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                javax.swing.JOptionPane.showInputDialog("Boom!");
            }
        });
    }

    public void annonymousCreation() {
        new spoon.test.secondaryclasses.testclasses.AnonymousClass.I() {};
    }
}

