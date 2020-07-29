CREATE OR REPLACE PROCEDURE TEMPLATE.SP_FINDBYGROUP (
  P_GROUP_ID  INTEGER,
  P_RESULT OUT SYS_REFCURSOR
) AS
BEGIN
    OPEN P_RESULT FOR
    SELECT
    u.*
    from
    "USER" u
    inner join "GROUP" g on g.id = u.group_id
    where
    u.group_id = P_GROUP_ID;
END;
/