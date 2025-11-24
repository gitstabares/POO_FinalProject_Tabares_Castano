package ui;

import java.io.InputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame{
    
    private int padding = 16;
    private int margin = 30;

    private JPanel pnlMain;
    private JPanel pnlActions;
    private JPanel pnlContent;
    private JLabel lblTitle;

    private JButton btnAddGame;
    private JButton btnMakePurchase;
    private JButton btnSearchCustomer;
    private JButton btnSearchGame;
    private JButton btnSearchSale;

    public MainWindow(){
        setTitle("VideoGame Store");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        initEvents();
        setVisible(true);
        setTitle("");
    }
    public static void changeFont(Component component,int style, float size) {
        InputStream is = ClassLoader.getSystemResourceAsStream("ui/GoogleSansFlex.ttf");
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(style,size);
            component.setFont(font);
            if (component instanceof Container) {
                for (Component child : ((Container) component).getComponents()) {
                    changeFont(child, style, size);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initComponents() {
        // Main container: split into actions (left) and content (right)
        pnlMain = new JPanel(new BorderLayout(padding,padding));
        pnlMain.setBorder(BorderFactory.createEmptyBorder(margin,margin,margin,margin));
        pnlMain.setBackground(Theme.bgColor);

        //Title
        lblTitle = new JLabel("VideoGame Store Manager", SwingConstants.CENTER);
        pnlMain.add(lblTitle, BorderLayout.NORTH);
        changeFont(lblTitle, Font.BOLD, 20f);

        // Left actions panel
        pnlActions = new JPanel();
        pnlActions.setLayout(new GridLayout(5,1,padding,padding));
        pnlActions.setBackground(Theme.bgColor);

        // Buttons
        btnAddGame = new JButton("Agregar juego al inventario");
        btnMakePurchase = new JButton("Realizar compra");
        btnSearchCustomer = new JButton("Buscar cliente");
        btnSearchGame = new JButton("Buscar juego");
        btnSearchSale = new JButton("Buscar venta");
        JButton[] buttons = {btnAddGame, btnMakePurchase, btnSearchCustomer, btnSearchGame, btnSearchSale};
        for (JButton b : buttons) {
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder(padding,padding,padding,padding));
            b.setBorderPainted(false);
            b.setOpaque(true);
            b.setBackground(Theme.btnColor);
            b.setForeground(Theme.txtColor);
            pnlActions.add(b);
        }
        changeFont(pnlActions, Font.PLAIN, 16f);

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
                // Replace the content area center with the AddGameWindow panel
                try {
                    pnlContent.removeAll();;
                } catch (Exception ex) { }
                AddGameWindow addPanel = new AddGameWindow();
                pnlContent.add(addPanel, BorderLayout.CENTER);
                pnlContent.revalidate();
                pnlContent.repaint();
            }
        });
        btnMakePurchase.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // Replace the content area center with the AddSaleWindow panel
                try {
                    pnlContent.removeAll();;
                } catch (Exception ex) { }
                AddSaleWindow salePanel = new AddSaleWindow();
                pnlContent.add(salePanel, BorderLayout.CENTER);
                pnlContent.revalidate();
                pnlContent.repaint();
            }
        });
        btnSearchCustomer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // Replace the content area center with the LookForClientWindow panel
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
                // Replace the content area center with the LookForGameWindow panel
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
                // Replace the content area center with the LookForSaleWindow panel
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
