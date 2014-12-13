window.helpme.gps = function(callback) {
    cordova.exec(callback, function(err) {
        callback('Nothing to echo.');
    }, "GPS", "GPS", []);
};

window.helpme.token = function(token, callback) {
    cordova.exec(callback, function(err) {
        callback('Nothing to echo.');
    }, "Token", "Token", [token]);
};