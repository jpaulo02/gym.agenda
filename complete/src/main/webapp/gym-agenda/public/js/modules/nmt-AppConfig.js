angular.module('nmtApp')
.constant('nmtAppConfig',{
	environment: {
		dev: {
			host: 'http://localhost:8080/gym.agenda'
		},
		prod: {
			host: 'https://nmtapp.herokuapp.com'
		}
	}
})

.factory('SpotifyService',['Restangular', 'nmtAppConfig', function(Restangular, nmtAppConfig){
	return Restangular.withConfig(function(RestangularConfigurer){
		var baseUrl = nmtAppConfig.environment.dev.host;
		RestangularConfigurer.setBaseUrl(baseUrl);
	});
}])
.factory('GymService',['Restangular', 'nmtAppConfig', function(Restangular, nmtAppConfig){
	return Restangular.withConfig(function(RestangularConfigurer){
		var baseUrl = nmtAppConfig.environment.dev.host;
		RestangularConfigurer.setBaseUrl(baseUrl);
	});
}]);