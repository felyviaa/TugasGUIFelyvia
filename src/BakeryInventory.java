import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class BakeryInventory extends JFrame {
    
    private JTextField txtNamaBahan, txtJumlah;
    private JComboBox<String> cbSatuan;
    private JButton btnTambah, btnEdit, btnHapus, btnClear;
    private JTable tableStok;
    private DefaultTableModel tableModel;

    public BakeryInventory() {
        setTitle("Sistem Invetory Bakery ");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        JPanel panelInput = new JPanel(new GridLayout(3, 2, 5, 10)); 
        
        panelInput.add(new JLabel("Nama Bahan Baku:"));
        txtNamaBahan = new JTextField();
        panelInput.add(txtNamaBahan);

        panelInput.add(new JLabel("Jumlah:"));
        txtJumlah = new JTextField();
        panelInput.add(txtJumlah);

        panelInput.add(new JLabel("Satuan:"));
        String[] satuan = {"Pcs", "Kg"};
        cbSatuan = new JComboBox<>(satuan);
        panelInput.add(cbSatuan);

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)); 
        btnTambah = new JButton("Tambah");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnClear = new JButton("Bersihkan");
        
        panelTombol.add(btnTambah);
        panelTombol.add(btnEdit);
        panelTombol.add(btnHapus);
        panelTombol.add(btnClear);

        panelAtas.add(panelInput, BorderLayout.CENTER);
        panelAtas.add(panelTombol, BorderLayout.SOUTH);
        add(panelAtas, BorderLayout.NORTH);

        String[] kolom = {"Nama Bahan Baku", "Jumlah", "Satuan"};
        tableModel = new DefaultTableModel(kolom, 0);
        tableStok = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(tableStok);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);

        // EVENT HANDLING
        ActionListener aksiTombol = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btnTambah) {
                    tambahData();
                } else if (e.getSource() == btnEdit) {
                    editData(); 
                } else if (e.getSource() == btnHapus) {
                    hapusData();
                } else if (e.getSource() == btnClear) {
                    clearForm();
                }
            }
        };
        btnTambah.addActionListener(aksiTombol);
        btnEdit.addActionListener(aksiTombol);
        btnHapus.addActionListener(aksiTombol);
        btnClear.addActionListener(aksiTombol);

        tableStok.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int baris = tableStok.getSelectedRow();
                if (baris >= 0) {
                    txtNamaBahan.setText(tableModel.getValueAt(baris, 0).toString());
                    txtJumlah.setText(tableModel.getValueAt(baris, 1).toString());
                    cbSatuan.setSelectedItem(tableModel.getValueAt(baris, 2).toString());
                }
            }
        });
        
        txtJumlah.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar())) {
                    e.consume(); 
                }
            }
        });
    }

    // CREATE DENGAN VALIDASI DUPLIKASI
    private void tambahData() {
        String nama = txtNamaBahan.getText().trim();
        String jumlah = txtJumlah.getText().trim();
        String satuan = cbSatuan.getSelectedItem().toString();

        if (nama.isEmpty() || jumlah.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data tidak boleh kosong!");
            return; // Hentikan eksekusi method
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String namaDiTabel = tableModel.getValueAt(i, 0).toString();
            
            // IgnoreCase 
            if (nama.equalsIgnoreCase(namaDiTabel)) {
                JOptionPane.showMessageDialog(this, 
                    "Bahan baku '" + nama + "' sudah ada!\nSilakan klik data tersebut di tabel, lalu Edit jumlahnya atau Hapus.", 
                    "Data Duplikat", 
                    JOptionPane.WARNING_MESSAGE);
                return; // Hentikan proses agar data tidak bertambah
            }
        }

        // Jika lolos pengecekan (tidak ada duplikat), data dimasukkan
        tableModel.addRow(new Object[]{nama, jumlah, satuan});
        clearForm();
    }

    // UPDATE
    private void editData() {
        int baris = tableStok.getSelectedRow();
        if (baris >= 0) {
            String nama = txtNamaBahan.getText().trim();
            String jumlah = txtJumlah.getText().trim();
            String satuan = cbSatuan.getSelectedItem().toString();

            if (nama.isEmpty() || jumlah.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Data tidak boleh kosong!");
            } else {
                tableModel.setValueAt(nama, baris, 0);
                tableModel.setValueAt(jumlah, baris, 1);
                tableModel.setValueAt(satuan, baris, 2);
                clearForm();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris di tabel yang ingin diedit!");
        }
    }

    // DELETE
    private void hapusData() {
        int baris = tableStok.getSelectedRow();
        if (baris >= 0) {
            tableModel.removeRow(baris);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris di tabel terlebih dahulu!");
        }
    }

    private void clearForm() {
        txtNamaBahan.setText("");
        txtJumlah.setText("");
        cbSatuan.setSelectedIndex(0);
        tableStok.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BakeryInventory().setVisible(true));
    }
}