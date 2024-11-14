use projects;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS project;


CREATE TABLE project (
	project_id INT AUTO_INCREMENT NOT NULL,
	project_name VARCHAR(128) not null,
	 estimated_hours DECIMAL (7,2), 
	actual_hours DECIMAL (7,2),
	 difficulty VARCHAR(64) NOT NULL,
	  notes VARCHAR(64) NOT null,
	  primary key (project_id)
	  
	  );
	 
CREATE TABLE step (
	step_id INT AUTO_INCREMENT NOT NULL,
	 project_id INT  NOT NULL,
	step_text TEXT,
	 step_order INT,
	 primary key (step_id),
	 foreign key (project_id) references project (project_id) on delete cascade
);


CREATE TABLE category (
	category_id INT AUTO_INCREMENT NOT NULL,
	category_name VARCHAR(128) NOT null,
	primary key (category_id)
);

CREATE TABLE project_category (
	project_id INT  NOT NULL,
	 category_id INT  NOT null,
	 foreign key (project_id) references project (project_id) on delete cascade,
	 foreign key (category_id) references category (category_id) on delete cascade
	 
);



CREATE TABLE material (
	material_id INT AUTO_INCREMENT NOT NULL, 
	project_id INT NOT NULL,
	material_name VARCHAR(128) NOT NULL,
	 num_required INT NOT NULL,
	 cost INT NOT NULL,
	 primary key (material_id),
	 foreign key (project_id) references project (project_id) on delete cascade
	
);



