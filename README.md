# task1
task for learning testng and rest-assured

# Publish ke Github:
1. Klik Source Control (atau Ctrl+Shift+G)
2. Pilih Publish to GitHub
3. Login ke Akun Github
4. Masukan nama repositori remote yang diinginkan, kemudian pilih Publish to GitHub public repository
5. Secara default akan terbentuk reposioty local dan remote dengan nama main

# Clone remote repository ke local via UI Source Control
1. Klik Source Control (atau Ctrl+Shift+G)
2. Klik ...(More Action)
3. Remote > Add Remote
4. Add Remote From GitHub, masukan link Github

# Install Dependencies:
1. Cari dependecy yang ingin ditambahkan di https://mvnrepository.com/
2. kemudian copy bagian mavennya contoh untuk testNG:
<!-- https://mvnrepository.com/artifact/org.testng/testng -->
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.11.0</version>
    <scope>test</scope>
</dependency>
3. Tambahkan ke pom.xml di dalam tag <dependencies></dependencies>
4. kemudian build dengan command mvn clean install di terminal



