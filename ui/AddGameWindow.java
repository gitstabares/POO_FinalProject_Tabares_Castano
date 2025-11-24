package ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import domain.*;

public class AddGameWindow extends JPanel {

	private JTextField txtTitle;
	private JTextField txtGenre;
	private JTextField txtPrice;
	private JTextField txtScore;
	private JButton btnAdd;

	public AddGameWindow() {
		initComponents();
		initEvents();
	}

	private void initComponents() {

		setBackground(Theme.BACKGROUND_COLOR);

		JPanel form = new JPanel(new GridBagLayout());
		form.setBackground(Theme.BACKGROUND_COLOR);
		form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(8,8,8,8);
		c.fill = GridBagConstraints.HORIZONTAL;

		JLabel lblTitle = new JLabel("Título:");
		lblTitle.setForeground(Theme.BUTTON_TEXT_COLOR);
		c.gridx = 0; c.gridy = 0; c.weightx = 0.2;
		form.add(lblTitle, c);

		txtTitle = new JTextField();
		txtTitle.setBackground(Color.WHITE);
		txtTitle.setForeground(Theme.BUTTON_COLOR);
		txtTitle.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
		c.gridx = 1; c.gridy = 0; c.weightx = 0.8;
		form.add(txtTitle, c);

		JLabel lblGenre = new JLabel("Género:");
		lblGenre.setForeground(Theme.BUTTON_TEXT_COLOR);
		c.gridx = 0; c.gridy = 1; c.weightx = 0.2;
		form.add(lblGenre, c);

		txtGenre = new JTextField();
		txtGenre.setBackground(Color.WHITE);
		txtGenre.setForeground(Theme.BUTTON_COLOR);
		txtGenre.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
		c.gridx = 1; c.gridy = 1; c.weightx = 0.8;
		form.add(txtGenre, c);

		JLabel lblPrice = new JLabel("Precio:");
		lblPrice.setForeground(Theme.BUTTON_TEXT_COLOR);
		c.gridx = 0; c.gridy = 2; c.weightx = 0.2;
		form.add(lblPrice, c);

		txtPrice = new JTextField();
		txtPrice.setBackground(Color.WHITE);
		txtPrice.setForeground(Theme.BUTTON_COLOR);
		txtPrice.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
		c.gridx = 1; c.gridy = 2; c.weightx = 0.8;
		form.add(txtPrice, c);

		JLabel lblScore = new JLabel("Puntaje (opcional 0-5):");
		lblScore.setForeground(Theme.BUTTON_TEXT_COLOR);
		c.gridx = 0; c.gridy = 3; c.weightx = 0.2;
		form.add(lblScore, c);

		txtScore = new JTextField();
		txtScore.setBackground(Color.WHITE);
		txtScore.setForeground(Theme.BUTTON_COLOR);
		txtScore.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
		c.gridx = 1; c.gridy = 3; c.weightx = 0.8;
		form.add(txtScore, c);

		// Button area
		JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		actions.setBackground(Theme.BACKGROUND_COLOR);
		btnAdd = new JButton("Add Videogame");
		btnAdd.setEnabled(false);
		btnAdd.setBackground(Theme.BUTTON_COLOR);
		btnAdd.setForeground(Theme.BUTTON_TEXT_COLOR);
		btnAdd.setFocusPainted(false);
		btnAdd.setBorder(BorderFactory.createEmptyBorder(6,12,6,12));
		btnAdd.setBorderPainted(false);
		actions.add(btnAdd);

		form.add(actions, BorderLayout.SOUTH);

		setLayout(new BorderLayout());
        Theme.applyFontOnFrame(form, Font.PLAIN, 16f);
		add(form, BorderLayout.CENTER);
	}

	private void initEvents() {
		DocumentListener dl = new DocumentListener() {
			public void insertUpdate(DocumentEvent e) { validateForm(); }
			public void removeUpdate(DocumentEvent e) { validateForm(); }
			public void changedUpdate(DocumentEvent e) { validateForm(); }
		};
		txtTitle.getDocument().addDocumentListener(dl);
		txtGenre.getDocument().addDocumentListener(dl);
		txtPrice.getDocument().addDocumentListener(dl);

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = txtTitle.getText().trim();
				String genre = txtGenre.getText().trim();
				String priceStr = txtPrice.getText().trim();
				String scoreStr = txtScore.getText().trim();

				float price;
				try {
					price = Float.parseFloat(priceStr);
					if (price < 0) throw new NumberFormatException();
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(AddGameWindow.this, "Precio inválido.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Score is optional
				if (scoreStr.isEmpty()) {
					// use constructor without score
					new Videogame(title, genre, price);
				} else {
					int score;
					try {
						score = Integer.parseInt(scoreStr);
						if (score < 0 || score > 5) throw new NumberFormatException();
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(AddGameWindow.this, "Puntaje inválido. Debe ser entero entre 0 y 5.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					new Videogame(title, genre, price, score);
				}

				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(AddGameWindow.this), "Videojuego agregado al inventario.", "OK", JOptionPane.INFORMATION_MESSAGE);
				// clear form so user can add more if embedded; do not close parent
				clearFields();
			}
		});
	}

	private void clearFields() {
		txtTitle.setText("");
		txtGenre.setText("");
		txtPrice.setText("");
		txtScore.setText("");
		btnAdd.setEnabled(false);
	}

	private void validateForm() {
		String title = txtTitle.getText().trim();
		String genre = txtGenre.getText().trim();
		String price = txtPrice.getText().trim();
		boolean ok = !title.isEmpty() && !genre.isEmpty() && !price.isEmpty();
		// price must be a valid non-negative float to enable the button
		if (ok) {
			try {
				float p = Float.parseFloat(price);
				if (p < 0) ok = false;
			} catch (NumberFormatException ex) {
				ok = false;
			}
		}
		btnAdd.setEnabled(ok);
	}
}
