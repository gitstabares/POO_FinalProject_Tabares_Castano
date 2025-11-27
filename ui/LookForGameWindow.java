package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.List;
import domain.Store;
import domain.Videogame;

public class LookForGameWindow extends JPanel {
	private JTextField txtSearch;
	private JComboBox<String> cmbType;
	private JTable resultTable;
	private DefaultTableModel tableModel;
	private JButton btnEditPrice;
	private JButton btnEditScore;

	public LookForGameWindow() {
		setLayout(new BorderLayout(8, 8));
		setBackground(Theme.BACKGROUND_COLOR);
		setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		initComponents();
		initEvents();
	}

	private void initComponents() {
		// Top panel for search fields (combo + realtime text)
		JPanel top = new JPanel(new BorderLayout(6, 6));
		top.setBackground(Theme.BACKGROUND_COLOR);
		JPanel searchType = new JPanel(new FlowLayout(FlowLayout.LEFT));
		searchType.setBackground(Theme.BACKGROUND_COLOR);
		cmbType = new JComboBox<>(new String[]{"Title", "Genre"});
		txtSearch = new JTextField();
		txtSearch.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		JLabel lbl = new JLabel("Look by videogame:");
		lbl.setForeground(Theme.TEXTFIELD_TEXT_COLOR);
		searchType.add(lbl);
		searchType.add(cmbType);
		top.add(searchType, BorderLayout.WEST);
		top.add(txtSearch, BorderLayout.CENTER);

		add(top, BorderLayout.NORTH);

		// Table for results
		String[] columns = {"Title", "Genre", "Price", "Score"};
		tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		resultTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(resultTable);
		add(scrollPane, BorderLayout.CENTER);

		// Bottom actions (edit price / score)
		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bottom.setBackground(Theme.BACKGROUND_COLOR);
		btnEditPrice = new JButton("Edit price");
		btnEditPrice.setEnabled(false);
		btnEditScore = new JButton("Edit score");
		btnEditScore.setEnabled(false);
		btnEditPrice.setBackground(Theme.BUTTON_COLOR);
		btnEditPrice.setForeground(Theme.BUTTON_TEXT_COLOR);
		btnEditScore.setBackground(new Color(80,130,70));
		btnEditScore.setForeground(Theme.BUTTON_TEXT_COLOR);
		btnEditPrice.setFocusPainted(false);
		btnEditScore.setFocusPainted(false);
		bottom.add(btnEditPrice);
		bottom.add(btnEditScore);
		add(bottom, BorderLayout.SOUTH);

		Theme.applyFontOnFrame(this, Font.PLAIN, 16f);
	}

	private void initEvents() {
		// Real-time search on typing
		txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			public void insertUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
			public void removeUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
			public void changedUpdate(javax.swing.event.DocumentEvent e) { performSearch(); }
		});

		// Enable buttons when a row is selected
		resultTable.getSelectionModel().addListSelectionListener(e -> {
			boolean sel = resultTable.getSelectedRow() != -1;
			btnEditPrice.setEnabled(sel);
			btnEditScore.setEnabled(sel);
		});

		// Button actions
		btnEditPrice.addActionListener(ev -> {
			int row = resultTable.getSelectedRow();
			if (row == -1) return;
			String title = tableModel.getValueAt(row, 0).toString();
			Videogame g = Store.lookGameByTitle(title);
			if (g == null) return;
			String in = JOptionPane.showInputDialog(SwingUtilities.getWindowAncestor(LookForGameWindow.this), "New Price ($COP):", String.valueOf(g.getPrice()));
			if (in == null) return;
			in = in.trim();
			try {
				float p = Float.parseFloat(in);
				if (p < 0) throw new NumberFormatException();
				g.setPrice(p);
				performSearch();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(LookForGameWindow.this), "Invalid Price. It must be more than zero.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnEditScore.addActionListener(ev -> {
			int row = resultTable.getSelectedRow();
			if (row == -1) return;
			String title = tableModel.getValueAt(row, 0).toString();
			Videogame g = Store.lookGameByTitle(title);
			if (g == null) return;
			String current = g.getScore() > 0 ? String.valueOf(g.getScore()) : "";
			String in = JOptionPane.showInputDialog(SwingUtilities.getWindowAncestor(LookForGameWindow.this), "New score (0-5):", current);
			if (in == null) return;
			in = in.trim();
			try {
				int s = Integer.parseInt(in);
				if (s < 0 || s > 5) throw new NumberFormatException();
				g.setScore(s);
				performSearch();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(LookForGameWindow.this), "Invalid score. It must be between 0 and 5.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void performSearch() {
		String f = txtSearch.getText().trim().toLowerCase();
		List<Videogame> results;
		String type = (String) cmbType.getSelectedItem();

		if (f.isEmpty()) {
			results = Store.getVideoGames();
			updateTable(results);
			return;
		}

		if ("Title".equalsIgnoreCase(type)) {
			results = Store.lookGamesByTitle(f);
		} else if ("Genre".equalsIgnoreCase(type)) {
			results = Store.lookGamesByGenre(f);
		} else {
            results = Store.getVideoGames();
        }
		updateTable(results);
	}

	private void updateTable(List<Videogame> games) {
		tableModel.setRowCount(0);
		for (Videogame game : games) {
			Object[] row = {
				game.getTitle(),
				game.getGenre(),
				game.getPrice(),
				(game.getScore() > 0 ? game.getScore() : "-")
			};
			tableModel.addRow(row);
		}
	}
}
