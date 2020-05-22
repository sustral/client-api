--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` char(32) NOT NULL,
  `email` varchar(320) NOT NULL,
  `name` varchar(100) NOT NULL,
  `auth` char(60) NOT NULL,
  `email_verified` BOOLEAN NOT NULL DEFAULT FALSE,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `email_index` (`email`)
) ENGINE=InnoDB
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_bin;

--
-- Table structure for table `organizations`
--

DROP TABLE IF EXISTS `organizations`;

CREATE TABLE `organizations` (
  `id` char(32) NOT NULL,
  `name` varchar(100) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_bin;

--
-- Table structure for table `ffields`
--

DROP TABLE IF EXISTS `ffields`;

CREATE TABLE `ffields` (
  `id` char(32) NOT NULL,
  `name` varchar(100) NOT NULL,
  `coordinates` polygon NOT NULL,
  `approved` BOOLEAN NOT NULL DEFAULT FALSE,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `approved_index` (`approved`)
) ENGINE=InnoDB
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_bin;

--
-- Table structure for table `scans`
--

DROP TABLE IF EXISTS `scans`;

CREATE TABLE `scans` (
  `ffield_id` char(32) NOT NULL,
  `id` char(32) NOT NULL,
  `scan_status` enum('PENDING_COLLECTION','COLLECTION','PENDING_ANALYSIS','ANALYSIS','PENDING_COMPLETE','COMPLETE') NOT NULL,
  `coordinates` polygon NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ffield_id`,`id`),
  FOREIGN KEY (`ffield_id`) REFERENCES `ffields`(`id`) ON DELETE CASCADE,
  INDEX `scan_status_index` (`ffield_id`, `scan_status`)
) ENGINE=InnoDB
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_bin;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;

CREATE TABLE `files` (
  `ffield_id` char(32) NOT NULL,
  `scan_id` char(32) NOT NULL,
  `id` char(32) NOT NULL,
  `file_type` enum('RGB_RAW','RGB_ORTHOMOSAIC') NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ffield_id`,`scan_id`,`id`),
  FOREIGN KEY (`ffield_id`) REFERENCES `ffields`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`ffield_id`,`scan_id`) REFERENCES `scans`(`ffield_id`,`id`) ON DELETE CASCADE,
  INDEX `file_type_index` (`ffield_id`, `scan_id`, `file_type`)
) ENGINE=InnoDB
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_bin;

--
-- Table structure for table `user_organization_relationships`
--

DROP TABLE IF EXISTS `user_organization_relationships`;

CREATE TABLE `user_organization_relationships` (
  `user_id` char(32) NOT NULL,
  `organization_id` char(32) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`organization_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`organization_id`) REFERENCES `organizations`(`id`) ON DELETE CASCADE,
  INDEX `organization_id_index` (`organization_id`, `user_id`)
) ENGINE=InnoDB
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_bin;

--
-- Table structure for table `ffield_organization_relationships`
--

DROP TABLE IF EXISTS `ffield_organization_relationships`;

CREATE TABLE `ffield_organization_relationships` (
  `ffield_id` char(32) NOT NULL,
  `organization_id` char(32) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ffield_id`,`organization_id`),
  FOREIGN KEY (`ffield_id`) REFERENCES `ffields`(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`organization_id`) REFERENCES `organizations`(`id`) ON DELETE CASCADE,
  INDEX `organization_id_index` (`organization_id`, `ffield_id`)
) ENGINE=InnoDB
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_bin;

--
-- Table structure for table `email_verifications`
--

DROP TABLE IF EXISTS `email_verifications`;

CREATE TABLE `email_verifications` (
  `token` char(64) NOT NULL,
  `user_id` char(32) NOT NULL,
  `email` varchar(320) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`token`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  INDEX `created_index` (`created`)
) ENGINE=InnoDB
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_bin;

--
-- Table structure for table `password_resets`
--

DROP TABLE IF EXISTS `password_resets`;

CREATE TABLE `password_resets` (
  `token` char(64) NOT NULL,
  `user_id` char(32) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`token`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  INDEX `created_index` (`created`)
) ENGINE=InnoDB
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_bin;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;

CREATE TABLE `sessions` (
  `token` char(64) NOT NULL,
  `user_id` char(32) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`token`),
  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
  INDEX `created_index` (`created`)
) ENGINE=InnoDB
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_bin;
