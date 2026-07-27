-- Process Governance API - sanitized local portfolio data
-- This script is repeatable: each row is inserted only when its demo key is absent.

USE `process_governance`;

-- Reference data required by AuthController. These are role names, not user data.
INSERT INTO `roles` (`name`)
SELECT 'ROLE_USER'
WHERE NOT EXISTS (
    SELECT 1 FROM `roles` WHERE `name` = 'ROLE_USER'
);

INSERT INTO `roles` (`name`)
SELECT 'ROLE_STRATEGY'
WHERE NOT EXISTS (
    SELECT 1 FROM `roles` WHERE `name` = 'ROLE_STRATEGY'
);

INSERT INTO `roles` (`name`)
SELECT 'ROLE_ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM `roles` WHERE `name` = 'ROLE_ADMIN'
);

-- Fictional demonstration departments. Each has two demo processes below.
INSERT INTO `departments` (`dpt_name`, `dpt_section`, `num_processes`)
SELECT 'Portfolio Operations', 'Service Design', 2
WHERE NOT EXISTS (
    SELECT 1
    FROM `departments`
    WHERE `dpt_name` = 'Portfolio Operations'
      AND `dpt_section` = 'Service Design'
);

INSERT INTO `departments` (`dpt_name`, `dpt_section`, `num_processes`)
SELECT 'Portfolio Strategy', 'Governance Planning', 2
WHERE NOT EXISTS (
    SELECT 1
    FROM `departments`
    WHERE `dpt_name` = 'Portfolio Strategy'
      AND `dpt_section` = 'Governance Planning'
);

INSERT INTO `departments` (`dpt_name`, `dpt_section`, `num_processes`)
SELECT 'Portfolio Services', 'Operational Assurance', 2
WHERE NOT EXISTS (
    SELECT 1
    FROM `departments`
    WHERE `dpt_name` = 'Portfolio Services'
      AND `dpt_section` = 'Operational Assurance'
);

-- Fictional demonstration processes: one row for each status used by the API.
INSERT INTO `process` (
    `process_name`,
    `process_dpt_section`,
    `process_dpt`,
    `process_status`,
    `process_owner`,
    `process_objective`,
    `process_strategy_note`,
    `process_input`,
    `process_output`,
    `process_customer`,
    `process_kpi`,
    `process_description`,
    `process_chart_file`
)
SELECT
    'Demo Intake Review',
    'Service Design',
    'Portfolio Operations',
    'For Review',
    'Intake Team',
    'Review new workflow requests consistently',
    'Awaiting governance review',
    'Synthetic workflow request',
    'Reviewed intake record',
    'Internal Demo Teams',
    'Review completed within five working days',
    'Demonstrates a process waiting for its first review',
    NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM `process`
    WHERE `process_name` = 'Demo Intake Review'
      AND `process_dpt` = 'Portfolio Operations'
);

INSERT INTO `process` (
    `process_name`,
    `process_dpt_section`,
    `process_dpt`,
    `process_status`,
    `process_owner`,
    `process_objective`,
    `process_strategy_note`,
    `process_input`,
    `process_output`,
    `process_customer`,
    `process_kpi`,
    `process_description`,
    `process_chart_file`
)
SELECT
    'Demo Procedure Refresh',
    'Service Design',
    'Portfolio Operations',
    'For Update',
    'Quality Team',
    'Keep operating procedures current',
    'Revision requested',
    'Synthetic review feedback',
    'Updated procedure draft',
    'Internal Demo Teams',
    'Updates completed within ten working days',
    'Demonstrates a process returned for an update',
    NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM `process`
    WHERE `process_name` = 'Demo Procedure Refresh'
      AND `process_dpt` = 'Portfolio Operations'
);

INSERT INTO `process` (
    `process_name`,
    `process_dpt_section`,
    `process_dpt`,
    `process_status`,
    `process_owner`,
    `process_objective`,
    `process_strategy_note`,
    `process_input`,
    `process_output`,
    `process_customer`,
    `process_kpi`,
    `process_description`,
    `process_chart_file`
)
SELECT
    'Demo Control Update Approval',
    'Governance Planning',
    'Portfolio Strategy',
    'For Update Approve',
    'Governance Team',
    'Validate a revised control workflow',
    'Updated draft ready for approval',
    'Synthetic revised control',
    'Approval decision',
    'Internal Demo Reviewers',
    'Approval decision recorded within three working days',
    'Demonstrates an updated process awaiting approval',
    NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM `process`
    WHERE `process_name` = 'Demo Control Update Approval'
      AND `process_dpt` = 'Portfolio Strategy'
);

INSERT INTO `process` (
    `process_name`,
    `process_dpt_section`,
    `process_dpt`,
    `process_status`,
    `process_owner`,
    `process_objective`,
    `process_strategy_note`,
    `process_input`,
    `process_output`,
    `process_customer`,
    `process_kpi`,
    `process_description`,
    `process_chart_file`
)
SELECT
    'Demo Service Control Validation',
    'Governance Planning',
    'Portfolio Strategy',
    'Approved By IC',
    'Control Team',
    'Record internal committee approval',
    'Internal committee approval recorded',
    'Synthetic control evidence',
    'Validated control record',
    'Internal Demo Reviewers',
    'All required evidence attached to the record',
    'Demonstrates an internally approved process',
    NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM `process`
    WHERE `process_name` = 'Demo Service Control Validation'
      AND `process_dpt` = 'Portfolio Strategy'
);

INSERT INTO `process` (
    `process_name`,
    `process_dpt_section`,
    `process_dpt`,
    `process_status`,
    `process_owner`,
    `process_objective`,
    `process_strategy_note`,
    `process_input`,
    `process_output`,
    `process_customer`,
    `process_kpi`,
    `process_description`,
    `process_chart_file`
)
SELECT
    'Demo Operating Model Alignment',
    'Operational Assurance',
    'Portfolio Services',
    'Approved By Strategy',
    'Planning Team',
    'Align a workflow with the demonstration operating model',
    'Strategy approval recorded',
    'Synthetic operating model proposal',
    'Aligned workflow definition',
    'Internal Demo Teams',
    'Alignment review completed successfully',
    'Demonstrates a strategy-approved process',
    NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM `process`
    WHERE `process_name` = 'Demo Operating Model Alignment'
      AND `process_dpt` = 'Portfolio Services'
);

INSERT INTO `process` (
    `process_name`,
    `process_dpt_section`,
    `process_dpt`,
    `process_status`,
    `process_owner`,
    `process_objective`,
    `process_strategy_note`,
    `process_input`,
    `process_output`,
    `process_customer`,
    `process_kpi`,
    `process_description`,
    `process_chart_file`
)
SELECT
    'Demo Executive Endorsement',
    'Operational Assurance',
    'Portfolio Services',
    'Approved By GM',
    'Oversight Team',
    'Record final demonstration workflow endorsement',
    'Final endorsement recorded',
    'Synthetic approved workflow',
    'Endorsed process record',
    'Internal Demo Teams',
    'Final decision recorded within two working days',
    'Demonstrates a process with final approval',
    NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM `process`
    WHERE `process_name` = 'Demo Executive Endorsement'
      AND `process_dpt` = 'Portfolio Services'
);

-- Fictional demonstration tasks linked to the process rows above.
INSERT INTO `process_tasks` (
    `task_name`,
    `task_description`,
    `task_wla`,
    `task_owner`,
    `process_id`
)
SELECT
    'Capture intake details',
    'Record the synthetic request and its expected outcome.',
    'One working day',
    'Intake Team',
    p.`id`
FROM `process` AS p
WHERE p.`process_name` = 'Demo Intake Review'
  AND p.`process_dpt` = 'Portfolio Operations'
  AND NOT EXISTS (
      SELECT 1
      FROM `process_tasks` AS t
      WHERE t.`process_id` = p.`id`
        AND t.`task_name` = 'Capture intake details'
  );

INSERT INTO `process_tasks` (
    `task_name`,
    `task_description`,
    `task_wla`,
    `task_owner`,
    `process_id`
)
SELECT
    'Revise procedure draft',
    'Apply the fictional quality review notes to the procedure.',
    'Three working days',
    'Quality Team',
    p.`id`
FROM `process` AS p
WHERE p.`process_name` = 'Demo Procedure Refresh'
  AND p.`process_dpt` = 'Portfolio Operations'
  AND NOT EXISTS (
      SELECT 1
      FROM `process_tasks` AS t
      WHERE t.`process_id` = p.`id`
        AND t.`task_name` = 'Revise procedure draft'
  );

INSERT INTO `process_tasks` (
    `task_name`,
    `task_description`,
    `task_wla`,
    `task_owner`,
    `process_id`
)
SELECT
    'Record approval evidence',
    'Add a synthetic approval note to the process record.',
    'Two working days',
    'Governance Team',
    p.`id`
FROM `process` AS p
WHERE p.`process_name` = 'Demo Control Update Approval'
  AND p.`process_dpt` = 'Portfolio Strategy'
  AND NOT EXISTS (
      SELECT 1
      FROM `process_tasks` AS t
      WHERE t.`process_id` = p.`id`
        AND t.`task_name` = 'Record approval evidence'
  );
