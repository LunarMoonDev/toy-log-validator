# Changelog

## Version v0.1.0 (2024-09-16)

### Features

- add kafka library in gradle (24bddf7f)
- add method to save error event in report table (14f447b7)
- add method to get non-processed and latest reports from repository (2bf60206)
- add new field for generic exception (075dc908)
- add new error enum and exception listener (cbbf44cc)
- add new field to Reports (52218220)
- add validation exception (46a9607a)
- add publish kafka message tasklet as step for the job (295d57b8)
- add POJO for kafka message with serialization (53681459)
- add kafka service for publishing message (84a3ff12)
- added properties for kafka (64869c09)
- added config for kafka (f16c7292)
- add more exception handling (54935d3b)
- added new method in report service to grab report (ee29b018)
- added parse report tasklet (c3bfdc21)
- add main application (8f77cbb7)
- add main and local property file (70e95993)
- add job config and job launcher (22ecfef9)
- add data and schema validation tasklet (cb9b6032)
- add work book util (6fb883db)
- add report service (42e41e25)
- add data and schema validator service (5a4901fa)
- add validation enum response (321af8d3)
- add error handling POJOs (54a48a68)
- add parse logs database config (6bd2b866)
- add google storage and parse report service (f365f3b8)

### Fixes

- change iterator of schema size check (325fe76f)

### Refactoring

- register exception listener in steps and change job schedule (45fd0465)
- fix error handling in tasklet (0d1ad3de)
- add new property for pecent regex and rearrange (a708592e)
- change iterator of while loop in work book util (6f86a5d5)
- complete implem of data validator service (ac6d15e2)
- change data validator service method signature (09fce1a0)
- change job cron schedule (a7225a12)

### Chores and tidying

- add new properties and add bash command history (1baaa2ab)
- move around variables in validator launcher config (7ced192b)
- remove unnecessary lines in validate data and schema tasklets (c7457d62)
- add gradle configs (9ae3d485)
- add gitignore for the dir of project (0efe15c5)
- add environment variables for sql (14aa05b3)
- add docker file for the spring project (151d45d9)
- add semanticore for versioning (799c77dd)
- add git merge request templates (09e033de)
- add devcontainer for remote development (528928e8)
- add git stuff for ignore and attribute (282d425c)

### Other

- Initial commit (526d1045)

