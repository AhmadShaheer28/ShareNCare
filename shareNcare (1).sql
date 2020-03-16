-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 10, 2019 at 12:17 AM
-- Server version: 5.7.28-0ubuntu0.18.04.4
-- PHP Version: 7.2.24-0ubuntu0.18.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shareNcare`
--

-- --------------------------------------------------------

--
-- Table structure for table `requests`
--

CREATE TABLE `requests` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `restaurant_id` int(11) NOT NULL,
  `fee` int(11) NOT NULL,
  `person_limit` int(11) NOT NULL,
  `meal_time` time NOT NULL,
  `meal_date` date NOT NULL,
  `last_join_date` date NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `requests`
--

INSERT INTO `requests` (`id`, `title`, `description`, `user_id`, `restaurant_id`, `fee`, `person_limit`, `meal_time`, `meal_date`, `last_join_date`, `created_at`, `updated_at`) VALUES
(1, 'hello', '123123', 1, 1, 500, 4, '08:15:00', '2019-12-02', '2019-12-01', '2019-11-30 17:54:04', '2019-11-30 17:54:04'),
(2, 'pizza ', 'round house ', 1, 2, 50, 5, '01:41:00', '2019-11-22', '2019-11-08', '2019-11-30 20:41:42', '2019-11-30 20:41:42'),
(5, 'karhai', 'karhai at habibi', 1, 2, 200, 3, '09:42:00', '2019-11-01', '2019-11-02', '2019-11-30 21:29:08', '2019-11-30 21:29:08'),
(6, 'pizza party', 'at round house', 1, 1, 50, 5, '15:43:00', '2019-11-08', '2019-11-07', '2019-12-04 16:44:02', '2019-12-04 16:44:02'),
(7, 'pizza party ', 'at pizza hut', 1, 2, 50, 5, '03:45:00', '2019-11-15', '2019-11-06', '2019-12-04 16:46:10', '2019-12-04 16:46:10'),
(8, 'hello', '123123', 1, 1, 500, 4, '08:15:00', '2019-12-02', '2019-12-01', '2019-12-04 16:50:39', '2019-12-04 16:50:39'),
(9, 'pizza party', 'at pait pooja', 1, 1, 50, 5, '03:57:00', '2019-11-29', '2019-11-28', '2019-12-04 16:57:25', '2019-12-04 16:57:25'),
(10, 'pizza party', 'at habibi', 3, 2, 5, 5, '10:00:00', '2019-11-14', '2019-11-22', '2019-12-04 17:01:10', '2019-12-04 17:01:10'),
(11, 'pizza party ', 'at habibi', 3, 2, 50, 5, '03:03:00', '2019-11-22', '2019-11-08', '2019-12-04 17:04:14', '2019-12-04 17:04:14'),
(12, 'pizza', 'party', 3, 2, 5, 5, '10:06:00', '2019-11-03', '2019-11-03', '2019-12-04 17:07:12', '2019-12-04 17:07:12'),
(13, 'pizza', 'party', 3, 2, 5, 5, '03:11:00', '2019-11-22', '2019-11-06', '2019-12-04 17:12:40', '2019-12-04 17:12:40'),
(14, 'pizza', 'party', 3, 1, 2, 6, '10:17:00', '2019-11-03', '2019-11-03', '2019-12-04 17:19:05', '2019-12-04 17:19:05'),
(15, 'pizza', 'party', 3, 2, 50, 5, '10:54:00', '2019-11-22', '2019-11-20', '2019-12-04 17:54:45', '2019-12-04 17:54:45'),
(16, 'pizza', 'party', 3, 2, 50, 5, '10:54:00', '2019-11-22', '2019-11-20', '2019-12-04 17:57:10', '2019-12-04 17:57:10'),
(17, 'pizza', 'party', 3, 2, 50, 5, '10:54:00', '2019-11-22', '2019-11-20', '2019-12-04 17:58:17', '2019-12-04 17:58:17'),
(18, 'pizza', 'burger', 3, 2, 5, 3, '04:03:00', '2019-11-07', '2019-11-06', '2019-12-04 18:03:36', '2019-12-04 18:03:36'),
(19, 'pizza', 'burger', 3, 1, 50, 6, '15:11:00', '2019-11-29', '2019-11-14', '2019-12-04 18:12:05', '2019-12-04 18:12:05'),
(20, 'pizza', 'burger', 3, 1, 50, 6, '15:11:00', '2019-11-29', '2019-11-14', '2019-12-04 18:12:34', '2019-12-04 18:12:34'),
(21, 'pizza ', 'party', 3, 2, 50, 6, '03:15:00', '2019-11-22', '2019-11-14', '2019-12-04 18:16:02', '2019-12-04 18:16:02'),
(22, 'pizza', 'burger', 3, 2, 5, 5, '11:54:00', '2019-11-03', '2019-11-03', '2019-12-04 18:54:23', '2019-12-04 18:54:23'),
(23, 'pulao', 'saviour', 3, 1, 50, 10, '03:19:00', '2019-11-15', '2019-11-27', '2019-12-04 19:19:20', '2019-12-04 19:19:20');

-- --------------------------------------------------------

--
-- Table structure for table `restaurants`
--

CREATE TABLE `restaurants` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `location` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `restaurants`
--

INSERT INTO `restaurants` (`id`, `name`, `location`) VALUES
(1, 'Pait Pooja', 'G11 Markaz'),
(2, 'Habibi', 'F7 Markaz');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_no` varchar(50) NOT NULL,
  `gender` varchar(50) NOT NULL,
  `image_name` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `name`, `password`, `phone_no`, `gender`, `image_name`, `description`, `created_at`, `updated_at`) VALUES
(1, 'shaheer@gmail.com', 'Ahmad Shaheer', '$2b$10$5gths3EolZHBcrHsy0ZenOcmU89xr2x6o5S2ssDy/khl.0bF7a4PK', '03077880037', 'male', NULL, NULL, '2019-11-30 13:52:50', '2019-11-30 13:52:50'),
(2, 'manan@co.com', 'Abdul manan', '$2b$10$Xm7JPiq7uJRUcVAv/m708OFkbiZ85gZhiuOCzgJSptKlDoPaHv80O', '03034321408', 'male', NULL, NULL, '2019-11-30 16:17:37', '2019-11-30 16:17:37'),
(3, 'atif@gmail.com', 'Atif', '$2b$10$vWXGO799auyIbJJ4u8OwzePXORzhP1tWekj18at04UEDEqqJ1Dsz2', '03031234567', 'male', NULL, NULL, '2019-12-04 16:59:48', '2019-12-04 16:59:48'),
(4, 'atif1@gmail.com', 'atif', '$2b$10$N7/6OTPDXZNpW6vqURkenOVwYgzr.lfvCOMjquhi.69fALgsjVem2', '03031234567', 'male', NULL, NULL, '2019-12-08 16:57:57', '2019-12-08 16:57:57');

-- --------------------------------------------------------

--
-- Table structure for table `user_requests`
--

CREATE TABLE `user_requests` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `request_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_requests`
--

INSERT INTO `user_requests` (`id`, `user_id`, `request_id`, `created_at`, `updated_at`) VALUES
(1, 1, 1, '2019-12-08 16:08:26', '2019-12-08 16:08:26'),
(2, 2, 1, '2019-12-08 16:20:07', '2019-12-08 16:20:07'),
(3, 4, 1, '2019-12-08 17:16:46', '2019-12-08 17:16:46'),
(4, 4, 1, '2019-12-08 17:18:37', '2019-12-08 17:18:37'),
(5, 4, 2, '2019-12-08 17:18:58', '2019-12-08 17:18:58'),
(6, 4, 2, '2019-12-08 17:19:11', '2019-12-08 17:19:11'),
(7, 4, 2, '2019-12-08 17:19:18', '2019-12-08 17:19:18'),
(8, 4, 5, '2019-12-08 17:21:37', '2019-12-08 17:21:37'),
(9, 4, 5, '2019-12-08 17:21:39', '2019-12-08 17:21:39'),
(10, 4, 5, '2019-12-08 17:21:40', '2019-12-08 17:21:40'),
(11, 4, 5, '2019-12-08 17:21:42', '2019-12-08 17:21:42'),
(12, 4, 5, '2019-12-08 17:23:09', '2019-12-08 17:23:09'),
(13, 4, 6, '2019-12-08 17:23:47', '2019-12-08 17:23:47'),
(14, 4, 10, '2019-12-08 17:28:56', '2019-12-08 17:28:56'),
(15, 4, 23, '2019-12-08 17:29:44', '2019-12-08 17:29:44'),
(16, 4, 22, '2019-12-08 17:32:15', '2019-12-08 17:32:15'),
(17, 4, 21, '2019-12-08 17:38:39', '2019-12-08 17:38:39'),
(18, 4, 20, '2019-12-08 17:45:38', '2019-12-08 17:45:38');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `requests`
--
ALTER TABLE `requests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `restaurants`
--
ALTER TABLE `restaurants`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `user_requests`
--
ALTER TABLE `user_requests`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `requests`
--
ALTER TABLE `requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT for table `restaurants`
--
ALTER TABLE `restaurants`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `user_requests`
--
ALTER TABLE `user_requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
