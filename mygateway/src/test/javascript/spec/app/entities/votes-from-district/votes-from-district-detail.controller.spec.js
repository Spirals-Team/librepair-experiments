'use strict';

describe('Controller Tests', function() {

    describe('VotesFromDistrict Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockVotesFromDistrict, MockElectoralDistrict, MockCandidate, MockMyUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockVotesFromDistrict = jasmine.createSpy('MockVotesFromDistrict');
            MockElectoralDistrict = jasmine.createSpy('MockElectoralDistrict');
            MockCandidate = jasmine.createSpy('MockCandidate');
            MockMyUser = jasmine.createSpy('MockMyUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'VotesFromDistrict': MockVotesFromDistrict,
                'ElectoralDistrict': MockElectoralDistrict,
                'Candidate': MockCandidate,
                'MyUser': MockMyUser
            };
            createController = function() {
                $injector.get('$controller')("VotesFromDistrictDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mygatewayApp:votesFromDistrictUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
