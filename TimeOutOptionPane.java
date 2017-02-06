/**
 * @author Steven Sams
 * Dialog with timeout on 'OK' button to prevent blocking interface
 */

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class TimeOutOptionPane {

    final static int PRESET_TIME = 15;

    private TimeOutOptionPane() {
    }

    public static void showMessageDialog(Component parentComponent, Object message, String title, int optionType, int messageType) {

        JOptionPane pane = new JOptionPane(message, messageType, optionType, null);
        pane.setMessageType(messageType);
        JPanel buttonPanel = (JPanel) pane.getComponent(1);
        final JButton buttonOk = (JButton) buttonPanel.getComponent(0);
        final JDialog dialog = pane.createDialog(parentComponent, title);
        buttonOk.setText("OK [" + PRESET_TIME + "]");
        new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = PRESET_TIME; i >= 0; i--) {
                        Thread.sleep(1000);
                        if (dialog.isVisible() && i < PRESET_TIME) {
                            buttonOk.setText("OK [" + i + "]");
                        }
                    }
                    if (dialog.isVisible()) {
                        dialog.setVisible(false);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                } finally {
                    dialog.dispose();
                }
            }
        }.start();
        dialog.setVisible(true);
    }

    public static void main(String args[]) {
        TimeOutOptionPane.showMessageDialog(null, "Message body", "Title", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }
}