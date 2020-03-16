var mysql = require("mysql");
const config = require('./config');

// config for your database
var MYSQL = {
    host: config.mysql,
    user: 'root',
    password: '',
    database: 'shareNcare'
};

// connect to your database
var sql = mysql.createConnection(MYSQL, function (err) {
    if (err) console.log(err);
});

sql.connect();

module.exports = sql;

