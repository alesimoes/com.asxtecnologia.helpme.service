window.helpme.gps = function(callback) {
    cordova.exec(callback, function(err) {
        callback('Nothing to echo.');
    }, "CordovaInterface", "GPS", []);
};

window.helpme.token = function(token, callback) {
    cordova.exec(callback, function(err) {
        callback('Nothing to echo.');
    }, "CordovaInterface", "Token", [token]);
};