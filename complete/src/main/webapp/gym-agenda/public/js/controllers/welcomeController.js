angular.module('nmtApp.controllers').
controller('WelcomeController', ['$scope', '$filter', '$state', '$stateParams', '$rootScope', function($scope, $filter, $state, $stateParams, $rootScope){ 
	
	$scope.username = $stateParams.username;
	console.log('username',$scope.username);

	$scope.test = function(){
		console.log('hit WelcomeController');

	};

	$scope.test();

}]);