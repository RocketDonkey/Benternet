/**
 * API endpoint that is consumed by the Angular client. The underlying
 * Raspberry Pi setup uses RCSwitch (https://github.com/sui77/rc-switch) and
 * codesend (https://github.com/ninjablocks/433Utils/tree/master/RPi_utils).
 */

var childProcess = require('child_process');

// Map of outlets to the corresponding on/off codes.
var OUTLET_MAP = {
  // Button 1 - Dining room.
  'diningroom1': {'on': 70963, 'off': 70972},
  // Button 2 - Living room.
  'livingroom1': {'on': 71107, 'off': 71116},
  // Button 3 - unused.
  'button3': {'on': 71427, 'off': 71436},
  // Button 4 - unused.
  'button4': {'on': 72963, 'off': 72972},
  // Button 5 - unused.
  'button5': {'on': 79107, 'off': 79116},
};


// Handle incoming API request and toggle the appropriate outlet.
exports.outlets = function(request, response) {
  var state = request.body.state === true ? 'on' : 'off';
  var code = OUTLET_MAP[request.body.outlet][state];
  var command = 'codesend ' + code;
  childProcess.exec(command, function(error, stdout, stderr) {
    response.jsonp(stdout);
    if (error !== null) {
      console.log('Error encountered: ' + error);
    }
  });
};
