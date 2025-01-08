create schema db_sekolah;

use db_sekolah;

CREATE TABLE siswa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100),
    kelas VARCHAR(50),
    alamat TEXT
);
INSERT INTO siswa (nama, kelas, alamat) VALUES
('Andi Pratama', 'X IPA 1', 'Jl. Merdeka No. 10, Jakarta'),
('Budi Santoso', 'X IPA 2', 'Jl. Raya No. 25, Bandung'),
('Citra Dewi', 'X IPS 1', 'Jl. Cempaka No. 3, Yogyakarta'),
('Dwi Setiawan', 'X IPA 3', 'Jl. Anggrek No. 7, Surabaya'),
('Eka Putri', 'X IPA 2', 'Jl. Pahlawan No. 15, Medan'),
('Fajar Nugroho', 'X IPS 2', 'Jl. Kamboja No. 22, Semarang'),
('Gita Rahmawati', 'XI IPA 1', 'Jl. Raya No. 18, Makassar'),
('Hendra Wijaya', 'XI IPA 3', 'Jl. Melati No. 5, Malang'),
('Intan Sari', 'XI IPS 1', 'Jl. Alamsari No. 10, Bali'),
('Joko Prabowo', 'XI IPA 2', 'Jl. Sate No. 13, Bogor'),
('Kartika Ayu', 'XII IPA 1', 'Jl. Merpati No. 3, Surakarta'),
('Lina Dewi', 'XII IPA 3', 'Jl. Sumber Rejeki No. 14, Bekasi'),
('Maya Susanti', 'XII IPS 2', 'Jl. Sekar No. 6, Tangerang'),
('Nia Azkia', 'XII IPA 2', 'Jl. Sukarno Hatta No. 2, Cirebon'),
('Oki Fadilah', 'X IPA 1', 'Jl. Sumber Jaya No. 8, Depok'),
('Petrus Alam', 'X IPS 1', 'Jl. Bunga No. 20, Solo'),
('Qina Anggraini', 'XI IPS 3', 'Jl. Harapan No. 12, Palembang'),
('Rudi Hartono', 'XI IPA 2', 'Jl. Raya No. 30, Batam'),
('Siti Aisyah', 'XII IPS 1', 'Jl. Kenanga No. 9, Pontianak'),
('Tono Wicaksono', 'XII IPA 3', 'Jl. Sejahtera No. 17, Samarinda');
