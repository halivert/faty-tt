/**
 * INSPINIA - Responsive Admin Theme
 *
 * Inspinia theme use AngularUI Router to manage routing and views
 * Each view are defined as state.
 * Initial there are written stat for all view in theme.
 *
 */
function config($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise("/login");

    $stateProvider

        .state('login', {
            url: "/login",
            templateUrl: "views/login/login.html",
            authenticate: false,
            data: { pageTitle: 'Inicio sesion' }
        })
        .state('index', {
            abstract: true,
            url: "/index",
            templateUrl: "views/common/content.html",
            authenticate: true
        })
        .state('index.main', {
            url: "/main",
            templateUrl: "views/main.html",
            authenticate: true,
            data: { pageTitle: 'Inicial' }
        })
        .state('index.imc', {
            url: "/imc",
            templateUrl: "views/imc/imc.html",
            authenticate: true,
            data: { pageTitle: 'Calcular IMC' }
        })
        
}
angular
    .module('trabajoTerminal')
    .config(config)
    .run(function($rootScope, $state,$log,$cookies) {
        $rootScope.$state = $state;

        $rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState, fromParams){
            $log.debug("toState.authenticate : " + toState.authenticate);
            $log.debug("$cookies.get(auth) : " + $cookies.get("auth"));
        if (toState.authenticate && $cookies.get("auth")!="true"){
            // User isn’t authenticated
            $state.transitionTo("login");
            event.preventDefault(); 
        }
    });
    });
