-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2017 at 09:14 PM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";






/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `elibrary`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(500) NOT NULL,
  `token` varchar(200) DEFAULT NULL,
  `expiratio_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `email`, `password`, `token`, `expiratio_date`) VALUES
('Eivsen', 'eivydas.senkus@gmail.com', '9cfad57e5ca95b909ca72a411b9144fc9064f4c7d351c7e76f6818904985cc7ae4316427ac44f54686446f19e38b60774cf969ec5dc4df1c8ac550604d82becf', 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJFaXZzZW4iLCJleHAiOjE0OTMyNDQ3MzIsImVtYWlsIjoiZWl2eWRhcy5zZW5rdXNAZ21haWwuY29tIn0.DBkZMAi0A_o9WbBFM0pPFVXvb-3xm8Tz5wrZk39gzRk', '2017-04-27 01:12:12'),
('kebob', 'kebob@slayer.xxx', '9f5fd700227a2de162c555f723942551096e0589c69060db61d7cbf6ab64d66eb39be8cb1fe2c7199af3fb6753dc5627a52cbb088b7a5609389c041a38600932', 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrZWJvYiIsImV4cCI6MTQ5MzI0NDcyMSwiZW1haWwiOiJrZWJvYkBzbGF5ZXIueHh4In0.OI4c9mpbaN3olKfBQsMrAeqXa17z_CZms-buRwhCFww', '2017-04-27 01:12:01'),
('Rolas', 'testUpdate', '4404983be4c4a889fa0afdf807b0cb736dfd7479d7b936494f5cd631591f13834cda112a5e7dfabec3fcde8c3f33046bae19f35c882608c59dcee69c50af4cf6', 'eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJSb2xhcyIsImV4cCI6MTQ5MzI0NDcyNiwiZW1haWwiOiJ0ZXN0VXBkYXRlIn0.iHx4xeZQN6udy3YJmMoEm--EbNwlVafG5zo2lC1P31g', '2017-04-27 01:12:06');

--
-- Indexes for dumped tables
--
dqwdwqfqwfqwfwq
--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`),
  ADD UNIQUE KEY `nickname` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `token` (`token`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
