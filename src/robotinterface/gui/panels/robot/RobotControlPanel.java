/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package robotinterface.gui.panels.robot;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import robotinterface.robot.Robot;
import robotinterface.robot.connection.Connection;
import robotinterface.robot.connection.Serial;
import robotinterface.robot.device.Compass;
import robotinterface.robot.device.HBridge;
import robotinterface.robot.device.IRProximitySensor;
import robotinterface.robot.device.ReflectanceSensorArray;
import robotinterface.robot.simulation.VirtualConnection;

/**
 *
 * @author antunes
 */
public class RobotControlPanel extends javax.swing.JPanel {

    public static final String VIRTUAL_CONNECTION = "Virtual";
    public static int INSTANCE = 0;
    private TitledBorder border;
    private Serial serial;
    private Connection connection = null;
    private RobotManager robotManager;
    private boolean connected = false;
    private Robot robot;
    
    public RobotControlPanel(RobotManager rm) {
        INSTANCE++;
        serial = new Serial(57600);
        robot = new Robot();
        robot.add(new HBridge(1));
        robot.add(new Compass());
        robot.add(new IRProximitySensor());
        robot.add(new ReflectanceSensorArray());
        
        initComponents();
        border = javax.swing.BorderFactory.createTitledBorder("Robô " + INSTANCE);
        setBorder(border);
        refreshButtonActionPerformed(null);
        robotManager = rm;
    }
    
    public Robot getRobot() {
        return robot;
    }

    @Override
    public String toString() {
         if (border == null){
            return super.getName();
        } else {
            return border.getTitle();
        }
    }
    
    public void refresh(){
        deviceComboBox.removeAllItems();
        deviceComboBox.addItem(VIRTUAL_CONNECTION);
        for (String str : serial.getAvailableDevices()) {
            deviceComboBox.addItem(str);
        }
    }
    
    public void setConnection(int index){
        deviceComboBox.setSelectedIndex(index);
    }
    
    public boolean tryConnect (){
        String str = (String) deviceComboBox.getSelectedItem();
        if (str.equals(VIRTUAL_CONNECTION)){
            connection = new VirtualConnection();
        } else {
            serial.setDefaultPort(str);
            connection = new VirtualConnection(serial);
        }
        connected = connection.establishConnection();
        statusLabel.setText("Conectando");
        statusLabel.setForeground(Color.gray);
        if (connected) {
            statusLabel.setForeground(Color.green);
            statusLabel.setText("Conectado");
            
            connectButton.setForeground(Color.red);
            connectButton.setText("Desconectar");
        } else {
            statusLabel.setForeground(Color.red);
            statusLabel.setText("Falha");
            return false;
        }
        
        robot.add(connection);
        
        if (connection instanceof VirtualConnection) {
            VirtualConnection v = (VirtualConnection) connection;
            v.setRobot(robot);
        }
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        deviceComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        connectButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        setBorder(null);

        jLabel1.setText("Conexão:");

        deviceComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Status:");

        statusLabel.setText("Conectado");

        connectButton.setText("Conectar");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/tango/16x16/actions/view-refresh.png"))); // NOI18N
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        removeButton.setText("Remover");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(deviceComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(connectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(removeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(statusLabel)
                            .addComponent(jLabel4))))
                .addGap(0, 30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deviceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(connectButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeButton))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        int val = JOptionPane.showConfirmDialog(null, "Deseja realmente remover o robô " + "X" + "?");
        if (val == JOptionPane.YES_OPTION) {
            robotManager.remove(this);
            robotManager.updateUI();
            INSTANCE--;
        }
    }//GEN-LAST:event_removeButtonActionPerformed

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        tryConnect();
    }//GEN-LAST:event_connectButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connectButton;
    private javax.swing.JComboBox deviceComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables

}
