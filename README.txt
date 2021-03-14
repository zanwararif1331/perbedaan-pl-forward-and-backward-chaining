PL RESOLUTION, BACKWARD CHAINING DAN FORWARD CHAINING

Jadi, dari program ini terdapat 3 tugas, yaitu: Forward Chaining, Backward Chaining, dan Propositional Logic.

1) Forward Chaining: Di sini kita telah memberikan pengetahuan-pengetahuan dasar yang mencakup aturan dan fakta. Ide dasar dibalik rangkaian maju adalah itu
dari fakta kita mempelajari aturan dan melihat mana di antara mereka yang bisa dipecat. Aturan yang disalahkan atau dipecat (?), akan dimasukkan ke dalam list fakta. Kemudian list fakta yang telah dikumpulkan kemudian digunakan untuk membuat aturan baru yang lebih kompleks.

2) Backward Chaining: Di sini kita memiliki kesimpulan dan kita harus membuktikan bahwa kesimpulan tsb terjadi, dengan cara menganalisa mundur, yaitu dari aturan ke fakta. Jadi, di setiap iterasinya, kita sudah punya sudah punya daftar goals (atau disini disebutkan sebagai tempat atau bagain pengantarnya) dan kita akan mencoba menggunakan hal ini kita untuk mendapat fakta dari dari kesimpulan tsb. Kalau bisa, berarti kesimpulannya bisa kita dapatkan dari fakta-fakta itu. Dan jika tidak, maka kesimpulannya tidak dapat kita buktikan dari fakta-fakta yang tersebut.

3) Logika Proposisional: Input-an akan diberikan pada kita dalam klausa HORN. Jadi, untuk mengimplementasikan logika proposisional, kita harus mengubah bentuk klausa klausa menjadi Conjunctive Normal Form (CNF). Aturan yang diterapkan untuk membentuk suatu resolved klausa dari dua klausa adalah sebagai berikut: -

(A v B) (-B v C) akan menghasilkan (A v C).
Saat mengikuti aturan ini, ada 2 hal yang perlu diperhatikan. Pertama adalah kita perlu menghapus duplikat literal yang mungkin akan muncul saat kita telah menyelesaikan klausa-nya. Kedua, dan yang paling penting, jika klausa memiliki literal sehingga ada tipe positif dan negatif dari literal tersebut (Misalnya, katakanlah A v B v C v -A), maka kami tidak menyertakan klausa ini. Ini karena, nilainya akan dihitung menjadi 1.

Kompilasi dan Instruksi Menjalankan
1) Compile program: javac pl.java
2) Jalankan program: java pl -t 1 -kb kb2.txt -q q2.txt -oe output.txt -ol logs.txt

Waktu yang dibutuhkan untuk menjalankan resolusi untuk kasus I: 35-40 detik pada mesin aludra
Waktu yang dibutuhkan untuk menjalankan resolusi untuk kasus II: 2:30 hingga 3 menit pada mesin aludra.

Persamaan / Perbedaan antara Tugas 1,2 dan 3

Kesimpulan saya:
Jadi, pada program ini membedakan bagaimana kinerja fungsi Forward dan Backward Chaining dengan PL Resolution, yang dimana Forward dan Backward Chaining memiliki keterbatasan yang hanya dapat digunakan pada klausa, sedangkan PL Resolution dapat bekerja untuk semua jenis klausa hanya saja akan memakan waktu yang lebih banyak dimana perbandingannya pada perhitungan kasus I dan II, yaitu 35-40 detik dengan 2,5-3 menit. Hal ini dikarenakan Log dari Resolution lebih banyak daripada Forward dan Backward Chaining.

