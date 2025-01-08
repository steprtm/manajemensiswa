package ManajemenSiswaGradle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MainForm extends JFrame {
    private JTextField txtID, txtNama, txtKelas, txtAlamat, txtCari;
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    public MainForm() {
        initUI();
        initTable();
        loadData();
    }

    private void initUI() {
        setTitle("Manajemen Siswa");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Panel untuk form input
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Nama
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0;
        txtNama = new JTextField(30);
        inputPanel.add(txtNama, gbc);

        // Kelas
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Kelas:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1;
        txtKelas = new JTextField(30);
        inputPanel.add(txtKelas, gbc);

        // Alamat
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        txtAlamat = new JTextField(30);
        inputPanel.add(txtAlamat, gbc);

        // Panel untuk tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnTambah = new JButton("Tambah Siswa");
        JButton btnUbah = new JButton("Perbaharui Siswa");
        JButton btnHapus = new JButton("Hapus Siswa");
        JButton btnReset = new JButton("Bersihkan Form");

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUbah);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnReset);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);

        // Panel untuk pencarian
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari Siswa:"));
        txtCari = new JTextField(30);
        searchPanel.add(txtCari);
        
        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(searchPanel, gbc);

        // Setup table
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nama");
        model.addColumn("Kelas");
        model.addColumn("Alamat");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Tambah action listeners
        btnTambah.addActionListener(e -> addData());
        btnUbah.addActionListener(e -> updateData());
        btnHapus.addActionListener(e -> deleteData());
        btnReset.addActionListener(e -> resetForm());

        txtCari.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchData();
            }
        });

        // Hidden ID field
        txtID = new JTextField();
        txtID.setVisible(false);

        // Table selection listener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtID.setText(table.getValueAt(row, 0).toString());
                    txtNama.setText(table.getValueAt(row, 1).toString());
                    txtKelas.setText(table.getValueAt(row, 2).toString());
                    txtAlamat.setText(table.getValueAt(row, 3).toString());
                }
            }
        });

        // Set frame properties
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void initTable() {
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
    }

    private void searchData() {
        String searchText = txtCari.getText().toLowerCase();
        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }

    // [Metode lain tetap sama seperti sebelumnya: loadData(), addData(), updateData(), deleteData(), resetForm()]
    
    private void resetForm() {
        txtID.setText("");
        txtNama.setText("");
        txtKelas.setText("");
        txtAlamat.setText("");
        txtCari.setText("");
        sorter.setRowFilter(null);
        table.clearSelection();
    }

    private void loadData() {
        model.setRowCount(0);
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM siswa")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("kelas"),
                        rs.getString("alamat")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addData() {
        String nama = txtNama.getText();
        String kelas = txtKelas.getText();
        String alamat = txtAlamat.getText();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO siswa (nama, kelas, alamat) VALUES (?, ?, ?)")) {
            ps.setString(1, nama);
            ps.setString(2, kelas);
            ps.setString(3, alamat);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
            loadData();
            resetForm();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateData() {
        if (txtID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diubah terlebih dahulu!");
            return;
        }
        int id = Integer.parseInt(txtID.getText());
        String nama = txtNama.getText();
        String kelas = txtKelas.getText();
        String alamat = txtAlamat.getText();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE siswa SET nama = ?, kelas = ?, alamat = ? WHERE id = ?")) {
            ps.setString(1, nama);
            ps.setString(2, kelas);
            ps.setString(3, alamat);
            ps.setInt(4, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
            loadData();
            resetForm();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteData() {
        if (txtID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus terlebih dahulu!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus data ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtID.getText());
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM siswa WHERE id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                loadData();
                resetForm();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainForm().setVisible(true));
    }
}