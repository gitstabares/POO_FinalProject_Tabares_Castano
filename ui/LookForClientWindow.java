package ui;

import domain.Customer;
import domain.Store;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LookForClientWindow extends JPanel {

	private JTextField txtSearch;
	private JComboBox<String> cmbType;
	private JList<Customer> lstResults;
	private DefaultListModel<Customer> model;
	private JButton btnEditName;
	private JButton btnMakeSale;
	private JButton btnAddCustomer;

	public LookForClientWindow() {
		initComponents();
		initEvents();
		updateList("");
	}

	private void initComponents() {
		setLayout(new BorderLayout(8,8));
		setBackground(Theme.BACKGROUND_COLOR);
		setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

		JPanel top = new JPanel(new BorderLayout(6,6));
		top.setBackground(Theme.BACKGROUND_COLOR);
		JPanel searchType = new JPanel(new FlowLayout(FlowLayout.LEFT));
		searchType.setBackground(Theme.BACKGROUND_COLOR);
		cmbType = new JComboBox<>(new String[]{"ID","Name"});
		txtSearch = new JTextField();
		txtSearch.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
		JLabel lbl = new JLabel("Look for client:");
		lbl.setForeground(Theme.TEXTFIELD_TEXT_COLOR);
		searchType.add(lbl);
		searchType.add(cmbType);
		top.add(searchType, BorderLayout.WEST);
		top.add(txtSearch, BorderLayout.CENTER);

		model = new DefaultListModel<>();
		lstResults = new JList<>(model);
		lstResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstResults.setCellRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel comp = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Customer) {
					Customer c = (Customer) value;
					comp.setText(c.getId() + " — " + c.getName());
				}
				comp.setForeground(Theme.TEXTFIELD_TEXT_COLOR);
				return comp;
			}
		});

		JScrollPane sc = new JScrollPane(lstResults);

		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bottom.setBackground(Theme.BACKGROUND_COLOR);
		btnEditName = new JButton("Edit name");
		btnEditName.setEnabled(false);
		btnMakeSale = new JButton("Sell");
		btnMakeSale.setEnabled(false);
			btnAddCustomer = new JButton("Add client");
			btnAddCustomer.setBackground(Theme.BUTTON_COLOR);
			btnAddCustomer.setForeground(Theme.BUTTON_TEXT_COLOR);
			btnAddCustomer.setFocusPainted(false);
		btnEditName.setBackground(Theme.BUTTON_COLOR);
		btnEditName.setForeground(Theme.BUTTON_TEXT_COLOR);
		btnMakeSale.setBackground(new Color(80,130,70));
		btnMakeSale.setForeground(Theme.BUTTON_TEXT_COLOR);
		btnEditName.setFocusPainted(false);
		btnMakeSale.setFocusPainted(false);
			bottom.add(btnAddCustomer);
			bottom.add(btnEditName);
			bottom.add(btnMakeSale);

		add(top, BorderLayout.NORTH);
		add(sc, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

		Theme.applyFontOnFrame(this, Font.PLAIN, 16f);

	}

	private void initEvents() {
		txtSearch.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) { updateList(txtSearch.getText()); }
			public void removeUpdate(DocumentEvent e) { updateList(txtSearch.getText()); }
			public void changedUpdate(DocumentEvent e) { updateList(txtSearch.getText()); }
		});

		lstResults.addListSelectionListener(e -> {
			boolean sel = !lstResults.isSelectionEmpty();
			btnEditName.setEnabled(sel);
			btnMakeSale.setEnabled(sel);
		});

		btnEditName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Customer sel = lstResults.getSelectedValue();
				if (sel == null) return;
				String newName = JOptionPane.showInputDialog(SwingUtilities.getWindowAncestor(LookForClientWindow.this), "Nuevo nombre:", sel.getName());
				if (newName != null && !newName.trim().isEmpty()) {
					sel.setName(newName.trim());
					updateList(txtSearch.getText());
				}
			}
		});

		btnMakeSale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Customer sel = lstResults.getSelectedValue();
				if (sel == null) return;
				Container parent = LookForClientWindow.this.getParent();
				if (parent != null) {
					parent.removeAll();
					AddSaleWindow salePanel = new AddSaleWindow(sel);
					salePanel.setReturnAction(() -> {
						parent.removeAll();
						parent.add(new LookForClientWindow(), BorderLayout.CENTER);
						parent.revalidate();
						parent.repaint();
					});
					parent.add(salePanel, BorderLayout.CENTER);
					parent.revalidate();
					parent.repaint();
				}
			}
		});

		// Add customer button: create a Customer using the search text or an input dialog
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtSearch.getText() == null ? "" : txtSearch.getText().trim();
				if (name.isEmpty()) {
					name = JOptionPane.showInputDialog(SwingUtilities.getWindowAncestor(LookForClientWindow.this), "Client's name:");
					if (name == null) return; // user cancelled
					name = name.trim();
					if (name.isEmpty()) return;
				}
				// Create and add the customer to the Store via constructor
				new Customer(name);
				// Refresh list and show the newly added customer
				updateList(name);
				// Optionally clear the search field
				// txtSearch.setText("");
			}
		});
	}

	private void updateList(String filter) {
		model.clear();
		String f = filter == null ? "" : filter.trim();
		if (f.isEmpty()) {
			List<Customer> all = Store.getCustomers();
			for (Customer c : all) model.addElement(c);
			return;
		}
		String type = (String) cmbType.getSelectedItem();
		List<Customer> found;
		if ("ID".equalsIgnoreCase(type)) {
			found = Store.lookCustomerById(f);
		} else {
			found = Store.lookCustomersByName(f);
		}
		for (Customer c : found) model.addElement(c);
	}

}
