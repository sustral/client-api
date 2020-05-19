--
-- Clear all tables
--

/*
This uses DELETE instead of TRUNCATE to avoid disabling FOREIGN KEY checks for any period of time.
Since AUTO INCREMENT is never used, there is no difference other than a slight performance decrease.
"WHERE TRUE=TRUE" is used to get around unsafe query checks if present.
*/

DELETE FROM sessions WHERE TRUE=TRUE;

DELETE FROM password_resets WHERE TRUE=TRUE;

DELETE FROM email_confirmations WHERE TRUE=TRUE;

DELETE FROM ffield_organization_relationships WHERE TRUE=TRUE;

DELETE FROM user_organization_relationships WHERE TRUE=TRUE;

DELETE FROM files WHERE TRUE=TRUE;

DELETE FROM scans WHERE TRUE=TRUE;

DELETE FROM ffields WHERE TRUE=TRUE;

DELETE FROM organizations WHERE TRUE=TRUE;

DELETE FROM users WHERE TRUE=TRUE;
