import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NFATrace {
    public static void main(String[] args) {
        JFrame frame = new JFrame("NFATrace");
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("Input the transition table of the NFA");
        frame.add(label, BorderLayout.NORTH);

        DefaultTableModel model = new DefaultTableModel(new String[]{"From", "To", "Transition"}, 0);
        JTable table = new JTable(model);
        table.setGridColor(Color.BLACK);
        
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addRowButton = new JButton("Add Row");
        JButton submitButton = new JButton("Submit");
        buttonPanel.add(addRowButton);
        buttonPanel.add(submitButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        addRowButton.addActionListener(e -> model.addRow(new Object[]{"", "", ""}));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                StringBuilder result = new StringBuilder("Transitions:\n");
                for (int i = 0; i < model.getRowCount(); i++) {
                    String from = (String) model.getValueAt(i, 0);
                    String to = (String) model.getValueAt(i, 1);
                    String transition = (String) model.getValueAt(i, 2);
                    result.append("From: ").append(from).append(", To: ").append(to).append(", Transition: ").append(transition).append("\n");
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
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
