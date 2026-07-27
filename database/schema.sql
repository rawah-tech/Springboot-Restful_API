-- Process Governance API - non-destructive MySQL baseline schema
-- Run this file before database/sample-data.sql.

CREATE DATABASE IF NOT EXISTS `process_governance`
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE `process_governance`;

CREATE TABLE IF NOT EXISTS `roles` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `uk_roles_name` UNIQUE (`name`)
) ENGINE=InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `users` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(20) NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `password` VARCHAR(120) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `uk_users_username` UNIQUE (`username`),
    CONSTRAINT `uk_users_email` UNIQUE (`email`)
) ENGINE=InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `user_roles` (
    `user_id` BIGINT NOT NULL,
    `role_id` INT NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`),
    KEY `idx_user_roles_role_id` (`role_id`),
    CONSTRAINT `fk_user_roles_user`
        FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `fk_user_roles_role`
        FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `departments` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `dpt_name` VARCHAR(255) DEFAULT NULL,
    `dpt_section` VARCHAR(255) DEFAULT NULL,
    `num_processes` INT NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `process` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `process_name` VARCHAR(255) DEFAULT NULL,
    `process_dpt_section` VARCHAR(255) DEFAULT NULL,
    `process_dpt` VARCHAR(255) DEFAULT NULL,
    `process_status` VARCHAR(255) DEFAULT NULL,
    `process_owner` VARCHAR(255) DEFAULT NULL,
    `process_objective` VARCHAR(255) DEFAULT NULL,
    `process_strategy_note` VARCHAR(255) DEFAULT NULL,
    `process_input` VARCHAR(255) DEFAULT NULL,
    `process_output` VARCHAR(255) DEFAULT NULL,
    `process_customer` VARCHAR(255) DEFAULT NULL,
    `process_kpi` VARCHAR(255) DEFAULT NULL,
    `process_description` VARCHAR(255) DEFAULT NULL,
    `process_chart_file` LONGBLOB DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `process_tasks` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `task_name` VARCHAR(255) DEFAULT NULL,
    `task_description` TEXT,
    `task_wla` TEXT,
    `task_owner` VARCHAR(255) DEFAULT NULL,
    `process_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_process_tasks_process_id` (`process_id`),
    CONSTRAINT `fk_process_tasks_process`
        FOREIGN KEY (`process_id`) REFERENCES `process` (`id`)
        ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
