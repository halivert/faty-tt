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
            data: { pageTitle: 'Inicio sesion' }
        })
        .state('index', {
            abstract: true,
            url: "/index",
            templateUrl: "views/common/content.html",
        })
        .state('index.main', {
            url: "/main",
            templateUrl: "views/main.html",
            data: { pageTitle: 'Inicial' }
        })
        .state('index.imc', {
            url: "/imc",
            templateUrl: "views/imc/imc.html",
            data: { pageTitle: 'Calcular IMC' }
        })
}
angular
    .module('trabajoTerminal')
    .config(config)
    .run(function($rootScope, $state) {
        $rootScope.$state = $state;
    });
