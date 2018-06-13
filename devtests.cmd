@REM
@REM fassets - Project for light-weight tracking of fixed assets
@REM Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
@REM
@REM This program is free software: you can redistribute it and/or modify
@REM it under the terms of the GNU General Public License as published by
@REM the Free Software Foundation, either version 3 of the License, or
@REM (at your option) any later version.
@REM
@REM This program is distributed in the hope that it will be useful,
@REM but WITHOUT ANY WARRANTY; without even the implied warranty of
@REM MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
@REM GNU General Public License for more details.
@REM
@REM You should have received a copy of the GNU General Public License
@REM along with this program. If not, see <http://www.gnu.org/licenses/>.
@REM

@REM This is a temporary script for running minutes select tests while during
@REM the development process.

@REM Random tests
mvnw -Dtest=AcquisitionDebitAccountIdResolverTest#* test