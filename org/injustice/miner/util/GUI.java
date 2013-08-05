/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.injustice.miner.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.injustice.miner.PowerMiner;
import org.injustice.miner.data.MinerMaster;
import org.injustice.miner.data.Rock;
import org.injustice.miner.data.RockOption;

/**
 *
 * @author Injustice
 */

@Deprecated
/**
 * Old version
 * @deprecated use {@link org.injustice.miner.util#MinerGUI} instead.
 */
public class GUI extends PowerMiner {

    private final String text = ("Just Mine by Injustice." + " Choose options above,"
            + " have selected ore in first slot," + " move to central tile and click start.");
    public static final JFrame frame = new JFrame();
    private final JLabel selectRock = new JLabel("Rock to mine:");
    private final JLabel whatToDo = new JLabel("What to do: ");
    private final JLabel loc = new JLabel("Location: ");
    private final JLabel instruction = new JLabel(String.format("<html><div WIDTH=%d align='center'>%s</div><html>", 190, text));
    private final JComboBox rocks = new JComboBox(new DefaultComboBoxModel(Rock.values()));
    private final JComboBox locations = new JComboBox(new DefaultComboBoxModel(MinerMaster.values()));
    private final JCheckBox hover = new JCheckBox("Hover next rock");
    private final JRadioButton bank = new JRadioButton("Bank");
    private final JRadioButton powermine = new JRadioButton("Drop");
    private final JButton start = new JButton("Start!");
    private final ButtonGroup group = new ButtonGroup();
    private final JPanel panel = new JPanel();

    public GUI() {
        init();
    }

    private final void startActionPerformed(ActionEvent e) {
        selectedRock = (Rock) rocks.getSelectedItem();
        if (bank.isSelected()) {
            master = (MinerMaster) locations.getSelectedItem();
            banking = true;
        }
        rockOption = (new RockOption(master, selectedRock, 5, hover.isSelected()));
        guiDone = true;
        frame.pack();
    }

    private final void init() {
        frame.setAlwaysOnTop(true);
        frame.setAutoRequestFocus(true);
        frame.setSize(200, 270);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setEnabled(true);
        frame.setTitle("Miner GUI");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel.setVisible(true);
        locations.setEnabled(false);

        bank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                locations.setEnabled(bank.isSelected());
            }
        });
        powermine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                locations.setEnabled(!powermine.isSelected());
            }
        });
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startActionPerformed(e);
            }
        });

        group.add(bank);
        group.add(powermine);
        powermine.setSelected(true);

        panel.add(selectRock);
        panel.add(rocks);
        panel.add(whatToDo);
        panel.add(bank);
        panel.add(powermine);
        panel.add(loc);
        panel.add(locations);
        panel.add(hover);
        panel.add(instruction);
        panel.add(start);

        frame.add(panel);
    }
}
