import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NFATrace {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("NFATrace");
        frame.setLayout(new BorderLayout());

        // Add a label to the top of the frame
        JLabel label = new JLabel("Input the transition table of the NFA");
        frame.add(label, BorderLayout.NORTH);

        // Create a table to input the transition table
        DefaultTableModel model = new DefaultTableModel(new String[]{"From", "To", "Transition"}, 0);
        JTable table = new JTable(model);
        table.setGridColor(Color.BLACK);        

        // Add the table to a scroll pane and add it to the frame
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();        
        JButton addRowButton = new JButton("Add Row");
        JButton submitButton = new JButton("Submit");

        // Add a checkbox to enable/disable the label column
        JCheckBox labelCheckBox = new JCheckBox("Enable Label Column");

        // Add the buttons to the button panel and add the button panel to the frame
        buttonPanel.add(addRowButton);
        buttonPanel.add(submitButton);
        buttonPanel.add(labelCheckBox);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add an action listener to the add row button to add a new row to the table
        addRowButton.addActionListener(e -> model.addRow(new Object[]{"", "", ""}));

        // Add an action listener to the label checkbox to add/remove the label column
        labelCheckBox.addActionListener(e -> {
            if (labelCheckBox.isSelected()) {
                stopCellEditing(table);
                model.addColumn("Label");
            } else {
                stopCellEditing(table);
                model.setColumnCount(3);
            }
        });

        // Add an action listener to the submit button to display the input data
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopCellEditing(table);
                StringBuilder result = new StringBuilder("Transitions:\n");
                for (int i = 0; i < model.getRowCount(); i++) {
                    String from = (String) model.getValueAt(i, 0);
                    String to = (String) model.getValueAt(i, 1);
                    String transition = (String) model.getValueAt(i, 2);
                    String label = labelCheckBox.isSelected() ? (String) model.getValueAt(i, 3) : "";

                    if (from.isEmpty() && to.isEmpty() && transition.isEmpty()) {
                        continue;
                    } else if (from.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "From state cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else if (to.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "To state cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else if (transition.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Transition cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    result.append("From: ").append(from).append(", To: ").append(to).append(", Transition: ").append(transition);
                    if (labelCheckBox.isSelected()) {
                        result.append(", Label: ").append(label);
                    }
                    result.append("\n");
                }
                JOptionPane.showMessageDialog(frame, result.toString(), "Input Data", JOptionPane.INFORMATION_MESSAGE);
                JDialog nfaDialog = new JDialog(frame, "NFA Visualization", true);
                NFADisplayPanel displayPanel = new NFADisplayPanel();
                displayPanel.setPreferredSize(new Dimension(400, 300));

                nfaDialog.add(displayPanel);
                nfaDialog.pack();
                nfaDialog.setLocationRelativeTo(frame);
                nfaDialog.setVisible(true);
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setVisible(true);
    }

    // Stop cell editing if the table is currently editing
    private static void stopCellEditing(JTable table) {
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }
    }
}
