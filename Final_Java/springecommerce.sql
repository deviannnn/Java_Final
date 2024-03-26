-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 06, 2023 at 03:58 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `springecommerce`
--

DROP DATABASE IF EXISTS springecommerce;
CREATE DATABASE IF NOT EXISTS springecommerce;
USE springecommerce;

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `username` varchar(64) NOT NULL,
  `password` varchar(256) NOT NULL,
  `role` varchar(10) NOT NULL,
  `permission` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`id`, `username`, `password`, `role`, `permission`) VALUES
(1, 'adminjava@gmail.com', '$2a$10$uikgOtrvu3TAcTMZEK.nUefK29xNBFrNRUm.EsdmnZG0RTa13XHpC', 'ADMIN', 2),
(2, 'temp@gmail.com', '$2a$10$uikgOtrvu3TAcTMZEK.nUefK29xNBFrNRUm.EsdmnZG0RTa13XHpC', 'USER', 1);

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `total_amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`id`, `customer_id`, `status`, `total_amount`) VALUES
(1, 1, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `cartdetail`
--

CREATE TABLE `cartdetail` (
  `id` int(11) NOT NULL,
  `cart_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`) VALUES
(1, 'Medicine'),
(2, 'Health Care'),
(3, 'Personal Care'),
(4, 'Dietary Supplement'),
(5, 'Mother and Baby'),
(6, 'Beauty Care'),
(7, 'Medical Equipment');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `last_active` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `account_id`, `name`, `last_active`) VALUES
(1, 2, 'temp', '2023-04-05');

-- --------------------------------------------------------

--
-- Table structure for table `feature`
--

CREATE TABLE `feature` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `feature`
--

INSERT INTO `feature` (`id`, `name`) VALUES
(1, 'Alternative Medicine'),
(2, 'Anti-infection'),
(3, 'Antibiotic'),
(4, 'Prevent Cancer'),
(5, 'Cardiovascular'),
(6, 'Stomach'),
(7, 'Musculoskeletal'),
(8, 'Flu & Fever'),
(9, 'First Aid'),
(10, 'Eye, ear, nose Care'),
(11, 'Bathroom Care'),
(12, 'Deodorant'),
(13, 'Teeth Care'),
(14, 'Body Care'),
(15, 'Baby Care'),
(16, 'Pregnant Women'),
(17, 'Facial Skin Care'),
(18, 'Cosmetics'),
(19, 'Thermometer'),
(20, 'Covid Kit Test'),
(21, 'Pregnancy Test');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `cart_id` int(11) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `specific_address` varchar(128) NOT NULL,
  `city` varchar(50) NOT NULL,
  `district` varchar(50) NOT NULL,
  `ward` varchar(50) NOT NULL,
  `date_created` date NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `feature_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `image` varchar(50) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `unit` varchar(50) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `category_id`, `feature_id`, `name`, `image`, `quantity`, `price`, `unit`, `description`) VALUES
(1, 1, 1, 'Aloe Vera Extract Capsules', 'aloe_vera_extract.png', 50, 147000, 'Bottle', 'Aloe vera is a plant that has been used for centuries for its health benefits. These capsules contain a concentrated extract of aloe vera that may help with digestion and immune system support.'),
(2, 2, 2, 'Antibacterial Hand Sanitizer Gel', 'hand_sanitizer.png', 100, 53000, 'Bottle', 'This hand sanitizer gel contains 70% alcohol and is effective in killing germs and bacteria.'),
(3, 3, 3, 'Antibiotic Ointment', 'antibiotic_ointment.png', 30, 280000, 'Tube', 'This antibiotic ointment is used to prevent infection and promote healing in minor cuts and burns.'),
(4, 4, 4, 'Calcium Supplements', 'calcium_supplements.png', 60, 12000, 'Bottle', 'These calcium supplements may help support bone health and prevent osteoporosis.'),
(5, 5, 5, 'Cholesterol-Lowering Supplements', 'cholesterol_lowering_supplements.png', 40, 45000, 'Bottle', 'These supplements may help lower cholesterol levels and promote heart health.'),
(6, 6, 6, 'Digestive Enzyme Supplements', 'digestive_enzyme_supplements.png', 50, 22000, 'Bottle', 'These digestive enzyme supplements may help improve digestion and alleviate symptoms of bloating and gas.'),
(7, 4, 7, 'Joint Supplements', 'joint_supplements.png', 70, 18000, 'Bottle', 'These joint supplements may help reduce inflammation and pain in the joints.'),
(8, 1, 8, 'Hand Sanitizing Wipes', 'hand_sanitizing_wipes.png', 80, 71000, 'Pack', 'These hand sanitizing wipes contain 75% alcohol and are effective in killing germs and bacteria.'),
(9, 2, 9, 'First Aid Kit', 'first_aid_kit.png', 20, 156000, 'Kit', 'This first aid kit contains essential items for treating minor injuries and emergencies.'),
(10, 3, 10, 'Nasal Spray', 'nasal_spray.png', 40, 120000, 'Bottle', 'This nasal spray may help relieve nasal congestion and allergy symptoms.'),
(11, 4, 11, 'Bath Soap', 'bath_soap.png', 90, 34000, 'Bar', 'This bath soap contains moisturizing ingredients and a refreshing scent.'),
(12, 5, 12, 'Deodorant Spray', 'deodorant_spray.png', 60, 85000, 'Bottle', 'This deodorant spray provides long-lasting protection against odor and sweat.'),
(13, 6, 13, 'Whitening Toothpaste', 'whitening_toothpaste.png', 70, 71000, 'Tube', 'This whitening toothpaste may help remove stains and promote oral health.'),
(14, 7, 21, 'Pregnancy Test Kit', 'pregnancy_test_HCG.png', 80, 99000, 'box', 'This pregnancy test kit includes two tests and provides accurate results in just a few minutes. Easy to use and reliable, this kit is a must-have for any woman who suspects she may be pregnant.'),
(15, 5, 15, 'Baby Shampoo', 'baby_shampoo.png', 50, 60000, 'Bottle', 'This baby shampoo contains gentle essences suitable for baby\'s skin.'),
(16, 3, 11, 'Shampoo for Dry Hair', 'shampoo_dry_hair.png', 30, 150000, 'Bottle', 'This shampoo is specially formulated for dry hair, with nourishing ingredients that help to hydrate and soften the hair.'),
(17, 4, 5, 'Omega-3 Fish Oil Supplement', 'fish_oil.png', 60, 99000, 'Bottle', 'This fish oil supplement contains omega-3 fatty acids, which may help to support heart health and reduce inflammation.'),
(18, 7, 7, 'Wrist Support Brace', 'wrist_support.png', 20, 84000, 'Pair', 'This wrist support brace is designed to provide compression and support for the wrist, helping to relieve pain and prevent injury.'),
(19, 6, 13, 'Electric Toothbrush', 'electric_toothbrush.png', 15, 330000, 'Each', 'This electric toothbrush features multiple brush modes and a built-in timer, helping to ensure a thorough clean for your teeth and gums.'),
(20, 7, 16, 'Maternity Belt', 'maternity_belt.png', 10, 76000, 'Each', 'This maternity belt provides support for the belly and back during pregnancy, helping to relieve discomfort and improve posture.'),
(21, 7, 19, 'Thermometer', 'thermometer.png', 50, 91000, 'Each', 'This digital thermometer provides quick and accurate temperature readings, with a clear digital display.'),
(22, 1, 2, 'Antacid Tablets', 'antacid.png', 40, 83000, 'Bottle', 'These antacid tablets contain calcium carbonate and magnesium hydroxide, which may help to relieve heartburn and acid indigestion.'),
(23, 2, 6, 'Pain Relief Cream', 'pain_relief.png', 25, 67000, 'Tube', 'This pain relief cream contains menthol and camphor, which may help to relieve muscle aches and joint pain.'),
(24, 3, 17, 'Carave cleanser', 'carave.png', 35, 59000, 'Bottle', 'This body lotion contains nourishing ingredients like shea butter and vitamin E, helping to hydrate and soften the skin.'),
(25, 7, 20, 'At-Home Covid Test Kit', 'covid_test.png', 5, 45000, 'Kit', 'This at-home Covid test kit provides quick and accurate results, with a simple swab test that can be done in the comfort of your own home.'),
(26, 4, 4, 'Green Tea Extract Capsules', 'green_tea_extract.png', 30, 150000, 'Bottle', 'These capsules contain a concentrated extract of green tea that may help with weight loss and antioxidant support.'),
(27, 3, 10, 'Saline Nasal Spray', 'saline_nasal_spray.png', 20, 82000, 'Bottle', 'This saline nasal spray helps relieve congestion and dryness in the nasal passages.'),
(28, 5, 15, 'Pampers Baby Diapers', 'pampers_diapers.png', 100, 250000, 'Pack', 'These diapers are designed for babies and feature a comfortable and absorbent design.'),
(29, 1, 6, 'Ibuprofen Tablets', 'ibuprofen.png', 50, 120000, 'Bottle', 'These tablets contain ibuprofen, which is used to relieve pain and reduce fever.'),
(30, 7, 19, 'Digital Thermometer', 'digital_thermometer.png', 10, 57000, 'Unit', 'This digital thermometer can measure body temperature with high accuracy.'),
(31, 2, 8, 'Cold and Flu Tablets', 'cold_flu_tablets.png', 30, 98000, 'Bottle', 'These tablets contain a combination of ingredients that may help relieve cold and flu symptoms.'),
(32, 6, 18, 'Lipstick', 'lipstick.png', 5, 78000, 'Tube', 'This lipstick is available in a range of shades and features a long-lasting formula.'),
(33, 3, 7, 'Band-Aids', 'bandaid.png', 50, 52000, 'Box', 'These band-aids are designed to protect cuts and scrapes while they heal.'),
(34, 3, 13, 'Sensodyne Toothpaste', 'sensodyne.png', 20, 73000, 'Tube', 'This toothpaste helps fight cavities and freshens breath.'),
(35, 4, 5, 'Omega-3 Fish Oil Capsules', 'fish_oil2.png', 40, 250000, 'Bottle', 'These capsules contain omega-3 fatty acids, which may help support heart health and brain function.'),
(36, 3, 14, 'Vaseline Petroleum Jelly', 'vaseline_petroleum_jelly.png', 100, 49000, 'Jar', 'Vaseline Petroleum Jelly is a versatile product that can be used for a variety of purposes, including moisturizing dry skin, protecting minor cuts and burns, and preventing chapped lips. It is made from 100% pure petroleum jelly and is hypoallergenic and non-comedogenic. The jar contains 100 grams of product.'),
(37, 3, 14, 'Biodema', 'biodema.png', 20, 157000, 'Bottle', 'Biodema is a dietary supplement that contains a blend of vitamins and minerals that are beneficial for skin health.'),
(38, 4, 4, 'Chanca Piedra', 'chanca_piedra.png', 30, 124000, 'Bottle', 'Chanca Piedra is a herbal supplement that is traditionally used to support liver and kidney health.'),
(39, 4, 8, 'Umcka ColdCare', 'umcka_coldcare.png', 40, 216000, 'Bottle', 'Umcka ColdCare is a natural herbal supplement that may help with the symptoms of colds and flu.'),
(40, 4, 7, 'Natrol CetylPure', 'natrol_cetypure.png', 25, 35000, 'Bottle', 'Natrol CetylPure is a dietary supplement that contains cetyl myristoleate, a fatty acid that may help reduce joint pain and inflammation.'),
(41, 4, 7, 'CLA Core', 'cla_core.png', 50, 88000, 'Bottle', 'CLA Core is a dietary supplement that contains conjugated linoleic acid (CLA), which may help reduce body fat and increase lean muscle mass.');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cart_fk_customer_id` (`customer_id`);

--
-- Indexes for table `cartdetail`
--
ALTER TABLE `cartdetail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cartdetail_fk_product_id` (`product_id`),
  ADD KEY `cartdetail_fk_cart_id` (`cart_id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer_fk_account_id` (`account_id`);

--
-- Indexes for table `feature`
--
ALTER TABLE `feature`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `orders_fk_cart_id` (`cart_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_category_id` (`category_id`),
  ADD KEY `fk_feature_id` (`feature_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `cartdetail`
--
ALTER TABLE `cartdetail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `feature`
--
ALTER TABLE `feature`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `cart_fk_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`);

--
-- Constraints for table `cartdetail`
--
ALTER TABLE `cartdetail`
  ADD CONSTRAINT `cartdetail_fk_cart_id` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `cartdetail_fk_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_fk_account_id` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`);

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_fk_cart_id` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `fk_category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  ADD CONSTRAINT `fk_feature_id` FOREIGN KEY (`feature_id`) REFERENCES `feature` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
