# MavenProject
MavenProjectHavelsan

Projede beklenen  jar dosyasını decompile edip tekrar source code'u ile bir proje haline getirmek. Bu jardan oluşacak proje dosyası bir Maven Projesidir.

TestProseduruAnaliziv2.9FINAl.jar dosyası jarın yaptığı çok genel olarak bir test prosedürünü alıp gereksinimlerini bulup bizim yazdığımız formata uygun olup
olmadığını anlama, içinde verdiğimiz srs koduyla arama yapma ve bundan izlenebilirlik dökümanı çıkarma(TD-0001 testinde mesela şu gereksinimler vardır diye
söyleyen döküman). 

Bu aşamalar geçildikten sonra kaynak kodlar geliştirilerek, kullanıcının arama sembolü seçebileceği bir arayüz tasarlandı.

------------
TestProseduruAnaliziv2.9FINAl.jar dosyasi çalıştırıldığı zaman 1.docx dosyası seçilir ve kontrol et butonuna basılır. Tarama başlar.  Tarama bittikten sonra uygulama size " Dosya içerisindeki gereksinimler başarıyla okundu. Doğrulama yapmak ister misiniz?" uyarısı verir. Eğer evet e tıklarsanız, Gereksinim Listesini aşağıya kopyalamak için bir alan açılır. Bu alana 'srs' kodunu yapıştırıp doğrulayabilirsiniz. Doğrulama bitince size şöyle bir çıktı verecektir

Test Prosedüründeki Fazlalıklar :
HVL-PYBS-SSS-08260
HVL-PYBS-SSS-19290
HVL-PYBS-SSS-19331
HVL-PYBS-SSS-19340
Test Prosedüründeki Eksiklikler :
srs
Toplam 5 adet fark bulundu.

Ayarlar kısmından projeninn okunma özelliklerini değiştirebilir, kullanıcının istediği sembolü seçebilecek olduğu arayüze ulaşabilirsiniz. Önizlenilebilirlik oluştur diyerek Excel dosyasına(ayarlardan dosya uzantısını değiştirebilirsiniz.) Gereksinim Tablosu yazdırabilirsiniz. Ancak bu özeliik işletim sistemi ve sürüm farkından kaynaklı doğru şekilde çalışmamaktadır.
