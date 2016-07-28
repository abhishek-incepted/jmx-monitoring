var monitoringApp = angular.module('MonitoringApp', [
                                     'ui.router',
                                     'ui.bootstrap',
                                     'ngResource',
                                     'highcharts-ng',
                                     'MainController'
                                     ]);

monitoringApp.config(['$stateProvider', '$urlRouterProvider', function($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/');

	var charts = {
			name: 'jmx',
			url:'/',
			templateUrl: 'partials/jmx.html',
			controller: 'JmxController'/*,
			data: {
		        requireLogin: false
		      }*/
	};

	$stateProvider.state(charts);
	
//	$stateProvider.state('charts', {
//					views: {
//						'charts': {
//							
//						}
//					}
//				}).state('thread',{
//					
//				});
	
	
}]);
