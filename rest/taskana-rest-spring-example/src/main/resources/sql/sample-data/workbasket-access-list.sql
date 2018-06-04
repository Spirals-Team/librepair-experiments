INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('1', '1', 'Elena',   'Ferrante, Elena',      true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('2', '2', 'Max',     'Behrendt, Maximilian', true, true, true, true, true, true,  true,  true,  true,  false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('3', '3', 'Simone',  'Mueller, Simone',      true, true, true, true, true, true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('4', '4', 'user_1_1','Rojas, Miguel',        true, true, true, true, true, true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true,  true);

-- KSC authorizations (                    ID,                                                                                     WB_KEY,    ACCESS_ID,  ACCESS_NAME         , READ, OPEN, APPEND, TRANSFER, DISTRIBUTE, C1, .., C12)
-- PPKs
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000001', 'WBI:100000000000000000000000000000000004', 'teamlead_1', 'Meyer, Dominik'     , true, true, true,   true,     true,       true, true, true, true, true, true, true, true, true, true, true, true);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000002', 'WBI:100000000000000000000000000000000005', 'teamlead_2', 'Hagen, Holger'      , true, true, true,   true,     true,       true, true, true, true, true, true, true, true, true, true, true, true);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000003', 'WBI:100000000000000000000000000000000006',   'user_1_1', 'Rojas, Miguel'      , true, true, true,   true,     true,       true, true, true, true, true, true, true, true, true, true, true, true);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000004', 'WBI:100000000000000000000000000000000007',   'user_1_2', 'Lengl, Marcel'      , true, true, true,   true,     true,       true, true, true, true, true, true, true, true, true, true, true, true);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000005', 'WBI:100000000000000000000000000000000008',   'user_2_1', 'Zorgati, Mustapha'  , true, true, true,   true,     true,       true, true, true, true, true, true, true, true, true, true, true, true);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000006', 'WBI:100000000000000000000000000000000009',   'user_2_2', 'Breier, Bernd'      , true, true, true,   true,     true,       true, true, true, true, true, true, true, true, true, true, true, true);
-- group internal access
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000007', 'WBI:100000000000000000000000000000000004',    'group_1',    'DevelopersGroup' , true, true, true,   true,     false,      false, false, false, false, false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000008', 'WBI:100000000000000000000000000000000005',    'group_2',    'UsersGroup'      , true, true, true,   true,     false,      false, false, false, false, false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000009', 'WBI:100000000000000000000000000000000006',    'group_1',    'DevelopersGroup' , true, true, true,   true,     false,      false, false, false, false, false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000010', 'WBI:100000000000000000000000000000000007',    'group_1',    'DevelopersGroup' , true, true, true,   true,     false,      false, false, false, false, false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000011', 'WBI:100000000000000000000000000000000008',    'group_2',    'UsersGroup'      , true, true, true,   true,     false,      false, false, false, false, false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000012', 'WBI:100000000000000000000000000000000009',    'group_2',    'UsersGroup'      , true, true, true,   true,     false,      false, false, false, false, false, false, false, false, false, false, false, false);
-- teamlead substitution
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000013', 'WBI:100000000000000000000000000000000004', 'teamlead_2', 'Hagen, Holger'      , true, true, true,   true,     false,      false, false, false, false, false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000014', 'WBI:100000000000000000000000000000000005', 'teamlead_1', 'Meyer, Dominik'     , true, true, true,   true,     false,      false, false, false, false, false, false, false, false, false, false, false, false);
-- cross team tranfers
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000015', 'WBI:100000000000000000000000000000000006',    'group_2',    'UsersGroup'      , true, false, true,  false,    false,      false, false, false, false, false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000016', 'WBI:100000000000000000000000000000000007',    'group_2',    'UsersGroup'      , true, false, true,  false,    false,      false, false, false, false, false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000017', 'WBI:100000000000000000000000000000000008',    'group_1',    'DevelopersGroup' , true, false, false,  false,    false,      false, false, false, false, false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000018', 'WBI:100000000000000000000000000000000009',    'group_1',    'DevelopersGroup' , true, false, true,  false,    false,      false, false, false, false, false, false, false, false, false, false, false, false);
-- Team GPK access
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000019', 'WBI:100000000000000000000000000000000002',    'group_1',    'DevelopersGroup' , true, true, true,   true,     true,       true, true, true, true, true, true, true, true, true, true, true, true);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000020', 'WBI:100000000000000000000000000000000003',    'group_2',    'UsersGroup'      , true, true, true,   true,     true,       true, true, true, true, true, true, true, true, true, true, true, true);
-- Cross team GPK access
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000021', 'WBI:100000000000000000000000000000000001', 'teamlead_1', 'Meyer, Dominik'     , true, true, true,   true,     true,       true, true, true, true, true, true, true, true, true, true, true, true);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000022', 'WBI:100000000000000000000000000000000001', 'teamlead_2', 'Hagen, Holger'      , true, true, true,   true,     true,       true, true, true, true, true, true, true, true, true, true, true, true);

-- Access to other domains
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000023', 'WBI:100000000000000000000000000000000012',    'group_1',    'DevelopersGroup' , true, false, true,  true,     false,      false, false, false, false, false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000024', 'WBI:100000000000000000000000000000000013',    'group_2',    'UsersGroup'      , true, false, true,  true,     false,      false, false, false, false, false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000025', 'WBI:100000000000000000000000000000000014',    'group_2',    'UsersGroup'      , true, true, true,  true,     false,      false, false, false, false, false, false, false, false, false, false, false, false);
INSERT INTO TASKANA.WORKBASKET_ACCESS_LIST VALUES ('WAI:100000000000000000000000000000000026', 'WBI:100000000000000000000000000000000015',    'group_2',    'UsersGroup'      , true, true, true,  true,     false,      false, false, false, false, false, false, false, false, false, false, false, false);

