angular.module('nmtApp.controllers').
controller('WorkoutsController', ['$scope', '$filter', '$state', '$stateParams', '$rootScope', function($scope, $filter, $state, $stateParams, $rootScope){ 
	
	console.log('username',$rootScope.username);

	$scope.test = function(){
		console.log('hit WorkoutsController');

	};


	$scope.test();

}]);