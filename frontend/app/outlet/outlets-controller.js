/**
 * Outlet controller configuration.
 */
var rpioutlets = {};

rpioutlets.OutletsController = function($http) {
  var self = this;

  self.http_ = $http;

  // Outlets.
  self.diningRoom1 = false;
  self.livingRoom1 = false;

  // TODO: Pull these from the server.
  // outletService
  //     .loadOutletStats()
  //     .then(function(outlets) {
  //         self.diningRoom1 = outlets.diningRoom1;
  //         self.livingRoom1 = outlets.livingRoom1;
  //     });
}


/**
 * Fire a request to the server when a switch is toggled.
 *
 * @param{string} outlet The outlet to toggle.
 */
rpioutlets.OutletsController.prototype.toggleOutlet = function(outlet, state) {
  var params = {
    'outlet': outlet,
    'state': state
  };

  var self = this;
  self.http_.post('/api/outlets', params)
    .success(function(data, status) {
      console.log(data);
    });
};


// Configure the controller.
angular
    .module('outlets')
    .controller('OutletsController', [
        '$http', rpioutlets.OutletsController
    ]);
