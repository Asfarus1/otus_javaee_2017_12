CREATE OR REPLACE FUNCTION change_password() RETURNS trigger AS $BODY$
BEGIN	
 IF (TG_OP = 'INSERT') THEN
  NEW.pass = crypt(NEW.pass, gen_salt('bf',8));
  return NEW;
 END IF;
 IF (TG_OP = 'UPDATE') AND (OLD.pass != NEW.pass) THEN
  NEW.pass = crypt(NEW.pass, gen_salt('bf',8));
  return NEW;
 end IF;
 return NEW;
END; 
$BODY$
language 'plpgsql';

DROP TRIGGER IF EXISTS users_password ON users;
CREATE TRIGGER users_password 
    BEFORE INSERT OR UPDATE
    ON users FOR EACH ROW 
    EXECUTE PROCEDURE change_password();

CREATE OR REPLACE FUNCTION getEmployeesWithMaxSalary() RETURNS SetOF Employees AS $$
SELECT * FROM employees e WHERE e.salary = (SELECT max(salary) FROM employees)
$$ LANGUAGE sql;