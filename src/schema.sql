CREATE TABLE Defect (
    Id INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(255) NOT NULL,
    DefectDetails VARCHAR(500),
    StepsToReproduce VARCHAR(1000),
    Priority VARCHAR(10) CHECK (priority IN ('P1', 'P2', 'P3')),
    DetectedOn DATE NOT NULL,
    ExpectedResolution DATE,
    ReportedByTesterId VARCHAR(20),
    AssignedToDeveloperId VARCHAR(20),
    Severity VARCHAR(10) CHECK (severity IN ('Blocking', 'Critical', 'Major', 'Minor', 'Low')),
    Status VARCHAR(255),
    ProjectCode INT
);
 
CREATE TABLE Resolution (
    id INT PRIMARY KEY AUTO_INCREMENT,
    resolutionDate DATE,
    resolution VARCHAR(500),
    defect_id INT,
    FOREIGN KEY (defect_id) REFERENCES Defect(Id)
);
CREATE TABLE users (
    user_name VARCHAR(40) PRIMARY KEY,
    password VARCHAR(40) NOT NULL,
    role VARCHAR(40) NOT NULL,
    is_account_locked BOOLEAN
);