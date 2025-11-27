package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import data.StorageManager;

public class MainWindow extends JFrame{
    
    private int padding = 16;
    private int margin = 30;

    private JPanel pnlMain;
    private JPanel pnlActions;
    private JPanel pnlContent;
    private JLabel lblTitle;

    private JButton btnAddGame;
    private JButton btnSearchCustomer;
    private JButton btnSearchGame;
    private JButton btnSearchSale;

    public MainWindow(){
        StorageManager.loadStore();
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        initEvents();

        // Save store on window close
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                StorageManager.saveStore();
            }
        });

        setVisible(true);
    }
    private void initComponents() {
        // Main container: split into actions (left) and content (right)
        pnlMain = new JPanel(new BorderLayout(padding,padding));
        pnlMain.setBorder(BorderFactory.createEmptyBorder(margin,margin,margin,margin));
        pnlMain.setBackground(Theme.BACKGROUND_COLOR);

        //Title
        lblTitle = new JLabel("VideoGame Store Manager", SwingConstants.CENTER);
        pnlMain.add(lblTitle, BorderLayout.NORTH);
        Theme.applyFontOnFrame(lblTitle, Font.BOLD, 20f);

        // Left actions panel
        pnlActions = new JPanel();
        pnlActions.setLayout(new GridLayout(4,1,padding,padding));
        pnlActions.setBackground(Theme.BACKGROUND_COLOR);

        // Buttons
        btnAddGame = new JButton("Agregar juego al inventario");
        btnSearchCustomer = new JButton("Buscar cliente");
        btnSearchGame = new JButton("Buscar juego");
        btnSearchSale = new JButton("Buscar venta");
        JButton[] buttons = {btnAddGame, btnSearchCustomer, btnSearchGame, btnSearchSale};
        for (JButton b : buttons) {
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder(padding,padding,padding,padding));
            b.setBorderPainted(false);
            b.setOpaque(true);
            b.setBackground(Theme.BUTTON_COLOR);
            b.setForeground(Theme.BUTTON_TEXT_COLOR);
            pnlActions.add(b);
        }
        Theme.applyFontOnFrame(pnlActions, Font.PLAIN, 16f);

        // Right content panel
        pnlContent = new JPanel(new BorderLayout(padding,padding));
        pnlContent.add(new AddGameWindow()); //Default view
        pnlMain.add(pnlActions, BorderLayout.WEST);
        pnlMain.add(pnlContent, BorderLayout.CENTER);

        // Add main panel to frame
        add(pnlMain);
    }
    private void initEvents() {
        btnAddGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    pnlContent.removeAll();;
                } catch (Exception ex) { }
                AddGameWindow addPanel = new AddGameWindow();
                pnlContent.add(addPanel, BorderLayout.CENTER);
                pnlContent.revalidate();
                pnlContent.repaint();
            }

        });
        btnSearchCustomer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    pnlContent.removeAll();;
                } catch (Exception ex) { }
                LookForClientWindow clientPanel = new LookForClientWindow();
                pnlContent.add(clientPanel, BorderLayout.CENTER);
                pnlContent.revalidate();
                pnlContent.repaint();
            }
        });
        btnSearchGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    pnlContent.removeAll();;
                } catch (Exception ex) { }
                LookForGameWindow gamePanel = new LookForGameWindow();
                pnlContent.add(gamePanel, BorderLayout.CENTER);
                pnlContent.revalidate();
                pnlContent.repaint();
            }
        });
        btnSearchSale.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    pnlContent.removeAll();;
                } catch (Exception ex) { }
                LookForSaleWindow salePanel = new LookForSaleWindow();
                pnlContent.add(salePanel, BorderLayout.CENTER);
                pnlContent.revalidate();
                pnlContent.repaint();
            }
        });
    }
}
