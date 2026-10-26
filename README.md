# README : Dokumentasi GUI Sistem Inventory Bakery 

### 1. Top Level Container

Top level container adalah komponen paling atas yang menjadi wadah utama bagi seluruh antarmuka aplikasi.

* **`JFrame`** : Digunakan sebagai bingkai/jendela utama aplikasi. Semua panel, form, dan tombol ditempatkan di dalam frame ini. Pada kode, `JFrame` diinisialisasi melalui *constructor* dan diatur perilakunya seperti `setTitle()` untuk judul, `setSize()` untuk ukuran, dan `setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)` agar program benar-benar berhenti saat tombol 'X' ditekan.

### 2. Intermediate Component

Intermediate component adalah komponen perantara yang berfungsi untuk mengelompokkan komponen lain agar tata letaknya (layout) rapi dan terorganisir.

* **`JPanel`** : Digunakan sebagai kanvas transparan untuk mengelompokkan elemen. Terdapat beberapa panel dalam aplikasi ini:
* `panelAtas` : Menampung seluruh form input dan jejeran tombol.
* `panelInput` : Khusus menampung label dan kolom isian (TextField).
* `panelTombol` : Khusus menampung tombol-tombol (Tambah, Edit, Hapus, Bersihkan).


* **`JScrollPane`** : Komponen perantara yang membungkus `JTable`. Berfungsi untuk memberikan *scroll bar* otomatis apabila jumlah data bahan baku di dalam tabel sudah melebihi tinggi layar.

### 3. Atomic Component

Atomic component adalah komponen dasar yang berinteraksi langsung dengan pengguna (menerima input atau menampilkan output).

* **`JLabel`** : Menampilkan teks statis (contoh: "Nama Bahan Baku:", "Jumlah:").
* **`JTextField`** : Kolom input tempat pengguna mengetikkan teks atau angka (`txtNamaBahan` dan `txtJumlah`).
* **`JComboBox`** : Menampilkan menu *dropdown* (`cbSatuan`) yang berisi pilihan "Pcs" dan "Kg" agar pengguna tidak perlu mengetik manual.
* **`JButton`** : Tombol aksi yang bisa diklik (`btnTambah`, `btnEdit`, `btnHapus`, `btnClear`).
* **`JTable`** : Menampilkan data bahan baku ke dalam bentuk grid (baris dan kolom) secara visual.

### 4. Layout Manager

Aplikasi ini tidak menggunakan tata letak mutlak (*absolute/null layout*), melainkan menggunakan 3 jenis Layout Manager agar antarmuka tetap rapi meskipun ukuran jendela diubah:

* **`BorderLayout`** : Membagi area menjadi 5 zona (North, South, East, West, Center). Digunakan pada `JFrame` utama (meletakkan panel input di Utara/`NORTH` dan tabel di Tengah/`CENTER`).
* **`GridLayout`** : Menyusun komponen ke dalam bentuk sel berukuran sama (seperti matriks). Digunakan pada `panelInput` dengan parameter `(3, 2, 5, 10)` yang artinya membentuk **3 baris dan 2 kolom** untuk menjejerkan Label dan TextField agar lurus.
* **`FlowLayout`** : Menyusun komponen berjejer menyamping dari kiri ke kanan. Digunakan pada `panelTombol` agar keempat tombol berjejer rapi di tengah (`FlowLayout.CENTER`).

### 5. Event Handling (3 Interface)

Kode ini memenuhi (bahkan melebihi) syarat minimal 2 *interface* untuk penanganan *event*. Event handling ini yang membuat aplikasi "hidup" dan bisa merespon tindakan pengguna.

1. **`ActionListener` (Interface ke-1)**
* **Implementasi:** Diterapkan pada keempat `JButton`.
* **Fungsi:** Mendeteksi aksi "Klik" pada tombol. Saat tombol diklik, method `actionPerformed(ActionEvent e)` akan mengecek tombol mana yang ditekan, lalu memanggil *method* logika yang sesuai (seperti `tambahData()`, `editData()`, dll).


2. **`MouseListener` via `MouseAdapter` (Interface ke-2)**
* **Implementasi:** Diterapkan pada `tableStok` (JTable).
* **Fungsi:** Mendeteksi klik *mouse* pada area tabel. Melalui method `mouseClicked(MouseEvent e)`, baris tabel yang diklik akan dibaca datanya, kemudian teks tersebut dikirim (di-set) kembali ke dalam `JTextField` dan `JComboBox`. Ini mempermudah proses Edit dan Hapus.


3. **`KeyListener` via `KeyAdapter` (Interface ke-3 / Nilai Plus)**
* **Implementasi:** Diterapkan pada `txtJumlah` (JTextField).
* **Fungsi:** Memvalidasi ketikan pengguna melalui keyboard. Saat pengguna mengetik sesuatu (`keyTyped(KeyEvent e)`), program mengecek apakah karakternya angka atau bukan. Jika bukan angka (misal: huruf), perintah `e.consume()` dipanggil untuk menolak input tersebut masuk ke kotak teks.



