/**
 * Bowland Palace - Outlets app.
 *
 * Very simple app for controlling 433MHz remote controlled outlets from a web
 * interface.
 */

angular
    .module('outletsApp', ['ngMaterial', 'outlets'])
    .config(function($mdThemingProvider) {
      $mdThemingProvider.theme('default')
        .primaryPalette('blue')
        .accentPalette('red');
    });
