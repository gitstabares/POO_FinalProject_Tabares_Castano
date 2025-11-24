package ui;

import domain.Store;
import domain.Videogame;
import domain.Sale;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddSaleWindow extends JPanel {

	private JTextField txtSearch;
	private JList<Videogame> lstInventory;
	private DefaultListModel<Videogame> invModel;

	private JList<Videogame> lstCart;
	private DefaultListModel<Videogame> cartModel;

	private JButton btnAddToCart;
	private JButton btnRemoveFromCart;
	private JButton btnSell;

	public AddSaleWindow() {
		initComponents();
		initEvents();
	}

	private void initComponents() {
		// Theme colors
		Color bg = new Color(34,34,34);
		Color panelBg = new Color(36,36,40);
		Color textColor = Color.WHITE;
		Color btnBg = new Color(60,63,65);

		setLayout(new BorderLayout(8,8));
		setBackground(bg);
		setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

		// Top search
		JPanel top = new JPanel(new BorderLayout(6,6));
		top.setBackground(bg);
		JLabel lbl = new JLabel("Buscar videojuego por título:");
		lbl.setForeground(textColor);
		txtSearch = new JTextField();
		txtSearch.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
		top.add(lbl, BorderLayout.WEST);
		top.add(txtSearch, BorderLayout.CENTER);

		// Center: inventory list (left) and cart (right)
		JPanel center = new JPanel(new GridLayout(1,2,8,8));
		center.setBackground(bg);

		// Inventory panel
		JPanel invPanel = new JPanel(new BorderLayout(6,6));
		invPanel.setBackground(panelBg);
		invPanel.setBorder(BorderFactory.createTitledBorder("Inventario"));

		invModel = new DefaultListModel<>();
		lstInventory = new JList<>(invModel);
		lstInventory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstInventory.setCellRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel comp = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Videogame) {
					Videogame g = (Videogame) value;
					comp.setText(g.getTitle() + " — " + g.getGenre() + " — $" + g.getPrice());
				}
				comp.setBackground(isSelected ? new Color(70,70,74) : panelBg);
				comp.setForeground(textColor);
				return comp;
			}
		});
		JScrollPane invScroll = new JScrollPane(lstInventory);
		invPanel.add(invScroll, BorderLayout.CENTER);

		// Buttons for inventory
		JPanel invBtns = new JPanel(new FlowLayout(FlowLayout.CENTER));
		invBtns.setBackground(panelBg);
		btnAddToCart = new JButton("Añadir al carrito");
		btnAddToCart.setEnabled(false);
		btnAddToCart.setBackground(btnBg);
		btnAddToCart.setForeground(textColor);
		btnAddToCart.setFocusPainted(false);
		invBtns.add(btnAddToCart);
		invPanel.add(invBtns, BorderLayout.SOUTH);

		// Cart panel
		JPanel cartPanel = new JPanel(new BorderLayout(6,6));
		cartPanel.setBackground(panelBg);
		cartPanel.setBorder(BorderFactory.createTitledBorder("Carrito"));

		cartModel = new DefaultListModel<>();
		lstCart = new JList<>(cartModel);
		lstCart.setCellRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel comp = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Videogame) {
					Videogame g = (Videogame) value;
					comp.setText(g.getTitle() + " — $" + g.getPrice());
				}
				comp.setBackground(isSelected ? new Color(70,70,74) : panelBg);
				comp.setForeground(textColor);
				return comp;
			}
		});
		JScrollPane cartScroll = new JScrollPane(lstCart);
		cartPanel.add(cartScroll, BorderLayout.CENTER);

		JPanel cartBtns = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cartBtns.setBackground(panelBg);
		btnRemoveFromCart = new JButton("Quitar");
		btnRemoveFromCart.setEnabled(false);
		btnRemoveFromCart.setBackground(btnBg);
		btnRemoveFromCart.setForeground(textColor);
		btnSell = new JButton("Vender");
		btnSell.setEnabled(false);
		btnSell.setBackground(new Color(80,130,70));
		btnSell.setForeground(textColor);
		btnSell.setFocusPainted(false);
		cartBtns.add(btnRemoveFromCart);
		cartBtns.add(btnSell);
		cartPanel.add(cartBtns, BorderLayout.SOUTH);

		center.add(invPanel);
		center.add(cartPanel);

		add(top, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);

		// Load full inventory initially
		updateInventoryList("");
	}

	private void initEvents() {
		txtSearch.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) { updateInventoryList(txtSearch.getText()); }
			public void removeUpdate(DocumentEvent e) { updateInventoryList(txtSearch.getText()); }
			public void changedUpdate(DocumentEvent e) { updateInventoryList(txtSearch.getText()); }
		});

		lstInventory.addListSelectionListener(e -> {
			boolean has = !lstInventory.isSelectionEmpty();
			btnAddToCart.setEnabled(has);
		});

		lstCart.addListSelectionListener(e -> {
			btnRemoveFromCart.setEnabled(!lstCart.isSelectionEmpty());
		});

		btnAddToCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Videogame sel = lstInventory.getSelectedValue();
				if (sel != null) {
					// Avoid duplicates in cart
					if (!cartModel.contains(sel)) {
						cartModel.addElement(sel);
						btnSell.setEnabled(cartModel.getSize() > 0);
					}
				}
			}
		});

		btnRemoveFromCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Videogame sel = lstCart.getSelectedValue();
				if (sel != null) {
					cartModel.removeElement(sel);
					btnSell.setEnabled(cartModel.getSize() > 0);
				}
			}
		});

		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cartModel.getSize() == 0) return;
				ArrayList<Videogame> purchase = new ArrayList<>();
				for (int i = 0; i < cartModel.getSize(); i++) purchase.add(cartModel.getElementAt(i));
				Sale sale = new Sale(purchase);
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(AddSaleWindow.this), "Venta realizada. Total: $" + sale.getCost(), "Venta", JOptionPane.INFORMATION_MESSAGE);
				cartModel.clear();
				btnSell.setEnabled(false);
			}
		});
	}

	private void updateInventoryList(String filter) {
		invModel.clear();
		List<Videogame> all = Store.getVideoGames();
		String f = filter == null ? "" : filter.trim().toLowerCase();
		for (Videogame g : all) {
			if (f.isEmpty() || g.getTitle().toLowerCase().contains(f)) {
				invModel.addElement(g);
			}
		}
	}

	// Optional standalone test
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame f = new JFrame("Realizar venta");
			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			f.setContentPane(new AddSaleWindow());
			f.setSize(700,400);
			f.setLocationRelativeTo(null);
			f.setVisible(true);
		});
	}
}
