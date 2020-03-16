const jwt = require('jsonwebtoken');
const config = require('../config/config');
const Joi = require('joi');
const apiCtrl = require('./api.controller.js');
const bcrypt = require('bcrypt');
const sql = require('../config/mysql');
const dateFormat = require('dateformat');

module.exports = {
    generateToken,
}

function generateToken(user) {
    const payload = JSON.stringify(user);
    return jwt.sign(payload, config.jwtSecret);
}