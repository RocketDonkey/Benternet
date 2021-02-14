/**
 * Benternet - Outlet Server.
 *
 * A (very) simple server to handle requests from a Raspberry Pi to switch
 * 433MHz Etekcity remote sockets on/off.
 */

// Node imports.
var bodyParser = require('body-parser');
var express = require('express');
var path = require('path');
var requests = require('request');

var api = require('./routes/api');

var app = module.exports = express();

// The port on which the server will be running.
const PORT=8081; 

// Allow parsing POST requests.
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({'extended': true}));

// Configure routes.
app.post('/api/outlets', api.outlets);

// Static routes.
app.use(express.static('app'));
app.use('/outlet', express.static(path.join(__dirname, '/outlet')));
app.use(
    '/bower_components',
    express.static(path.join(__dirname, '/bower_components')));


// All GET requests are handled by Angular.
app.get('*', function(req, res, next) {
  res.sendFile(__dirname + '/app/index.html');
});


// Initialize the server.
app.listen(PORT, function() {
  console.log('Starting server on port ' + PORT);
});
