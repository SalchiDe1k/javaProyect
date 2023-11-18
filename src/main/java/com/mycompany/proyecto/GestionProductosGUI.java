/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto;

/**
 *
 * @author David
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GestionProductosGUI extends JFrame {

    private JTextField tfNombre, tfPrecio, tfStock;
    private JButton btnAgregar, btnMostrar;
    private JTextArea taResultados;

    public GestionProductosGUI() {
        super("Gestión de Productos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        tfNombre = new JTextField();
        tfPrecio = new JTextField();
        tfStock = new JTextField();
        btnAgregar = new JButton("Agregar Producto");
        btnMostrar = new JButton("Mostrar Productos");
        taResultados = new JTextArea();

        add(new JLabel("Nombre:"));
        add(tfNombre);
        add(new JLabel("Precio:"));
        add(tfPrecio);
        add(new JLabel("Stock:"));
        add(tfStock);
        add(btnAgregar);
        add(btnMostrar);
        add(new JScrollPane(taResultados));

        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        btnMostrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarProductos();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void agregarProducto() {
        String nombre = tfNombre.getText();
        double precio = Double.parseDouble(tfPrecio.getText());
        int stock = Integer.parseInt(tfStock.getText());

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_productos", "tu_usuario", "tu_contraseña")) {
            String query = "INSERT INTO productos (nombre, precio, stock) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, nombre);
                pstmt.setDouble(2, precio);
                pstmt.setInt(3, stock);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar el producto.");
        }
    }

    private void mostrarProductos() {
        taResultados.setText("");
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_productos", "tu_usuario", "tu_contraseña")) {
            String query = "SELECT * FROM productos";
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    int stock = rs.getInt("stock");
                    taResultados.append("ID: " + id + ", Nombre: " + nombre + ", Precio: $" + precio + ", Stock: " + stock + "\n");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al recuperar los productos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GestionProductosGUI());
    }
}

