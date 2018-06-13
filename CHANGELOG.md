
Changelog for fassets implementation by ghacupha

## Unreleased
### No issue

**Inclusion of license info in the AccountIdServiceTest**


[e84a0740aa2c91e](https://github.com/ghacupha/fassets/commit/e84a0740aa2c91e) Edwin Njeru *2018-06-13 16:02:20*

**Completed mocks and test for the currencyCode method**


[ce6a971303f2446](https://github.com/ghacupha/fassets/commit/ce6a971303f2446) Edwin Njeru *2018-06-13 15:51:58*

**Completed mocks for accountNumberMotif method in the accountIdPolicy**


[af95f6e65253e28](https://github.com/ghacupha/fassets/commit/af95f6e65253e28) Edwin Njeru *2018-06-13 15:42:39*

**Completed mocks for the accountPlaceHolder method**


[40a278e0b1ad181](https://github.com/ghacupha/fassets/commit/40a278e0b1ad181) Edwin Njeru *2018-06-13 15:38:35*

**Completed mocks for the accountLedgerCode method**


[eee6009e77706a0](https://github.com/ghacupha/fassets/commit/eee6009e77706a0) Edwin Njeru *2018-06-13 15:33:08*

**Implemented and mocked out the accountName method in the accountIdPolicy for the test**


[87b024d04760794](https://github.com/ghacupha/fassets/commit/87b024d04760794) Edwin Njeru *2018-06-13 15:24:30*

**Created shell for the AccountIdService unit test**


[285c5db96bbdc82](https://github.com/ghacupha/fassets/commit/285c5db96bbdc82) Edwin Njeru *2018-06-13 15:15:51*

**Made the AccountIdServiceTest an integrated test to isolate actual configs from the tests**


[2597986189bb54d](https://github.com/ghacupha/fassets/commit/2597986189bb54d) Edwin Njeru *2018-06-13 15:09:55*

**Created accountNumber test in the accountIdServiceTest**


[78e0f0d370d0e38](https://github.com/ghacupha/fassets/commit/78e0f0d370d0e38) Edwin Njeru *2018-06-13 15:07:50*

**Completed tests for the account number method of the AcquisitionDebitAccountIdResolver object**


[a0beff40f6cd6fd](https://github.com/ghacupha/fassets/commit/a0beff40f6cd6fd) Edwin Njeru *2018-06-13 13:39:57*

**Added resolution of account number in AcquisitionDebitAccountIdResolver using accountIdService motif from policy**


[b0f11d26f1b9eee](https://github.com/ghacupha/fassets/commit/b0f11d26f1b9eee) Edwin Njeru *2018-06-13 06:50:52*

**Added account number test to the AcquisitionCreditAccountIdTest**


[3098dd75548dcfa](https://github.com/ghacupha/fassets/commit/3098dd75548dcfa) Edwin Njeru *2018-06-13 06:45:57*

**Added accountNumberMotif to the accountIdService interface**


[f50e2a198635ddf](https://github.com/ghacupha/fassets/commit/f50e2a198635ddf) Edwin Njeru *2018-06-13 06:12:23*

**Made the AbstractAccountIdResolver a common abstraction for AcquisitionDebitAccountIdResolver and AcquisitionCreditAccountIdResolver**


[add19ca181493f2](https://github.com/ghacupha/fassets/commit/add19ca181493f2) Edwin Njeru *2018-06-13 05:56:58*

**Implemented the contraAccountId method in the abstraction above the AcquisitionCreditAccountIdResolver which throws an unsupported operation since we don't need a contra account for credit entry during an acquisition**


[735ea028bbdd573](https://github.com/ghacupha/fassets/commit/735ea028bbdd573) Edwin Njeru *2018-06-13 05:52:24*

**Implemented account number motif method**


[84a2d98dfa70479](https://github.com/ghacupha/fassets/commit/84a2d98dfa70479) Edwin Njeru *2018-06-12 15:56:51*

**Created the accountNumberMotif method to configure account number sequence.**


[cfb07d0c362b4c1](https://github.com/ghacupha/fassets/commit/cfb07d0c362b4c1) Edwin Njeru *2018-06-12 15:44:52*

**Implemented missing methods in the AcquisitionCreditAccountIDResolver**


[a87ecf37ca2f7d8](https://github.com/ghacupha/fassets/commit/a87ecf37ca2f7d8) Edwin Njeru *2018-06-12 15:37:01*

**Created shell for the AcquisitionCreditAccountIdResolverTest with all necessary but yet-to-edited mocks**


[62bbb6b7bc61d9f](https://github.com/ghacupha/fassets/commit/62bbb6b7bc61d9f) Edwin Njeru *2018-06-12 14:10:44*

**Completed tests for the AcquisitionDebitAccountIdResolver, with the contra account method covered with acquisition mocks**


[c46bfe63d6d6f39](https://github.com/ghacupha/fassets/commit/c46bfe63d6d6f39) Edwin Njeru *2018-06-12 14:07:26*

**Added accountPrefix and appendant methods to the accountIdPolicy**


[3eff2fdf82bfb54](https://github.com/ghacupha/fassets/commit/3eff2fdf82bfb54) Edwin Njeru *2018-06-12 13:17:43*

**Added test file launching individual tests,and added resolveContraAccountName in the accountIdService**


[31414849bd123a2](https://github.com/ghacupha/fassets/commit/31414849bd123a2) Edwin Njeru *2018-06-12 12:08:08*

**Added tests for the resolution of general ledger name**


[b4cf479a86c002b](https://github.com/ghacupha/fassets/commit/b4cf479a86c002b) Edwin Njeru *2018-06-12 06:19:31*

**Refactored the generalLedgerName in the accounIdResolver interface to use the accountIdService**


[6b33355d0715368](https://github.com/ghacupha/fassets/commit/6b33355d0715368) Edwin Njeru *2018-06-12 06:10:07*

**Implemented unimplemented methods in the AcquisitionDebitAccountIdResolver**


[377a23508d69859](https://github.com/ghacupha/fassets/commit/377a23508d69859) Edwin Njeru *2018-06-12 06:01:57*

**Set up to refactor mocks for the AcquisitionDebitAccountIdResolverTest. Complete implementation would be great but that would mean that the test is coupled with implementation of its dependencies. What we wanna know is if the dependencies are working will the object work?**


[04333c6744f3563](https://github.com/ghacupha/fassets/commit/04333c6744f3563) Edwin Njeru *2018-06-12 05:34:06*

**Implemented tests for the accountIdService interface**


[a2d5142b56e45ec](https://github.com/ghacupha/fassets/commit/a2d5142b56e45ec) Edwin Njeru *2018-06-12 05:23:51*

**Refactored the tests to work with the new API**


[73087e3f1673c2e](https://github.com/ghacupha/fassets/commit/73087e3f1673c2e) Edwin Njeru *2018-06-12 05:11:51*

**Refactored accountId infrastructure to use AccountSide enum**


[846b8f9eaeec4e8](https://github.com/ghacupha/fassets/commit/846b8f9eaeec4e8) Edwin Njeru *2018-06-12 05:09:15*

**Removed Posting enum as it does the same thing that the acccountSide enum is doing**


[f985b6c4a0aab52](https://github.com/ghacupha/fassets/commit/f985b6c4a0aab52) Edwin Njeru *2018-06-12 04:56:55*

**Renamed dependency for AcquisitionAccountResolver as debitAccountIdResolver**


[22970a9eccc44fb](https://github.com/ghacupha/fassets/commit/22970a9eccc44fb) Edwin Njeru *2018-06-12 04:53:20*

**Transferred the KeyFormatter utility to the policy package and made it package private**


[59409d1c497e33e](https://github.com/ghacupha/fassets/commit/59409d1c497e33e) Edwin Njeru *2018-06-11 15:35:05*

**Added missing #accountName method to the FileAccountIdServiceTest for office renovations category**


[fdcbb20878a525d](https://github.com/ghacupha/fassets/commit/fdcbb20878a525d) Edwin Njeru *2018-06-11 14:23:37*

**Final clean up for the FileAccountIdServiceTestmethods adding a test for office renovations**


[8d12298e6be89de](https://github.com/ghacupha/fassets/commit/8d12298e6be89de) Edwin Njeru *2018-06-11 14:21:29*

**Final clean up for the AccountIdPolicyVersion1**


[87eead679b37cdc](https://github.com/ghacupha/fassets/commit/87eead679b37cdc) Edwin Njeru *2018-06-11 13:42:29*

**Restated the accountPlaceHolder test to adhere to new semantics in the properties file**


[df054fcbcff7ecc](https://github.com/ghacupha/fassets/commit/df054fcbcff7ecc) Edwin Njeru *2018-06-11 13:21:16*

**Changed semantics in the property files from electronics to electronic equipment**


[cf96be5e7f90f14](https://github.com/ghacupha/fassets/commit/cf96be5e7f90f14) Edwin Njeru *2018-06-11 13:06:00*

**Reconfigured tests to adopt the new constructor parameters for the FileAccountIdService class**


[d26ab71bb19a30a](https://github.com/ghacupha/fassets/commit/d26ab71bb19a30a) Edwin Njeru *2018-06-11 12:44:30*

**Created version1 method as a static initializer in accountIdPolicyVersion1**


[4511b487be90042](https://github.com/ghacupha/fassets/commit/4511b487be90042) Edwin Njeru *2018-06-11 12:31:32*

**Combined the properties files to remove the need for mutliple args when creating accountIdPoliVersion1**


[3985ccf35ccb775](https://github.com/ghacupha/fassets/commit/3985ccf35ccb775) Edwin Njeru *2018-06-11 12:27:19*

**Implemented remaining methods of the accountIdPolicyVersion1**


[b686fac0acf6961](https://github.com/ghacupha/fassets/commit/b686fac0acf6961) Edwin Njeru *2018-06-11 12:22:26*

**Moved the policy package to properties inorder to more efficiently hide some properties, and also becasue the policy interface currently under implementation uses files**


[7ab5288c2a78f78](https://github.com/ghacupha/fassets/commit/7ab5288c2a78f78) Edwin Njeru *2018-06-11 12:18:00*

**Implented account placeholder method**


[1818c71d0539479](https://github.com/ghacupha/fassets/commit/1818c71d0539479) Edwin Njeru *2018-06-11 12:13:30*

**Implented test for placeHolder method in the accountIdPolicyVersion1**


[24e81f67670aefc](https://github.com/ghacupha/fassets/commit/24e81f67670aefc) Edwin Njeru *2018-06-11 11:56:15*

**Implemented currencyCode method in the accountIdPolicyVersion1**


[060454d8164c786](https://github.com/ghacupha/fassets/commit/060454d8164c786) Edwin Njeru *2018-06-11 11:53:30*

**Created test shell for AccountIdPolicyVersion1**


[a4271531f974605](https://github.com/ghacupha/fassets/commit/a4271531f974605) ghacupha *2018-06-10 12:16:03*

**Implemented version 1 of the AccountIdPolicy**


[6cddc635b0288ab](https://github.com/ghacupha/fassets/commit/6cddc635b0288ab) ghacupha *2018-06-10 12:06:11*

**Added additional methods to the AccountIdPolicy**


[7ad2789a45c768a](https://github.com/ghacupha/fassets/commit/7ad2789a45c768a) ghacupha *2018-06-10 11:46:16*

**Completion of the AccountIdPolicy shell**


[75aa5abc378b6aa](https://github.com/ghacupha/fassets/commit/75aa5abc378b6aa) ghacupha *2018-06-10 11:41:43*

**Created policy package under nomenclature package as a single folder to house nomenclature policy interfaces and implementations**


[44648708970eef9](https://github.com/ghacupha/fassets/commit/44648708970eef9) ghacupha *2018-06-10 10:41:14*

**Created AccountIdPolicy interface shell**


[71054a4021abbbe](https://github.com/ghacupha/fassets/commit/71054a4021abbbe) ghacupha *2018-06-10 10:39:34*

**Created tests for AccountIdService#currencyCode method**


[5294e08625c322a](https://github.com/ghacupha/fassets/commit/5294e08625c322a) ghacupha *2018-06-10 10:27:51*

**Implemented additional tests to the AccountIdService#placeholder and AccountIdService#generalLedgerCode covering both debit and credit side transactions**


[3ea8e4625392195](https://github.com/ghacupha/fassets/commit/3ea8e4625392195) ghacupha *2018-06-10 10:11:46*

**Changed the name of electronics accounts to ELECTRONIC EQUIPMENT**


[06d31968879c730](https://github.com/ghacupha/fassets/commit/06d31968879c730) ghacupha *2018-06-10 10:01:54*

**Added additional tests to the AccountIdService#accountName method to represent debit trnsactions**


[1ec65ac613afc0e](https://github.com/ghacupha/fassets/commit/1ec65ac613afc0e) ghacupha *2018-06-10 09:58:49*

**Added TODO list to track changes required for the AccountIdService implementations**


[a244e69395072bb](https://github.com/ghacupha/fassets/commit/a244e69395072bb) ghacupha *2018-06-10 09:22:48*

**Specified the FileAccountIdServiceTest to be a unit test**


[0e78487b1050622](https://github.com/ghacupha/fassets/commit/0e78487b1050622) ghacupha *2018-06-10 08:29:11*

**Renamed AcquisitionAccountIdService to FileAccountIdService, this being because the service can be used for various types of transactions and also because the properties are read from a properties file at application startup**


[7282ca40b945224](https://github.com/ghacupha/fassets/commit/7282ca40b945224) ghacupha *2018-06-10 08:24:47*

**Recorded new changes in the CHANGELOG**


[33165ca3b09199b](https://github.com/ghacupha/fassets/commit/33165ca3b09199b) ghacupha *2018-06-10 08:12:49*

**Ammended format key which was returning the category of the asset in proper case instead of lower case**


[54d60c341f02d06](https://github.com/ghacupha/fassets/commit/54d60c341f02d06) Edwin Njeru *2018-06-08 06:27:05*

**Muted broken tests in the AcquisitionDebitAccountIdResolver**


[c10e281e5d75dbf](https://github.com/ghacupha/fassets/commit/c10e281e5d75dbf) Edwin Njeru *2018-06-08 06:22:40*

**Created package private KeyFormatter formatting property keys**


[e2eec7565f1dbcd](https://github.com/ghacupha/fassets/commit/e2eec7565f1dbcd) Edwin Njeru *2018-06-08 06:18:59*

**Removed unnecessary methods from the accountIdService interface**


[e4d4269b0083b3d](https://github.com/ghacupha/fassets/commit/e4d4269b0083b3d) Edwin Njeru *2018-06-08 06:14:45*

**Made additional assertions for the accountIdService with computer software and motor vehicle items**


[36631bb25a818e2](https://github.com/ghacupha/fassets/commit/36631bb25a818e2) Edwin Njeru *2018-06-08 06:04:57*

**Enhanced AcquisitionAccountIdService tests to computers and furniture and fittings**


[87b3fb3d0d62b1d](https://github.com/ghacupha/fassets/commit/87b3fb3d0d62b1d) Edwin Njeru *2018-06-08 05:48:55*

**Muted tests for the AccountIdConfigurationPropertiesService inorder to Isolate the acquistionAccountIdService for testing purposes**


[2e0ef2abe227580](https://github.com/ghacupha/fassets/commit/2e0ef2abe227580) Edwin Njeru *2018-06-08 05:42:13*

**Amended wrongly labelled general ledger code segment tags**


[16df06ef3921b75](https://github.com/ghacupha/fassets/commit/16df06ef3921b75) Edwin Njeru *2018-06-08 05:34:12*

**Redefined the case nomenclature used in the properties files**


[41b5a7701a82853](https://github.com/ghacupha/fassets/commit/41b5a7701a82853) Edwin Njeru *2018-06-08 05:30:39*

**Defined the generalLedgerCode with new parameters to allow more generalized re use**


[d866bf50e44b9b3](https://github.com/ghacupha/fassets/commit/d866bf50e44b9b3) Edwin Njeru *2018-06-08 05:24:59*

**Created accounts definitions package**


[45fe515c539e5af](https://github.com/ghacupha/fassets/commit/45fe515c539e5af) Edwin Njeru *2018-06-08 05:11:46*

**Created AccountNumberSegment definition**


[84d56858caa29ee](https://github.com/ghacupha/fassets/commit/84d56858caa29ee) Edwin Njeru *2018-06-08 05:11:09*

**Implemented account naming in AcquisitionAccountIdService**


[e5bd425ad5f6e20](https://github.com/ghacupha/fassets/commit/e5bd425ad5f6e20) Edwin Njeru *2018-06-07 15:44:28*

**Created FassetsProperties**


[b7f5523650ba732](https://github.com/ghacupha/fassets/commit/b7f5523650ba732) Edwin Njeru *2018-06-07 09:15:45*

**Enhanced methods of the AccountIdService implementations**


[fa1a23661e3bcf3](https://github.com/ghacupha/fassets/commit/fa1a23661e3bcf3) Edwin Njeru *2018-06-07 09:02:11*

**Implemented #creditAccountName method using configuration of labels**


[5e89460d3bcc100](https://github.com/ghacupha/fassets/commit/5e89460d3bcc100) Edwin Njeru *2018-06-07 08:35:37*

**Created AbstractAccountIdResolver**


[b064524e4c692ec](https://github.com/ghacupha/fassets/commit/b064524e4c692ec) Edwin Njeru *2018-06-07 07:13:47*

**Deprecated resolveConttraAccountId and resolveCategoryId methods, the later for redundancy the former for relevance**


[02766eaabc3a44c](https://github.com/ghacupha/fassets/commit/02766eaabc3a44c) Edwin Njeru *2018-06-07 06:51:23*

**Deprecated resolveConttraAccountId and resolveCategoryId methods, the later for redundancy the former for relevance**


[3e02435deb937d6](https://github.com/ghacupha/fassets/commit/3e02435deb937d6) Edwin Njeru *2018-06-07 06:51:15*

**Renamed methods in the AccountIdService to allow it to be reused for more transactions**


[eb943c526262e10](https://github.com/ghacupha/fassets/commit/eb943c526262e10) Edwin Njeru *2018-06-07 06:06:08*

**Created AbstractAccountIdService to share common methods and made the AcquisitionAccountIdService final**


[acdc4969611f827](https://github.com/ghacupha/fassets/commit/acdc4969611f827) Edwin Njeru *2018-06-07 05:55:13*

**Created specilized package for accountIdConfigurationService**


[8c1f7c77c7e4fe7](https://github.com/ghacupha/fassets/commit/8c1f7c77c7e4fe7) Edwin Njeru *2018-06-07 05:20:49*

**Amended the name of the config file to account-id-config**


[a0523721465e861](https://github.com/ghacupha/fassets/commit/a0523721465e861) Edwin Njeru *2018-06-07 05:17:11*

**Initial rearrangment for accounts management classes**


[b53099107e7ec95](https://github.com/ghacupha/fassets/commit/b53099107e7ec95) Edwin Njeru *2018-06-06 15:07:33*

**Fixed broken timepoint dependency links in the tests**


[93af300fc35da86](https://github.com/ghacupha/fassets/commit/93af300fc35da86) Edwin Njeru *2018-06-06 12:20:09*

**changed license header definition from javadoc to block comment**


[a487f14cffef597](https://github.com/ghacupha/fassets/commit/a487f14cffef597) Edwin Njeru *2018-06-06 11:20:31*

**Fixed broken timePoint imports**


[409c61e258ecb31](https://github.com/ghacupha/fassets/commit/409c61e258ecb31) Edwin Njeru *2018-06-06 11:11:21*

**Removed time-point implementation from the project**


[8db680ef267b1b6](https://github.com/ghacupha/fassets/commit/8db680ef267b1b6) Edwin Njeru *2018-06-06 11:03:40*

**Update springboot to version 2.0.2**


[215ab23db38b9dc](https://github.com/ghacupha/fassets/commit/215ab23db38b9dc) Edwin Njeru *2018-06-06 11:01:02*

**Fixed pending checkstyle issues. Something to do with the licenses not being properly formatted. I highly suspect my playing around with eclipse ide last week**


[de1ab1571de0758](https://github.com/ghacupha/fassets/commit/de1ab1571de0758) ghacupha *2018-06-01 08:49:17*

**Moved the client to resources to avoid it's monitoring by checkstyle**


[fed44995e6b5693](https://github.com/ghacupha/fassets/commit/fed44995e6b5693) Edwin Njeru *2018-05-31 15:56:15*

**Fixed issues causing failure of context**


[4266aa3836bb2c4](https://github.com/ghacupha/fassets/commit/4266aa3836bb2c4) Edwin Njeru *2018-05-31 15:55:12*

**removed boostrap and fixed styling issues, using vanilla css**


[d891c6394496e4f](https://github.com/ghacupha/fassets/commit/d891c6394496e4f) Edwin Njeru *2018-05-30 17:37:15*

**modified asset list component to activate both the delete button and the asset id link**


[d17755c1de15904](https://github.com/ghacupha/fassets/commit/d17755c1de15904) Edwin Njeru *2018-05-30 17:00:07*

**setup basic styling for the assets list**


[96168ebb04c6377](https://github.com/ghacupha/fassets/commit/96168ebb04c6377) Edwin Njeru *2018-05-30 16:25:10*

**amended wrong method call to the clear method in the messageService**


[7269a14faee19ae](https://github.com/ghacupha/fassets/commit/7269a14faee19ae) Edwin Njeru *2018-05-30 15:08:47*

**added search feature to the client**


[628514084b62c2b](https://github.com/ghacupha/fassets/commit/628514084b62c2b) Edwin Njeru *2018-05-30 15:06:04*

**added application client shell**


[43d1a7496247beb](https://github.com/ghacupha/fassets/commit/43d1a7496247beb) Edwin Njeru *2018-05-29 16:40:02*

**amended client installation**


[5b492638b6c3673](https://github.com/ghacupha/fassets/commit/5b492638b6c3673) Edwin Njeru *2018-05-29 16:38:45*

**created application client shell**


[c7306fc16275373](https://github.com/ghacupha/fassets/commit/c7306fc16275373) Edwin Njeru *2018-05-29 16:23:54*

**Fixed a failing test in the AcquisitionAccountResolverTest**


[39d7dd175769fd8](https://github.com/ghacupha/fassets/commit/39d7dd175769fd8) Edwin Njeru *2018-05-28 15:59:03*

**Sorted unit test checkstyle issues**


[b02e160cf44ba1e](https://github.com/ghacupha/fassets/commit/b02e160cf44ba1e) Edwin Njeru *2018-05-28 14:28:13*

**Segregated IT tests from unit tests**


[aceacb801f3e970](https://github.com/ghacupha/fassets/commit/aceacb801f3e970) ghacupha *2018-05-27 09:45:26*

**Adopted the Cash library to replace internal representation**


[0da481326411845](https://github.com/ghacupha/fassets/commit/0da481326411845) ghacupha *2018-05-23 16:16:14*

**further test amendments to the DefaultBatchDepreciationEntryResolverTest to enhance readability**


[3d5fa82746edc54](https://github.com/ghacupha/fassets/commit/3d5fa82746edc54) ghacupha *2018-05-23 06:41:26*

**Made additional framework structure and additional data structures to support depreciation accounting**


[c068636f1da5e5c](https://github.com/ghacupha/fassets/commit/c068636f1da5e5c) ghacupha *2018-05-22 11:59:11*

**Working on BatchDepreciationEntryResolver framework**


[100dd52aa05f4bb](https://github.com/ghacupha/fassets/commit/100dd52aa05f4bb) Edwin Njeru *2018-05-22 08:28:15*

**rename managers package to 'accounts'**


[7f579f7f125c94f](https://github.com/ghacupha/fassets/commit/7f579f7f125c94f) Edwin Njeru *2018-05-21 18:47:30*

**Created  the DefaultBatchDepreciationEntryResolverTest to cover depreciation entries resolution**


[00485a895493e4d](https://github.com/ghacupha/fassets/commit/00485a895493e4d) Edwin Njeru *2018-05-21 18:43:11*

**Created  the DefaultBatchDepreciationEntryResolverTest to cover depreciation entries resolution**


[66caea55a3a979c](https://github.com/ghacupha/fassets/commit/66caea55a3a979c) Edwin Njeru *2018-05-21 18:42:50*

**rename managers package to 'accounts'**


[3f7173449dc4b24](https://github.com/ghacupha/fassets/commit/3f7173449dc4b24) Edwin Njeru *2018-05-21 18:15:04*

**rename managers package to 'accounts'**


[ff2c75f822e4b90](https://github.com/ghacupha/fassets/commit/ff2c75f822e4b90) Edwin Njeru *2018-05-21 18:12:20*

**rename managers package to 'accounts'**


[fe8c5026c6203a8](https://github.com/ghacupha/fassets/commit/fe8c5026c6203a8) Edwin Njeru *2018-05-21 18:11:36*

**Removed version declaration for EntityManager which is a spring-boot managed dependency**


[b8895a2c211c22d](https://github.com/ghacupha/fassets/commit/b8895a2c211c22d) Edwin Njeru *2018-05-21 17:34:04*

**added the pom checkstyle:check command to mvn verify**


[07714b7642dfff3](https://github.com/ghacupha/fassets/commit/07714b7642dfff3) ghacupha *2018-05-21 08:23:40*

**Enhanced the DefaultEntryResolverDebitsTest to integrate DebitEntriesResolver and the CreditEntriesResolver in creating accounts during assets acqusition. Also the ImmutableListCollector was enahanced with a FastList implementation using the method #toImmutableFastList**


[6a6a3f833b730ea](https://github.com/ghacupha/fassets/commit/6a6a3f833b730ea) ghacupha *2018-05-21 07:37:28*

**Implemented #add(collection) method in AccountBalance to enable aggregation of account balances from a list**


[5edba043c2430ec](https://github.com/ghacupha/fassets/commit/5edba043c2430ec) ghacupha *2018-05-20 15:56:07*

**Implemented the #add method in the AccountBalance object to enable aggregation of balances from different accounts**


[dd9f4faa9df8476](https://github.com/ghacupha/fassets/commit/dd9f4faa9df8476) ghacupha *2018-05-20 15:36:09*

**Implemented the AcquisitionCreditAccountIdResolver interface together with accompanying unit tests**


[773b51212186efd](https://github.com/ghacupha/fassets/commit/773b51212186efd) ghacupha *2018-05-18 12:07:34*

**Implemented PropertiesUtil for reading properties files at start-up with the classloader**


[eba177db6d5760b](https://github.com/ghacupha/fassets/commit/eba177db6d5760b) ghacupha *2018-05-17 17:08:16*

**additional tests to cover resolution of debit acquisition accounts**


[493445fc5ab7fc8](https://github.com/ghacupha/fassets/commit/493445fc5ab7fc8) ghacupha *2018-05-17 16:59:48*

**Complete implementation of the AccountIdConfigService and its test**


[6c4af34ed631432](https://github.com/ghacupha/fassets/commit/6c4af34ed631432) ghacupha *2018-05-17 16:11:26*

**included formatting logic for extracting keys in accordance with the properties configurattion annotation policy version 1.0**


[65919c0d51816b0](https://github.com/ghacupha/fassets/commit/65919c0d51816b0) ghacupha *2018-05-17 14:01:48*

**Implemented the DebitAccountIDResolver, and the AccountIDConfigurationService with AccountIDPropertiesConfigurationService together with its unit test, and configured a spring bean called 'accountIdPropertiesConfigurationService' for intergration with the rest of the application's container**


[8f9f5e88a731710](https://github.com/ghacupha/fassets/commit/8f9f5e88a731710) ghacupha *2018-05-17 13:53:37*

**Definition of the fixed assets transaction types in the FixedAssetsTransactionType enum**


[d94f1f88f2e0f4d](https://github.com/ghacupha/fassets/commit/d94f1f88f2e0f4d) ghacupha *2018-05-17 09:00:06*

**added DefaultAccountIDResolver to resolve failing integration test, since an implementation of the AccountIDResolver interface must be detected and loaded into the spring container before the context-dependent tests can run properly**


[e8ae2aeb8cbf731](https://github.com/ghacupha/fassets/commit/e8ae2aeb8cbf731) ghacupha *2018-05-15 13:50:31*

**added DefaultAccountIDResolver to resolve failing integration test, since an implementation of the AccountIDResolver interface must be detected and loaded into the spring container before the context-dependent tests can run properly**


[07e598b65da6bed](https://github.com/ghacupha/fassets/commit/07e598b65da6bed) ghacupha *2018-05-15 13:09:08*

**Made the AccountingTransaction implementation date-agnostic by overloading the #addEntry method with constructor-supplied date where the Entry bookingDate is null**


[6959f6e6abbdf6e](https://github.com/ghacupha/fassets/commit/6959f6e6abbdf6e) ghacupha *2018-05-15 12:53:18*

**Made the AccountingTransaction implementation date-agnostic by overloading the #addEntry method with constructor-supplied date where the Entry bookingDate is null**


[6208293e604acc5](https://github.com/ghacupha/fassets/commit/6208293e604acc5) ghacupha *2018-05-15 12:32:30*

**added maven-license-plugin for scaffolding license headers with commands 'mvn license:check', 'mvn license:format' and 'mvn license:remove' and updated the same in the checkstyle config, eliminating dependence on ide setups**


[608cb1ab395dae4](https://github.com/ghacupha/fassets/commit/608cb1ab395dae4) ghacupha *2018-05-15 12:10:27*

**Created and tested the methods for the EntryResolver interface using the DEfaultEntryREsolver implementation**


[0e37dbfc5cce83c](https://github.com/ghacupha/fassets/commit/0e37dbfc5cce83c) ghacupha *2018-05-15 11:25:37*

**Created and tested the methods for the EntryResolver interface using the DEfaultEntryREsolver implementation**


[44e1496a78427b4](https://github.com/ghacupha/fassets/commit/44e1496a78427b4) ghacupha *2018-05-15 10:59:37*

**created AccountIdResolver to resolve account names required to implement the account proliferation policy version 1.0**


[7631e305b4494d5](https://github.com/ghacupha/fassets/commit/7631e305b4494d5) ghacupha *2018-05-14 19:42:59*

**Implemented Acquirer interface with AcquirerImpl, and change EntryAttributes map from 'Map<EntryAttribute,String>' to 'Map<String, String>' to allow flexible use of the persistentBookKeeper implementation**


[6a9c073499fa006](https://github.com/ghacupha/fassets/commit/6a9c073499fa006) ghacupha *2018-05-14 16:35:41*

**Implemented Acquirer interface with AcquirerImpl, and change EntryAttributes map from 'Map<EntryAttribute,String>' to 'Map<String, String>' to allow flexible use of the persistentBookKeeper implementation**


[be84af13dc00f27](https://github.com/ghacupha/fassets/commit/be84af13dc00f27) ghacupha *2018-05-14 16:12:31*

**reset accountService to 10k transactions and fixed failing tests**


[71eeabea3fe4698](https://github.com/ghacupha/fassets/commit/71eeabea3fe4698) ghacupha *2018-05-14 15:02:17*

**Changed accountService Iterations to 10k**


[c7532d2f5fd87ba](https://github.com/ghacupha/fassets/commit/c7532d2f5fd87ba) Edwin Njeru *2018-05-13 08:56:40*

**Created Accountant abstraction together with TransactionBuilder and Acquirer interface**


[4a11512754ee440](https://github.com/ghacupha/fassets/commit/4a11512754ee440) Edwin Njeru *2018-05-11 04:59:32*

**Added the accountant and acquirer interfaces**


[74a4022428b37bc](https://github.com/ghacupha/fassets/commit/74a4022428b37bc) Edwin Njeru *2018-05-09 15:37:34*

**Fixed some checktyle formatting issues**


[fafbb642c561123](https://github.com/ghacupha/fassets/commit/fafbb642c561123) Edwin Njeru *2018-05-08 15:21:20*

**configure accountServiceStressTest to run 100000 items**


[8657ea29fc83b5a](https://github.com/ghacupha/fassets/commit/8657ea29fc83b5a) Edwin Njeru *2018-05-08 12:39:03*

**Added a stress testing adding a transaction of 100000 items and changed the base field in Cash implementation to prevent precision errors**


[46bda4efaed4870](https://github.com/ghacupha/fassets/commit/46bda4efaed4870) Edwin Njeru *2018-05-08 10:24:21*

**Added test methods for accountService to cover exceptional situations**


[12ac0432099f2e3](https://github.com/ghacupha/fassets/commit/12ac0432099f2e3) Edwin Njeru *2018-05-08 07:29:58*

**added tests to cover AccoutingService, Account, AccountingEntry and AccountingTraction posting through a repository**


[c135ad7b90f70c9](https://github.com/ghacupha/fassets/commit/c135ad7b90f70c9) Edwin Njeru *2018-05-07 09:34:22*

**Hid internal package in the Account, Entry and the SimpleTransaction**


[20655f19fc31abd](https://github.com/ghacupha/fassets/commit/20655f19fc31abd) Edwin Njeru *2018-05-07 06:46:03*

**fixed some javadoc issues preventing maven goal package from completing successfully**


[014375aea4cbfef](https://github.com/ghacupha/fassets/commit/014375aea4cbfef) Edwin Njeru *2018-05-06 11:50:35*

**fixed some javadoc issues preventing maven goal package from completing successfully**


[106618a61934974](https://github.com/ghacupha/fassets/commit/106618a61934974) Edwin Njeru *2018-05-06 11:40:33*

**work in progress to replicate the YearMonthAttributeConverter 'month' related exception into a test**


[fd1c3f3cdae252b](https://github.com/ghacupha/fassets/commit/fd1c3f3cdae252b) Edwin Njeru *2018-05-04 16:51:25*

**Added the transaction interface, its implementation and basic tests for the same**


[9e4a36ec74dfa3a](https://github.com/ghacupha/fassets/commit/9e4a36ec74dfa3a) Edwin Njeru *2018-05-03 18:26:47*

**Added the transaction interface, its implementation and basic tests for the same**


[1d01a9c424610f3](https://github.com/ghacupha/fassets/commit/1d01a9c424610f3) Edwin Njeru *2018-05-03 18:16:04*

**Removed Account and Entry interfaces, for practical compatibility with JPA specification and created test for AccountService**


[22bb4639b6e569b](https://github.com/ghacupha/fassets/commit/22bb4639b6e569b) Edwin Njeru *2018-05-03 17:05:25*

**Removed Account and Entry interfaces, for practical compatibility with JPA specification, created test for Accounts, and implemented equals and hashcode in the Cash interface**


[fb6d85eabc2c07a](https://github.com/ghacupha/fassets/commit/fb6d85eabc2c07a) Edwin Njeru *2018-05-03 16:57:01*

**Removed Account and Entry interfaces, for practical compatibility with JPA specification, created test for Accounts, and implemented equals and hashcode in the Cash interface**


[776736cbba5ece9](https://github.com/ghacupha/fassets/commit/776736cbba5ece9) Edwin Njeru *2018-05-03 16:32:26*

**Tested consistency of the Account when adding entries**


[4ae5391d3341c69](https://github.com/ghacupha/fassets/commit/4ae5391d3341c69) Edwin Njeru *2018-05-03 16:06:26*

**Removed Account and Entry interfaces, for practical compatibility with JPA specification and created test for AccountService**


[3fc915ec7cba530](https://github.com/ghacupha/fassets/commit/3fc915ec7cba530) Edwin Njeru *2018-05-03 15:26:41*

**Created the persistentAccount tests**


[0850f85aa2e340d](https://github.com/ghacupha/fassets/commit/0850f85aa2e340d) Edwin Njeru *2018-05-03 13:17:12*

**replaced integers with timestamps for version locking in hibernate**


[1e697e4e7b293fb](https://github.com/ghacupha/fassets/commit/1e697e4e7b293fb) Edwin Njeru *2018-05-02 17:14:23*

**Created the persistentAccont and all related objects and their modifications, including making the ImmutableListCollector concurrent**


[e7d44767264c4ea](https://github.com/ghacupha/fassets/commit/e7d44767264c4ea) Edwin Njeru *2018-05-02 16:21:29*

**made the ImmutableListCollector concurrent**


[79cf37c5ad35614](https://github.com/ghacupha/fassets/commit/79cf37c5ad35614) Edwin Njeru *2018-05-02 15:21:58*

**Created the PersitentEntry class with relevant attributeConverters for the custom fields, their decorators and appropriate tests**


[ad3840c1d79cf7c](https://github.com/ghacupha/fassets/commit/ad3840c1d79cf7c) Edwin Njeru *2018-05-02 14:43:41*

**added base classes and interface required to start implementing persistent account and transaction patterns**


[2648f92694c52ac](https://github.com/ghacupha/fassets/commit/2648f92694c52ac) Edwin Njeru *2018-05-02 11:19:22*

**added base classes and interface required to start implementing persistent account and transaction patterns**


[06be8f5ca29d436](https://github.com/ghacupha/fassets/commit/06be8f5ca29d436) Edwin Njeru *2018-05-02 11:17:39*

**created package arrays for repositories, and for entities to reduce spring startup scanning activities**


[968d42b65aaacc3](https://github.com/ghacupha/fassets/commit/968d42b65aaacc3) Edwin Njeru *2018-05-02 10:43:04*

**Removed table unique contraints from the AccruedDepreciation and NetBookValue models to allow multi-annual monthly depreciations for the same fixed assets**


[dd48c65ed455beb](https://github.com/ghacupha/fassets/commit/dd48c65ed455beb) ghacupha *2018-04-29 08:59:49*

**Reduced logs for adding months to depreciationRelay from debug to trace level, and increased initial depreciation period to 5 years.TODO make the start date dynamic marking the date that the data is uploaded to the server**


[ef36b5dc246754e](https://github.com/ghacupha/fassets/commit/ef36b5dc246754e) ghacupha *2018-04-29 08:25:39*

**Reduced logs for adding months to depreciationRelay from debug to trace level, and increased initial depreciation period to 5 years.TODO make the start date dynamic marking the date that the data is uploaded to the server**


[ced7a24f4e0bb54](https://github.com/ghacupha/fassets/commit/ced7a24f4e0bb54) ghacupha *2018-04-29 08:21:53*

**Reduced logs for adding months to depreciationRelay from debug to trace level, and increased initial depreciation period to 5 years.TODO make the start date dynamic marking the date that the data is uploaded to the server**


[c43af91ea10b1c9](https://github.com/ghacupha/fassets/commit/c43af91ea10b1c9) ghacupha *2018-04-29 08:18:58*

**Fixed missing dataTables js script when running the path '/reports/depreciations/categories'**


[5a48ceb2714594b](https://github.com/ghacupha/fassets/commit/5a48ceb2714594b) ghacupha *2018-04-29 08:10:55*

**configured prevention of auto start for all batch jobs using batch.properties configuration file**


[23a82009c84ea7b](https://github.com/ghacupha/fassets/commit/23a82009c84ea7b) ghacupha *2018-04-29 07:57:48*

**changed order of uploaded files in the FileUpload test to allow test to pass in a linux based file system**


[2108b563caf48b9](https://github.com/ghacupha/fassets/commit/2108b563caf48b9) ghacupha *2018-04-29 07:28:32*

**configured the batch operations to be non restartable**


[e9c82fc4d49a08e](https://github.com/ghacupha/fassets/commit/e9c82fc4d49a08e) Edwin Njeru *2018-04-20 14:46:02*

**additional tests to the batch processes launch**


[acd56563ee5749a](https://github.com/ghacupha/fassets/commit/acd56563ee5749a) Edwin Njeru *2018-04-20 08:11:52*

**added base tests for importExcelJob and depreciationJob**


[a0d0cef01e59eb9](https://github.com/ghacupha/fassets/commit/a0d0cef01e59eb9) Edwin Njeru *2018-04-19 13:46:41*

**minor alignement to install coverage reporting**


[17535e83d2649d6](https://github.com/ghacupha/fassets/commit/17535e83d2649d6) Edwin Njeru *2018-04-19 13:13:22*

**Fixed most spotbugs and javadoc issues and error to stabilize the build**


[c226f4bd79e9ff8](https://github.com/ghacupha/fassets/commit/c226f4bd79e9ff8) Edwin Njeru *2018-04-19 12:10:45*

**Fixed all checkstyle issues**


[8a9c353ca35b706](https://github.com/ghacupha/fassets/commit/8a9c353ca35b706) Edwin Njeru *2018-04-19 08:04:19*

**migration to springboot 2.0.1.RELEASE**


[49bbf69e4ce7ce7](https://github.com/ghacupha/fassets/commit/49bbf69e4ce7ce7) Edwin Njeru *2018-04-16 16:11:01*

**fix on all checkstyle errors**


[c097b48efc8e095](https://github.com/ghacupha/fassets/commit/c097b48efc8e095) Edwin Njeru *2018-04-16 15:55:43*

**fix on all checkstyle errors**


[fac0a3379ea4564](https://github.com/ghacupha/fassets/commit/fac0a3379ea4564) Edwin Njeru *2018-04-16 10:19:24*

**amendment to build configuration to automate deployment**


[1be28452b2f3357](https://github.com/ghacupha/fassets/commit/1be28452b2f3357) ghacupha *2018-04-15 11:21:01*

**amendment to build configuration to automate deployment**


[b64a986b4e710c7](https://github.com/ghacupha/fassets/commit/b64a986b4e710c7) ghacupha *2018-04-15 10:56:15*

**amendment to build configuration to automate deployment**


[d2ad6f79e360803](https://github.com/ghacupha/fassets/commit/d2ad6f79e360803) ghacupha *2018-04-15 10:45:57*

**Create LICENSE**


[035d85518c96872](https://github.com/ghacupha/fassets/commit/035d85518c96872) Edwin Njeru *2018-04-15 10:05:08*

**Delete LICENSE**


[66871652c247aec](https://github.com/ghacupha/fassets/commit/66871652c247aec) Edwin Njeru *2018-04-15 09:48:00*

**amendment to build configuration to automate deployment**


[7014e332df11315](https://github.com/ghacupha/fassets/commit/7014e332df11315) ghacupha *2018-04-15 09:44:15*

**amendment to build configuration to automate deployment**


[c8b1d578958ef58](https://github.com/ghacupha/fassets/commit/c8b1d578958ef58) ghacupha *2018-04-15 09:42:55*

**Create LICENSE**


[0a43cee8e51720c](https://github.com/ghacupha/fassets/commit/0a43cee8e51720c) Edwin Njeru *2018-04-15 09:41:19*

**Delete LICENSE**


[452f8fa51196ec6](https://github.com/ghacupha/fassets/commit/452f8fa51196ec6) Edwin Njeru *2018-04-15 09:39:12*

**replaced fixedAsset with FixedAssetFormDto to handle data from the request,which is later converted into FixedAsset at persistence**


[76f9d8f2282f936](https://github.com/ghacupha/fassets/commit/76f9d8f2282f936) Edwin Njeru *2018-03-14 10:02:53*

**tried to add coveralls to project**


[3ccb35183fcc574](https://github.com/ghacupha/fassets/commit/3ccb35183fcc574) Edwin Njeru *2018-03-14 08:29:49*

**added minor config for pdf plugin**


[18965f6e2a6e91f](https://github.com/ghacupha/fassets/commit/18965f6e2a6e91f) Edwin Njeru *2018-03-14 07:37:32*

**Configuration of the pdf plugin to make the build more stable**


[f935fd145526c14](https://github.com/ghacupha/fassets/commit/f935fd145526c14) Edwin Njeru *2018-03-14 07:18:38*

**Additional automated tests configurations**


[1473de997c8dcc8](https://github.com/ghacupha/fassets/commit/1473de997c8dcc8) Edwin Njeru *2018-03-14 06:59:16*

**configured the application for code coverage**


[fc0195b0dc47557](https://github.com/ghacupha/fassets/commit/fc0195b0dc47557) Edwin Njeru *2018-03-14 06:40:38*

**FileUploadsTest completed**


[7cbb8941c8a4cfe](https://github.com/ghacupha/fassets/commit/7cbb8941c8a4cfe) Edwin Njeru *2018-03-14 06:00:31*

**To fix fileUploadTest assertion error**


[13955b77d5f3656](https://github.com/ghacupha/fassets/commit/13955b77d5f3656) Edwin Njeru *2018-03-13 18:22:27*

**FixedAssetsControllerTests completed**


[cbf4a3ace3e9315](https://github.com/ghacupha/fassets/commit/cbf4a3ace3e9315) Edwin Njeru *2018-03-13 17:30:34*

**Refactored wrong tests for monthlyCategoryBrief**


[f7985f9dd05b0e7](https://github.com/ghacupha/fassets/commit/f7985f9dd05b0e7) Edwin Njeru *2018-03-13 12:16:49*

**MonthlyDepreciationControllerTest amendment**


[39546d619cb4e92](https://github.com/ghacupha/fassets/commit/39546d619cb4e92) Edwin Njeru *2018-03-12 17:00:40*

**MonthlyDepreciationControllerTest in progress**


[a93783b7fbbc683](https://github.com/ghacupha/fassets/commit/a93783b7fbbc683) Edwin Njeru *2018-03-12 16:56:54*

**BriefController test completed**


[1dd1570f111aaa1](https://github.com/ghacupha/fassets/commit/1dd1570f111aaa1) Edwin Njeru *2018-03-12 16:22:24*

**AssetListingControllerTest completed**


[ba0b7ee1ea7d7f1](https://github.com/ghacupha/fassets/commit/ba0b7ee1ea7d7f1) Edwin Njeru *2018-03-12 11:12:48*

**TODO: Add DTOs for the Brief interface implementations**


[ae4a6f3f0bc6b11](https://github.com/ghacupha/fassets/commit/ae4a6f3f0bc6b11) ghacupha *2018-03-11 13:29:09*

**updated brief interface with monetary types. To create appropriate DTO for each response body**


[23caf1f96abf7f7](https://github.com/ghacupha/fassets/commit/23caf1f96abf7f7) ghacupha *2018-03-11 13:04:30*

**to finish updating the Brief interface to use Money and Monetary types together with all support converters needed**


[89cd537a83b8b90](https://github.com/ghacupha/fassets/commit/89cd537a83b8b90) ghacupha *2018-03-11 12:39:19*

**replaced Double types in monthly constructors with MonetaryValue types**


[15629f16edea356](https://github.com/ghacupha/fassets/commit/15629f16edea356) ghacupha *2018-03-11 12:01:19*

**replaced double with Money types for main application models**


[e26b819cf2c99ba](https://github.com/ghacupha/fassets/commit/e26b819cf2c99ba) ghacupha *2018-03-11 11:46:02*

**added cache configurations for caffeine**


[d414aee90be512b](https://github.com/ghacupha/fassets/commit/d414aee90be512b) Edwin Njeru *2018-03-08 13:25:41*

**added ITs for repositories, asynch queries and cached the service implementations**


[b437e0dbd8fc04a](https://github.com/ghacupha/fassets/commit/b437e0dbd8fc04a) Edwin Njeru *2018-03-08 09:47:00*

**removed wrong dependencies from the pom**


[fe5861b7cfff296](https://github.com/ghacupha/fassets/commit/fe5861b7cfff296) Edwin Njeru *2018-03-07 14:11:00*

**applied subscriber pattern to the FileSytemStorageService**


[5336ac971d5f80f](https://github.com/ghacupha/fassets/commit/5336ac971d5f80f) Edwin Njeru *2018-03-07 11:31:42*

**subscriber pattern complete with minor adjustments expected**


[48a97b6fc369044](https://github.com/ghacupha/fassets/commit/48a97b6fc369044) Edwin Njeru *2018-03-07 07:15:04*

**changed the queue method used from add to offer as per proper queue semantics**


[e11b49788961a9d](https://github.com/ghacupha/fassets/commit/e11b49788961a9d) Edwin Njeru *2018-03-06 16:22:18*

**implemented a generic observer pattern**


[1826f7fa23604fd](https://github.com/ghacupha/fassets/commit/1826f7fa23604fd) Edwin Njeru *2018-03-06 16:13:55*

**Update .travis.yml**


[328092647720b37](https://github.com/ghacupha/fassets/commit/328092647720b37) Edwin Njeru *2018-03-06 11:25:52*

**Update README.md**


[ef2530270aa5269](https://github.com/ghacupha/fassets/commit/ef2530270aa5269) Edwin Njeru *2018-03-06 11:23:55*

**Create .travis.yml**


[097806dbc94449f](https://github.com/ghacupha/fassets/commit/097806dbc94449f) Edwin Njeru *2018-03-06 11:22:38*

**creating brokerService in the accruedDepreciationTest**


[28e0dbec4b46c9c](https://github.com/ghacupha/fassets/commit/28e0dbec4b46c9c) Edwin Njeru *2018-03-06 11:12:35*

**adding integration tests to assist in migration to money representations in the repositories and depreciation algorithm**


[68d88aeb3bdc297](https://github.com/ghacupha/fassets/commit/68d88aeb3bdc297) ghacupha *2018-03-04 11:42:40*

**prepared the dependencies to implement money-types for server-side use**


[002309a0466e7c6](https://github.com/ghacupha/fassets/commit/002309a0466e7c6) ghacupha *2018-03-04 09:23:23*

**created and regularised ProcessingList as the main depreciation process data structure**


[db82f36b442a2bb](https://github.com/ghacupha/fassets/commit/db82f36b442a2bb) ghacupha *2018-03-04 08:48:44*

**fixed missing archive that prevented mvn packaging**


[c4780d5565780f0](https://github.com/ghacupha/fassets/commit/c4780d5565780f0) Edwin Njeru *2018-03-02 16:48:52*

**fixed the non-declining depreciation bug**


[c80094c81141b4a](https://github.com/ghacupha/fassets/commit/c80094c81141b4a) Edwin Njeru *2018-03-02 16:24:14*

**disabled dependency management which is interfering with spring-data-jpa versions**


[7da8d482dc11c34](https://github.com/ghacupha/fassets/commit/7da8d482dc11c34) Edwin Njeru *2018-03-02 10:27:52*

**update to version 2.0.0.RELEASE**


[8a4d730f0c61586](https://github.com/ghacupha/fassets/commit/8a4d730f0c61586) Edwin Njeru *2018-03-02 06:38:46*

**fixed some pom configuration errors**


[0b6a9d598122052](https://github.com/ghacupha/fassets/commit/0b6a9d598122052) Edwin Njeru *2018-02-28 16:46:04*

**removed depreciationListener from the filter implementation inorder to further simplify the model.ReducingBalance depreciation is however compromised**


[fa76299e1abafb2](https://github.com/ghacupha/fassets/commit/fa76299e1abafb2) Edwin Njeru *2018-02-28 15:01:41*

**removed the mediator, to make the program simpler**


[fc2c0223f9a0e35](https://github.com/ghacupha/fassets/commit/fc2c0223f9a0e35) Edwin Njeru *2018-02-28 08:48:35*

**created batch sub-folder in the depreciation package**


[903cee1ebd64228](https://github.com/ghacupha/fassets/commit/903cee1ebd64228) Edwin Njeru *2018-02-28 06:43:55*

**removed the accrued depreciation batch**


[8da594f9ad698a4](https://github.com/ghacupha/fassets/commit/8da594f9ad698a4) Edwin Njeru *2018-02-22 16:21:05*

**removed the queue and related files to reduce program complexity**


[59a28170f3311ce](https://github.com/ghacupha/fassets/commit/59a28170f3311ce) Edwin Njeru *2018-02-22 16:09:20*

**Provider pattern implemented using the UpdateProvider to sort NPE in depreciation update**


[6bf3c84d80425ff](https://github.com/ghacupha/fassets/commit/6bf3c84d80425ff) Edwin Njeru *2018-02-21 17:15:24*

**NullPointerExceptions in the mediator system**


[db4a51d0469a7f3](https://github.com/ghacupha/fassets/commit/db4a51d0469a7f3) Edwin Njeru *2018-02-20 16:20:47*

**introduced queueing model for accruedDepreciation. Can't transmit list items to the batch reader**


[85135c549e49492](https://github.com/ghacupha/fassets/commit/85135c549e49492) Edwin Njeru *2018-02-20 15:15:51*

**implemented monthlyCategoryDepreciation component**


[8aee366387ae5a5](https://github.com/ghacupha/fassets/commit/8aee366387ae5a5) Edwin Njeru *2018-02-19 15:02:48*

**implemented callBack for depreciation from the agent<Depreciation>**


[e2319f4dd57a20e](https://github.com/ghacupha/fassets/commit/e2319f4dd57a20e) Edwin Njeru *2018-02-19 10:44:41*

**implemented depreciationAgentsHandler in place of filter pattern**


[f25479a521e4343](https://github.com/ghacupha/fassets/commit/f25479a521e4343) ghacupha *2018-02-18 14:15:31*

**trying to lay filters for depreciation execution before, inProcess and afterProcess**


[777dd7d7e14287d](https://github.com/ghacupha/fassets/commit/777dd7d7e14287d) ghacupha *2018-02-18 13:35:43*

**Implmented the depreciationRelay in the postContruct sequence**


[64283b9410e8dc9](https://github.com/ghacupha/fassets/commit/64283b9410e8dc9) ghacupha *2018-02-18 11:52:18*

**implemented stateless accruedDepreciationAgent algorithm**


[5613e214bd15397](https://github.com/ghacupha/fassets/commit/5613e214bd15397) ghacupha *2018-02-18 09:12:45*

**Agent interface to be extended by filter interfaces**


[04c1395a3adf621](https://github.com/ghacupha/fassets/commit/04c1395a3adf621) ghacupha *2018-02-18 06:21:32*

**to include observer between the depreciationAgent and the depreciationExecutorImpl**


[7cba576ce75fb87](https://github.com/ghacupha/fassets/commit/7cba576ce75fb87) Edwin Njeru *2018-02-16 15:01:00*

**bumped version to 0.0.1**


[8baf0beb181fef6](https://github.com/ghacupha/fassets/commit/8baf0beb181fef6) Edwin Njeru *2018-02-14 09:41:28*

**implemented the DepreciationUpdateDispatcher interface**


[5f03cfd67d97665](https://github.com/ghacupha/fassets/commit/5f03cfd67d97665) Edwin Njeru *2018-02-14 09:20:06*

**added mediator to replace jms in the DepreciationUpdateDispatcher**


[ca99bf04604263b](https://github.com/ghacupha/fassets/commit/ca99bf04604263b) Edwin Njeru *2018-02-13 16:26:25*

**created DepreciationJMSFactory as a fix for null pointers introduced in the listener**


[03bceb3466f2002](https://github.com/ghacupha/fassets/commit/03bceb3466f2002) Edwin Njeru *2018-02-12 15:40:47*

**jms listeners failing to download payloads. To integrate kafka**


[bcdbd9b6a3338d7](https://github.com/ghacupha/fassets/commit/bcdbd9b6a3338d7) Edwin Njeru *2018-02-08 12:54:02*

**added DepreciationExecutor interface**


[6d92203e634c313](https://github.com/ghacupha/fassets/commit/6d92203e634c313) Edwin Njeru *2018-02-07 13:39:12*

**removed unnecessary IT applicationTest**


[d4c8e46350c8ed8](https://github.com/ghacupha/fassets/commit/d4c8e46350c8ed8) Edwin Njeru *2018-02-06 15:55:35*

**refactored depreciation to skip non depreciation items**


[b76971f50efcfde](https://github.com/ghacupha/fassets/commit/b76971f50efcfde) Edwin Njeru *2018-02-06 15:48:44*

**added monthlySolDepreciation**


[1173a05cf81680b](https://github.com/ghacupha/fassets/commit/1173a05cf81680b) Edwin Njeru *2018-02-06 05:27:23*

**added monthlySolDepreciation model and workflows**


[ef7e17dcdb5238a](https://github.com/ghacupha/fassets/commit/ef7e17dcdb5238a) Edwin Njeru *2018-02-05 19:25:13*

**fine-tuning for long range depreciation job**


[751c3d64c2fca1d](https://github.com/ghacupha/fassets/commit/751c3d64c2fca1d) Edwin Njeru *2018-02-02 15:12:30*

**created created for MonthlyAssetDepreciation**


[0f9bb161deba562](https://github.com/ghacupha/fassets/commit/0f9bb161deba562) Edwin Njeru *2018-02-02 09:48:56*

**introduced DTO for monthlyAssetDepreciation**


[6563c620afb2359](https://github.com/ghacupha/fassets/commit/6563c620afb2359) Edwin Njeru *2018-02-01 15:59:39*

**made dynamic by-annual job launcher for the MonthlyAssetDepreciation**


[3f1d86bff663a7d](https://github.com/ghacupha/fassets/commit/3f1d86bff663a7d) Edwin Njeru *2018-02-01 13:42:22*

**using 5 year depreciation period**


[9a5cfc6f5325604](https://github.com/ghacupha/fassets/commit/9a5cfc6f5325604) Edwin Njeru *2018-02-01 05:19:28*

**TODO: fix depreciationSeq relay duplication**


[a5341c17f45e140](https://github.com/ghacupha/fassets/commit/a5341c17f45e140) Edwin Njeru *2018-01-31 15:46:19*

**TODO: fix duplication in monthlyAssetDepreciation model**


[b872d32c391ddaa](https://github.com/ghacupha/fassets/commit/b872d32c391ddaa) Edwin Njeru *2018-01-31 11:50:11*

**DepreciationPreprocessor**


[770daf8daee37d5](https://github.com/ghacupha/fassets/commit/770daf8daee37d5) Edwin Njeru *2018-01-30 13:46:13*

**to fix the no_of_months algorithmn**


[2914d24a434f123](https://github.com/ghacupha/fassets/commit/2914d24a434f123) Edwin Njeru *2018-01-29 18:01:43*

**installed stateful checks to file uploads and uniqueContraints in FixedAsset model**


[8062cc8b5756531](https://github.com/ghacupha/fassets/commit/8062cc8b5756531) Edwin Njeru *2018-01-29 10:29:19*

**removed cyclic dependency problem, to fix jackson localDateTime deserializer**


[00de56e58c27e2e](https://github.com/ghacupha/fassets/commit/00de56e58c27e2e) Edwin Njeru *2018-01-26 15:37:50*

**removing unnecessary js files**


[bfa202f2506a8de](https://github.com/ghacupha/fassets/commit/bfa202f2506a8de) Edwin Njeru *2018-01-26 13:55:41*

**to resolve circular dependency by noficationService and fileUploadService**


[d28e6fedf328ebb](https://github.com/ghacupha/fassets/commit/d28e6fedf328ebb) Edwin Njeru *2018-01-26 07:32:03*

**logging up depreciation workflows**


[f6abe1c0c881f72](https://github.com/ghacupha/fassets/commit/f6abe1c0c881f72) Edwin Njeru *2018-01-25 17:17:50*

**depreciation processor not working**


[c1a3d95b66acf01](https://github.com/ghacupha/fassets/commit/c1a3d95b66acf01) Edwin Njeru *2018-01-25 14:10:44*

**to finish working on monthly incrementer**


[f62cf9cc73fd291](https://github.com/ghacupha/fassets/commit/f62cf9cc73fd291) Edwin Njeru *2018-01-24 13:03:03*

**working on categoryConfiguration template**


[5ec91c9d361097c](https://github.com/ghacupha/fassets/commit/5ec91c9d361097c) Edwin Njeru *2018-01-23 15:07:10*

**working on category onfiguration template**


[6cb649675e539b5](https://github.com/ghacupha/fassets/commit/6cb649675e539b5) Edwin Njeru *2018-01-22 15:57:35*

**completed debugging the brief reports**


[8f0c3f057fcc817](https://github.com/ghacupha/fassets/commit/8f0c3f057fcc817) Edwin Njeru *2018-01-22 11:27:44*

**debugging flows**


[74a1915c0edd8fd](https://github.com/ghacupha/fassets/commit/74a1915c0edd8fd) ghacupha *2018-01-21 18:47:19*

**implemented briefUpdate job**


[b91d8b0bf693731](https://github.com/ghacupha/fassets/commit/b91d8b0bf693731) ghacupha *2018-01-21 16:20:22*

**making brief queries**


[07a645f35c8406f](https://github.com/ghacupha/fassets/commit/07a645f35c8406f) Edwin Njeru *2018-01-19 15:25:03*

**working on briefing services**


[92f23871a77a671](https://github.com/ghacupha/fassets/commit/92f23871a77a671) Edwin Njeru *2018-01-19 10:12:13*

**included briefs model, repositories,services and controllers**


[13c856834b1fefe](https://github.com/ghacupha/fassets/commit/13c856834b1fefe) Edwin Njeru *2018-01-18 15:36:46*

**added column-visibility feature to asset-list**


[126c9670517756a](https://github.com/ghacupha/fassets/commit/126c9670517756a) Edwin Njeru *2018-01-18 13:28:01*

**implemented asset listing and it's report workflows**


[d43d5d44b72f8e4](https://github.com/ghacupha/fassets/commit/d43d5d44b72f8e4) Edwin Njeru *2018-01-18 11:54:51*

**created add asset and category templates**


[55e03c43d2d3962](https://github.com/ghacupha/fassets/commit/55e03c43d2d3962) Edwin Njeru *2018-01-17 15:47:59*

**Update footer.html**


[b27e9617c4f1dfd](https://github.com/ghacupha/fassets/commit/b27e9617c4f1dfd) Edwin Njeru *2018-01-17 09:40:24*

**Create LICENSE**


[b46bd9fb57292ee](https://github.com/ghacupha/fassets/commit/b46bd9fb57292ee) Edwin Njeru *2018-01-17 09:36:36*

**added template for categories configuration**


[124491e06e9ad20](https://github.com/ghacupha/fassets/commit/124491e06e9ad20) Edwin Njeru *2018-01-17 09:34:26*

**fixed the zeroDoubleInteger bug**


[27aa6264bef5d30](https://github.com/ghacupha/fassets/commit/27aa6264bef5d30) Edwin Njeru *2018-01-16 15:52:40*

**To fix doubleInteger bug**


[556ef3ca7ca7022](https://github.com/ghacupha/fassets/commit/556ef3ca7ca7022) Edwin Njeru *2018-01-16 13:59:47*

**added the YearMonthAttributeConverter**


[0fbbabc9f607feb](https://github.com/ghacupha/fassets/commit/0fbbabc9f607feb) Edwin Njeru *2018-01-16 13:18:59*

**added NBV batch upload update in step3**


[3079ec60eaa628d](https://github.com/ghacupha/fassets/commit/3079ec60eaa628d) Edwin Njeru *2018-01-16 13:06:19*

**Create README.md**


[6ca8f71172d4376](https://github.com/ghacupha/fassets/commit/6ca8f71172d4376) Edwin Njeru *2018-01-15 17:01:01*

**created uploads module**


[2bbcac7aa038300](https://github.com/ghacupha/fassets/commit/2bbcac7aa038300) Edwin Njeru *2018-01-15 16:49:13*

**working on trigger mechanisms for excel upload**


[fec011a14a9ba24](https://github.com/ghacupha/fassets/commit/fec011a14a9ba24) Edwin Njeru *2018-01-15 10:58:57*

**completed the depreciation computation, to create controller and batch services**


[0de8d825da6af73](https://github.com/ghacupha/fassets/commit/0de8d825da6af73) ghacupha *2018-01-14 11:24:38*

**added main models and their repositories**


[d576a2d21750865](https://github.com/ghacupha/fassets/commit/d576a2d21750865) ghacupha *2018-01-14 09:53:37*



