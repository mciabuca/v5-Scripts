/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.miner.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import org.injustice.miner.PowerMiner;
import org.injustice.miner.data.MinerMaster;
import org.injustice.miner.data.Rock;
import org.injustice.miner.data.RockOption;

/**
 *@author Overflow
 */
public class MinerGUI extends JFrame {

    private final JComboBox<Rock> rockCombo;
    private final JCheckBox bankCheckBox;
    private final JComboBox<MinerMaster> locationCombo;
    private final JCheckBox hoverCheckBox;
    private final JPanel centrePanel;
    private final String text = ("Choose options above,"
            + " have selected ore in first slot," + " move to central tile and click start.");
    private final JLabel instructions;
    private final JButton startButton;

    public MinerGUI(final PowerMiner scriptInstance) {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Miner GUI");
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setAutoRequestFocus(true);
        centrePanel = new JPanel(new GridLayout(4, 2));
        centrePanel.add(new JLabel("Select Rock:"));
        centrePanel.add(rockCombo = new JComboBox<>(Rock.values()));
        centrePanel.add(new JLabel("Bank: "));
        centrePanel.add(bankCheckBox = new JCheckBox() {
            {
                addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        locationCombo.setEnabled(bankCheckBox.isSelected());
                    }
                });
            }
        });
        centrePanel.add(new JLabel("Location:"));
        centrePanel.add(locationCombo = new JComboBox<MinerMaster>(MinerMaster.values()) {
            {
                setEnabled(false);
                setToolTipText("The banking checkbox must be selected to enable this option");
            }
        });
        centrePanel.add(new JLabel("Hover next Rock: "));
        centrePanel.add(hoverCheckBox = new JCheckBox());
        this.add(centrePanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        instructions = new JLabel(String.format("<html><div WIDTH=%d align='center'>%s</div><html>", 190, text));
        bottomPanel.add(instructions);
        bottomPanel.add(startButton = new JButton("Start") {
            {
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        scriptInstance.selectedRock = (Rock) rockCombo.getSelectedItem();
                        if (scriptInstance.banking = bankCheckBox.isSelected()) {
                            scriptInstance.master = (MinerMaster) locationCombo.getSelectedItem();
                        }
                        scriptInstance.rockOption = (new RockOption(scriptInstance.master, scriptInstance.selectedRock, 5, hoverCheckBox.isSelected()));
                        scriptInstance.guiDone = true;
                        dispose();
                    }
                });
            }
        });
        this.add(bottomPanel, BorderLayout.SOUTH);
        pack();
    }
}
