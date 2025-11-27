package ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import domain.Sale;
import domain.Store;

public class LookForSaleWindow extends JPanel {
    private JTextField txtSearch;
	private JComboBox<String> cmbType;
	private JTable resultTable;
	private DefaultTableModel tableModel;

	public LookForSaleWindow() {
		setLayout(new BorderLayout(8, 8));
		setBackground(Theme.BACKGROUND_COLOR);
		setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		// Top panel for search fields (combo + realtime text)
		JPanel top = new JPanel(new BorderLayout(6, 6));
		top.setBackground(Theme.BACKGROUND_COLOR);
		JPanel searchType = new JPanel(new FlowLayout(FlowLayout.LEFT));
		searchType.setBackground(Theme.BACKGROUND_COLOR);
		cmbType = new JComboBox<>(new String[]{"ID", "Client", "Date"});
		txtSearch = new JTextField();
		txtSearch.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		JLabel lbl = new JLabel("Search for sale:");
		lbl.setForeground(Theme.TEXTFIELD_TEXT_COLOR);
		searchType.add(lbl);
		searchType.add(cmbType);
		top.add(searchType, BorderLayout.WEST);
		top.add(txtSearch, BorderLayout.CENTER);

		// Search button to trigger lookup on demand
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(evt -> performSearch());
		btnSearch.setBackground(Theme.BUTTON_COLOR);
		btnSearch.setForeground(Theme.BUTTON_TEXT_COLOR);
		top.add(btnSearch, BorderLayout.EAST);

		add(top, BorderLayout.NORTH);

		// Table for results
		String[] columns = {"ID","Date","Purchase"};
		tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		resultTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(resultTable);
		add(scrollPane, BorderLayout.CENTER);

		Theme.applyFontOnFrame(this, Font.PLAIN, 16f);

		// Search is triggered by the button now (no real-time updates)

	}

	private void performSearch() {
		String f = txtSearch.getText().trim().toLowerCase();
		List<Sale> results;
		String type = (String) cmbType.getSelectedItem();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");

		if (f.isEmpty()) {
			results = Store.getSales();
			updateTable(results);
			return;
		}

		if ("ID".equalsIgnoreCase(type)) {
			results = new ArrayList<>();
			results.add(Store.lookSalesById(Integer.parseInt(f)));
		} else if ("Date".equalsIgnoreCase(type)) {
			results = Store.lookSalesByDate(LocalDate.parse(f, formatter));
		} else if ("Client".equalsIgnoreCase(type)) {
            results = Store.lookSalesByClient(Store.lookCustomersByName(f));
        } else {
            results = Store.getSales();
        }
		updateTable(results);
	}

	private void updateTable(List<Sale> sales) {
		tableModel.setRowCount(0);
		for (Sale sale : sales) {
			Object[] row = {
				sale.getId(),
				sale.getDate(),
				sale.getPurchase(),
                sale.getCost()
			};
			tableModel.addRow(row);
		}
	}
}