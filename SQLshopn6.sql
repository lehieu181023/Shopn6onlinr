-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: shopn6
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `giohang`
--

DROP TABLE IF EXISTS `giohang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `giohang` (
  `id_giohang` int NOT NULL AUTO_INCREMENT,
  `SDT` varchar(20) DEFAULT NULL,
  `DaDat` tinyint(1) DEFAULT '0',
  `NgayDat` datetime DEFAULT NULL,
  `ThanhTien` double DEFAULT NULL,
  `Diachi` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id_giohang`),
  KEY `KEY_USER_GIOHANG_idx` (`SDT`),
  CONSTRAINT `KEY_USER_GIOHANG` FOREIGN KEY (`SDT`) REFERENCES `user` (`SDT`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `giohang`
--

LOCK TABLES `giohang` WRITE;
/*!40000 ALTER TABLE `giohang` DISABLE KEYS */;
INSERT INTO `giohang` VALUES (1,'0397790753',1,'2024-09-17 03:20:57',63306000,NULL),(4,'0397790752',0,NULL,NULL,NULL),(5,'0397790753',1,'2024-09-17 03:23:25',200000,NULL),(6,'0397790753',1,'2024-09-17 15:54:09',200000,'an khê'),(7,'0397790753',0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `giohang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loai_san_pham`
--

DROP TABLE IF EXISTS `loai_san_pham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loai_san_pham` (
  `id_loai_san_pham` int NOT NULL AUTO_INCREMENT,
  `tem_lsp` varchar(45) DEFAULT NULL,
  `thong_tin` varchar(500) DEFAULT NULL,
  `icon_sp` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_loai_san_pham`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loai_san_pham`
--

LOCK TABLES `loai_san_pham` WRITE;
/*!40000 ALTER TABLE `loai_san_pham` DISABLE KEYS */;
INSERT INTO `loai_san_pham` VALUES (1,'Điện thoại','Điện thoại là một thiết bị viễn thông cho phép người dùng thực hiện cuộc gọi và gửi tin nhắn văn bản qua mạng di động. Ngày nay, điện thoại di động đã phát triển thành những thiết bị thông minh, thường được gọi là smartphone, với nhiều tính năng tiên tiến như truy cập internet, chụp ảnh, quay video, nghe nhạc, và sử dụng ứng dụng.','https://cdn-icons-png.flaticon.com/128/644/644458.png'),(2,'Laptop','Laptop là một máy tính xách tay di động với kích thước nhỏ gọn, dễ dàng mang theo. Nó tích hợp màn hình, bàn phím, trackpad, và các linh kiện phần cứng khác trong một thiết bị duy nhất, cho phép người dùng làm việc, học tập, và giải trí ở bất cứ đâu. Laptop thường có pin để sử dụng khi không có nguồn điện, và hỗ trợ kết nối internet, chạy nhiều phần mềm như máy tính để bàn truyền thống.','https://cdn-icons-png.flaticon.com/128/610/610021.png');
/*!40000 ALTER TABLE `loai_san_pham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sanpham`
--

DROP TABLE IF EXISTS `sanpham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sanpham` (
  `id_sanpham` int NOT NULL AUTO_INCREMENT,
  `tensp` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `anhsp` varchar(300) COLLATE utf8mb4_general_ci NOT NULL,
  `giasp` double NOT NULL,
  `gioi_thieu` varchar(5000) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_loai_san_pham` int DEFAULT NULL,
  `ngaynhap` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_sanpham`),
  KEY `key_loai_sp_idx` (`id_loai_san_pham`),
  CONSTRAINT `key_loai_sp` FOREIGN KEY (`id_loai_san_pham`) REFERENCES `loai_san_pham` (`id_loai_san_pham`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sanpham`
--

LOCK TABLES `sanpham` WRITE;
/*!40000 ALTER TABLE `sanpham` DISABLE KEYS */;
INSERT INTO `sanpham` VALUES (2,'samsum Galaxy s11','https://tinypic.host/images/2024/09/08/1702744900608.jpg',35000000,'hohoho',1,'2024-09-14 14:43:53'),(3,'POCO M5','https://cdn.tgdd.vn/Products/Images/42/289442/poco-m5-den-1.jpg',3453000,'hahahaha',1,'2024-09-14 14:43:53'),(4,'HP Laptop dy-15','https://microless.com/cdn/products/8087fb9d12de01817d4442841a39aded-md.jpg',28000000,'ram 8g',2,'2024-09-14 14:43:53'),(5,'Samsung Galaxy Z Fold6','https://cdn.tgdd.vn/Products/Images/42/320721/samsung-galaxy-z-fold6-xanh-navy-1.jpg',38990000,'Đặc điểm nổi bật của Samsung Galaxy Z Fold6 • Thiết kế siêu mỏng nhẹ • AI tích hợp giúp trải nghiệm dễ dàng hơn bao giờ hết: Note Assist, Photo Assist, Interpreter (phiên dịch viên) • Bứt phá hiệu năng từ Snapdragon 8 Gen 3 for Galaxy • Pin 4400 mAh, sẵn sàng đồng hành suốt ngày dài • Bền bỉ hơn, an tâm hơn với chuẩn kháng nước, bụi IP48',1,'2024-09-14 15:02:19');
/*!40000 ALTER TABLE `sanpham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sp_in_giohang`
--

DROP TABLE IF EXISTS `sp_in_giohang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sp_in_giohang` (
  `idSP_in_giohang` int NOT NULL AUTO_INCREMENT,
  `id_sanpham` int DEFAULT NULL,
  `id_giohang` int DEFAULT NULL,
  `so_luong` int DEFAULT NULL,
  PRIMARY KEY (`idSP_in_giohang`),
  KEY `KEY_SP_GIOHANG_idx` (`id_sanpham`),
  KEY `KEY_GH_GIOHANG_idx` (`id_giohang`),
  CONSTRAINT `KEY_GH_GIOHANG` FOREIGN KEY (`id_giohang`) REFERENCES `giohang` (`id_giohang`),
  CONSTRAINT `KEY_SP_GIOHANG` FOREIGN KEY (`id_sanpham`) REFERENCES `sanpham` (`id_sanpham`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sp_in_giohang`
--

LOCK TABLES `sp_in_giohang` WRITE;
/*!40000 ALTER TABLE `sp_in_giohang` DISABLE KEYS */;
INSERT INTO `sp_in_giohang` VALUES (21,2,1,2),(22,4,1,2),(23,3,1,2),(25,2,4,3),(26,2,5,1),(27,2,6,1),(32,3,7,2);
/*!40000 ALTER TABLE `sp_in_giohang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `SDT` varchar(20) NOT NULL,
  `Password` varchar(60) DEFAULT NULL,
  `Email` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`SDT`),
  UNIQUE KEY `Email_UNIQUE` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('0397790752',NULL,NULL),('0397790753','$2a$10$HXlUMjWSrD1qiHXxyXKuOuWgKWDo3jOLgZUToLsZ51JXbkiBuihuK','lehieu181023@gmail.com'),('0397790757','$2a$10$6xH70tIe.tXOxNPF.yfTmOCh5MNwC2BxSHaupdpPzSTZHLhCDoSKS','viphieuak12345@gmail.com'),('0397790758','$2a$10$I5r.U2J0T/XXAO63SOhfduO1WHO32GKFoTS9Jzocfqb/QyAc3JUTe','20214045@eaut.edu.vn');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'shopn6'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-21 13:00:32
