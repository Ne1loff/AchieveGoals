ALTER TABLE goal_table RENAME TO task_table;

CREATE TABLE task_label_table (
    id BIGINT NOT NULL,
    uid BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_task_label_table PRIMARY KEY (id)
);

CREATE TABLE task_table_labels
(
    task_id   BIGINT NOT NULL,
    labels_id BIGINT NOT NULL
);

ALTER TABLE task_table_labels
    ADD CONSTRAINT uc_goal_table_labels_labels UNIQUE (labels_id);

ALTER TABLE task_table_labels
    ADD CONSTRAINT fk_tastablab_on_task FOREIGN KEY (task_id) REFERENCES task_table (id);

ALTER TABLE task_table_labels
    ADD CONSTRAINT fk_tastablab_on_task_label FOREIGN KEY (labels_id) REFERENCES task_label_table (id);